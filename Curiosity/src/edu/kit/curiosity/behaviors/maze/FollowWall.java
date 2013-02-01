package edu.kit.curiosity.behaviors.maze;

import edu.kit.curiosity.Settings;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class FollowWall implements Behavior {
	
	private boolean suppressed = false;
	
	private UltrasonicSensor sonic;
	private DifferentialPilot pilot;
	
	private int distanceToWall;
	
	public FollowWall(int distance) {
		distanceToWall = distance;
		pilot = Settings.PILOT;
		sonic = Settings.SONIC;
	}

	@Override
	public boolean takeControl() {
		
		return (!Settings.inSwamp);
	}

	@Override
	public void action() {
		suppressed = false;
		System.out.println("FOLLOOW WALLLLL");
		while(!suppressed) {
			if(sonic.getDistance() > (distanceToWall + 10)) {
				pilot.arc(-15, -90, true);
			} else if(sonic.getDistance() < distanceToWall) {
				pilot.arc(70, 20, true);
			} else if(sonic.getDistance() >= distanceToWall) {
				pilot.arc(-70, -20, true);
			}
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
