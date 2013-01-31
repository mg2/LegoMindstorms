package edu.kit.curiosity.behaviors.tape;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class GapFound implements Behavior {

	private static boolean suppressed = false;
	DifferentialPilot dp;
	LightSensor ls;
	
	public GapFound() {
		dp = Settings.PILOT;
		ls = Settings.LIGHT;
	}
	
	@Override
	public boolean takeControl() {
		if (Math.abs(Settings.angle) > 50) return true;
		return false;
	}

	@Override
	public void action() {
		System.out.println("GAP?");
		suppressed = false;
		dp.stop();
		dp.rotate(Settings.angle);
		dp.travel(20);
		Settings.angle = 15;
	}

	@Override
	public void suppress() {
		dp.stop(); //WAS PASSIERT WENN UTERBROCHEN. SOLL SETTINGS.ANGLE ZURUCK ZU 15???
		suppressed = true;
	}

}
