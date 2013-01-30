package edu.kit.curiosity;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class Settings {

	public final static double DRIVE_SPEED = 40;
	public final static double ROTATION_SPEED = 40;
	public final static double TRACK_WIDTH = 14.5;
	public final static double WHEEL_WIDTH = 8;
	
	public static DifferentialPilot pilot = new DifferentialPilot(WHEEL_WIDTH, TRACK_WIDTH, Motor.B, Motor.C);
	
	public final static SensorPort SONIC_SENSOR_PORT = SensorPort.S1;
	public final static SensorPort LIGHT_SENSOR_PORT = SensorPort.S2;
	public final static SensorPort TOUCH_SENSOR_PORT = SensorPort.S3;
	
}
