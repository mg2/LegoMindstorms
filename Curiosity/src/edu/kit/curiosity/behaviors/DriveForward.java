package edu.kit.curiosity.behaviors;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class DriveForward implements Behavior {
	private DifferentialPilot pilot;
	private boolean suppressed = false;

	public boolean takeControl() {
		return true;
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		suppressed = false;
		Motor.A.forward();
		Motor.C.forward();
		while (!suppressed)
			Thread.yield();
		Motor.A.stop(); // clean up
		Motor.C.stop();
	}
}