package edu.kit.curiosity.sensortests;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

/**
* Simplest 3 motor commands
* @author owner.GLASSEY
*
*/
public class MotorTest
{
/**
* @param args
*/
	public static void main(String[] args)
	{
		//LCD.drawString("Program 1", 0, 0);
		Motor.A.setSpeed(600);
		while(!Button.ESCAPE.isDown()) {
			if (Button.LEFT.isDown()) {
				Motor.A.backward();
				//Motor.B.backward();
				LCD.drawString(Integer.toString(Motor.A.getSpeed()), 0, 0);
			}
			else if (Button.RIGHT.isDown()) {
				Motor.A.forward();
				//Motor.B.forward();
				LCD.drawString(Integer.toString(Motor.A.getSpeed()), 0, 0);
			}
			else if (Button.ENTER.isDown()){
				Motor.A.forward();
				//Motor.B.backward();
				LCD.drawString(Integer.toString(Motor.A.getSpeed()), 0, 0);
			}
		}
	}
}