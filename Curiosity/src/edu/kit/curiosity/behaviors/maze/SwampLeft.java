package edu.kit.curiosity.behaviors.maze;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

/**
 * The class {@code SwampLeft} describes the Behavior which takes place, if the
 * robot leaves the swamp.
 * 
 * @author Team Curiosity
 * 
 */
public class SwampLeft implements Behavior {

	private LightSensor light;

	private boolean suppressed;

	/**
	 * Constructs a new SwampLeft Behavior
	 */
	public SwampLeft() {
		light = Settings.LIGHT;
	}

	/**
	 * The Behavior takes control, if the robot is in the swamp and the
	 * {@link LightSensor} detects a low value.
	 */
	@Override
	public boolean takeControl() {

		return (Settings.inSwamp && (light.getNormalizedLightValue() < Settings.light_black));
	}

	/**
	 * Switches state from Swamp to afterSwamp and rotates the SensorHead back.
	 */
	@Override
	public void action() {
		suppressed = false;
		Settings.inSwamp = false;
		Settings.afterSwamp = true;
		Settings.motorAAngle = -90;
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
