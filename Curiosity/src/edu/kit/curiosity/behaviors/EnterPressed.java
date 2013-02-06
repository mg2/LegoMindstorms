package edu.kit.curiosity.behaviors;

import edu.kit.curiosity.RobotState;
import edu.kit.curiosity.Settings;
import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class EnterPressed implements Behavior {

	@Override
	public boolean takeControl() {
		return Button.ENTER.isDown() || Button.ESCAPE.isDown() || Button.LEFT.isDown() || Button.RIGHT.isPressed();
	}

	@Override
	public void action() {
		System.out.println("Waiting...");
		Button.waitForAnyPress();
		Settings.arbiMgr.changeState(RobotState.READCODE);		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
