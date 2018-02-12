package net.dom.supermariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dom.supermariobros.GameMain;

import net.dom.supermariobros.screens.GameScreen;

public class Goomba extends Enemy {
	
	public Body body;
	private Animation<TextureRegion> walk;
	private float stateTimer;
	private boolean walkingRight;
	private int count;
	boolean goingRight;

	public Goomba(World world, GameScreen screen, float x, float y, String sheet) {
		super(world, screen, x, y, sheet);
		walkAnimation();
		stateTimer = 0;
		walkingRight = true;
		goingRight = true;
		count = 0;
		setBounds(getX(), getY(), 16 / GameMain.scale, 16 / GameMain.scale);
		fixture.setUserData(this);
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
		
		stateTimer += delta;
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
		checkChange();
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight()/2);
		setRegion(getFrame(delta));
	}
}
