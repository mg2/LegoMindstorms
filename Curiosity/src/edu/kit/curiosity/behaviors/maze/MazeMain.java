package edu.kit.curiosity.behaviors.maze;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.DriveForward;
import edu.kit.curiosity.behaviors.SensorHeadPosition;

public class MazeMain implements ButtonListener {


	public MazeMain() {
		Button.ESCAPE.addButtonListener(this);
	}

	public static void main(String[] args) throws Exception {
		new MazeMain();
		
		
		// MAZE
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed());
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 4);
		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
		new SensorHeadCalibrate();
		Settings.motorAAngle = -90;
		
		Behavior b1 = new DriveForward();
		Behavior b2 = new FollowWall(12);
		Behavior b3 = new BeginMaze();
		Behavior b4 = new HitWall();
 		Behavior b5 = new SensorHeadPosition();
		
		Behavior[] bArray = { b1, b2, b3, b4, b5};

		CustomArbitrator arbitrator = new CustomArbitrator(bArray);		
		Thread t = new Thread(arbitrator);
		t.start();
		
		//Settings.arbiMgr = new ArbitratorManager(RobotState.MAZE);
	}

	@Override
	public void buttonPressed(Button b) {
		stopRunning();
	}

	@Override
	public void buttonReleased(Button b) {
		stopRunning();
	}

	private void stopRunning() {
		// Stop the arbitrator, the main program and the motors.
		System.exit(0);
		// arbitrator.stop();
		// resetMotors();
	}
}
