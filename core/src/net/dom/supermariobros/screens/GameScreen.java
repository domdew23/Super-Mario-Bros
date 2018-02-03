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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.dom.supermariobros.objects.Bricks;
import net.dom.supermariobros.objects.Coins;
import net.dom.supermariobros.objects.Ground;
import net.dom.supermariobros.objects.Pipes;

/*
 * 0 - Background
 * 1 - Graphics
 * 2 - Ground
 * 3 - Pipes
 * 4 - Coins
 * 5 - Bricks
 */

public class GameScreen implements Screen {
	
	private World world;
	private Box2DDebugRenderer debug;
	private OrthographicCamera camera;
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	Viewport gamePort;


	BodyDef bodyDef;
	Body body;
	FixtureDef fixDef;
	PolygonShape shape;

	public void inputHandler(float delta) {
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) camera.position.x += 300 * delta;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) camera.position.x -= 300 * delta;
		if (Gdx.input.isKeyPressed(Keys.UP)) camera.position.y += 300 * delta;
		if (Gdx.input.isKeyPressed(Keys.DOWN)) camera.position.y -= 300 * delta;
	}
	
	public void show() {
		world = new World(new Vector2(0, -9.81f), true);
		debug = new Box2DDebugRenderer();
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("map/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		camera = new OrthographicCamera();
		camera.zoom = 1 / 3.45f;
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		bodyDef = new BodyDef();
		fixDef = new FixtureDef();
		shape = new PolygonShape();
		
		Ground ground = new Ground(map, world, bodyDef, body, shape, fixDef);
		Pipes pipes = new Pipes(map, world, bodyDef, body, shape, fixDef);
		Coins coins = new Coins(map, world, bodyDef, body, shape, fixDef);
		Bricks bricks = new Bricks(map, world, bodyDef, body, shape, fixDef);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(.37f, .592f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		inputHandler(delta);
		renderer.setView(camera);
		camera.update();
		renderer.render();
		debug.render(world, camera.combined);

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
