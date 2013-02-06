package edu.kit.curiosity;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

/**
 * This is the main class of Curiosity. It starts the robot in a given
 * {@code RobotState}.
 * 
 * @author Curiosity
 */
public class CuriosityMain implements ButtonListener {

	public CuriosityMain() {
		Button.ENTER.addButtonListener(this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SensorHeadCalibrate();
//		new LightCalibrate(false, false);
		Settings.LIGHT.setHigh(Settings.light_line);
		Settings.LIGHT.setLow(Settings.light_black);
		new CuriosityMain();

		Settings.arbiMgr = new ArbitratorManager(RobotState.START);
	}

	@Override
	public void buttonPressed(Button b) {

	}

	@Override
	public void buttonReleased(Button b) {
//		System.out.println("started: " + Settings.raceStarted);
//		System.out.println("running: " + Settings.isRunning);
		//Settings.arbiMgr.stopArbitrator();
		if (Settings.raceStarted) {
			if (Settings.isRunning) {
//				Settings.arbiMgr.changeState(RobotState.RELOCATE);
				//Settings.isRunning = false;
				//Settings.PILOT.stop();
				//Button.ENTER.waitForPressAndRelease();
				//Settings.arbiMgr = new ArbitratorManager(RobotState.READCODE);
			}
		}
	}

}
