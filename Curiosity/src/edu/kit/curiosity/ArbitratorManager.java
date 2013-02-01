package edu.kit.curiosity;

import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.behaviors.DriveForward;
import edu.kit.curiosity.behaviors.bridge.AbyssDetected;
import edu.kit.curiosity.behaviors.bridge.DriveUntilAbyss;

/**
 * This class manages the different arbitrators for all the different levels.
 * 
 * @author Curiosity
 */
public class ArbitratorManager {
	// bridge behaviors
	private Behavior b1 = new DriveUntilAbyss();
	private Behavior b2 = new AbyssDetected();
	private Behavior[] bridgeBehavior = { b1, b2 };
	private CustomArbitrator arbitrator;
	private Thread thread = new Thread();
	private RobotStates curState;

	// maze behaviors
	private Behavior m1 = new DriveForward();
	private Behavior[] mazeBehavior = { m1 };

	public ArbitratorManager() {
		init();
	}

	private void init() {
		updateArbitrator(Settings.FIRST_LEVEL);
	}

	public void changeState(RobotStates state) {
		this.curState = state;
		updateArbitrator(this.curState);
	}

	private void updateArbitrator(RobotStates state) {
		switch (state) {
			case BRIDGE:
				System.out.println("Bridge arbitrator selected");
				this.arbitrator = new CustomArbitrator(this.bridgeBehavior);
				break;
			case MAZE:
				System.out.println("Maze arbitrator selected");
				this.arbitrator = new CustomArbitrator(mazeBehavior);
				break;
			case LINE:
				System.out.println("Line arbitrator selected");
				this.arbitrator = new CustomArbitrator(mazeBehavior);
				break;
			default:
				System.out.println("No arbitrator selected! Error! Error!");
				break;
		}

		this.thread = new Thread(this.arbitrator);
		this.thread.start();
	}
}
