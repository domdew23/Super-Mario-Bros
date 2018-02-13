package net.dom.supermariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import net.dom.supermariobros.screens.GameScreen;

public abstract class Enemy extends Sprite {
	protected World world;
	protected GameScreen screen;
	protected Fixture fixture;
	public Body body;
	public boolean destroy;
	public boolean destroyed;
	
	public Enemy(World world, GameScreen screen, float x, float y, String sheet) {
		super(screen.getAtlas().findRegion(sheet));
		this.world = world;
		this.screen = screen;
		this.destroy = false;
		this.destroyed = false;
		setPosition(x, y);
		makeBody();
	}
	
	protected abstract void makeBody();
	public abstract void update(float delta);
	public abstract void collisionHead();
	public abstract void collisionBody();
}
