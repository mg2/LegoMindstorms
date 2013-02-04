package edu.kit.curiosity.behaviors;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

/**
 * This class describes the Behavior when Motor A is stalled.
 * @author Team Curiosity
 *
 */
public class MotorAStall implements Behavior {
	private boolean suppressed = false;
	
	/**
	 * Constructs a new DriveForward Behavior
	 */
	public MotorAStall() {
	}

	/**
	 * Takes always Control.
	 * returns true
	 */
	public boolean takeControl() {
		return (Motor.A.isStalled());
	}

	/**
	 * Initiates the cleanup when this Behavior is suppressed
	 */
	public void suppress() {
		suppressed = true;
	}

	/**
	 * Moves forward as long as this Behavior is active
	 */
	public void action() {
		System.out.println("Stall.");
		suppressed = false;
		Motor.A.flt();
		//TODO direction of travel
		Settings.PILOT.travel(-10 * Math.signum(Settings.PILOT.getTravelSpeed()));
		Motor.A.rotateTo(Settings.motorAAngle, true);
		Motor.A.flt();
		while (!suppressed && Motor.A.isStalled()) {
			Thread.yield();
		}
		
		Motor.A.stop();
		Motor.A.flt();
	}
}