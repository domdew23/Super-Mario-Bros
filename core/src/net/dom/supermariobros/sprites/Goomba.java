package net.dom.supermariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dom.supermariobros.GameMain;

import net.dom.supermariobros.screens.GameScreen;

public class Goomba extends Enemy {
	
	private Animation<TextureRegion> walk;
	private float stateTimer;
	private boolean walkingRight;
	private int count;
	private boolean goingRight;

	public Goomba(World world, GameScreen screen, float x, float y, String sheet) {
		super(world, screen, x, y, sheet);
		walkAnimation();
		stateTimer = 0;
		walkingRight = true;
		goingRight = true;
		count = 0;
		setBounds(getX(), getY(), 16 / GameMain.scale, 16 / GameMain.scale);
		//fixture.setUserData(this);
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
	
	private void walkAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		int size = 60;
		for (int i = 399; i < 399 + (size*5); i+=size) {
			frames.add(new TextureRegion(getTexture(), i, 195, size, 55));
		}
		walk = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();
	}
	
	private TextureRegion getFrame(float delta) {
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
	
	private void checkChange() {		
		if(++count % 150 == 0) {
			changeDirection();
		}
		
		if (goingRight) {
			if (body.getLinearVelocity().x <= 0.5) body.applyLinearImpulse(new Vector2(0.05f, 0), body.getWorldCenter(), true);
		} else {
			if (body.getLinearVelocity().x >= -0.5) body.applyLinearImpulse(new Vector2(-0.05f, 0), body.getWorldCenter(), true);
		}
	}
	
	public void update(float delta) {
		stateTimer += delta;
		if (!destroy) {
			checkChange();
			setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight()/2);
			setRegion(getFrame(delta)); 
		} else {
			setRegion(new TextureRegion(getTexture(), 699, 54, 60, 55));
		}
	}
	
	public void draw(Batch batch) {
		if (!destroyed || stateTimer < 1) super.draw(batch);
	}

	public void collisionHead() {
		destroy = true;
		stateTimer = 0;
	}

	public void collisionBody() {
		System.out.println("kill mario");
	}
}
