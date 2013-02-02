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
	 * Calibrates the light values.
	 * Sets black as low and line as high.
	 */
	public LightCalibrate() {
		System.out.println("Calibrating light.");
		
		//Calibrate BLACK
		System.out.print("Black: ");
		Button.ENTER.waitForPressAndRelease();
		light.calibrateLow();
		Settings.light_black = light.getLow();
		System.out.println(Settings.LIGHT.getLow());
		
		//Calibrate BRIDGE
		System.out.println("Bridge: ");
		Button.ENTER.waitForPressAndRelease();
		Settings.light_bridge = light.getNormalizedLightValue();
		System.out.println(Settings.light_bridge);
		
		//Calibrate LINE
		System.out.println("Line: ");
		Button.ENTER.waitForPressAndRelease();
		light.calibrateHigh();
		Settings.light_line = light.getHigh();
		System.out.println(Settings.LIGHT.getHigh());
		
		System.out.println("Press ENTER to continue.");
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
	}
}
