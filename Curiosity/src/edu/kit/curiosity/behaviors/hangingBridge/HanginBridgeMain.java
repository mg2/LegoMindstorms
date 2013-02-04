package edu.kit.curiosity.behaviors.hangingBridge;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.MotorAStall;
import edu.kit.curiosity.behaviors.SensorHeadPosition;
import edu.kit.curiosity.behaviors.tapefollow.TapeFollow;

public class HanginBridgeMain {
	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double speed = pilot.getMaxTravelSpeed() * 0.15;
		pilot = Settings.PILOT;
		pilot.setTravelSpeed(speed);
		pilot.setRotateSpeed(pilot.getRotateMaxSpeed());
		new SensorHeadCalibrate();

		Settings.motorAAngle = 0;

		new LightCalibrate(false, false);

		Behavior hb0 = new TapeFollow();
		Behavior hb1 = new FollowBridgeBack();
		Behavior hb2 = new FollowBridgeGas();
		Behavior hb5 = new SensorHeadPosition();
		Behavior hb6 = new MotorAStall();

		Behavior[] hangingBridgeArray = {hb0, hb2, hb5, hb6 };

		CustomArbitrator hangingBridgeArbitrator = new CustomArbitrator(hangingBridgeArray);
		Thread t = new Thread(hangingBridgeArbitrator);
		t.start();
	}

}
