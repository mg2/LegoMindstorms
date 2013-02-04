package edu.kit.curiosity.behaviors.race;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

/**
 * This class describes the Behavior to drive forward.
 * 
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
	 * Takes always Control. returns true
	 */
	public boolean takeControl() {
		return Settings.atStart;
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
		while (!suppressed) {
			pilot.steer(-20, -100, true);
			
			/*if (Settings.inFirstRow) {
				pilot.travel(30, true);
			} else {
				pilot.steer(-200, -100, true);
			}*/
		}
		pilot.stop();
	}
}