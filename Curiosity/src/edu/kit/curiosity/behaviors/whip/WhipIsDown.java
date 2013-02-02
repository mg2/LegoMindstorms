package edu.kit.curiosity.behaviors.whip;

import edu.kit.curiosity.Settings;
import lejos.robotics.subsumption.Behavior;

public class WhipIsDown implements Behavior {

	@Override
	public boolean takeControl() {
		return (Settings.beforeWhip && Settings.LIGHT.getLightValue() > 50);
	}

	@Override
	public void action() {
		Settings.motorAAngle = 0;
		Settings.PILOT.travel(20);
		Settings.beforeWhip = false;
	}

	@Override
	public void suppress() {
	}

}
