package edu.kit.curiosity.behaviors.slider;

import edu.kit.curiosity.Settings;
import lejos.robotics.subsumption.Behavior;

public class DetectTape implements Behavior {

	@Override
	public boolean takeControl() {
		return Settings.LIGHT.getLightValue() > 80 && !Settings.atStartofSlider;
	}

	@Override
	public void action() {
		System.out.println("LINE Detected");
		Settings.afterSlider = true;
		Settings.motorAAngle = Settings.SENSOR_FRONT;
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.15);
	}

	@Override
	public void suppress() {
	}

}
