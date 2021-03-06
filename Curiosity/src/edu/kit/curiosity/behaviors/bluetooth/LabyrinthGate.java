package edu.kit.curiosity.behaviors.bluetooth;

import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;

public class LabyrinthGate implements Behavior {

	private boolean suppressed = false;
	private GateControl gateControl;
	
	private DifferentialPilot pilot = Settings.PILOT;
	private TouchSensor touchL = Settings.TOUCH_L;
	private TouchSensor touchR = Settings.TOUCH_R;

	public LabyrinthGate() {
		gateControl = new GateControl();
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		Settings.readState = true;
		while (!suppressed
				&& !gateControl.connectionToGateSuccessful());
		if(!suppressed) {
			if(gateControl.openGate()) {

				while(!suppressed) {
					if(touchL.isPressed()) {
						pilot.steer(50, -10);
					} else if(touchR.isPressed()) {
						pilot.steer(-50, 10);
					}
					pilot.steer(0, 30, true);
				}
				gateControl.disconnectFromGate();
			}
		} else {
//			System.out.println("-!- Suppressed -!-");
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
