package net.dom.supermariobros.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dom.supermariobros.GameMain;

public class NonInteractive {
	public NonInteractive(TiledMap map, World world, int id) {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		Body body;
		
		for (MapObject object : map.getLayers().get(id).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bodyDef.type = BodyDef.BodyType.StaticBody;
			bodyDef.position.set((rect.getX() + rect.getWidth()/2)/GameMain.scale, (rect.getY() + rect.getHeight()/2)/GameMain.scale);
			
			body = world.createBody(bodyDef);
			shape.setAsBox(rect.getWidth() / 2 /GameMain.scale, rect.getHeight() / 2 /GameMain.scale);
			fixDef.shape = shape;
			body.createFixture(fixDef);			
		}
	}
}
