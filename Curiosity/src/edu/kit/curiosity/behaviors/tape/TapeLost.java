package edu.kit.curiosity.behaviors.tape;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TapeLost implements Behavior {
	//Auf dem schwarzen Planner entsprechen 90 Grad 136.5 CodeGrad
	private boolean suppressed = false;
	private int m = 1;
	
	DifferentialPilot dp;
	LightSensor ls;
	
	public TapeLost() {
		dp = Settings.PILOT;
		ls = Settings.LIGHT;
	}

	@Override
	public boolean takeControl() {
		if (ls.getLightValue() < 20) return true;
		else return false;
	}

	@Override
	public void action() {
		System.out.print(" Lost");
		suppressed = false;
		Settings.angle = 15;
		while (!suppressed && ls.getLightValue() < 40) {
			if (!dp.isMoving()) {
				dp.rotate(Settings.angle, true);
				System.out.print(" " + Settings.angle);
				Settings.angle *= -2;
			}			
		}
		/*
		 * 5-er Inkrement
		while (!suppressed && ls.getLightValue() < 20) {
			Settings.angle += 5 * m;
			Settings.angle *= (-1);
			m = m * (-1);
			dp.rotate(Settings.angle);
			//dp.travel(0.35 + Math.abs(Settings.angle) * 0.05);
			System.out.print(" " + Settings.angle);
		}
		if (!suppressed) {
			System.out.println("Suppressed");
			dp.rotate(7.5*m, true);
		}
		*/
	}

	@Override
	public void suppress() {
		suppressed = true;
		dp.stop();
	}

}
