package org.burnix.heliumbulldozer.models;

import com.badlogic.gdx.math.Vector2;

public class Block extends GameObject {
	
	public Block(Vector2 position) {
		init(position);
		
		this.width = 1f;
		this.height = 1f;
	}
}
