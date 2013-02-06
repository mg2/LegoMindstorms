package edu.kit.curiosity;

import lejos.robotics.subsumption.Behavior;

public class StopArbitrator implements Behavior {
	boolean changingMode = true;

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return changingMode;
	}

	@Override
	public void action() {
		changingMode = false;
		Settings.arbiMgr.changeState(RobotState.READCODE);
	}

	@Override
	public void suppress() {

	}

}
