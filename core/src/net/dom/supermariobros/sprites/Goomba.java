package net.dom.supermariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import net.dom.supermariobros.screens.GameScreen;

public class Goomba extends Enemy {
	
	public Goomba(World world, GameScreen screen, float x, float y, String sheet) {
		super(world, screen, x, y, sheet);
		walkAnimation();
	}
	
	protected void walkAnimation() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		int size = 60;
		for (int i = 399; i < 399 + (size*5); i+=size) {
			frames.add(new TextureRegion(getTexture(), i, 195, size, 55));
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
			setRegion(new TextureRegion(getTexture(), 699, 54, 60, 55));
		}
	}
}
