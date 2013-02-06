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
	private final int distance = 30; // TODO genauerer Abstand
	UltrasonicSensor sonic = Settings.SONIC;
	DifferentialPilot pilot = Settings.PILOT;

	@Override
	public boolean takeControl() {
		return (Settings.beforeWhip && sonic.getDistance() <= distance);
	}

	@Override
	public void action() {
		System.out.println("Wait for whip");
		System.out.println("Distance: " + Settings.SONIC.getDistance());
		suppressed = false;
		while (!suppressed && sonic.getDistance() <= distance) {
			pilot.travel(0, true);
		}
		Delay.msDelay(2000);
		if (!suppressed) {
			pilot.travel(30); // TODO genauere Strecke
			Settings.motorAAngle = Settings.SENSOR_RIGHT;
			Settings.beforeWhip = false;
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
