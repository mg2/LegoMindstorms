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
		return (Math.abs(Motor.A.getTachoCount() - Settings.motorAAngle) > 5); //not sure
	}

	/**
	 * Turns the SensorHead to the right position.
	 */
	@Override
	public void action() {
		suppressed = false;
		Motor.A.flt(true);
		Motor.A.rotateTo(Settings.motorAAngle, false);
		while (Motor.A.isMoving() && !suppressed) {
			Thread.yield();
		}
		Motor.A.stop();
		//Motor.A.flt(true);
	}

	/**
	 * Initiates the cleanup when this Behavior is suppressed
	 */
	@Override
	public void suppress() {
		suppressed = true;

	}

}
