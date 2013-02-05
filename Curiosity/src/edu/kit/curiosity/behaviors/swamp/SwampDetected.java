package edu.kit.curiosity.behaviors.swamp;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

/**
 * The class {@code SwampDetected} describes the Behavior which takes place, if
 * the robot detects the swamp.
 * 
 * @author Team Curiosity
 * 
 */
public class SwampDetected implements Behavior {

	private LightSensor light;
	private DifferentialPilot pilot;

	private boolean suppressed;

	/**
	 * Constructs a new SwampDetected Behavior
	 */
	public SwampDetected() {
		light = Settings.LIGHT;
		pilot = Settings.PILOT;
	}

	/**
	 * The Behavior takes control, if the robot is before the swamp and the
	 * {@link LightSensor} detects a high value.
	 */
	@Override
	public boolean takeControl() {
		return !Settings.afterSwamp;
	}

	/**
	 * Moves the SensorHead to the front and drives forward until the end of the
	 * swamp.
	 */
	@Override
	public void action() {
		suppressed = false;
		Settings.inSwamp = true;
		Settings.motorAAngle = 0;
		Motor.A.flt(true);
		Motor.A.rotateTo(Settings.motorAAngle); // TODO warum???
		Motor.A.flt(true);
		pilot.forward();
		while (pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
		pilot.stop();
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
