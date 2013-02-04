package edu.kit.curiosity.behaviors.bridge;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

/**
 * This class drives along an arc until it reaches an abyss.
 * 
 * @author Curiosity
 */
public class DriveUntilAbyss implements Behavior {

	private boolean suppressed = false;
	private DifferentialPilot pilot;
	private LightSensor lightSensor;

	/**
	 * Constructs new DriveUntilAbyss behavior.
	 */
	public DriveUntilAbyss() {
		pilot = Settings.PILOT;
		lightSensor = Settings.LIGHT;
	}

	/**
	 * This behavior will always take control, if there are not other higher
	 * priority behaviors.
	 */
	@Override
	public boolean takeControl() {
		return (lightSensor.getNormalizedLightValue() > Settings.light_bridge - 50);
	}

	/**
	 * Move along a given arc.
	 */
	@Override
	public void action() {
		suppressed = false;
		Settings.reachedBridge = true;
		Settings.whipAndBridgeCounter = 0;
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
