package edu.kit.curiosity.behaviors.turntable;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TapeFollowForCertainTime implements Behavior {

	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;

	private boolean suppressed = false;

	/**
	 * Light threshold.
	 */
	static final int blackWhiteThreshold = 20;

	/**
	 * Thread sleep time.
	 */
	static final int sleep = 10;

	/**
	 * Number of steps before making turn steeper.
	 */
	static final int out = 100;

	/**
	 * Number of consecutive left turns.
	 */
	private static int l = 0;

	/**
	 * Number of consecutive right turns.
	 */
	private static int r = 0;

	/**
	 * Turn rate.
	 */
	private static int tr = 90;

	@Override
	public boolean takeControl() {
		return Settings.travelBack;
	}

	@Override
	public void action() {
		System.out.println("Follow line for 4 seconds");
		suppressed = false;

		boolean timesUp = false;
		long curTime = System.currentTimeMillis();

		while (!suppressed && !timesUp) {
			if (System.currentTimeMillis() - curTime > 4000) {
				timesUp = true;
				Settings.travelBack = false;
				Settings.goBack = true;
			}
			if (light.getLightValue() > blackWhiteThreshold) {
				// On white, turn right
				l = 0;
				// System.out.print("right ");
				pilot.steer(-tr, -10, true);
				r++;
			} else {
				// On black, turn left
				r = 0;
				// System.out.print("left ");
				pilot.steer(tr, 10, true);
				r = 1;
				l++;
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;

	}
}