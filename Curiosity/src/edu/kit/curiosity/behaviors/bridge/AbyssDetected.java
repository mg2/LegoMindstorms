package edu.kit.curiosity.behaviors.bridge;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * This class detects an abyss to the right of the robot.
 * 
 * @author Team Curiosity
 */
public class AbyssDetected implements Behavior {

	private boolean suppressed = false;
	private DifferentialPilot pilot;
	private LightSensor lightSensor;
	private double threshold = 50;

	/**
	 * Constructs new AbyssDetected behavior.
	 */
	public AbyssDetected() {
		pilot = Settings.PILOT;
		lightSensor = Settings.LIGHT;
	}

	/**
	 * This behavior will take control if the current normalized light value is
	 * lower than a given threshold.
	 */
	@Override
	public boolean takeControl() {
		return (Settings.reachedBridge && lightSensor.getLightValue() < threshold);
	}

	/**
	 * If an abyss is detected, the robot will rotate 10 degrees, until the
	 * light sensor sees normal ground again.
	 */
	@Override
	public void action() {
		suppressed = false;
		while (lightSensor.getLightValue() < threshold  && !suppressed) {
			pilot.rotate(10);
			Settings.whipAndBridgeCounter++;
		}
		while (pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
		pilot.stop();
	}

	/**
	 * Initiates the cleanup when this behavior is suppressed.
	 */
	@Override
	public void suppress() {
		suppressed = true;
	}

}
