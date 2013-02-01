package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.Settings;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class GoVerticalToLine implements Behavior {

	private boolean suppressed = false;

	private DifferentialPilot pilot;
	private LightSensor light;

	int settingsSilverLightNorm = 350;

	public GoVerticalToLine() {
		pilot = Settings.PILOT;
		light = Settings.LIGHT;
	}

	@Override
	public boolean takeControl() {
		// TODO if it hits a vertical line (code?)
		// until finished high(est) priority
		return true;
	}

	@Override
	public void action() {
		pilot.setRotateSpeed(Settings.ROTATION_SPEED / 4);
		pilot.setTravelSpeed(Settings.DRIVE_SPEED / 10);
		while (!suppressed
				&& light.getNormalizedLightValue() < settingsSilverLightNorm) {
			pilot.backward();
		}
		while (!suppressed
				&& light.getNormalizedLightValue() < settingsSilverLightNorm) {
			pilot.rotate(10, true);
		}
		int i = 0;
		while (!suppressed && light.getNormalizedLightValue() > settingsSilverLightNorm) {
			pilot.rotate(10, true);
			i++;
		}
		pilot.rotate(-10*i/2);
		ReadCodes.READ_STATE = true;
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
