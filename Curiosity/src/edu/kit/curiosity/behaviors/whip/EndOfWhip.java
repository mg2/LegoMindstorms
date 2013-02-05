package edu.kit.curiosity.behaviors.whip;

import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class EndOfWhip implements Behavior {

	@Override
	public boolean takeControl() {
		return (Settings.whipAndBridgeCounter >= 10 && !Settings.afterWhip);
	}

	@Override
	public void action() {
		Settings.PILOT.travel(20);
		Settings.PILOT.rotate(Settings.whipAndBridgeCounter * (-10));
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * Settings.tapeFollowSpeed);
		Settings.whipAndBridgeCounter = 0;
		Settings.afterWhip = true;
	}

	@Override
	public void suppress() {
	}

}
