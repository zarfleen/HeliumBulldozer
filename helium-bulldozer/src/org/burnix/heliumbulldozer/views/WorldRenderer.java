package org.burnix.heliumbulldozer.views;

import org.burnix.heliumbulldozer.models.Actor;
import org.burnix.heliumbulldozer.models.Block;
import org.burnix.heliumbulldozer.models.Player;
import org.burnix.heliumbulldozer.models.World;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	public static final float CAMERA_WIDTH = 20f;
	public static final float CAMERA_HEIGHT = 14f;
	public static final float MOVEMENT_FRAME_DURATION = 0.06f;
	
	private World world;
	private OrthographicCamera camera;
	
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	private boolean debug = false;
	
	private int width;
	private int height;
	private float ppuX;
	private float ppuY;
	
	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.camera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.camera.update();
		this.debug = debug;
		
		loadTextures();
	}
	
	private void loadTextures() {
		
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.ppuX = (float)this.width / CAMERA_WIDTH;
		this.ppuY = (float)this.height / CAMERA_HEIGHT;
	}

	public void render() {
		float x1 = Math.max(this.world.getPlayer().getPosition().x, CAMERA_WIDTH / 2f);
		x1 = Math.min(x1, this.world.getLevel().getWidth() - (CAMERA_WIDTH / 2f));
		this.camera.position.set(x1, CAMERA_HEIGHT / 2f, 0);
		this.camera.update();

		if(debug)
		{
			drawCollisionBlocks();
			drawDebug();
		}
	}
	
	private void drawDebug() {
		this.debugRenderer.setProjectionMatrix(this.camera.combined);
		this.debugRenderer.begin(ShapeType.Line);
		
		for(Block block : this.world.getDrawableBlocks((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
			Rectangle rect = block.bounds();
			this.debugRenderer.setColor(new Color(1, 0, 0, 1));
			this.debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
		Player player = world.getPlayer();
		Rectangle rect = player.bounds();
		this.debugRenderer.setColor(new Color(0, 1, 0, 1));
		this.debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);

		for(Actor actor : world.getActors()) {
			rect = actor.bounds();
			this.debugRenderer.setColor(new Color(0, 0, 1, 1));
			this.debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
		this.debugRenderer.end();
	}
	
	private void drawCollisionBlocks() {
		this.debugRenderer.setProjectionMatrix(this.camera.combined);
		this.debugRenderer.begin(ShapeType.Filled);
		this.debugRenderer.setColor(new Color(1, 1, 1, 1));

		for(Rectangle rect : this.world.getCollisionRects()) {
			this.debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
		this.debugRenderer.end();
	}
}
