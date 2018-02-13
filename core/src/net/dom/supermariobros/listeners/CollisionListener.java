package net.dom.supermariobros.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dom.supermariobros.GameMain;

import net.dom.supermariobros.objects.Interactive;
import net.dom.supermariobros.sprites.Enemy;
import net.dom.supermariobros.sprites.Mario;

public class CollisionListener implements ContactListener {

	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
				
		if (a.getUserData() instanceof Enemy || b.getUserData() instanceof Enemy) {
			handleEnemyHit(a, b);
		}
		
		if (a.getUserData() instanceof Interactive || b.getUserData() instanceof Interactive) {
			handleObjectHit(a, b);
		}
	}
	
	private void handleEnemyHit(Fixture a, Fixture b) {
		Mario mario = null;
		Enemy enemy = null;
		
		if (a.getUserData() instanceof Mario) {
			mario = (Mario) a.getUserData();
			enemy = (Enemy) b.getUserData();
		} else if (b.getUserData() instanceof Mario) {
			mario = (Mario) b.getUserData();
			enemy = (Enemy) a.getUserData();
		} else {
			return;
		}
		
		if (mario.body.getPosition().y - (14 / GameMain.scale) > enemy.body.getPosition().y) {
			enemy.collisionHead();
		} else {
			enemy.collisionBody();
		}
	}
	
	private void handleObjectHit(Fixture a, Fixture b) {
		Fixture mario = null;
		Fixture object = null;
		
		if (a.getUserData() == "head") {
			mario = a;
			object = b;
		} else if (b.getUserData() == "head") {
			mario = b; 
			object = a;
		} else {
			return;
		}
		((Interactive) object.getUserData()).collision();
	}

	public void endContact(Contact contact) {
		
	}

	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
