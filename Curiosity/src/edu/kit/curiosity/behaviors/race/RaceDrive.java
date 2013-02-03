package edu.kit.curiosity.behaviors.race;

import edu.kit.curiosity.Settings;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

/**
 * This class describes the Behavior to drive forward.
 * @author Team Curiosity
 *
 */
public class RaceDrive implements Behavior {
	private DifferentialPilot pilot;
	private boolean suppressed = false;
	
	/**
	 * Constructs a new DriveForward Behavior
	 */
	public RaceDrive() {
		this.pilot = Settings.PILOT;
	}

	/**
	 * Takes always Control.
	 * returns true
	 */
	public boolean takeControl() {
		return true;
	}

	/**
	 * Initiates the cleanup when this Behavior is suppressed
	 */
	public void suppress() {
		suppressed = true;
	}

	/**
	 * Moves forward as long as this Behavior is active
	 */
	public void action() {
		suppressed = false;
		if (Settings.inFirstRow) {
			pilot.forward();
		} else {
			pilot.arcForward(-200);
		}
		while (pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
		pilot.stop();
	}
}