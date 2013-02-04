package edu.kit.curiosity.behaviors;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

/**
 * The class {@code SensorHeadPosition} describes the behavior which takes place
 * if the SensorHead is at a wrong position.
 * 
 * @author Team Curiosity
 * 
 */
public class SensorHeadPosition implements Behavior {

	private boolean suppressed;
	
	public SensorHeadPosition() {
		suppressed = false;
		Motor.A.setSpeed((int) (Motor.A.getMaxSpeed() * 0.2));
		Motor.A.setStallThreshold(10, 100);
	}

	/**
	 * The Behavior takes control if the SensorHead is at a wrong position
	 */
	@Override
	public boolean takeControl() {
		return (Math.abs(Motor.A.getTachoCount() - Settings.motorAAngle) > 5 || (Motor.A.getTachoCount() == 0 && Settings.motorAAngle == -90));
	}

	/**
	 * Turns the SensorHead to the right position.
	 */
	@Override
	public void action() {
		suppressed = false;
		//if (Settings.motorAAngle == -90) Settings.motorAAngle = -95;

		Motor.A.rotateTo(Settings.motorAAngle, true);

		while (Motor.A.isMoving() && !Motor.A.isStalled() && !suppressed);			
		
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
