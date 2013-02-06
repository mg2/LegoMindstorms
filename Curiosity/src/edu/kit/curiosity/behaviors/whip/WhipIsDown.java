package edu.kit.curiosity.behaviors.whip;

import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class WhipIsDown implements Behavior {

	@Override
	public boolean takeControl() {
		return (Settings.beforeWhip && !Settings.afterWhip && Settings.LIGHT.getLightValue() > Settings.light_bridge_rel);
	}

	@Override
	public void action() {
		System.out.println("Whip down");
		System.out.println("Distance: " + Settings.SONIC.getDistance());
		Settings.motorAAngle = -90;
		Settings.PILOT.travel(40);
		Settings.beforeWhip = false;
	}

	@Override
	public void suppress() {
	}

}
