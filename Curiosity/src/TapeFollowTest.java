import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;


public class TapeFollowTest {
	private static DifferentialPilot diffPilot;
	static int lightLow;
	static int lightHigh;
	
	static LightSensor light = new LightSensor(SensorPort.S1);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		diffPilot = 
				new DifferentialPilot(8.5, 15, Motor.C, Motor.B);
		configDiffPilot(200, 400);
		calibrateLightSensor();
		
		while( Button.ESCAPE.isUp() ) {
			if( light.getLightValue() > 70 ) {
				diffPilot.forward();
			} else {
				diffPilot.travel(-5);
				searchTape();
			}
		}
		
	}
	
	public static void searchTape() {
		int i = 0;
		while(light.getLightValue() < 70) {
			if(i % 2 == 0 && i < 10) {
				diffPilot.rotate(10 * (i + 1));
				i++;
			} else if (i % 2 == 1 && i < 10) {
				diffPilot.rotate(-10 * (i + 1));
				i++;
			}
		}
	}
	
	public static void calibrateLightSensor() {
		Button.ENTER.waitForPressAndRelease();
		System.out.println("Calibrate LightSensor!");
		System.out.print("Calibrate Low: ");
		Button.ENTER.waitForPressAndRelease();
		light.calibrateLow();
		lightLow = light.getLow();
		System.out.println(lightLow);
		Button.ENTER.waitForPressAndRelease();
		System.out.print("Calibrate High: ");
		light.calibrateHigh();
		lightHigh = light.getHigh();
		System.out.println(lightHigh);
		System.out.println("");
		System.out.println("Start Running with ENTER!");
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
	}
	
	private static void configDiffPilot(int rotateSpeed, int travelSpeed) {
		diffPilot.setRotateSpeed(rotateSpeed);
		diffPilot.setTravelSpeed(travelSpeed);
	}
}
