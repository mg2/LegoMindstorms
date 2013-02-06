package edu.kit.curiosity.behaviors.turntable;

import edu.kit.curiosity.Settings;
import lejos.robotics.subsumption.Behavior;

public class TurntableBegin implements Behavior {

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return Settings.atStartOfTurntable && Settings.bluetooth;
	}

	@Override
	public void action() {
		Settings.PILOT.travel(40);
		Settings.atStartOfTurntable = false;

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
