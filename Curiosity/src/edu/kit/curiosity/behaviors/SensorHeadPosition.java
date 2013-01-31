package edu.kit.curiosity.behaviors;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class SensorHeadPosition implements Behavior {

	private boolean suppressed = false;

	@Override
	public boolean takeControl() {

		return (Motor.A.getTachoCount() > 5 || Motor.A.getTachoCount() < -5);
	}

	@Override
	public void action() {
		suppressed = false;
		Motor.A.rotateTo(0);
		while (Motor.A.isMoving() && !suppressed) {
			Thread.yield();
		}

	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
