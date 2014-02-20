package org.burnix.heliumbulldozer.models;

import com.badlogic.gdx.math.Vector2;

public class Level {

	int width;
	int height;
	Block[][] blocks;
	
	public Level() {
		loadDemoLevel();
	}
	
	private void loadDemoLevel() {
		this.width = 40;
		this.height = 14;
		
		this.blocks = new Block[width][height];
		
		for(int col = 0;col < width;col++) {
			for(int row = 0;row < height;row++) {
				blocks[col][row] = null;
			}
		}

		for(int col = 0;col < width;col++) {
			this.blocks[col][0] = new Block(new Vector2(col, 0));
			this.blocks[col][6] = new Block(new Vector2(col, 6));
			if(col > 2) {
				this.blocks[col][1] = new Block(new Vector2(col, 1));
			}
		}

		this.blocks[14][2] = new Block(new Vector2(14, 2));
		this.blocks[14][3] = new Block(new Vector2(14, 3));
		
		this.blocks[39][2] = new Block(new Vector2(39, 2));
		this.blocks[39][3] = new Block(new Vector2(39, 3));
		this.blocks[39][4] = new Block(new Vector2(39, 4));
		this.blocks[39][5] = new Block(new Vector2(39, 5));

		this.blocks[9][3] = new Block(new Vector2(9, 3));
		this.blocks[9][4] = new Block(new Vector2(9, 4));
		this.blocks[9][5] = new Block(new Vector2(9, 5));

		this.blocks[6][3] = new Block(new Vector2(6, 3));
		this.blocks[6][4] = new Block(new Vector2(6, 4));
		this.blocks[6][5] = new Block(new Vector2(6, 5));
		
		this.blocks[0][1] = new Block(new Vector2(0, 1));		
		this.blocks[0][2] = new Block(new Vector2(0, 2));
		this.blocks[0][3] = new Block(new Vector2(0, 3));
		this.blocks[0][4] = new Block(new Vector2(0, 4));
		this.blocks[0][5] = new Block(new Vector2(0, 5));		
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Block[][] getBlocks() {
		return this.blocks;
	}
	
	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}
	
	public Block get(int x, int y) {
		return this.blocks[x][y];
	}
}
