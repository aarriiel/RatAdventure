package com.aarriiel.game.desktop;

import com.aarriiel.game.RatAdventure;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.x=0;
		config.y=0;
		//config.fullscreen = true;
		new LwjglApplication(new RatAdventure(), config);
	}
}
