package edu.kit.curiosity.behaviors.bridge;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.bridge.AbyssDetected;

public class Main implements ButtonListener {

	// private static CustomArbitrator arbitrator;
	private static LightSensor light = Settings.LIGHT;

	public Main() {
		Button.ESCAPE.addButtonListener(this);
	}

	public static void main(String[] args) throws Exception {
		new Main();

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

		/**
		 * Go up bridge, go up as long as ultrasonic sensor shows values < 255.
		 * Keep close to the right side. Keep big enough distance. Follow right
		 * edge with light sensor.
		 */
		Behavior b1 = new DriveUntilAbyss();
		Behavior b2 = new AbyssDetected();

		Behavior[] bArray = { b1, b2 };

		Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
		Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 5);
		Settings.PILOT.setTravelSpeed(30);

		CustomArbitrator arbitrator = new CustomArbitrator(bArray);
		Thread t = new Thread(arbitrator);
		t.start();
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
