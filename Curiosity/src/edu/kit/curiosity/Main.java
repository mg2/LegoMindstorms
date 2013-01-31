package edu.kit.curiosity;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.behaviors.maze.DriveForward;
import edu.kit.curiosity.behaviors.maze.HitWall;
import edu.kit.curiosity.behaviors.maze.MotorAStall;
import edu.kit.curiosity.behaviors.maze.NoWallInReach;
import edu.kit.curiosity.behaviors.maze.SensorHeadPosition;
import edu.kit.curiosity.behaviors.maze.TurnedToMuch;
import edu.kit.curiosity.behaviors.maze.WallTooClose;
import edu.kit.curiosity.behaviors.maze.WallTooFar;

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


		CustomArbitrator arbitrator = new CustomArbitrator(bArray);
		Thread t = new Thread(arbitrator);
		t.start();
		
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
