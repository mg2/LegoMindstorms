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
		LCD.drawString("Program 1", 0, 0);
		while(!Button.ESCAPE.isDown()) {
			if (Button.LEFT.isDown()) {
				Motor.A.backward();
				Motor.B.backward();
			}
			else if (Button.RIGHT.isDown()) {
				Motor.A.forward();
				Motor.B.forward();
			}
			else if (Button.ENTER.isDown()){
				Motor.A.forward();
				Motor.B.backward();
			}
			
			
		}
/*Button.waitForAnyPress();
LCD.clear();
Motor.A.forward();
LCD.drawString("FORWARD",0,0);
Button.waitForAnyPress();
LCD.drawString("BACKWARD",0,0);
Motor.A.backward();
//Button.waitForAnyPress();
Button.ESCAPE.waitForPress();
Motor.A.stop();*/     
	}
}