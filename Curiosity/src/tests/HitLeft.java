package tests;


import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class HitLeft implements Behavior {

	private boolean suppressed = false;
	
	private TouchSensor touch_r;
	private TouchSensor touch_l;
	private DifferentialPilot pilot;

	private UltrasonicSensor sonic;
	
	
	/**
	 * Constructs a new HitWall Behavior
	 */
	public HitLeft() {
		touch_r = Settings.TOUCH_R;
		touch_l = Settings.TOUCH_L;
		pilot = Settings.PILOT;
		sonic = Settings.SONIC;
	}
	@Override
	public boolean takeControl() {
		return (touch_l.isPressed() && !touch_r.isPressed() && sonic.getDistance() < 40);
	}

	@Override
	public void action() {
		suppressed = false;
		Settings.atStartOfMaze = false;
		pilot.travel(-7);
		pilot.rotate(100);
		while( pilot.isMoving() && !suppressed ) {
			Thread.yield();
		}
		pilot.stop();

	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
