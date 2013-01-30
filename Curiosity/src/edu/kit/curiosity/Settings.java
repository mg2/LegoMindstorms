package edu.kit.curiosity;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Settings {

	public final static double DRIVE_SPEED = 40;
	public final static double ROTATION_SPEED = 40;
	public final static double TRACK_WIDTH = 14.5;
	public final static double WHEEL_WIDTH = 8;
	
	public final static DifferentialPilot PILOT = new DifferentialPilot(WHEEL_WIDTH, TRACK_WIDTH, Motor.C, Motor.B);
	
	public final static SensorPort SONIC_SENSOR_PORT = SensorPort.S1;
	public final static SensorPort LIGHT_SENSOR_PORT = SensorPort.S2;
	public final static SensorPort TOUCH_SENSOR_PORT = SensorPort.S3;
	
	public static final UltrasonicSensor SONIC = new UltrasonicSensor(Settings.SONIC_SENSOR_PORT);
	public static final TouchSensor TOUCH = new TouchSensor(Settings.TOUCH_SENSOR_PORT);
}
