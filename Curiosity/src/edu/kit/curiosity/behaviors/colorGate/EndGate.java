package edu.kit.curiosity.behaviors.colorGate;

import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.behaviors.bluetooth.ColorGateControl;

public class EndGate implements Behavior {

	private boolean suppressed = false;
	ColorGateControl gateControl;

	public EndGate() {
		gateControl = new ColorGateControl();
	}

	@Override
	public boolean takeControl() {
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		while (!suppressed
				&& !gateControl.connectionToColorGateSuccessful());
		if(!suppressed) {
			int i = gateControl.readColor();
			System.out.println("Color Nr. " + i);
//			
		} else {
			System.out.println("Suppressed!");
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
