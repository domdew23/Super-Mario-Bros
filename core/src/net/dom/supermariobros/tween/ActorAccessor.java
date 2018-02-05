package net.dom.supermariobros.tween;

import com.badlogic.gdx.scenes.scene2d.Actor;
import aurelienribon.tweenengine.TweenAccessor;

/*
 * Class to add fading in animations to the actors on the stage for the main menu
 * using the Tween engine: https://code.google.com/archive/p/java-universal-tween-engine
 */

public class ActorAccessor implements TweenAccessor<Actor>{
	
	public static final int Y = 0, RGB = 1, ALPHA = 2;
	
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch (tweenType) {
			case Y:
				returnValues[0] = target.getY();
				return 1;
			case RGB:
				returnValues[0] = target.getColor().r;
				returnValues[1] = target.getColor().g;
				returnValues[2] = target.getColor().b;
				return 3;
			case ALPHA:
				returnValues[0] = target.getColor().a;
				return 1;
			default:
				assert false;
				return -1;
		}
	}

	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch (tweenType) {
			case Y:
				target.setY(newValues[0]);
				break;
			case RGB:
				target.setColor(newValues[0], newValues[1], newValues[2], target.getColor().a);
				break;
			case ALPHA:
				target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
				break;
			default:
				assert false;
		}
	}

}