package edu.kit.curiosity.behaviors.maze;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.*;
import edu.kit.curiosity.behaviors.maze.*;
import edu.kit.curiosity.behaviors.tape.GapFound;
import edu.kit.curiosity.behaviors.tape.LineFollow;
import edu.kit.curiosity.behaviors.tape.TapeLost;

public class Main implements ButtonListener {


	public Main() {
		Button.ESCAPE.addButtonListener(this);
	}

	public static void main(String[] args) throws Exception {
		new Main();
		
		
		// MAZE
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() / 2);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 4);
		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
		Settings.motorAAngle = 0;
		
		Behavior b1 = new DriveForward();
		Behavior b2 = new FollowWall(12);
		Behavior b3 = new BeginMaze();
		Behavior b4 = new HitWall();
 		Behavior b5 = new SensorHeadPosition();
 		Behavior b6 = new SwampDetected();
 		Behavior b7 = new SwampLeft();
 		Behavior b8 = new FoundEndLine();
		Behavior b9 = new MotorAStall();
		
		Behavior[] bArray = { b1, b2, b3, b4, b5, b6, b7, b8, b9};

		CustomArbitrator arbitrator = new CustomArbitrator(bArray);		
		Thread t = new Thread(arbitrator);
		t.start();
		
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
