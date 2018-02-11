package net.dom.supermariobros.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import net.dom.supermariobros.objects.Interactive;

public class CollisionListener implements ContactListener {

	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if (!(a.getUserData() instanceof Interactive) && !(b.getUserData() instanceof Interactive)) return;
		
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
