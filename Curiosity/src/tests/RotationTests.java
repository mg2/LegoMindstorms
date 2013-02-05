package tests;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import edu.kit.curiosity.Settings;

public class RotationTests {
	static UltrasonicSensor sonic = Settings.SONIC;
	static LightSensor light = Settings.LIGHT;
	static DifferentialPilot pilot = Settings.PILOT;

	public RotationTests() {
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed());
	}

	public static void main(String[] args) {
		Button.waitForAnyPress();
		pilot.steer(100, 90);
		System.out.println(pilot.getMovementIncrement());
		Button.waitForAnyPress();

	}

}
