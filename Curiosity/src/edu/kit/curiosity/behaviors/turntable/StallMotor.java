package edu.kit.curiosity.behaviors.turntable;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class StallMotor implements Behavior {
	private int DELAY = 100;
	private long lastTime = System.currentTimeMillis();

	// make sure he doesn't stall at the beginning
	private int tachoCountA = -1;
	private int tachoCountB = -1;

	@Override
	public boolean takeControl() {

		long timePassed = System.currentTimeMillis() - lastTime;

		if (timePassed > DELAY) {
			int currentCountA = Motor.B.getTachoCount();
			int currentCountB = Motor.C.getTachoCount();

			if (currentCountA == tachoCountA || currentCountB == tachoCountB) {
				Settings.motorStalled = true;
				System.out.println("stalled");
			} else {
				System.out.println("not stalled");
			}

			tachoCountA = currentCountA;
			tachoCountB = currentCountB;
			lastTime = System.currentTimeMillis();
		}
		return Settings.motorStalled && Settings.goBack;
	}

	@Override
	public void action() {
		System.out.println("STOP!");
		Settings.PILOT.stop();
	}

	@Override
	public void suppress() {
	}
	
	/**
	 * Different approach with variation of the threshold.
	 */
//	@Override
//	public boolean takeControl() {
//		Motor.B.setStallThreshold(20, 100);
//		Motor.C.setStallThreshold(20, 100);
//
//		return Motor.B.isStalled() && Motor.C.isStalled();
//	}
//
//	@Override
//	public void action() {
//		System.out.println("STOP!");
//		Settings.PILOT.stop();
//	}
//
//	@Override
//	public void suppress() {
//	}

}
