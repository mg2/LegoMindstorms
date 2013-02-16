package edu.kit.curiosity.behaviors.tapefollow;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import edu.kit.curiosity.Settings;

public class TapeFollow implements Behavior {

	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;

	private boolean suppressed = false;

	/**
	 * Light threshold.
	 */
	static final int blackWhiteThreshold = 50;

	/**
	 * Thread sleep time.
	 */
	static final int sleep = 10;

	/**
	 * Number of steps before making turn steeper.
	 */
	static final int out = 150;

	/**
	 * Number of steps.
	 */
	private static int i = 0;

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
		return true;
	}

	@Override
	public void action() {

		suppressed = false;

		while (!suppressed) {
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
			// between out and 2 * out
			if (i > out && i <= 2 * out) {
				// last out turns were in same direction
				if (r > out || l > out) {
					// make turn steeper
					tr = 120;
				}
			} else if (i > 2 * out) { // more than 2 * out
				if (r > 2 * out || l > 2 * out) {

					// travel back until line found
					// TODO
					pilot.rotate(180);
					pilot.travel(25);
				}
				// Set values to defaults.
				i = 0;
				l = 0;
				r = 0;
				tr = 90;
			}
			i++;
			Delay.msDelay(sleep);
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;

	}
}