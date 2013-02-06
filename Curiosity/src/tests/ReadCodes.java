package tests;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import edu.kit.curiosity.Settings;

public class ReadCodes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("READ");
		boolean suppressed = false;
		// readState = false;
		boolean reading = false;

		boolean counted = false;
		int numOfTapes = 0;
		DifferentialPilot pilot = Settings.PILOT;
		LightSensor light = Settings.LIGHT;

		while (true) {
			while (!suppressed) {
				if (!suppressed && !counted && light.getLightValue() > 50) {
					numOfTapes++;
					counted = true;
					reading = true;
					// pilot.forward();
					pilot.travel(1, true);
				} else if (!suppressed && counted && light.getLightValue() < 40) {
					// pilot.forward();
					pilot.travel(1, true);
					counted = false;
				}
				if (reading && pilot.getMovementIncrement() > 10) {
					LCD.clear();
					// set States..
					System.out.println("CodeNumber: " + numOfTapes);
					// self-suppress (finished job)
					Settings.readState = false;
					suppressed = true;
				}
			}

			pilot.stop();
			Button.waitForAnyPress();
		}
	}

}
