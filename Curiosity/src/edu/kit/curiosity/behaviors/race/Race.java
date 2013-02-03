package edu.kit.curiosity.behaviors.race;

import edu.kit.curiosity.Settings;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * The class {@code Race} describes the Behavior which takes place,
 * if a something is hitten during the race.
 * @author Team Curiosity
 *
 */
public class Race implements Behavior {

	private DifferentialPilot pilot;
	private boolean suppressed = false;
	
	/**
	 * Constructs a new Race Behavior
	 */
	public Race() {
		pilot = Settings.PILOT;
	}
	
	/**
	 * The Behavior takes control if one of the {@link TouchSensor} is pressed
	 */
	@Override
	public boolean takeControl() {

		return (Settings.TOUCH_R.isPressed() || Settings.TOUCH_L.isPressed());
	}

	/**
	 * Rotates to the left if right {@link TouchSensor} is pressed,
	 * to the right if left, and more to the left if both.
	 */
	@Override
	public void action() {
		suppressed = false;
		Settings.inFirstRow = true;
		if (Settings.TOUCH_L.isPressed() && Settings.TOUCH_R.isPressed()) {
			pilot.travel(-5);
			pilot.rotate(110);
		} else if (Settings.TOUCH_R.isPressed()) {
			pilot.rotate(70);
		} else if (Settings.TOUCH_L.isPressed()) {
			pilot.rotate(-70);
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
		suppressed  = true;

	}

}
