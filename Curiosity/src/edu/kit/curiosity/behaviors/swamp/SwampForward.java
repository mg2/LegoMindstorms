package edu.kit.curiosity.behaviors.swamp;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

/**
 * This class describes the Behavior to simple drive forward.
 * @author Team Curiosity
 *
 */
public class SwampForward implements Behavior {
	private DifferentialPilot pilot;
	private boolean suppressed = false;
	private boolean inSwamp = false;
	
	/**
	 * Constructs a new DriveForward Behavior
	 */
	public SwampForward() {
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
		//pilot.forward();
		while (!suppressed) {
			if (Settings.LIGHT.getLightValue() > 30) {
				
				inSwamp = true;
			} else if(inSwamp && Settings.LIGHT.getLightValue() < 30) {
				inSwamp = false;
				Settings.readState = true;
			}
			//if (!pilot.isMoving()) {
				pilot.travel(10, true);
			//}
		}
		//pilot.stop();
	}
}