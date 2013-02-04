package tests;


import edu.kit.curiosity.Settings;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class NoWallInReach implements Behavior {

	private boolean suppressed = false;
	
	private DifferentialPilot pilot;
	private UltrasonicSensor sonic;

	/**
	 * Constructs new NoWallInReach Behavior.
	 */
	public NoWallInReach()
	{
		pilot = Settings.PILOT;
		sonic = Settings.SONIC;
	}
	
	/**
	 * This Behavior takes control if the Distance is higher than 40
	 */
	@Override
	public boolean takeControl() {
		return (sonic.getDistance() > 40);
	}

	/**
	 * Moves to the right.
	 */
	@Override
	public void action() {
		suppressed = false;
		pilot.travel(15);
		pilot.arc(-10, -100, true);
		while(pilot.isMoving() && !suppressed) {
			Thread.yield();
		}
		pilot.stop();
	}

	/**
	 * Initiates the cleanup when this Behavior is suppressed
	 */
	@Override
	public void suppress() {
		suppressed = true;
	}
}
