package edu.kit.curiosity.behaviors.tapefollow;

import edu.kit.curiosity.Settings;
import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class TapeGapFound implements Behavior {

	@Override
	public boolean takeControl() {
		return Settings.gap;
	}

	@Override
	public void action() {
		System.out.println(" GAP!");
		Button.ENTER.waitForPressAndRelease();
		//TODO
		Settings.PILOT.travel(10);
		Settings.gap = false;

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
