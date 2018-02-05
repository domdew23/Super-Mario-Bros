package net.dom.supermariobros.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Brick extends Interactive {
	
	public Brick (World world, TiledMap map, Rectangle rect) {	
		super(world, map, rect);
	}
}
