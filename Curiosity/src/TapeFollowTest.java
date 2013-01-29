import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;


public class TapeFollowTest {
	private static DifferentialPilot diffPilot;
	static int lightLow;
	static int lightHigh;
	
	static LightSensor light = new LightSensor(SensorPort.S2);
	static TouchSensor touch = new TouchSensor(SensorPort.S3);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		diffPilot = 
				new DifferentialPilot(8.5, 15, Motor.C, Motor.B);
		configDiffPilot(400, 400);
		calibrateLightSensor();

		int mode = 0;
		while( Button.ESCAPE.isUp() && !touch.isPressed() ) {
			if (Button.ENTER.isDown()) {
				System.out.println("Pauseeee... :)");
				Button.ENTER.waitForPressAndRelease();
				LCD.clear();
			}
			if (Button.LEFT.isDown()) {
				LCD.clear();
				System.out.println("Normal follow.");
				mode = 0;
			}
			if (Button.RIGHT.isDown()) {
				LCD.clear();
				System.out.println("Edge Follow.");
				mode = 1;
			}
			if (mode == 0) {
				LCD.clear();
				System.out.println("Normal follow.");
				searchTape();				
			} else if (mode == 1) {
				LCD.clear();
				System.out.println("Edge Follow.");
				searchTapeEdge();				
			}
		}
		
	}
	
	public static void searchTapeEdge() {
		if( light.getLightValue() > 70 ) {
			diffPilot.rotate(-10);
		} else {
			diffPilot.arcForward(10);
		}
	}

	
	public static void searchTape() {
		if( light.getLightValue() > 70 ) {
			diffPilot.forward();
		} else {
			int i = 0;
			diffPilot.quickStop();
			diffPilot.travel(-5);
			while(light.getLightValue() < 70 && !touch.isPressed()) {
				if(i % 2 == 0 && i < 10) {
					diffPilot.rotate(10 * (i + 1));
					i++;
				} else if (i % 2 == 1 && i < 10) {
					diffPilot.rotate(-10 * (i + 1));
					i++;
				} else {
					diffPilot.stop();
				}
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
