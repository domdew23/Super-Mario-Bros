package net.dom.supermariobros.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import net.dom.supermariobros.tween.ActorAccessor;

public class MainMenu implements Screen {
	
	private Stage stage;
	private Skin skin;
	private Table table;
	private Label heading;
	private TextButton exitButton, playButton;
	private BitmapFont white, black;
	private TextureAtlas atlas;
	private TweenManager tweenManager;
	
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(atlas);
		black = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);
		white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		addButtons();
		
		LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
		heading = new Label("Super Mario Bros", headingStyle);

		setStage();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		fadeIn();
		
		rainbow();
		

		Tween.from(table, ActorAccessor.ALPHA, 1f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, 1f).target(Gdx.graphics.getHeight()/8).start(tweenManager);
	}
	
	private void rainbow() {
		/* rainbow text */
		Timeline.createSequence().beginSequence()
			.push(Tween.to(heading, ActorAccessor.RGB, 1f).target(0, 0, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, 1f).target(0, 1, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, 1f).target(1, 0, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, 1f).target(1, 1, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, 1f).target(1, 0, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, 1f).target(1, 1, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, 1f).target(0, 1, 1))
			.end().repeat(Tween.INFINITY, 0).start(tweenManager);	
	}
	
	private void fadeIn() {
		/* Add fading in animation for actors */
		Timeline.createSequence().beginSequence()
			.push(Tween.set(playButton, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(exitButton, ActorAccessor.ALPHA).target(0))
			.push(Tween.from(heading, ActorAccessor.ALPHA, .5f).target(0))
			.push(Tween.to(playButton, ActorAccessor.ALPHA, .5f).target(1))
			.push(Tween.to(exitButton, ActorAccessor.ALPHA, .5f).target(1))
			.end().start(tweenManager);	
	}
	
	private void addButtons() {
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 4;
		textButtonStyle.pressedOffsetY = -2;
		textButtonStyle.font = black;
		
		exitButton = new TextButton("EXIT", textButtonStyle);
		exitButton.pad(35);
		exitButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		playButton = new TextButton("PLAY", textButtonStyle);
		playButton.pad(31);
		playButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
				Tween.from(table, ActorAccessor.ALPHA, 1f).target(0).start(tweenManager);
				Tween.from(table, ActorAccessor.Y, 0).target(Gdx.graphics.getHeight()/8).start(tweenManager);			
				}
		});
	}
	
	private void setStage() {
		table.add(heading);
		table.getCell(heading).spaceBottom(150); // get cell containing heading actor
		table.row();
		table.add(playButton);
		table.getCell(playButton).spaceBottom(15);
		table.row();
		table.add(exitButton);
		stage.addActor(table);	
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(.37f, .592f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		stage.act(delta);
		stage.draw();
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		table.setClip(true);
		table.invalidateHierarchy();
		table.setSize(width, height);
		table.setFillParent(true);
	}

	public void pause() {

	}

	public void resume() {

	}

	public void hide() {
		dispose();
	}

	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		white.dispose();
		black.dispose();
	}

}
