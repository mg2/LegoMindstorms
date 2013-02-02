package edu.kit.curiosity.behaviors.whip;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.DriveForward;
import edu.kit.curiosity.behaviors.MotorAStall;
import edu.kit.curiosity.behaviors.SensorHeadPosition;

/**
 * This class is responsible for all the settings needed to run the robot.
 * 
 * @author Curiosity
 */
public class WhipMain {
	
	public WhipMain(){
		Settings.motorAAngle = 90;
	}
	
	public static void main(String[] args) {
		
		/**
		 * Motors speed
		 */
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.75);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() * 0.15);
		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
		
		
		new LightCalibrate();
		
		Behavior w0 = new DriveForward();
		Behavior w1 = new DriveUntilWhipAbyss();
		Behavior w2 = new WhipAbyssDetected();
		Behavior w3 = new WaitForWhip();
		Behavior w4 = new SensorHeadPosition();
		Behavior w5 = new MotorAStall();
		Behavior[] whipArray = {w0, w1, w2, w3, w4, w5};

		CustomArbitrator arbitrator = new CustomArbitrator(whipArray);		
		Thread t = new Thread(arbitrator);
		t.start();
	}
}
