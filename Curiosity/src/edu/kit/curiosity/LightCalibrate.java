package edu.kit.curiosity;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;

/**
 * This class is responsible for all the settings needed to run the robot.
 * 
 * @author Curiosity
 */
public class LightCalibrate {
	LightSensor light = Settings.LIGHT;

	/**
	 * Calibrates the light values. if allLights = true - calibrates all values
	 * Value for whip is value of bridge + 20 Sets black as low and line as
	 * high.
	 */
	public LightCalibrate() {
		System.out.println("Calibrating light.");

		// Calibrate BLACK
		System.out.print("Black: ");
		Button.ENTER.waitForPressAndRelease();
		light.calibrateLow();
		Settings.light_black = light.getLow();
		System.out.println(Settings.LIGHT.getLow());

		// Calibrate BRIDGE
		System.out.println("Bridge: ");
		Button.ENTER.waitForPressAndRelease();
		Settings.light_bridge = light.getNormalizedLightValue();
		System.out.println(Settings.light_bridge);

		// Calibrate SWAMP
		System.out.println("Swamp: ");
		Button.ENTER.waitForPressAndRelease();
		Settings.light_bridge = light.getNormalizedLightValue();
		System.out.println(Settings.light_swamp);

		// Calibrate LINE
		System.out.println("Line: ");
		Button.ENTER.waitForPressAndRelease();
		light.calibrateHigh();
		Settings.light_line = light.getHigh();
		System.out.println(Settings.LIGHT.getHigh());

		// Calibrate Color1
		System.out.println("Color1: ");
		Button.ENTER.waitForPressAndRelease();
		Settings.color1 = light.getNormalizedLightValue();
		System.out.println(Settings.color1);
		// Calibrate Color2
		System.out.println("Color2: ");
		Button.ENTER.waitForPressAndRelease();
		Settings.color2 = light.getNormalizedLightValue();
		System.out.println(Settings.color2);
		// Calibrate Color3
		System.out.println("Color3: ");
		Button.ENTER.waitForPressAndRelease();
		Settings.color3 = light.getNormalizedLightValue();
		System.out.println(Settings.color3);

		System.out.println("Press ENTER to continue.");
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
	}

	/**
	 * Only for test purposes. Value for whip is value of bridge + 20
	 * 
	 * @param black
	 * @param bridge
	 * @param swamp
	 * @param line
	 */
	public LightCalibrate(boolean black, boolean bridge, boolean swamp, boolean line, boolean colorGate) {
		System.out.println("Calibrating light.");

		if (black) {
			// Calibrate BLACK
			System.out.print("Black: ");
			Button.ENTER.waitForPressAndRelease();
			light.calibrateLow();
			Settings.light_black = light.getLow();
			System.out.println(Settings.LIGHT.getLow());
		}

		if (bridge) {
			// Calibrate BRIDGE
			System.out.println("Bridge: ");
			Button.ENTER.waitForPressAndRelease();
			Settings.light_bridge = light.getNormalizedLightValue();
			System.out.println(Settings.light_bridge);
		}

		if (swamp) {
			// Calibrate SWAMP
			System.out.println("Swamp: ");
			Button.ENTER.waitForPressAndRelease();
			Settings.light_bridge = light.getNormalizedLightValue();
			System.out.println(Settings.light_swamp);
		}

		if (line) {
			// Calibrate LINE
			System.out.println("Line: ");
			Button.ENTER.waitForPressAndRelease();
			light.calibrateHigh();
			Settings.light_line = light.getHigh();
			System.out.println(Settings.LIGHT.getHigh());
		}

		if (colorGate) {
			// Calibrate Color1
			System.out.println("Color1: ");
			Button.ENTER.waitForPressAndRelease();
			Settings.color1 = light.getNormalizedLightValue();
			System.out.println(Settings.color1);
			// Calibrate Color2
			System.out.println("Color2: ");
			Button.ENTER.waitForPressAndRelease();
			Settings.color2 = light.getNormalizedLightValue();
			System.out.println(Settings.color2);
			// Calibrate Color3
			System.out.println("Color3: ");
			Button.ENTER.waitForPressAndRelease();
			Settings.color3 = light.getNormalizedLightValue();
			System.out.println(Settings.color3);
		}

		System.out.println("Press ENTER to continue.");
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
	}

	/**
	 * Test purposes only. Set light for high and low.
	 */
	public LightCalibrate(boolean black, boolean white) {
		double speed = Settings.PILOT.getTravelSpeed();
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.1);
		System.out.println("Calibrate light.");
		Button.ENTER.waitForPressAndRelease();
		int lights[] = new int[300];

		for (int j = 0; j < lights.length; j++) {
			Settings.PILOT.travel(1, true);
			lights[j] = light.getNormalizedLightValue();
		}
		java.util.Arrays.sort(lights);
		int min = 1024;
		int max = -1;
		int tempSum = 0;
		for (int j = 0; j < 100; j++) {
			tempSum += lights[j];
		}
		min = tempSum / 100;
		tempSum = 0;
		for (int j = lights.length - 100; j < lights.length; j++) {
			tempSum += lights[j];
		}
		max = tempSum / 100;
		System.out.println("min: " + min + ", max: " + max);
		light.setLow(min);
		light.setHigh(max);
		System.out.println("\nPress ENTER to continue.");
		Button.ENTER.waitForPressAndRelease();
		Settings.PILOT.setTravelSpeed(speed);
		LCD.clear();
	}
}
