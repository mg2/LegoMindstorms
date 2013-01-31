package edu.kit.curiosity.behaviors.maze;

import edu.kit.curiosity.Settings;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * The class {@code WallToFar} describes the Behavior when the Wall is to far from the sensor.
 * @author Team Curiosity
 *
 */
public class WallTooFar implements Behavior {

	private boolean suppressed = false;
	
	private DifferentialPilot pilot;
	private UltrasonicSensor sonic;

	/**
	 * Constructs  new WallToClose Behavior.
	 */
	public WallTooFar()
	{
		pilot = Settings.PILOT;
		sonic = Settings.SONIC;
	}
	
	/**
	 * This Behavior takes control if the Distance is higher than 15
	 */
	@Override
	public boolean takeControl() {
		return (sonic.getDistance() > 18);
	}

	/**
	 * Moves to the right.
	 */
	@Override
	public void action() {
		suppressed = false;
		pilot.travel(5);
		pilot.arc(-30, -50, true);
		while(pilot.isMoving() && !suppressed) {
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
