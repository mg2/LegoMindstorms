package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.PilotProps;

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
		suppressed = false;
		int angle = 15 * m;
		//int m = 1; // 1: Kante wird links vom Sensor sein, -1: Kante wird rechts
		dp.rotate(angle);
		while (!suppressed && ls.getLightValue() < 20) {
			angle = angle + 5 * m;
			angle = angle * (-1);
			m = m * (-1);
			dp.rotate(angle);
			dp.travel(0.35);
		}
		dp.rotate(7.5*m);
		m = m * (-1);
	}

	@Override
	public void suppress() {
		suppressed = true;
		dp.stop();
	}

}
