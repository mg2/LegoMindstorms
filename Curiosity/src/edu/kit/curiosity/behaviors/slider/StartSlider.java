package edu.kit.curiosity.behaviors.slider;

import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class StartSlider implements Behavior {

	@Override
	public boolean takeControl() {
		return (Settings.atStartofSlider);
	}

	@Override
	public void action() {
		Settings.PILOT.travel(50);
		Settings.atStartofSlider = false;

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
