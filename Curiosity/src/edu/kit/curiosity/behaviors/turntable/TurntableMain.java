package edu.kit.curiosity.behaviors.turntable;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.SensorHeadPosition;
import edu.kit.curiosity.behaviors.tapefollow.TapeFollow;

public class TurntableMain {
	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;

	public static void main(String[] args) {

		pilot = Settings.PILOT;
		Settings.bluetooth = false;
		
		new SensorHeadCalibrate();

		Settings.motorAAngle = 0;

		new LightCalibrate(false, false);
		Behavior t1 = new TapeFollow();
		Behavior t2 = new TurntablePark();
		Behavior t3 = new TurntableRotate();
		Behavior t4 = new TurntableConnect();
		Behavior t5 = new SensorHeadPosition();

		Behavior[] tapeFollowArray = { t1, t2, t3, t4, t5 };

		CustomArbitrator tapeFollowArbitrator = new CustomArbitrator(tapeFollowArray);
		Thread t = new Thread(tapeFollowArbitrator);
		t.start();
	}
}
