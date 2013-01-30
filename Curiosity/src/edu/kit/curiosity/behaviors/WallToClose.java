package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * This class describes the Behavior when a wall comes to close
 * @author Team Curiosity
 *
 */
public class WallToClose implements Behavior {
	
	private boolean suppressed = false;
	
	private UltrasonicSensor sonic;
	private DifferentialPilot pilot;

	/**
	 * Constructs  new WallToClose Behavior.
	 */
	public WallToClose()
	{
		pilot = Settings.pilot;
		sonic = new UltrasonicSensor(Settings.SONIC_SENSOR_PORT);
	}
	
	/**
	 * This Behavior takes control if the Distance is lower than 8
	 */
	@Override
	public boolean takeControl() {
		return (sonic.getDistance() < 8);
	}

	/**
	 * Moves to the left.
	 */
	@Override
	public void action() {
		suppressed = false;
		pilot.arcForward(-10);
		pilot.rotate(10);
		while(!suppressed && pilot.isMoving()) {
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
