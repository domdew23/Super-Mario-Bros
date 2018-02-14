package net.dom.supermariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.supermariobros.GameMain;

import net.dom.supermariobros.screens.GameScreen;

public abstract class Enemy extends Sprite {
	
	public Body body;
	public boolean destroy;
	public boolean destroyed;
	
	protected float stateTimer;
	protected boolean walkingRight;
	protected int count;
	protected boolean goingRight;
	protected Animation<TextureRegion> walk;
	protected World world;
	protected GameScreen screen;
	protected Fixture fixture;
	
	public Enemy(World world, GameScreen screen, float x, float y, String sheet) {
		super(screen.getAtlas().findRegion(sheet));
		this.world = world;
		this.screen = screen;
		this.destroy = false;
		this.destroyed = false;
		this.stateTimer = 0;
		this.walkingRight = true;
		this.goingRight = true;
		this.count = 0;
		setBounds(getX(), getY(), 16 / GameMain.scale, 16 / GameMain.scale);
		setPosition(x, y);
		makeBody();
	}
	
	protected void makeBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(getX(), getY());
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDef);
	
		FixtureDef fixDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(7 / GameMain.scale);
		fixDef.shape = shape; 
		fixture = body.createFixture(fixDef);
		makeHead(fixDef);
	}
	
	protected void makeHead(FixtureDef fixDef) {
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-8 / GameMain.scale, 8 / GameMain.scale), new Vector2(8 / GameMain.scale, 8 / GameMain.scale));
		fixDef.shape = head;
		fixDef.restitution = 1;
		body.createFixture(fixDef).setUserData(this);
	}
	
	protected TextureRegion getFrame(float delta) {
		TextureRegion region = walk.getKeyFrame(stateTimer, true);
		
		if ((body.getLinearVelocity().x < 0 || !walkingRight) && !region.isFlipX()) {
			region.flip(true, false);
			walkingRight = false;
		} else if ((body.getLinearVelocity().x > 0 || walkingRight) && region.isFlipX()) {
			region.flip(true, false);
			walkingRight = true;
		}
		
		return region;
	}
	
	public void changeDirection() {
		walkingRight = walkingRight ? false : true;
		goingRight = goingRight ? false : true;
	}
	
	protected void checkChange() {		
		if(++count % 150 == 0) {
			changeDirection();
		}
		
		if (goingRight) {
			if (body.getLinearVelocity().x <= 0.5) body.applyLinearImpulse(new Vector2(0.05f, 0), body.getWorldCenter(), true);
		} else {
			if (body.getLinearVelocity().x >= -0.5) body.applyLinearImpulse(new Vector2(-0.05f, 0), body.getWorldCenter(), true);
		}
	}
	
	public void draw(Batch batch) {
		if (!destroyed || stateTimer < 1) super.draw(batch);
	}
	
	public void collisionHead() {
		destroy = true;
		stateTimer = 0;
	}
	
	public abstract void update(float delta);
	protected abstract void walkAnimation();
}
