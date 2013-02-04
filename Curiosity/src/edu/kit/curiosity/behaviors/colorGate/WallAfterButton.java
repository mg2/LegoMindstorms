package edu.kit.curiosity.behaviors.colorGate;

import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

/**
 * This class describes the Behavior when hitting a Wall
 * 
 * @author Team Curiosity
 * 
 */
public class WallAfterButton implements Behavior {

	private boolean suppressed = false;

	private TouchSensor touch_r;
	private TouchSensor touch_l;
	private DifferentialPilot pilot;

	/**
	 * Constructs a new HitWall Behavior
	 */
	public WallAfterButton() {
		touch_r = Settings.TOUCH_R;
		touch_l = Settings.TOUCH_L;
		pilot = Settings.PILOT;
	}

	/**
	 * This Behavior takes control if the TouchSensor is pressed.
	 */
	@Override
	public boolean takeControl() {
		return Settings.buttonPressed
				&& (touch_r.isPressed() || touch_l.isPressed());
	}

	/**
	 * The robot turns left.
	 * 
	 */
	@Override
	public void action() {
		suppressed = false;
		if (touch_r.isPressed() && touch_l.isPressed()) {
			pilot.travel(-5);
			pilot.rotate(-136.5);

		} else if (touch_r.isPressed()) {
			pilot.travel(-5);
			pilot.rotate(30);
		} else if (touch_l.isPressed()) {

			pilot.travel(-5);
			pilot.rotate(-30);
		}
		while (pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
		pilot.stop();
	}

	/**
	 * Initiates the cleanup when this Behavior is suppressed
	 */
	@Override
	public void suppress() {
		suppressed = true;
	}

}
