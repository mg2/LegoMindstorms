package edu.kit.curiosity.behaviors.tape;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TapeLost implements Behavior {
	// Auf dem schwarzen Planner entsprechen 90 Grad 136.5 CodeGrad
	private boolean suppressed = false;

	DifferentialPilot pilot;
	LightSensor light;

	public TapeLost() {
		pilot = Settings.PILOT;
		light = Settings.LIGHT;
	}

	@Override
	public boolean takeControl() {
		if (light.getLightValue() < 20)
			return true;
		else
			return false;
	}

	@Override
	public void action() {
		// System.out.print(" Lost");
		suppressed = false;
		if (Settings.fromLeft) {
			Settings.angle = -20;
		} else {
			Settings.angle = 20;
		}

		while (!suppressed && light.getLightValue() < 40) { // until suppressed
															// or line found
			if (!pilot.isMoving()) {
				pilot.rotate(Settings.angle, true);
				if (Settings.angle < 0)
					Settings.angle += 5;
				Settings.angle *= -2;
			}
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
