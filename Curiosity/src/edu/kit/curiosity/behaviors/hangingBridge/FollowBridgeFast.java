package edu.kit.curiosity.behaviors.hangingBridge;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class FollowBridgeFast implements Behavior {
	
	private boolean suppressed = false;
	
	static DifferentialPilot pilot = Settings.PILOT;
	static LightSensor light = Settings.LIGHT;
	
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
	private static int tr = 45;
	
	private int timer = 2000;

	@Override
	public boolean takeControl() {
		return !Settings.endOfHangBridge;
	}

	@Override
	public void action() {
		suppressed = false;
		
		long curTime = System.currentTimeMillis();
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.40);
		pilot.setRotateSpeed(pilot.getRotateMaxSpeed());

		/**
		 * Follow line with 40% of his speed.
		 * Simple line follow without sharper turns.
		 * If wall nearer than 1m for 2 seconds - switch speed to 15% and go in complex mode.
		 * Wall being far than 1m or touch sensors triggered (opponent hit) resets the timer.
		 */
		while (!suppressed && !Settings.endOfHangBridge) {
			if (System.currentTimeMillis() - curTime > timer) {
				Settings.endOfHangBridge = true;
			}
			if (Settings.SONIC.getDistance() > 100 || Settings.TOUCH_L.isPressed() || Settings.TOUCH_R.isPressed()) {
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
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!suppressed) {
			pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.15);
		}
	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
