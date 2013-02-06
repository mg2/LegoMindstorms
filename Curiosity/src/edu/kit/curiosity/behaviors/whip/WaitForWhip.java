package edu.kit.curiosity.behaviors.whip;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import edu.kit.curiosity.Settings;

/**
 * This class is responsible for all the settings needed to run the robot.
 * 
 * @author Curiosity
 */
public class WaitForWhip implements Behavior {

	private boolean suppressed = false;
	private final int distance = 25; // TODO genauerer Abstand
	UltrasonicSensor sonic = Settings.SONIC;
	DifferentialPilot pilot = Settings.PILOT;

	@Override
	public boolean takeControl() {
		return (Settings.beforeWhip && sonic.getDistance() <= distance);
	}

	@Override
	public void action() {
		suppressed = false;
		while (!suppressed && sonic.getDistance() <= distance) {
			pilot.travel(0, true);
			Thread.yield();
		}
		Delay.msDelay(1000);
		if (!suppressed) {
			pilot.travel(30); // TODO genauere Strecke
			Settings.motorAAngle = -90;
			Settings.beforeWhip = false;
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
