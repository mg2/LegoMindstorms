import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

public class Ultraschall {
	static UltrasonicSensor sonic;
	static DifferentialPilot test;
	static TouchSensor touch;
  public static void main(String[] args) throws Exception {
    sonic = new UltrasonicSensor(SensorPort.S1);
    touch = new TouchSensor(SensorPort.S2);

    test = new DifferentialPilot(8.5, 25, Motor.C, Motor.B);
    System.out.println("Ultrasonic test.");

    	
	    Button.ESCAPE.addButtonListener(new ButtonListener() {
		
		@Override
		public void buttonReleased(Button b) {
			//System.out.println(Integer.toString(sonic.getDistance()));
			System.exit(0);
		}
		
		@Override
		public void buttonPressed(Button b) {
			// TODO Auto-generated method stub
			
		}
	});
	    test.setTravelSpeed(25);
	    while (!touch.isPressed()) {
		    int i = 0;
		while (i < 18) {
			test.forward();
			i = sonic.getDistance();
		}
		test.stop();
		i = sonic.getDistance();
		while (i > 18) {
			test.arcForward(-25);
			//test.
			//test.
			//test.arc(25, -10);
			i = sonic.getDistance();

		}
		test.stop();
  
	    }
    //Button.ESCAPE.waitForPressAndRelease();
  }
}