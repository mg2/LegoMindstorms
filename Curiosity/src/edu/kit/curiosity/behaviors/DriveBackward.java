package edu.kit.curiosity.behaviors;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

/**
 * This class describes the behavior to simple drive backward.
 * 
 * @author Curiosity
 */
public class DriveBackward implements Behavior {
	private DifferentialPilot pilot;
	private boolean suppressed = false;

	/**
	 * Constructs a new DriveForward Behavior
	 */
	public DriveBackward() {
		this.pilot = Settings.PILOT;
	}

	/**
	 * Takes always Control. returns true
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
		// pilot.forward();
		while (!suppressed) {
			// if (!pilot.isMoving()) {
			pilot.travel(-10, true);
			// }
		}
		pilot.stop();
	}
}