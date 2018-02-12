package net.dom.supermariobros.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dom.supermariobros.GameMain;

import net.dom.supermariobros.gui.Score;
import net.dom.supermariobros.listeners.CollisionListener;
import net.dom.supermariobros.objects.Brick;
import net.dom.supermariobros.objects.Coin;
import net.dom.supermariobros.objects.Ground;
import net.dom.supermariobros.objects.Interactive;
import net.dom.supermariobros.objects.Pipes;
import net.dom.supermariobros.sprites.Enemy;
import net.dom.supermariobros.sprites.Goomba;
import net.dom.supermariobros.sprites.Mario;


public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewPort;
    private TmxMapLoader maploader;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;

    private World world;
    private Box2DDebugRenderer debug;
    private Body body;
    
    private Ground ground;
    private Pipes pipes;
    private Coin[] coins;
    private Brick[] bricks;
    private Mario mario;
    private Array<Enemy> enimies;
    
    private TextureAtlas atlas;
    private GameMain game;
    private Array<Interactive> interactiveObjects;
    private Score score;
    private int count = 0;
    /*
     * 0 - Background
     * 1 - Graphics
     * 2 - Ground
     * 3 - Pipes
     * 4 - Coins
     * 5 - Bricks
     */
    
    public GameScreen(GameMain game) {
    	this.game = game;
    	this.world = new World(new Vector2(0, -9.81f), true);
    	world.setContactListener(new CollisionListener());
    	this.debug = new Box2DDebugRenderer();
    	this.atlas = new TextureAtlas("sprites/mario_and_enimies.pack");
    	this.interactiveObjects = new Array<Interactive>();
    	this.enimies = new Array<Enemy>();
    	
    	loadMap();
    	loadCamera();
    	createObjects();
    }
    
    private void loadCamera() {
    	camera = new OrthographicCamera();
    	viewPort = new FitViewport(400/ GameMain.scale,208/ GameMain.scale, camera);
    	camera.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);    	
    }
    
    private void loadMap() {
    	maploader = new TmxMapLoader();
    	map = maploader.load("map/map.tmx");
    	renderer = new OrthogonalTiledMapRenderer(map, 1/ GameMain.scale);    	
    }
    
    private void createObjects() {    	
    	BodyDef bodyDef = new BodyDef();
    	FixtureDef fixDef = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	
    	score = new Score("x 0", Gdx.graphics.getWidth()/2, 550f);
    	ground = new Ground(map, world, bodyDef, body, shape, fixDef);
		pipes = new Pipes(map, world, bodyDef, body, shape, fixDef);
		mario = new Mario(world, this);
		enimies.add(new Goomba(world, this, 5.15f, .32f, "goomba_sheet"));
		enimies.add(new Goomba(world, this, 5.75f, .32f, "goomba_sheet"));
		
		for (MapObject obj : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			interactiveObjects.add(new Coin(world, map, rect));
		}
		
		for (MapObject obj : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			interactiveObjects.add(new Brick(world, map, rect));
		}
    }

    public void handleInput(float delta){
    	if (mario.body.getPosition().x < 0.5) {
    		mario.body.applyLinearImpulse(new Vector2(0.1f, 0), mario.body.getWorldCenter(), true);
    		return;
    	}
        if(Gdx.input.isKeyPressed(Keys.RIGHT) && mario.body.getLinearVelocity().x <= 2) {
        	mario.body.applyLinearImpulse(new Vector2(0.1f, 0), mario.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT) && mario.body.getLinearVelocity().x >= -2) {
        	mario.body.applyLinearImpulse(new Vector2(-0.1f, 0), mario.body.getWorldCenter(), true);        	
        }
        if (Gdx.input.isKeyJustPressed(Keys.UP) && mario.body.getLinearVelocity().y == 0) {
        	mario.body.applyLinearImpulse(new Vector2(0, 3.8f), mario.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
        	Gdx.app.exit();
        }
        if (mario.body.getPosition().x < 36 && mario.body.getPosition().x > 2) camera.position.x = mario.body.getPosition().x;
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(.37f, .592f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput(delta);
        update(delta);
        draw(delta);  
    }
    
    private void update(float delta) {
    	world.step(1/60f, 6, 2);
    	camera.update();
    	mario.update(delta);
    	for (Enemy e : enimies) e.update(delta);
    	renderer.setView(camera);
    	game.batch.setProjectionMatrix(camera.combined);
    	
    	if (mario.body.getPosition().y < -1 || mario.body.getPosition().y > viewPort.getWorldHeight()) {
    		((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game, "GAME OVER"));
    	}
    	for (Interactive obj : interactiveObjects) {
    		if(obj.destroy && !world.isLocked()) {
    			world.destroyBody(obj.body);
    			interactiveObjects.removeValue(obj, true);
    			score.update("x " + Integer.toString(++count));
    		}
    	}
    }
    
    private void draw(float delta) {
    	renderer.render();
    	score.draw(delta);
    	game.batch.begin();
    	mario.draw(game.batch);
    	for (Enemy e : enimies) e.draw(game.batch);
    	game.batch.end();
    	debug.render(world, camera.combined);    	
    }

    public void resize(int width, int height) {
    	viewPort.update(width, height);
    }

    public void show() {
    }
    
    public void pause() {

    }

    public void resume() {

    }

    public void hide() {
    
    }

    public void dispose() {
    	world.dispose();
    	renderer.dispose();
    	map.dispose();
    	debug.dispose();
    }
    
    public TextureAtlas getAtlas() {
    	return atlas;
    }
}
