package edu.kit.curiosity.behaviors.tape;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.*;
import edu.kit.curiosity.behaviors.maze.*;

public class Main implements ButtonListener {

	// private static CustomArbitrator arbitrator;

	public Main() {
		Button.ESCAPE.addButtonListener(this);
		Settings.motorAAngle = 90; //Move the head forward
	}

	public static void main(String[] args) throws Exception {
		new Main();
		
		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);

		
		//Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.25);
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.75);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() * 0.15);
		
		
		calibrateLight();
		//Settings.LIGHT.setLow(294); Settings.LIGHT.setHigh(422);
		//Settings.LIGHT.setLow(250); Settings.LIGHT.setHigh(450);
		Behavior tf1 = new LineFollow();
		Behavior tf2 = new TapeLost();
		Behavior tf3 = new GapFound(); // >130
		Behavior tf4 = new ObstacleFound();
		Behavior tf5 = new SensorHeadPosition();
		Behavior tf6 = new MotorAStall();
		Behavior[] tapeFollow = {tf1, tf2, tf3, tf4, tf5, tf6};
		Arbitrator tapeFollowArbitrator = new Arbitrator(tapeFollow);
		tapeFollowArbitrator.start();
	}

	private static void calibrateLight() {
		System.out.println("Calibrate LightSensor!");
		System.out.print("Calibrate Low: ");
		Button.ENTER.waitForPressAndRelease();
		Settings.LIGHT.calibrateLow();
		System.out.println(Settings.LIGHT.getLow());
		Button.ENTER.waitForPressAndRelease();
		System.out.print("\nCalibrate High: ");
		Settings.LIGHT.calibrateHigh();
		System.out.println(Settings.LIGHT.getHigh());
		System.out.println("Start Running with ENTER!");
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
	}

	@Override
	public void buttonPressed(Button b) {
		// TODO Auto-generated method stub
		stopRunning();
	}

	@Override
	public void buttonReleased(Button b) {
		// TODO Auto-generated method stub
		stopRunning();
	}

	private void stopRunning() {
		// Stop the arbitrator, the main program and the motors.
		System.exit(0);
		// arbitrator.stop();
		// resetMotors();
	}
}
