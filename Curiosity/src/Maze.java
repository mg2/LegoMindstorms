import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Maze {

	/**
	 * @param args
	 */
	final static double WHEEL_DIAMETER = 8.5;
	final static double TRACK_WIDTH = 14.5;
	final static int SPEED = 200;
	// final double ROBOT_WIDTH = 17;
	final static DifferentialPilot ROBOT = new DifferentialPilot(WHEEL_DIAMETER, TRACK_WIDTH, Motor.A, Motor.B);
	final static UltrasonicSensor SONIC_SENSOR = new UltrasonicSensor(SensorPort.S1);
	final static TouchSensor TOUCH_SENSOR = new TouchSensor(SensorPort.S2);

	public static void main(String[] args) {
		ROBOT.setRotateSpeed(SPEED);
		while (!Button.ESCAPE.isDown()) {
			do {
				ROBOT.forward();
			} while (SONIC_SENSOR.getRange() < 8 && SONIC_SENSOR.getRange() < 15 && !TOUCH_SENSOR.isPressed());

			do {
				ROBOT.forward();
				ROBOT.arcForward(10);
			} while (SONIC_SENSOR.getRange() > 15 && !TOUCH_SENSOR.isPressed());
			
			do {
				ROBOT.arcForward(-10);
			} while (SONIC_SENSOR.getRange() < 8);

			if (SONIC_SENSOR.getRange() < 15 && TOUCH_SENSOR.isPressed()) {
				do {
					ROBOT.arcForward(-10);
				} while (TOUCH_SENSOR.isPressed() && SONIC_SENSOR.getRange() > 15);
			}
		}
	}

}
