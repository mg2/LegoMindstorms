package edu.kit.curiosity.behaviors.whip;

import edu.kit.curiosity.Settings;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * This class is responsible for all the settings needed to run the robot.
 * 
 * @author Curiosity
 */
public class WaitForWhip implements Behavior {

	private boolean suppressed = false;
	private final int distance = 20; // TODO genauerer Abstand
	UltrasonicSensor sonic = Settings.SONIC;
	DifferentialPilot pilot = Settings.PILOT;

	@Override
	public boolean takeControl() {
		return (Settings.beforeWhip && sonic.getDistance() <= distance);
	}

	@Override
	public void action() {
		System.out.println("Wait");
		suppressed = false;
		while (!suppressed && sonic.getDistance() <= distance) {
			pilot.travel(0, true);
			Thread.yield();
		}
		if (!suppressed) {
			pilot.travel(30); // TODO genauere Strecke
			Settings.motorAAngle = 0;
			Settings.beforeWhip = false;
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
