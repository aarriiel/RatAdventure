package com.aarriiel.game;

import com.aarriiel.game.Screen.mainScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class RatAdventure extends Game {
	private static TextureAtlas arthurAtlas;
	private static SpriteBatch batch;
	private static AssetManager manager;


	public static float PPM = 100;
	public static final int V_WIDTH=1920;
	public static final int V_HEIGHT=1080;

	public static SpriteBatch getBatch(){
		return batch;
	}

	public static AssetManager getManager(){
		return manager;
	}

	public static TextureAtlas getArthurAtlas() {
		return arthurAtlas;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		arthurAtlas = new TextureAtlas("role/arthur/arthur_all.atlas");

		//use the asset manager to load all audio
		manager = new AssetManager();
		manager.load("hud/dialog.png", Texture.class);
		//ex : manager.load("audio/sounds/coin.wav", Sound.class);
		manager.finishLoading();
		setScreen(new mainScreen(this));
	}

	@Override
	public void render(){
		super.render();// about the gdx.graphic.getDeltaTime (the time span between the current frame and the last frame in seconds)
	}

	@Override
	public void dispose(){
		super.dispose();
		manager.dispose();
		batch.dispose();
	}
}
