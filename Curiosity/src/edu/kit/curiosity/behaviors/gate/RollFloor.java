package edu.kit.curiosity.behaviors.gate;

import edu.kit.curiosity.Settings;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class RollFloor implements Behavior {

	private boolean suppressed = false;

	private DifferentialPilot pilot;
	
	public RollFloor() {
		pilot = Settings.PILOT;
	}
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed());
		while (!suppressed) {
			if (Settings.TOUCH_R.isPressed()) {
				pilot.rotate(90, true);
			} else if (Settings.TOUCH_L.isPressed()) {
				pilot.rotate(-90, true);
			} else {
				pilot.forward();
			}
		}
		pilot.stop();

	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
