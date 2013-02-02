package edu.kit.curiosity.behaviors.tape;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.*;

/**
 * This class is responsible for all the settings needed to run the robot.
 * 
 * @author Curiosity
 */
public class TapeMain implements ButtonListener {

	// private static CustomArbitrator arbitrator;

	public TapeMain() {
		Button.ESCAPE.addButtonListener(this);
		Settings.motorAAngle = 90; // Move the head forward
	}

	public static void main(String[] args) throws Exception {
		new TapeMain();

		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);

		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.75);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() * 0.15);

		new LightCalibrate(true, false, false, true, false);

		Behavior tf1 = new LineFollow();
		Behavior tf2 = new TapeLost();
		Behavior tf3 = new GapFound(); // >130
		Behavior tf4 = new ObstacleFound();
		Behavior tf5 = new SensorHeadPosition();
		Behavior tf6 = new MotorAStall();
		// Behavior tf7 = new PilotStall();

		Behavior[] tapeFollow = { tf1, tf2, tf3, tf4, tf5, tf6 };
		CustomArbitrator tapeFollowArbitrator = new CustomArbitrator(tapeFollow);
		Thread t = new Thread(tapeFollowArbitrator);
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
