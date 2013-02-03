package edu.kit.curiosity.behaviors.colorGate;

import edu.kit.curiosity.Settings;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class ColorFound implements Behavior {

	private DifferentialPilot pilot = Settings.PILOT;
	private boolean suppressed;
	
	@Override
	public boolean takeControl() {
		return Settings.colorFound;
	}

	@Override
	public void action() {
		suppressed = false;
		
		pilot.travel(10);
		pilot.rotate(-135);
		pilot.forward();
		while (pilot.isMoving() && !suppressed) {
			Thread.yield();
		}

	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
