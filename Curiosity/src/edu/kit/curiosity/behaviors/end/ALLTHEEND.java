package edu.kit.curiosity.behaviors.end;

import edu.kit.curiosity.Settings;
import lejos.robotics.subsumption.Behavior;

public class ALLTHEEND implements Behavior {

	private boolean suppressed = false;

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		while (!suppressed ) {
			while (!Settings.TOUCH_L.isPressed()
					|| !Settings.TOUCH_R.isPressed()) {
				Settings.PILOT.travel(20);
			}
			Settings.PILOT.rotate(136.5);
			while (!Settings.TOUCH_L.isPressed()
					|| !Settings.TOUCH_R.isPressed()) {
				Settings.PILOT.travel(20);
			}
		}
	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
