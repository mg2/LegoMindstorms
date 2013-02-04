package edu.kit.curiosity.behaviors.whip;

import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class EndOfWhip implements Behavior {

	@Override
	public boolean takeControl() {
		return (Settings.whipAndBridgeCounter >= 10);
	}

	@Override
	public void action() {
		Settings.PILOT.travel(20);
		Settings.PILOT.rotate(Settings.whipAndBridgeCounter * (-10));
		Settings.whipAndBridgeCounter = 0;
		System.exit(0); // TODO WEG!!!
		//TODO danach???
	}

	@Override
	public void suppress() {
	}

}
