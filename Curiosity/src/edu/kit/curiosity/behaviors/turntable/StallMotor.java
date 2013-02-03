package edu.kit.curiosity.behaviors.turntable;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class StallMotor implements Behavior {

	@Override
	public boolean takeControl() {
		Motor.B.setStallThreshold(20, 100);
		Motor.C.setStallThreshold(20, 100);

		return Motor.B.isStalled() && Motor.C.isStalled();
	}

	@Override
	public void action() {
		System.out.println("STOP!");
		Settings.PILOT.stop();
	}

	@Override
	public void suppress() {
	}

}
