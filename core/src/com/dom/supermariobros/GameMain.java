package com.dom.supermariobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.dom.supermariobros.screens.Splash;

public class GameMain extends Game {
	SpriteBatch batch;
	Texture img;
	
	public void create () {
		//setScreen(new Splash());
		batch = new SpriteBatch();
		img = new Texture("splash.png");
	}

	public void render () {
		super.render();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		
	}
	
	public void dispose () {
		super.dispose();
		batch.dispose();
		img.dispose();
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
