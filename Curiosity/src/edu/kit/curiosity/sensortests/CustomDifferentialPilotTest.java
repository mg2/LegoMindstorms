package edu.kit.curiosity.sensortests;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.CustomDifferentialPilot;


public class CustomDifferentialPilotTest {
	
	private static CustomDifferentialPilot diffPilot;
	public static void main(String[] args) {
		diffPilot =	new CustomDifferentialPilot(8.5, 15, Motor.C, Motor.B);
		while(!Button.ENTER.isDown()) {
			diffPilot.forward();
		}
	}

}
