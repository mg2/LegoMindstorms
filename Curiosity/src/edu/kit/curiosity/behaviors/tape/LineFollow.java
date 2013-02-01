package edu.kit.curiosity.behaviors.tape;

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
		//System.out.println("Line");
		suppressed = false;
		if (Settings.angle < 0) {
			Settings.fromLeft = false;
		} else {
			Settings.fromLeft = true;
		}
		
		Settings.PILOT.forward();
		while (!suppressed) {
			Thread.yield();
		}
		Settings.PILOT.stop();

	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
