package edu.kit.curiosity.behaviors.turntable;

import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import edu.kit.curiosity.Settings;

public class TurntablePark implements Behavior {
	UltrasonicSensor sonic = Settings.SONIC;
	LightSensor light = Settings.LIGHT;
	DifferentialPilot pilot = Settings.PILOT;

	private static boolean suppressed;
	private static boolean timesUp;
	private static int mode;
	private static boolean parked = false;
	private final int blackWhiteThreshold = 20;
	private final int tr = 45;
	private final int sleep = 10;

	public TurntablePark() {
		timesUp = false;
		mode = 0;
	}

	@Override
	public boolean takeControl() {
		// TODO after bluetooth permission!
		return Settings.bluetooth && ! Settings.atStartOfTurntable && !parked;
	}

	@Override
	public void action() {
		suppressed = false;
		System.out.println("Parking...");
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
		while (!suppressed && mode == 1 && light.getLightValue() < 90) {
			pilot.steer(-100, 5, true);
			Delay.msDelay(sleep);
		}
		if (!suppressed && mode == 1) {
			mode = 2;
			pilot.rotate(-5);
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
	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
