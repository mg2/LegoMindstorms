package edu.kit.curiosity;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

/**
 * This is the main class of Curiosity. It starts the robot in a given
 * {@code RobotState}.
 * 
 * @author martin
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
		new LightCalibrate();
		new CuriosityMain();

		Settings.arbiMgr = new ArbitratorManager(RobotState.START);
	}

	@Override
	public void buttonPressed(Button b) {

	}

	@Override
	public void buttonReleased(Button b) {
//		if (!Settings.relocate) {
//			Settings.relocate = true;
//			Settings.arbiMgr.changeState(RobotState.START);
//		} else if (Settings.relocate) {
//			Settings.arbiMgr.changeState(RobotState.READCODE);
//		}

	}

}
