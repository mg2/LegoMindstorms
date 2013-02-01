package edu.kit.curiosity.sensortests;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;


public class MotorSpeedTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int speed = Motor.A.getSpeed();
		//Motor.A.setSpeed(speed);
		Motor.A.forward();		
		while(!Button.ESCAPE.isDown()) {
			if (Button.LEFT.isDown()) {
				speed = speed - 100;
				Motor.A.setSpeed(speed);
				Motor.A.forward();
			}
			else if (Button.RIGHT.isDown()) {
				speed = speed + 100;
				Motor.A.setSpeed(speed);
				Motor.A.forward();				
			}
			LCD.drawString(Integer.toString(Motor.A.getSpeed()), 0, 0);
		}

	}

}
