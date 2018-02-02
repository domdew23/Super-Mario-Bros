package net.dom.supermariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
	
	private World world;
	private Box2DDebugRenderer debug;
	private OrthographicCamera camera;
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	Viewport gamePort;
	
	public void inputHandler(float delta) {
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) camera.position.x += 100 * delta;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) camera.position.x -= 100 * delta;
	}
	
	public void show() {
		world = new World(new Vector2(0, -9.81f), true);
		debug = new Box2DDebugRenderer();
		
		camera = new OrthographicCamera();
		gamePort = new FitViewport(1280, 720, camera);

		mapLoader = new TmxMapLoader();
		map = mapLoader.load("map/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		camera.zoom = 1 / 3.5f;
		//camera.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(.37f, .592f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debug.render(world, camera.combined);

		inputHandler(delta);
		renderer.setView(camera);
		camera.update();
		renderer.render();
	}

	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	public void pause() {
		
	}

	public void resume() {
	}

	public void hide() {
		dispose();
	}

	public void dispose() {
		world.dispose();
		debug.dispose();
	}

}
