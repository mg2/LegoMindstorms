package edu.kit.curiosity.behaviors.colorGate;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.bluetooth.GateControl;

public class Main {
	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;
	static UltrasonicSensor sonic = Settings.SONIC;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * !!!!!!MARTIN!!!!!!!!! !!!!!!WICHTIG!!!!!!!!
		 * 
		 * Geschwindigkeit setzen
		 */
		double speed = pilot.getMaxTravelSpeed();
//		pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.25);
//		pilot.setRotateSpeed(pilot.getMaxRotateSpeed() * 0.5);

		pilot = Settings.PILOT;
		pilot.setTravelSpeed(speed);
		new SensorHeadCalibrate();

		Settings.motorAAngle = 0;

		new LightCalibrate(false, false);
		int numOfTapes = 0;
		boolean counted = false;
		boolean reading = false;
		boolean suppressed = false;
		boolean timesUp = false;
		int mode = 0;
		boolean parked = false;
		int blackWhiteThreshold = 40;
		int tr = 45;
		int sleep = 10;

		TouchSensor touchL = Settings.TOUCH_L;
		TouchSensor touchR = Settings.TOUCH_R;
		GateControl gateControl = new GateControl();
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.25);
		pilot.setRotateSpeed(pilot.getMaxRotateSpeed() * 0.5);

		// Mode 0
		// Follow line until wall < 40 for 1sec
		long curTime = System.currentTimeMillis();

		while (!suppressed && !timesUp && mode == 0) {
			if (System.currentTimeMillis() - curTime > 500) {
				timesUp = true;
			}
			if (sonic.getDistance() > 50) {
				curTime = System.currentTimeMillis();
			}
			if (light.getLightValue() > blackWhiteThreshold) {
				// On white, turn right
				// System.out.print("right ");
				pilot.steer(-tr, -10, true);
			} else {
				// On black, turn left
				// System.out.print("left ");
				pilot.steer(tr, 10, true);
			}
			Delay.msDelay(sleep);
		}
		if (!suppressed && mode == 0) {
			timesUp = false;
			mode = 1;
		}

		// Mode 1
		// Turn Around
		while (!suppressed && mode == 1 && light.getLightValue() < 60) {
			//pilot.rotate(5, true);
			pilot.steer(-100, 5, true);
			Delay.msDelay(sleep);
		}
		if (!suppressed && mode == 1) {
			pilot.reset();
			mode = 2;
//			pilot.rotate(-5);
			while (!suppressed && pilot.isMoving())
				;
		}

		// Mode 2
		// Follow line for 3 seconds
		curTime = System.currentTimeMillis();

		while (!suppressed && !timesUp && mode == 2) {
			if (System.currentTimeMillis() - curTime > 3000) {
				timesUp = true;
			}
			if (light.getLightValue() > blackWhiteThreshold) {
				// On white, turn right
				// System.out.print("right ");
				pilot.steer(-tr, -10, true);
			} else {
				// On black, turn left
				// System.out.print("left ");
				pilot.steer(tr, 10, true);
			}
			Delay.msDelay(sleep);
		}

		if (!suppressed && mode == 2) {
			timesUp = false;
			mode = 3;
		}

		// Mode 3
		// Sensor head on the side
		if (mode == 3 && !suppressed) {
			Settings.motorAAngle = Settings.SENSOR_RIGHT;
			mode = 4;
		}

		// Mode 4
		// Drive backwards until wall <10 for 1 sec
		curTime = System.currentTimeMillis();

		while (!suppressed && !Settings.parkCompleted && mode == 4 && !timesUp) {
			if (System.currentTimeMillis() - curTime > 500) {
				System.out.println("Parked.");
				Delay.msDelay(1000);
				parked = true;
				timesUp = true;
				Settings.motorAAngle = Settings.SENSOR_FRONT;
				Delay.msDelay(1000);
				Settings.parkCompleted = true;
				// TODO springen in anderen behavior manager (nach drehteller)
			}
			if (Settings.SONIC.getDistance() > 10) {
				curTime = System.currentTimeMillis();
			}
			Settings.PILOT.travel(-5, true);
		}
		if (!suppressed) {
			System.out.println("Parked.");
			Delay.msDelay(2000);
		}
		pilot.stop();
		Button.waitForAnyPress();

	}

}
