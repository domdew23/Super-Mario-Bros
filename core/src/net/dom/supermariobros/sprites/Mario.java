package net.dom.supermariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dom.supermariobros.GameMain;

import net.dom.supermariobros.screens.GameScreen;

public class Mario extends Sprite {
	private World world;
	public Body body;
	public enum State {
		FALLING,
		JUMPING,
		STANDING,
		RUNNING
	}
	public State currentState;
	public State prevState;
	private Animation<TextureRegion> run;
	private Animation<TextureRegion> jump;
	private boolean runningRight;
	private float stateTimer;
	
	private TextureRegion standing;
	
	public Mario(World world, GameScreen screen) {
		super(screen.getAtlas().findRegion("mario_sheet"));
		this.world = world;
		currentState = State.STANDING;
		prevState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		runAnimation();
		jumpAnimation();
	
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(32 / GameMain.scale, 32 / GameMain.scale);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDef);
		
		FixtureDef fixDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(14 / GameMain.scale);
		fixDef.shape = shape;
		body.createFixture(fixDef);
		
		//399 //495
		standing = new TextureRegion(getTexture(), 405, 272, 20, 40);
		setBounds(0, 0, 20 / GameMain.scale, 40 / GameMain.scale);
		setRegion(standing);
	}
	
	private void runAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for (int i = 476; i < 476 + (20*3); i+=20) {
			frames.add(new TextureRegion(getTexture(), i, 272, 20, 40));
		}
		
		run = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();
	}
	
	private void jumpAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for (int i = 536; i < 536 + (23*1); i+=23) {
			frames.add(new TextureRegion(getTexture(), i, 272, 23, 40));
		}
		
		jump = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();
	}
	
	public TextureRegion getFrame(float delta) {
		currentState = getState();
		TextureRegion region;
		switch (currentState) {
			case JUMPING: region = jump.getKeyFrame(stateTimer); break;
			case RUNNING: region = run.getKeyFrame(stateTimer, true); break;
			default:
				region = standing;
				break;
		}
		
		if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		} else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		
		stateTimer = currentState == prevState ? stateTimer + delta : 0;
		prevState = currentState;
		return region;
	}
	
	public State getState() {
		if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && prevState == State.JUMPING)) return State.JUMPING;
		else if (body.getLinearVelocity().y < 0) return State.FALLING;
		else if (body.getLinearVelocity().x != 0) return State.RUNNING;
		else return State.STANDING;
	}
	
	public void update(float delta) {
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(delta));
	}
}
