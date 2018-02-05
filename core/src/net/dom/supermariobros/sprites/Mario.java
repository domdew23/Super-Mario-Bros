package net.dom.supermariobros.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.supermariobros.GameMain;

import net.dom.supermariobros.screens.GameScreen;

public class Mario extends Sprite {
	private World world;
	public Sprite sprite;
	public Body body;
	
	private TextureRegion marioStand;
	
	public Mario(World world, GameScreen screen) {
		super(screen.getAtlas().findRegion("mario_sheet"));
		this.world = world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(32 / GameMain.scale, 32 / GameMain.scale);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDef);
		
		FixtureDef fixDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(14 / GameMain.scale);
		fixDef.shape = shape;
		body.createFixture(fixDef);
		
		marioStand = new TextureRegion(getTexture(), 399, 272, 30, 40);
		setBounds(0, 0, 30 / GameMain.scale, 40 / GameMain.scale);
		setRegion(marioStand);
	}
	
	public void update(float delta) {
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
	}
}
