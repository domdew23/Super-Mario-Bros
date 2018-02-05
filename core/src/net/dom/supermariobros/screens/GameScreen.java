package net.dom.supermariobros.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dom.supermariobros.GameMain;
import net.dom.supermariobros.objects.Brick;
import net.dom.supermariobros.objects.Coin;
import net.dom.supermariobros.objects.Ground;
import net.dom.supermariobros.objects.Pipes;
import net.dom.supermariobros.sprites.Mario;


public class GameScreen implements Screen{

    private OrthographicCamera camera;
    private Viewport gamePort;
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
    
    /*
     * 0 - Background
     * 1 - Graphics
     * 2 - Ground
     * 3 - Pipes
     * 4 - Coins
     * 5 - Bricks
     */
    
    public void show() {
    	world = new World(new Vector2(0, -9.81f), true);
    	debug = new Box2DDebugRenderer();
    	
    	maploader = new TmxMapLoader();
    	map = maploader.load("map/map.tmx");
    	renderer = new OrthogonalTiledMapRenderer(map, 1/ GameMain.scale);
    	
    	camera = new OrthographicCamera();
    	gamePort = new FitViewport(400/ GameMain.scale,208/ GameMain.scale, camera);
    	camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    	
    	BodyDef bodyDef = new BodyDef();
    	FixtureDef fixDef = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	
    	createObjects(bodyDef, shape, fixDef);
    }
    
    private void createObjects(BodyDef bodyDef, PolygonShape shape, FixtureDef fixDef) {
    	ground = new Ground(map, world, bodyDef, body, shape, fixDef);
		pipes = new Pipes(map, world, bodyDef, body, shape, fixDef);
		mario = new Mario(world);
		
		for (MapObject obj : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			new Coin(world, map, rect);
		}
		
		for (MapObject obj : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			new Brick(world, map, rect);
		}
    }


    public void inputHandler(float delta){
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
        	mario.body.applyLinearImpulse(new Vector2(0.1f, 0), mario.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
        	mario.body.applyLinearImpulse(new Vector2(-0.1f, 0), mario.body.getWorldCenter(), true);        	
        }
        if (Gdx.input.isKeyJustPressed(Keys.UP)) {
        	mario.body.applyLinearImpulse(new Vector2(0, 2f), mario.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
        	Gdx.app.exit();
        }

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(.37f, .592f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        inputHandler(delta);
        
        world.step(1/60f, 6, 2);
        
        if (mario.body.getPosition().x < 36 && mario.body.getPosition().x > 0) camera.position.x = mario.body.getPosition().x;
        camera.update();
        renderer.setView(camera);
        renderer.render();
        debug.render(world, camera.combined);
    }

    public void resize(int width, int height) {
        gamePort.update(width, height);
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
}
