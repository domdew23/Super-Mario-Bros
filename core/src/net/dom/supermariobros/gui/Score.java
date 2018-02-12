package net.dom.supermariobros.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Score {
	private BitmapFont white;
	private Stage stage;
	private Label label;
	public static Image coin;

	public Score(String text, float x, float y) {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);		
		LabelStyle labelStyle = new LabelStyle(white, Color.WHITE);
		label = new Label(text, labelStyle);
		label.setPosition(x, y);
		
		coin = new Image(new Texture("imgs/coin.png"));
		coin.setPosition(x - 70, y + 10);
		coin.setSize(50, 50);
		
		stage.addActor(coin);
		stage.addActor(label);
	}
	
	public void update(String text) {
		label.setText(text);
	}
	
	public void draw(float delta) {
		stage.act(delta);
		stage.draw();
	}
}
