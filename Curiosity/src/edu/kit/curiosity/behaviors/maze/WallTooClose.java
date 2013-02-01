package edu.kit.curiosity.behaviors.maze;

import edu.kit.curiosity.Settings;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * This class describes the Behavior when a wall comes to close
 * @author Team Curiosity
 *
 */
public class WallTooClose implements Behavior {
	
	private boolean suppressed = false;
	
	private DifferentialPilot pilot;
	private UltrasonicSensor sonic;

	/**
	 * Constructs  new WallToClose Behavior.
	 */
	public WallTooClose()
	{
		pilot = Settings.PILOT;
		sonic = Settings.SONIC;
	}
	
	/**
	 * This Behavior takes control if the Distance is lower than 9
	 */
	@Override
	public boolean takeControl() {
		return (sonic.getDistance() < 9);
	}

	/**
	 * Moves to the left.
	 */
	@Override
	public void action() {
		suppressed = false;
		pilot.arc(10, 15, true);
		Settings.numberOfTurns = 0;
		while(pilot.isMoving() && !suppressed) {
			System.out.println(sonic.getDistance() + " Close");
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
