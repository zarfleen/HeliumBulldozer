package org.burnix.heliumbulldozer.models;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class World {

	List<Rectangle> collisionRects = new ArrayList<Rectangle>();
	Player player;
	Level level;
	
	public World() {
		createWorld();
	}
	
	private void createWorld() {
		this.player = new Player(new Vector2(7, 2));

		this.level = new Level();
	}
	
	public List<Rectangle> getCollisionRects() {
		return this.collisionRects;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	public List<Block> getDrawableBlocks(int width, int height) {
		int x = (int)this.player.getPosition().x - width;
		int y = (int)this.player.getPosition().y - height;
		if(x < 0)
			x = 0;
		if(y < 0)
			y = 0;
		
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;
		
		if(x2 >= this.level.getWidth())
			x2 = this.level.getWidth() - 1;
		if(y2 >= this.level.getHeight())
			y2 = this.level.getHeight() - 1;
		
		List<Block> blocks = new ArrayList<Block>();
		Block block;
		for(int col = x;col <= x2;col++) {
			for(int row = y;row <= y2;row++) {
				block = level.getBlocks()[col][row];
				if(block != null) {
					blocks.add(block);
				}
			}
		}
		
		return blocks;
	}
}
