package edu.kit.curiosity.behaviors.swamp;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.DriveForward;
import edu.kit.curiosity.behaviors.SensorHeadPosition;

public class SwampMain implements ButtonListener {

	public SwampMain() {
		Button.ESCAPE.addButtonListener(this);
	}

	public static void main(String[] args) throws Exception {
		new SwampMain();

		// Swamp
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() / 2);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 4);
		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
		new SensorHeadCalibrate();
		Settings.motorAAngle = 0;

		Behavior b1 = new SwampForward();
		Behavior b5 = new SensorHeadPosition();

		Behavior[] bArray = { b1, b5};

		CustomArbitrator arbitrator = new CustomArbitrator(bArray);
		Thread t = new Thread(arbitrator);
		t.start();

		// Settings.arbiMgr = new ArbitratorManager(RobotState.MAZE);
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
