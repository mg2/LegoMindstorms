package edu.kit.curiosity.behaviors.whip;

import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class EndOfWhip implements Behavior {

	@Override
	public boolean takeControl() {
		return (Settings.whipAndBridgeCounter >= 10 || Settings.LIGHT
				.getLightValue() > 80) && !Settings.afterWhip;
	}

	@Override
	public void action() {
		System.out.println("End of Whip");
		// Settings.PILOT.travel(20);
		if (Settings.LIGHT.getLightValue() > 80) {
			Settings.PILOT.travel(-10);
			Settings.PILOT.rotate(-140);
		} else {
			Settings.PILOT.rotate(Settings.whipAndBridgeCounter * (-10));
		}
		Settings.readState = true;
		Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed()
				* Settings.tapeFollowSpeed);
		Settings.whipAndBridgeCounter = 0;
		Settings.afterWhip = true;
		Settings.motorAAngle = Settings.SENSOR_FRONT;
	}

	@Override
	public void suppress() {
	}

}
