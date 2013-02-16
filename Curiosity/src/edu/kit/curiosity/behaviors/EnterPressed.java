package edu.kit.curiosity.behaviors;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.RobotState;
import edu.kit.curiosity.Settings;

public class EnterPressed implements Behavior {

	@Override
	public boolean takeControl() {
		return Button.ENTER.isDown() || Button.ESCAPE.isDown() || Button.LEFT.isDown() || Button.RIGHT.isDown();
	}

	@Override
	public void action() {
		System.out.println("Waiting...");
		while (!Button.ENTER.isUp());
		Settings.arbiMgr.changeState(RobotState.START);		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
