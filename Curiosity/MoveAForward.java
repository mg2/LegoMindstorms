package edu.kit.curiosity;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class MoveAForward implements Behavior {
	private boolean suppressed = false;

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		System.out.println("Moving.");
		suppressed = false;
	    Motor.A.backward();
		while (!suppressed) {
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
		// TODO Auto-generated method stub

	}

}
