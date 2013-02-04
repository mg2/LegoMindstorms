package edu.kit.curiosity.behaviors.turntable;

import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class TurnAround implements Behavior {
	private boolean suppressed;

	@Override
	public boolean takeControl() {
		return Settings.SONIC.getDistance() < 36 || Settings.goBack;
	}

	@Override
	public void action() {
		suppressed = false;

		while (!suppressed) {
			if (!Settings.goBack) {
				System.out.println("Turn around");
				Settings.PILOT.setRotateSpeed(Settings.PILOT.getRotateMaxSpeed() * 0.1);
				while (Settings.LIGHT.getNormalizedLightValue() < 350) {
					Settings.PILOT.rotate(-20, true);
					Settings.travelBack = true;
				}
			} else if (Settings.goBack) {
				System.out.println("Go back, stupid!");
				Settings.PILOT.backward();
			}
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
