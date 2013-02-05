package edu.kit.curiosity.behaviors.whip;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

/**
 * This class is responsible for all the settings needed to run the robot.
 * 
 * @author Curiosity
 */
public class DriveUntilWhipAbyss implements Behavior {

	private boolean suppressed = false;
	private DifferentialPilot pilot;

	/**
	 * Constructs new DriveUntilWhipAbyss behavior.
	 */
	public DriveUntilWhipAbyss() {
		pilot = Settings.PILOT;
	}

	/**
	 * This behavior will always take control, if there are not other higher
	 * priority behaviors.
	 */
	@Override
	public boolean takeControl() {
		return !(Settings.beforeWhip) && !Settings.afterWhip;
	}

	/**
	 * Move along a given arc.
	 */
	@Override
	public void action() {
		suppressed = false;
		Settings.whipAndBridgeCounter = 0;
		pilot.arc(-60, -40, true);
		while (pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
		pilot.stop();
	}

	/**
	 * Initiates the cleanup when this behavior is suppressed
	 */
	@Override
	public void suppress() {
		suppressed = true;
	}

}
