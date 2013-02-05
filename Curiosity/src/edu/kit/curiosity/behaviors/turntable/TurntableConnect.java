package edu.kit.curiosity.behaviors.turntable;

import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.bluetooth.TurnControl;
import lejos.robotics.subsumption.Behavior;

public class TurntableConnect implements Behavior {
	
	TurnControl turnControl = new TurnControl();
	
	private static boolean suppressed = false; 

	@Override
	public boolean takeControl() {
		return !Settings.bluetooth && !Settings.parkCompleted;
	}

	@Override
	public void action() {
		suppressed = false;
		System.out.println("Connecting...");

		while (!turnControl.connectionToTurntableSuccessful() && !suppressed);
		if (!suppressed) {
			System.out.println("Connected!");
			Settings.bluetooth = true;
			Settings.PILOT.rotate(-15);			
			Settings.PILOT.travel(10);		
		}		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
