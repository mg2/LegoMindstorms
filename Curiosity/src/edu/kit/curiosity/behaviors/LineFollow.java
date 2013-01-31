package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.robotics.subsumption.Behavior;

public class LineFollow implements Behavior {
	//Auf dem schwarzen Planner entsprechen 90 Grad 136.5 CodeGrad
	
	private boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		Settings.PILOT.forward();
		while (!suppressed) {
			Thread.yield();
		}

	}

	@Override
	public void suppress() {
		suppressed = true;
		Settings.PILOT.stop();
	}

}
