package com.dom.supermariobros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.dom.supermariobros.screens.Splash;

public class GameMain extends Game {
	public SpriteBatch batch;
	Texture img;
	public static final float scale = 100;
	
	public void create () {
		batch = new SpriteBatch();
		setScreen(new Splash(this));
	}

	public void render () {
		super.render();
	}
	
	public void dispose () {
		super.dispose();
	}
	
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	public void pause() {
		super.pause();
	}
	
	public void resume() {
		super.pause();
	}
}
