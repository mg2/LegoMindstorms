package edu.kit.curiosity;

/**
 * This is the main class of Curiosity. It starts the robot in a given
 * {@code RobotState}.
 * 
 * @author Curiosity
 */
public class CuriosityMain {

	public CuriosityMain() {
		//Button.ENTER.addButtonListener(this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SensorHeadCalibrate();
		//new LightCalibrate(false, false);
		new CuriosityMain();

		Settings.arbiMgr = new ArbitratorManager(RobotState.START);
	}

}
