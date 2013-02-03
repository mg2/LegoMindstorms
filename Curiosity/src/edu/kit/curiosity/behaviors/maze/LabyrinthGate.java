package edu.kit.curiosity.behaviors.maze;

import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.behaviors.bluetooth.GateCommon;
import edu.kit.curiosity.behaviors.bluetooth.GateControl;
import edu.kit.curiosity.behaviors.bluetooth.TurnControl;

public class LabyrinthGate implements Behavior {

	private boolean suppressed = false;
	GateControl gateControl;

	public LabyrinthGate() {
		gateControl = new GateControl();
	}

	@Override
	public boolean takeControl() {
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		while (!suppressed
				&& !gateControl.connectionToGateSuccessful(GateCommon.GATE_1));
		if(!suppressed) {
			System.out.println("Connected to:"); 
			System.out.println("	Labyrinth Gate");
			if(gateControl.openGate()) {			
				System.out.println("Opened Gate!");
				if(gateControl.disconnectFromGate()) {
					System.out.println("Disconnected...");
				}
			}
		} else {
			System.out.println("Suppressed!");
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
