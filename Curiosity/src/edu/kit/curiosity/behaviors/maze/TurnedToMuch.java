package edu.kit.curiosity.behaviors.maze;

import edu.kit.curiosity.Settings;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class TurnedToMuch implements Behavior {

	private boolean suppressed = false;
	private DifferentialPilot pilot;
	
	public TurnedToMuch() {
		pilot = Settings.PILOT;
	}

	@Override
	public boolean takeControl() {
		return Settings.numberOfTurns > 5;
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.travel(100, true);
		Settings.numberOfTurns = 0;
		while(pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
