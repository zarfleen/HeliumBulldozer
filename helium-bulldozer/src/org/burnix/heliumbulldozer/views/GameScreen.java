package org.burnix.heliumbulldozer.views;

import org.burnix.heliumbulldozer.controllers.WorldController;
import org.burnix.heliumbulldozer.models.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
	private WorldController controller;
	
	private int width;
	private int height;
	
	@Override
	public boolean keyDown(int keycode) {
		this.controller.keyPressed(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		this.controller.keyReleased(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(float delta) {
		if(delta > 0.1f)
			delta = 0.1f;
		
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		this.controller.update(delta);
		this.renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;

		this.renderer.setSize(this.width, this.height);
	}

	@Override
	public void show() {
		this.world = new World();
		this.renderer = new WorldRenderer(this.world, true);
		this.controller = new WorldController(this.world);
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

}
