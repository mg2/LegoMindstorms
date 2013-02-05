package edu.kit.curiosity.behaviors.turntable;

import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.bluetooth.TurnControl;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

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
			turnControl.turnClockwise(-20);
			Delay.msDelay(2000);
		}
		while (sonic.getDistance() < 100) {
			System.out.println("Rotating 2...");
			turnControl.turnClockwise(-20);
			Delay.msDelay(2000);
		}
		Settings.PILOT.travel(20);
		while (Settings.LIGHT.getLightValue() < 90 && !suppressed) {
			System.out.println("Abmarsch!");
			Settings.PILOT.steer(45, 10, true);
			Delay.msDelay(10);
		}
		
		System.out.println("Rotating...");
		Delay.msDelay(2000);
		turnControl.turnClockwise(180);

//		while (sonic.getDistance() > 10 && !suppressed);
//		while (sonic.getDistance() < 100 && !suppressed);
		if (!suppressed) {
			Settings.PILOT.travel(50);
			turnControl.disconnectFromTurntable();
			Settings.bluetooth = false;
		}
	}


	@Override
	public void suppress() {
		suppressed = true;
		// TODO Auto-generated method stub

	}

}
