package edu.kit.curiosity.behaviors.tape;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class GapFound implements Behavior {

	private boolean suppressed = false;
	DifferentialPilot dp;
	LightSensor ls;
	
	public GapFound() {
		dp = Settings.PILOT;
		ls = Settings.LIGHT;
	}
	
	@Override
	public boolean takeControl() {
		if (Math.abs(Settings.angle) == 999) return true;
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		//System.out.print(" GAP?");
		Settings.angle = 15;
		dp.travel(15, true);
	}

	@Override
	public void suppress() {
		dp.stop(); //was passiert, wenn rammt und steht queer? :(
		suppressed = true;
	}

}
