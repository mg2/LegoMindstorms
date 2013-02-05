package edu.kit.curiosity.behaviors.slider;

import edu.kit.curiosity.RobotState;
import edu.kit.curiosity.Settings;
import lejos.robotics.subsumption.Behavior;

public class LineFound implements Behavior {

	@Override
	public boolean takeControl() {
		return Settings.LIGHT.getLightValue() > 80;
	}

	@Override
	public void action() {
		Settings.PILOT.quickStop();
		System.out.println("Line Found");
		Settings.arbiMgr.changeState(RobotState.LINE_OBSTACLE);

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
