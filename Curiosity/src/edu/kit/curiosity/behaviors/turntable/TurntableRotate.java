package edu.kit.curiosity.behaviors.turntable;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.bluetooth.TurnControl;

public class TurntableRotate implements Behavior {
	
	TurnControl turnControl = TurntableConnect.turnControl;
	UltrasonicSensor sonic = Settings.SONIC;

	private static boolean suppressed = false;

	@Override
	public boolean takeControl() {
		return Settings.parkCompleted && Settings.bluetooth;
	}

	@Override
	public void action() {
		suppressed = false;
		
		
		while (sonic.getDistance() > 20) {
			System.out.println("Rotating...");
			turnControl.turnClockwise(-10);
			Delay.msDelay(2000);
		}
		while (sonic.getDistance() < 100) {
			System.out.println("Rotating 2...");
			turnControl.turnClockwise(-20);
			Delay.msDelay(2000);
		}
		
		if (!suppressed) {
			Settings.PILOT.travel(50);
			turnControl.disconnectFromTurntable();
			Settings.bluetooth = false;
			Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.15);
		}
	}


	@Override
	public void suppress() {
		suppressed = true;
		// TODO Auto-generated method stub

	}

}
