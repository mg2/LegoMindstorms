package edu.kit.curiosity.behaviors.slider;

import edu.kit.curiosity.Settings;
import lejos.robotics.subsumption.Behavior;

public class DetectTape implements Behavior {

	@Override
	public boolean takeControl() {
		return Settings.LIGHT.getLightValue() > 90 && !Settings.atStartofSlider && !Settings.afterSlider;
	}

	@Override
	public void action() {
		Settings.afterSlider = true;
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.15);
	}

	@Override
	public void suppress() {
	}

}
