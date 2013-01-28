import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class ListenForButtons {
	static int speed;
  public static void main(String[] args) throws Exception {
	  Motor.A.forward();
	  speed = Motor.A.getSpeed();
		LCD.drawString(Integer.toString(Motor.A.getSpeed()), 0, 0);
	  
	Button.LEFT.addButtonListener(new ButtonListener() {
	      public void buttonPressed(Button b) {
	      }

	      public void buttonReleased(Button b) {
	        LCD.clear();
	        speed -= 100;
	        Motor.A.setSpeed(speed);
			LCD.drawString(Integer.toString(Motor.A.getSpeed()), 0, 0);
	      }
	    });
    Button.RIGHT.addButtonListener(new ButtonListener() {
      public void buttonPressed(Button b) {
      }

      public void buttonReleased(Button b) {
        LCD.clear();
        speed += 100;
        Motor.A.setSpeed(speed);
		LCD.drawString(Integer.toString(Motor.A.getSpeed()), 0, 0);
      }
    });

    Button.ESCAPE.waitForPressAndRelease();
  }
}