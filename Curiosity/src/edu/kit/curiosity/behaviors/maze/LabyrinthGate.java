package edu.kit.curiosity.behaviors.maze;

import lejos.nxt.Button;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import edu.kit.curiosity.Settings;
import edu.kit.curiosity.behaviors.bluetooth.GateControl;

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
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
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
