package edu.kit.curiosity.sensortests;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.SensorHeadCalibrate;
import edu.kit.curiosity.Settings;

public class RelativeLight {

	public RelativeLight() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LightSensor light = Settings.LIGHT;
		new SensorHeadCalibrate();
		new LightCalibrate(false, false); // initial calibrate

		while (true) {

			int sum = 0;
			for (int j = 0; j < 200; j++) {
				Settings.PILOT.travel(1, true);
				sum += light.getLightValue();
			}
			System.out.println(sum / 200);
			Button.waitForAnyPress();
		}
	}

}
