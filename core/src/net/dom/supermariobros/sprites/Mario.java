package net.dom.supermariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.supermariobros.GameMain;

public class Mario extends Sprite {
	private World world;
	public Body body;
	
	public Mario(World world) {
		this.world = world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(32 / GameMain.scale, 32 / GameMain.scale);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bodyDef);
		
		FixtureDef fixDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(5 / GameMain.scale);
		fixDef.shape = shape;
		body.createFixture(fixDef);
	}
}
