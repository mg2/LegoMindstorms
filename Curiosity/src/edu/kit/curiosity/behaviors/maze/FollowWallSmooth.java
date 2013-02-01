package edu.kit.curiosity.behaviors.maze;

import edu.kit.curiosity.Settings;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class FollowWallSmooth implements Behavior {
	
	private boolean suppressed = false;
	
	private UltrasonicSensor sonic;
	private DifferentialPilot pilot;
	
	private int distanceToWall;
	
	public FollowWallSmooth(int distance) {
		distanceToWall = distance;
		pilot = Settings.PILOT;
		sonic = Settings.SONIC;
	}

	@Override
	public boolean takeControl() {
		// TODO STATE_LABYRINTH
		
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		while(pilot.isMoving() && !suppressed) {
			if(sonic.getDistance() > (distanceToWall + 10)) {
				pilot.arc(20, 90, true);
			} else if(sonic.getDistance() < distanceToWall) {
				pilot.arc(50, 20, true);
			} else if(sonic.getDistance() >= distanceToWall) {
				pilot.arc(-50, -20, true);
			}
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = false;
	}

}
