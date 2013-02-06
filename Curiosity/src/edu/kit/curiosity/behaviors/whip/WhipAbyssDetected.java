package edu.kit.curiosity.behaviors.whip;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import edu.kit.curiosity.Settings;

/**
 * This class is responsible for all the settings needed to run the robot.
 * 
 * @author Curiosity
 */
public class WhipAbyssDetected implements Behavior {

	private boolean suppressed = false;
	private DifferentialPilot pilot;
	private LightSensor lightSensor;
	private double threshold = 50;

	/**
	 * Constructs new WhipAbyssDetected behavior.
	 */
	public WhipAbyssDetected() {
		pilot = Settings.PILOT;
		lightSensor = Settings.LIGHT;
	}

	/**
	 * This behavior will take control if the current normalized light value is
	 * lower than a given threshold.
	 */
	@Override
	public boolean takeControl() {
		return (!Settings.beforeWhip && !Settings.afterWhip && lightSensor
				.getLightValue() < threshold);
	}

	/**
	 * If an abyss is detected, the robot will rotate 10 degrees, until the
	 * light sensor sees normal ground again.
	 */
	@Override
	public void action() {
		System.out.println("Abys");
		suppressed = false;
		while (lightSensor.getLightValue() < threshold && !suppressed) {
			pilot.rotate(10, true);
			if (!pilot.isMoving()) {
				Settings.whipAndBridgeCounter++;
			}
			Delay.msDelay(10);
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
