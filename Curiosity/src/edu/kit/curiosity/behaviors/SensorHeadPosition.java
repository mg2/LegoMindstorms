package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

/**
 * The class {@code SensorHeadPosition} describes the behavior which takes place
 * if the SensorHead is at a wrong position.
 * @author Team Curiosity
 *
 */
public class SensorHeadPosition implements Behavior {

	private boolean suppressed = false;

	/**
	 * The Behavior takes control if the SensorHead is at a wrong position
	 */
	@Override
	public boolean takeControl() {

		return (Motor.A.getTachoCount() > 5 || Motor.A.getTachoCount() < -5);
	}

	/**
	 * Turns the SensorHead to the right position.
	 */
	@Override
	public void action() {
		suppressed = false;
		Motor.A.flt(true);
		Motor.A.rotateTo(Settings.motorAAngle);
		Motor.A.flt(true);
		while (Motor.A.isMoving() && !suppressed) {
			Thread.yield();
		}
		Motor.A.stop();

	}

	/**
	 * Initiates the cleanup when this Behavior is suppressed
	 */
	@Override
	public void suppress() {
		suppressed = true;

	}

}
