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
	// TODO ASUGANG!!!
	
	public WhipMain(){
	}
	
	public static void main(String[] args) {
		
		/**
		 * Motors speed
		 */
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.40);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() * 0.15);
		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);		

		Settings.motorAAngle = 90;
		
		
		new LightCalibrate(true, true, false, false);
		Settings.LIGHT.setLow(Settings.light_black);
		Settings.LIGHT.setHigh(Settings.light_bridge + 20);
		
		
		Behavior w0 = new DriveForward();
		Behavior w1 = new WhipIsDown();
		Behavior w2 = new DriveUntilWhipAbyss();
		Behavior w3 = new WhipAbyssDetected();
		Behavior w4 = new WaitForWhip();
		Behavior w5 = new EndOfWhip();
		Behavior w6 = new SensorHeadPosition();
		Behavior w7 = new MotorAStall();
		Behavior[] whipArray = {w0, w1, w2, w3, w4, w5, w6, w7};

		CustomArbitrator arbitrator = new CustomArbitrator(whipArray);		
		Thread t = new Thread(arbitrator);
		t.start();
	}
}
