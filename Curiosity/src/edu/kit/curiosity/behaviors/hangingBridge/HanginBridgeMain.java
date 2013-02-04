package edu.kit.curiosity.behaviors.hangingBridge;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.SensorHeadPosition;
import edu.kit.curiosity.behaviors.tapefollow.TapeFollow;

/**
 * Follow line with 40% of his speed.
 * Simple line follow without sharper turns.
 * If wall nearer than 1m for 2 seconds - switch speed to 15% and go in complex mode.
 * Wall being far than 1m or touch sensors triggered (opponent hit) resets the timer.
 */
public class HanginBridgeMain {
	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		pilot = Settings.PILOT;

		pilot.setRotateSpeed(pilot.getRotateMaxSpeed());
		new SensorHeadCalibrate();

		Settings.motorAAngle = 0;

		new LightCalibrate(false, false);

		Behavior hb1 = new TapeFollow();
		Behavior hb0 = new FollowBridgeFast();
		Behavior hb3 = new SensorHeadPosition();

		Behavior[] hangingBridgeArray = {hb1, hb0, hb3};

		CustomArbitrator hangingBridgeArbitrator = new CustomArbitrator(hangingBridgeArray);
		Thread t = new Thread(hangingBridgeArbitrator);
		t.start();
	}

}
