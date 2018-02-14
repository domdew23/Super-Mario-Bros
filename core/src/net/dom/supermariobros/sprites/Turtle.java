package net.dom.supermariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import net.dom.supermariobros.screens.GameScreen;

public class Turtle extends Enemy {

	public Turtle(World world, GameScreen screen, float x, float y, String sheet) {
		super(world, screen, x, y, sheet);
		walkAnimation();
	}

	protected void walkAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		int size = 40;
		int start = 190;
		for (int i = start; i < start + (size*4); i+=size) {
			frames.add(new TextureRegion(getTexture(), i, 41, size, 29));
		}
		walk = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();
	}
	
	public void update(float delta) {
		stateTimer += delta;
		if (!destroy) {
			checkChange();
			setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight()/2);
			setRegion(getFrame(delta)); 
		} else {
			setRegion(new TextureRegion(getTexture(), 1, 1, 35, 23));
		}
	}


	public void collisionBody() {
		
	}

}
