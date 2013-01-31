package edu.kit.curiosity;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.behaviors.*;
import edu.kit.curiosity.behaviors.maze.*;
import edu.kit.curiosity.behaviors.tape.GapFound;
import edu.kit.curiosity.behaviors.tape.LineFollow;
import edu.kit.curiosity.behaviors.tape.TapeLost;

public class Main implements ButtonListener {

	// private static CustomArbitrator arbitrator;

	public Main() {
		Button.ESCAPE.addButtonListener(this);
	}

	public static void main(String[] args) throws Exception {
		new Main();
		
		
		// MAZE
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() / 3);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 5);
		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
		
		Behavior b1 = new DriveForward();
		Behavior b2 = new WallTooClose();
		Behavior b3 = new WallTooFar();
		Behavior b4 = new NoWallInReach();
		Behavior b5 = new TurnedToMuch();
		Behavior b6 = new HitWall();
		Behavior b7 = new SensorHeadPosition();
		Behavior b10 = new MotorAStall();

		Behavior[] bArray = { b1, b2, b3, b4, b5, b6, b7, b10 };


		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
		
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.75);
		//calibrateLight();
		Settings.LIGHT.setLow(273); Settings.LIGHT.setHigh(452);
		Behavior tf1 = new LineFollow();
		Behavior tf2 = new TapeLost();
		Behavior tf3 = new GapFound(); // >130
		//Behavior tf4 = new ObstacleFound();
		Behavior tf5 = new SensorHeadPosition();
		Behavior tf6 = new MotorAStall();
		Behavior[] tapeFollow = {tf1, tf2, /*tf3,*/ tf5, tf6};
		Arbitrator tapeFollowArbitrator = new Arbitrator(tapeFollow);
		tapeFollowArbitrator.start();
		
		CustomArbitrator arbitrator = new CustomArbitrator(bArray);		
		Thread t = new Thread(arbitrator);
		t.start();
		
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
