package edu.kit.curiosity.behaviors.bridge;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import edu.kit.curiosity.ArbitratorManager;
import edu.kit.curiosity.LightCalibrate;
import edu.kit.curiosity.RobotState;
import edu.kit.curiosity.Settings;

public class BridgeMain implements ButtonListener {
	public BridgeMain() {
		Button.ESCAPE.addButtonListener(this);
	}

	public static void main(String[] args) throws Exception {
		new BridgeMain();

		new LightCalibrate(true, true);
		Settings.arbiMgr = new ArbitratorManager(RobotState.BRIDGE);
	}

	@Override
	public void buttonPressed(Button b) {
		// TODO Auto-generated method stub
		// stopRunning();
	}

	@Override
	public void buttonReleased(Button b) {
		// TODO Auto-generated method stub
		stopRunning();
	}

	private void stopRunning() {
		// Stop the arbitrator, the main program and the motors.
		System.exit(0);
		// arbitrator.stop();
		// resetMotors();
	}
}
