package edu.kit.curiosity;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Settings {

	/**
	 * Drive Speed
	 */
	public final static double DRIVE_SPEED = 40;
	
	/**
	 * Rotation Speed
	 */
	public final static double ROTATION_SPEED = 40;
	
	/**
	 * Pilot Track Width
	 */
	public final static double TRACK_WIDTH = 14.5;
	
	/**
	 * Pilot wheel width
	 */
	public final static double WHEEL_WIDTH = 8;
	
	/**
	 * Pilot which moves the robot
	 */
	public final static DifferentialPilot PILOT = new DifferentialPilot(WHEEL_WIDTH, TRACK_WIDTH, Motor.C, Motor.B);
	
	/**
	 * SensorPort of the ultrasonic sensor
	 */
	public final static SensorPort SONIC_SENSOR_PORT = SensorPort.S1;
	
	/**
	 * SensorPort of the light sensor
	 */
	public final static SensorPort LIGHT_SENSOR_PORT = SensorPort.S2;


	/**
	 * SensorPort of the right touch sensor
	 */
	public final static SensorPort TOUCH_R_SENSOR_PORT = SensorPort.S3;
	
	/**
	 * SensorPort of the left touch sensor
	 */
	public final static SensorPort TOUCH_L_SENSOR_PORT = SensorPort.S4;
	
	/**
	 * Ultrasonic sensor
	 */
	public static final UltrasonicSensor SONIC = new UltrasonicSensor(SONIC_SENSOR_PORT);
	/**
	 * right Touch sensor
	 */
	public static final TouchSensor TOUCH_R = new TouchSensor(TOUCH_R_SENSOR_PORT);
	/**
	 * left Touch sensor
	 */
	public static final TouchSensor TOUCH_L = new TouchSensor(TOUCH_L_SENSOR_PORT);
	/**
	 * light sensor
	 */
	public static final LightSensor LIGHT = new LightSensor(LIGHT_SENSOR_PORT);
	
	/**
	 * the angle
	 */
	public static int angle = 15;
	
	public static int numberOfTurns = 0;
	
	public static int motorAAngle = 0;
}
