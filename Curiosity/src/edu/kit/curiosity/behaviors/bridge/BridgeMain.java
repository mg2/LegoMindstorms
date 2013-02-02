package edu.kit.curiosity.behaviors.bridge;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import edu.kit.curiosity.ArbitratorManager;
import edu.kit.curiosity.RobotState;
import edu.kit.curiosity.Settings;

public class BridgeMain implements ButtonListener {

	// private static CustomArbitrator arbitrator;
	private static LightSensor light = Settings.LIGHT;

	public BridgeMain() {
		Button.ESCAPE.addButtonListener(this);
	}

	public static void main(String[] args) throws Exception {
		Settings.motorAAngle = 0;
		new BridgeMain();

		while (!Button.ENTER.isDown()) {
			if (Button.LEFT.isDown()) {
				light.calibrateLow();
				System.out.println("Low: ");
				System.out.print(light.getLow());
			} else if (Button.RIGHT.isDown()) {
				System.out.println("High: ");
				light.calibrateHigh();
				System.out.print(light.getHigh());
			}
		}

		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 5);
		Settings.PILOT.setTravelSpeed(30);
//		Behavior b1 = new DriveUntilAbyss();
//		Behavior b2 = new AbyssDetected();
//
//		Behavior[] bArray = { b1, b2 };
//
//
//		CustomArbitrator arbitrator = new CustomArbitrator(bArray);
//		Thread t = new Thread(arbitrator);
//		t.start();
		
		Settings.arbiMgr = new ArbitratorManager(RobotState.BRIDGE);
	}

	@Override
	public void buttonPressed(Button b) {
		// TODO Auto-generated method stub
		// stopRunning();
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
