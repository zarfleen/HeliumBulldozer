package org.burnix.heliumbulldozer.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	
	public static final int INACTIVE = -1;
	
	protected Vector2 position;
	protected Vector2 acceleration = new Vector2();
	protected Vector2 velocity = new Vector2();
	
	protected final Rectangle bounds = new Rectangle();
		
	protected int state;
	protected float stateTime;
	
	protected float width;
	protected float height;
	
	public GameObject() {
		init(new Vector2());
	}
	
	public GameObject(Vector2 position) {
		init(position);
	}
	
	protected void init(Vector2 position) {
		this.position = position;
		setState(GameObject.INACTIVE);
	}
	
	public Rectangle bounds() {
		this.bounds.x = this.position.x;
		this.bounds.y = this.position.y;
		this.bounds.width = this.width;
		this.bounds.height = this.height;
		return this.bounds;
	}
	
	public void setState(int state) {
		this.state = state;
		this.stateTime = 0.0f;
	}
	
	public int getState() {
		return this.state;
	}
	
	public void update(float delta) {
		
	}

	public Vector2 getPosition() {
		return this.position;
	}
	
	public Vector2 getAcceleration() {
		return this.acceleration;
	}
	
	public Vector2 getVelocity() {
		return this.velocity;
	}
}
