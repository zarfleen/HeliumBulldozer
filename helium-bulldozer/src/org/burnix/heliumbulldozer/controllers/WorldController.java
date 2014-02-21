package org.burnix.heliumbulldozer.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.burnix.heliumbulldozer.models.Actor;
import org.burnix.heliumbulldozer.models.Block;
import org.burnix.heliumbulldozer.models.Player;
import org.burnix.heliumbulldozer.models.World;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public class WorldController {

	enum GameKeys {
		LEFT, 
		RIGHT, 
		JUMP, 
		DUCK, 
		ATTACK, 
		ALT_ATTACK, 
		UNKNOWN
	}
	
	private static final int LONG_JUMP_PRESS = 150;
	
	private static final float ACCELERATION = 250f;
	private static final float GRAVITY = -20f;
	private static final float DAMPEN = 0.5f;
	private static final float MAX_JUMP_VELOCITY = 7f;
	private static final float MAX_VELOCITY = 4f;
	
	private World world;
	private Player player;
	
	private long jumpPressedTime;
	private boolean jumpPressed = false;
	private boolean grounded = false;
	
	private Map<GameKeys, Boolean> keys = new HashMap<GameKeys, Boolean>();
	
	private List<Block> collidable = new ArrayList<Block>();
	
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	
	public WorldController(World world) {
		this.world = world;
		this.player = this.world.getPlayer();
		
		init();
	}
	
	private void init() {
		for(GameKeys k : GameKeys.values()) {
			this.keys.put(k, false);
		}
	}
	
	public void update(float delta) {
		processInput();
		
		if(this.grounded && this.player.getState() == Actor.STATE_JUMPING) {
			this.player.setState(Actor.STATE_IDLE);
		}
		
		world.getCollisionRects().clear();
		
		updateActor(this.player, delta);
		
		for(Actor actor : this.world.getActors()) {
			updateActor(actor, delta);
		}
	}
	
	protected void updateActor(Actor actor, float delta) {
		actor.getAcceleration().y = GRAVITY;
		actor.getAcceleration().scl(delta);
		actor.getVelocity().add(actor.getAcceleration().x, actor.getAcceleration().y);
		
		checkCollisionWithBlocks(actor, delta);
		
		if(actor == this.player)
			actor.getVelocity().x *= DAMPEN;

		if(actor.getVelocity().x > MAX_VELOCITY) {
			actor.getVelocity().x = MAX_VELOCITY;
		}
		else if(actor.getVelocity().x < -MAX_VELOCITY) {
			actor.getVelocity().x = -MAX_VELOCITY;
		}
		
		actor.update(delta);
	}
	
	public void keyPressed(int keycode) {
		GameKeys key = translateKey(keycode);
		this.keys.put(key, true);
	}
	
	public void keyReleased(int keycode) {
		GameKeys key = translateKey(keycode);
		this.keys.put(key, false);
		
		if(key == GameKeys.JUMP)
		{
			this.jumpPressed = false;
		}
	}
	
	private GameKeys translateKey(int keycode) {
		GameKeys translatedKey = GameKeys.UNKNOWN;
		
		switch(keycode)
		{
		case Keys.LEFT:
			translatedKey = GameKeys.LEFT;
			break;
		case Keys.RIGHT:
			translatedKey = GameKeys.RIGHT;
			break;
		case Keys.SPACE:
			translatedKey = GameKeys.JUMP;
			break;
		case Keys.DOWN:
			translatedKey = GameKeys.DUCK;
			break;
		case Keys.Z:
			translatedKey = GameKeys.ATTACK;
			break;
		case Keys.X:
			translatedKey = GameKeys.ALT_ATTACK;
			break;
		}
		
		return translatedKey;
	}
	
	private void processInput() {
		if(this.keys.get(GameKeys.JUMP)) {
			if(this.player.getState() != Actor.STATE_JUMPING) {
				this.jumpPressed = true;
				this.jumpPressedTime = System.currentTimeMillis();
				this.player.setState(Actor.STATE_JUMPING);
				this.player.getVelocity().y = MAX_JUMP_VELOCITY;
				this.grounded = false;
			}
			else {
				if(this.jumpPressed && ((System.currentTimeMillis() - this.jumpPressedTime) >= LONG_JUMP_PRESS)) {
					this.jumpPressed = false;
				}
				else if (this.jumpPressed) {
					this.player.getVelocity().y = MAX_JUMP_VELOCITY;
				}
			}
		}
		
		if(this.keys.get(GameKeys.LEFT)) {
			this.player.setFacingLeft(true);
			if(this.player.getState() != Actor.STATE_JUMPING) {
				this.player.setState(Actor.STATE_WALKING);
			}
			this.player.getAcceleration().x = -ACCELERATION;
		}
		else if(this.keys.get(GameKeys.RIGHT)) {
			this.player.setFacingLeft(false);
			if(this.player.getState() != Actor.STATE_JUMPING) {
				this.player.setState(Actor.STATE_WALKING);
			}
			this.player.getAcceleration().x = ACCELERATION;
		}
		else {
			if(this.player.getState() != Actor.STATE_JUMPING) {
				this.player.setState(Actor.STATE_IDLE);
			}
			this.player.getAcceleration().x = 0;
		}
	}
	
	private void checkCollisionWithBlocks(Actor actor, float delta) {
		actor.getVelocity().scl(delta);
			
		Rectangle actorRect = this.rectPool.obtain();
		
		///
		
		actorRect.set(actor.bounds());
		
		int startY, endY;
		int startX = (int)actorRect.x;
		int endX = (int)(actorRect.x + actorRect.width);
		if(actor.getVelocity().y < 0)
			startY = endY = (int)Math.floor(actor.bounds().y + actor.getVelocity().y);
		else
			startY = endY = (int)Math.floor(actor.bounds().y + actor.bounds().height + actor.getVelocity().y);
		
		populateCollidableBlocks(startX, startY, endX, endY);
		
		actorRect.y += actor.getVelocity().y;
		
		for(Block block : this.collidable) {
			if(block == null)
				continue;
			
			if(actorRect.overlaps(block.bounds())) {
				if(actor.getVelocity().y < 0 && actor == this.player) {
					this.grounded = true;
				}
				actor.getVelocity().y = 0;
				actor.getAcceleration().y = 0;
				this.world.getCollisionRects().add(block.bounds());
				break;
			}
		}
		
		actorRect.set(actor.bounds());
		
		startY = (int)actorRect.y;
		endY = (int)actorRect.y + (int)actor.bounds().height;
		if(actor.getVelocity().x < 0)
			startX = endX = (int)Math.floor(actorRect.x + actor.getVelocity().x);
		else
			startX = endX = (int)Math.floor(actorRect.x + actorRect.width + actor.getVelocity().x);
		
		populateCollidableBlocks(startX, startY, endX, endY);
		
		actorRect.x += actor.getVelocity().x;
				
		for(Block block : this.collidable) {
			if(block == null)
				continue;
			
			if(actorRect.overlaps(block.bounds())) {
				actor.getVelocity().x = 0;
				this.world.getCollisionRects().add(block.bounds());
				break;
			}
		}
		
		actor.getPosition().add(actor.getVelocity());
		actor.getVelocity().scl(1 / delta);
	}
	
	private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
		this.collidable.clear();
		for(int x = startX;x <= endX;x++) {
			for(int y = startY;y <= endY;y++) {
				if(x >= 0 && x < this.world.getLevel().getWidth() && y >= 0 && y < this.world.getLevel().getHeight()) {
					this.collidable.add(this.world.getLevel().get(x,y));
				}
			}
		}
	}
}
