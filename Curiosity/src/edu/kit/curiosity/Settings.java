package edu.kit.curiosity;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * This class is responsible for all the settings needed to run the robot.
 * 
 * @author Curiosity
 */
public class Settings {

	/**
	 * General settings for the robot.
	 */
	public final static double DRIVE_SPEED = 40;
	public final static double ROTATION_SPEED = 40;
	public final static double TRACK_WIDTH = 14.5;
	public final static double WHEEL_WIDTH = 8;

	public final static DifferentialPilot PILOT = new DifferentialPilot(WHEEL_WIDTH, TRACK_WIDTH, Motor.C, Motor.B);

	/**
	 * Defining ports for sensors.
	 */
	public final static SensorPort SONIC_SENSOR_PORT = SensorPort.S1;
	public final static SensorPort LIGHT_SENSOR_PORT = SensorPort.S2;
	public final static SensorPort TOUCH_R_SENSOR_PORT = SensorPort.S3;
	public final static SensorPort TOUCH_L_SENSOR_PORT = SensorPort.S4;

	/**
	 * Initialization of sensors.
	 */
	public static final UltrasonicSensor SONIC = new UltrasonicSensor(SONIC_SENSOR_PORT);
	public static final LightSensor LIGHT = new LightSensor(LIGHT_SENSOR_PORT);
	public static final TouchSensor TOUCH_R = new TouchSensor(TOUCH_R_SENSOR_PORT);
	public static final TouchSensor TOUCH_L = new TouchSensor(TOUCH_L_SENSOR_PORT);

	/**
	 * Different angle settings.
	 */
	public static int angle = 15;
	public static int motorAAngle = 0;
	
	public static boolean atStartOfMaze = true;
	public static boolean endOfMaze = false;
	
	public static boolean inSwamp = false;
	public static boolean afterSwamp = false;

	/**
	 * Different light settings.
	 */
	public static int swampLight = 340;
	public static int blackLight = 300;


	/**
	 * Miscellaneous.
	 */
	public static int numberOfTurns = 0;
	public static boolean atStartOfMaze = true;
	public static boolean inSwamp = false;
	public static boolean afterSwamp = false;
}
