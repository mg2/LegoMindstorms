package edu.kit.curiosity.behaviors.whip;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.SensorHeadPosition;
import edu.kit.curiosity.behaviors.tapefollow.TapeFollow;

/**
 * This class is responsible for all the settings needed to run the robot.
 * 
 * @author Curiosity
 */
public class WhipMain {
	// TODO ASUGANG!!!

	public WhipMain() {
	}

	public static void main(String[] args) {

		/**
		 * Motors speed
		 */
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.40);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() * 0.15);
		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);

		new SensorHeadCalibrate();
		
		Settings.motorAAngle = Settings.SENSOR_FRONT;
		
		new LightCalibrate(false, false);


		Behavior t1 = new TapeFollow();
		Behavior w0 = new WhipDriveForward();
		Behavior w1 = new WhipIsDown();
		Behavior w2 = new DriveUntilWhipAbyss();
		Behavior w3 = new WhipAbyssDetected();
		Behavior w4 = new WaitForWhip();
		Behavior w5 = new EndOfWhip();
		Behavior w6 = new SensorHeadPosition();
		Behavior[] whipArray = { t1, w0, w1, w2, w3, w4, w5, w6};

		CustomArbitrator arbitrator = new CustomArbitrator(whipArray);
		Thread t = new Thread(arbitrator);
		t.start();
	}
}
