package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class DriveForward implements Behavior {
	private DifferentialPilot pilot;
	private boolean suppressed = false;
	
	public DriveForward()
	{
		this.pilot = Settings.pilot;
	}

	public boolean takeControl() {
		return true;
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		suppressed = false;
		pilot.forward();
		while (!suppressed)
			Thread.yield();
		pilot.stop();
	}
}