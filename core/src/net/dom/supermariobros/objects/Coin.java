package net.dom.supermariobros.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Coin extends Interactive {

	public Coin (World world, TiledMap map, Rectangle rect) {
		super(world, map, rect);
		fixture.setUserData(this);
	}

	public void collision() {
		getCell().setTile(map.getTileSets().getTile(28));
		jump = true;
	}
}
