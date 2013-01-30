package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * The class {@code WallToFar} describes the Behavior when the Wall is to far from the sensor.
 * @author Tobias Hey
 *
 */
public class WallToFar implements Behavior {

	private boolean suppressed = false;
	
	private UltrasonicSensor sonic;
	private DifferentialPilot pilot;

	/**
	 * Constructs  new WallToClose Behavior.
	 */
	public WallToFar()
	{
		pilot = Settings.pilot;
		sonic = new UltrasonicSensor(Settings.SONIC_SENSOR_PORT);
	}
	
	/**
	 * This Behavior takes control if the Distance is higher than 15
	 */
	@Override
	public boolean takeControl() {
		return (sonic.getDistance() > 15);
	}

	/**
	 * Moves to the right.
	 */
	@Override
	public void action() {
		suppressed = false;
		pilot.arcForward(10);
		pilot.rotate(-10);
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
