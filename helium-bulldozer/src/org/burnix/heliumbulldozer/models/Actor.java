package org.burnix.heliumbulldozer.models;

import com.badlogic.gdx.math.Vector2;

public class Actor extends GameObject {
	
	public static final int STATE_IDLE = 0;
	public static final int STATE_WALKING = 1;
	public static final int STATE_JUMPING = 2;
	public static final int STATE_DYING = 3;
	public static final int STATE_ATTACKING = 4;
	
	protected boolean facingLeft = true;
	
	public Actor() {
		super();
		initActor();
	}
	
	public Actor(Vector2 position) {
		super(position);
		initActor();
	}
	
	protected void initActor() {
		setState(Actor.STATE_IDLE);
		
		this.width = 0.333f;
		this.height = 0.6777f;		
	}
	
	public void update(float delta) {
		super.update(delta);
	}
	
	public boolean isFacingLeft() {
		return this.facingLeft;
	}
	
	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}
}
