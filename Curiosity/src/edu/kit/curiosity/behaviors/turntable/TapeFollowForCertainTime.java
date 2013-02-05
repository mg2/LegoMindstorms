package edu.kit.curiosity.behaviors.turntable;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class TapeFollowForCertainTime implements Behavior {

	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;

	private boolean suppressed = false;
	private boolean timesUp = false;
	/**
	 * Light threshold.
	 */
	static final int blackWhiteThreshold = 20;

	/**
	 * Thread sleep time.
	 */
	static final int sleep = 10;

	/**
	 * Turn rate.
	 */
	private static int tr = 45; // TODO not tested, previous was 90

	@Override
	public boolean takeControl() {
		return Settings.travelBack;
	}

	@Override
	public void action() {
		System.out.println("Follow line for 4 seconds");
		suppressed = false;
		
		long curTime = System.currentTimeMillis();

		while (!suppressed && !timesUp) {
			if (System.currentTimeMillis() - curTime > 4000) {
				timesUp = true;
				Settings.travelBack = false;
				Settings.goBack = true;
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