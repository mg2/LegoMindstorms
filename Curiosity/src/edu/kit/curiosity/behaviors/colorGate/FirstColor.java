package edu.kit.curiosity.behaviors.colorGate;

import edu.kit.curiosity.Settings;
import lejos.robotics.subsumption.Behavior;

public class FirstColor implements Behavior {

	private boolean suppressed;

	@Override
	public boolean takeControl() {
		return Math.abs(Settings.LIGHT.getNormalizedLightValue() - Settings.color1) < 20;
	}

	@Override
	public void action() {
		suppressed = false;
		Settings.onColors = true;
		if (Math.abs(Settings.LIGHT.getNormalizedLightValue() - Settings.searchedColor) < 20) {
			Settings.colorFound = true;
		}
		Settings.motorAAngle = 0;
		
		

	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
