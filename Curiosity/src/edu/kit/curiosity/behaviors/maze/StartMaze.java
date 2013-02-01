package edu.kit.curiosity.behaviors.maze;

import edu.kit.curiosity.Settings;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class StartMaze implements Behavior {

	private boolean suppressed;
	
	private DifferentialPilot pilot;
	
	public StartMaze() {
		pilot = Settings.PILOT;
	}

	@Override
	public boolean takeControl() {

		return Settings.AT_START_OF_MAZE;
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.arcForward(-30);
		while(pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
