package edu.kit.curiosity.behaviors.turntable;

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

public class TurntableMain {
	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;

	public static void main(String[] args) {
		/**
		 * !!!!!!MARTIN!!!!!!!!! !!!!!!WICHTIG!!!!!!!! Geschwindigkeit setzen
		 */
		double speed = pilot.getMaxTravelSpeed() * 0.15;
		pilot = Settings.PILOT;
		pilot.setTravelSpeed(speed);
		pilot.setRotateSpeed(pilot.getRotateMaxSpeed());
		new SensorHeadCalibrate();

		Settings.motorAAngle = 0;

		new LightCalibrate(true, true);

		Behavior t1 = new TapeFollow();
		Behavior t2 = new TurnAround();
		Behavior t3 = new TapeFollowForCertainTime();
		//Behavior t4 = new StallMotor();
		Behavior t5 = new SensorHeadPosition();
		Behavior t6 = new MotorAStall();

		Behavior[] tapeFollowArray = { t1, t2, t3, t5, t6 };

		CustomArbitrator tapeFollowArbitrator = new CustomArbitrator(tapeFollowArray);
		Thread t = new Thread(tapeFollowArbitrator);
		t.start();
	}
}
