package edu.kit.curiosity.behaviors.whip;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * This class is responsible for all the settings needed to run the robot.
 * 
 * @author Curiosity
 */
public class DriveUntilWhipAbyss implements Behavior {

	private boolean suppressed = false;
	private DifferentialPilot pilot;
	private LightSensor lightSensor;

	/**
	 * Constructs new DriveUntilWhipAbyss behavior.
	 */
	public DriveUntilWhipAbyss() {
		pilot = Settings.PILOT;
		lightSensor = Settings.LIGHT;
	}

	/**
	 * This behavior will always take control, if there are not other higher
	 * priority behaviors.
	 */
	@Override
	public boolean takeControl() {
		return (!Settings.beforeWhip);
	}

	/**
	 * Move along a given arc.
	 */
	@Override
	public void action() {
		System.out.println("drive");
		suppressed = false;
		pilot.arc(-60, -40, true);
		while (pilot.isMoving() && !suppressed) {
			System.out.println(lightSensor.getLightValue() + "Reached Abyss");
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
