package net.dom.supermariobros.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends NonInteractive {
	public Ground(TiledMap map, World world) {
		super(map, world, 2);
	}
}
