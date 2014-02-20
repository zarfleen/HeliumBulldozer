package org.burnix.heliumbulldozer;

import org.burnix.heliumbulldozer.views.GameScreen;

import com.badlogic.gdx.Game;

public class HeliumBulldozer extends Game {

	@Override
	public void create() {		
		setScreen(new GameScreen());
	}

}
