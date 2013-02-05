package edu.kit.curiosity.behaviors.slider;

import edu.kit.curiosity.Settings;
import lejos.robotics.subsumption.Behavior;

public class StartSlider implements Behavior {

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return (Settings.atStartofSlider);
	}

	@Override
	public void action() {
		Settings.PILOT.travel(50);
		Settings.atStartofSlider =false;

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
