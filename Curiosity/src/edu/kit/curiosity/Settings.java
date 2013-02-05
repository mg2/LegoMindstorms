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
	 * ArbitratorManager to change arbitrators depending on the current level.
	 */
	public static ArbitratorManager arbiMgr;
	public final static RobotState FIRST_LEVEL = RobotState.RACE;
	public static boolean relocate = false;

	/**
	 * General settings for the robot.
	 */
	public final static double DRIVE_SPEED = 40;
	public final static double ROTATION_SPEED = 40;
	public final static double TRACK_WIDTH = 14.5;
	public final static double WHEEL_WIDTH = 8;

	public static DifferentialPilot PILOT = new DifferentialPilot(WHEEL_WIDTH, TRACK_WIDTH, Motor.C, Motor.B);

	/**
	 * Settings for the position of the sensor arm. '0' is front, '-90' is right
	 * side.
	 */
	public static final int SENSOR_FRONT = 0;
	public static final int SENSOR_RIGHT = -90;

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
	public static int motorAAngle = 999;

	/**
	 * Miscellaneous.
	 */
	public static boolean atStartOfMaze = true;
	public static boolean endOfMaze = false;
	public static boolean inSwamp = false;
	public static boolean afterSwamp = false;
	public static boolean fromLeft;
	public static boolean inFirstRow = true;
	public static boolean atStart = true;
	public static boolean onColors = false;;
	public static boolean colorFound = false;
	public static boolean reachedBridge = false;
	public static boolean motorStalled = false;

	/**
	 * boolean if in obstacle found mode
	 */
	public static boolean obstacle = false;
	public static boolean gap = false;
	public static boolean beforeWhip = true;

	/**
	 * Saved light sensor settings with sample settings
	 */
	public static int light_black = 300;
	public static int light_bridge = 434;
	public static int light_line = 500;
	public static int light_yellow = 480;
	public static int light_red = 460;
	public static int light_green = 415;
	public static int searchedColor;
	public static int color1;
	
	/**
	 * Relative light percents.
	 */
	public final int light_bridge_rel = 66;
	public final int light_yellow_rel = 90;
	public final int light_red_rel = 80;
	public final int light_green_rel = 57;
	

	public static int whipAndBridgeCounter = 0;
	public static boolean travelBack = false;
	public static boolean goBack = false;
	public static boolean buttonPressed = false;
	public static boolean endOfHangBridge = false;

	public final static double tapeFollowSpeed = 0.25;

}
