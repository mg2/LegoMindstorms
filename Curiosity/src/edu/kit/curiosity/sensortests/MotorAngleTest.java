package edu.kit.curiosity.sensortests;
import lejos.nxt.Button;
import lejos.nxt.Motor;


public class MotorAngleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(!Button.ESCAPE.isDown()) {
			System.out.println(Motor.A.getTachoCount());
		}
	}

}
