package org.burnix.heliumbulldozer.models;

import com.badlogic.gdx.math.Vector2;

public class Walker extends Actor {
	public Walker(Vector2 position) {
		super(position);
		initWalker();
	}
	
	protected void initWalker() {
		this.height = 0.5f;
		this.width = 0.5f;
	}
	
	public void update(float delta) {
		super.update(delta);
		
		if(this.velocity.x == 0.0f)
		{
			this.facingLeft = !this.facingLeft;
			if(this.facingLeft)
				this.velocity.x = -5.0f;
			else
				this.velocity.x = 5.0f;
			this.setState(Actor.STATE_WALKING);
		}
		
	}
}
