package edu.kit.curiosity;

/**
 * This is the main class of Curiosity. It starts the robot in a given
 * {@code RobotState}.
 * 
 * @author martin
 */
public class CuriosityMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//new LightCalibrate();

		Settings.arbiMgr = new ArbitratorManager(RobotState.START);
	}

}
