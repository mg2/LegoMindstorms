package edu.kit.curiosity;

import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import edu.kit.curiosity.behaviors.DriveForward;
import edu.kit.curiosity.behaviors.MotorAStall;
import edu.kit.curiosity.behaviors.SensorHeadPosition;
import edu.kit.curiosity.behaviors.bridge.AbyssDetected;
import edu.kit.curiosity.behaviors.bridge.DriveUntilAbyss;

/**
 * This class manages the different arbitrators for all the different levels.
 * 
 * @author Curiosity
 */
public class ArbitratorManager {
	/**
	 * Bridge behavior and arbitrator
	 */
	private Behavior b1 = new DriveUntilAbyss();
	private Behavior b2 = new AbyssDetected();
	private Behavior b3 = new SensorHeadPosition();
	private Behavior b4 = new MotorAStall();
	private Behavior[] bridgeBehavior = { b1, b2, b3, b4 };
	private CustomArbitrator arbitrator;
	private Thread thread = new Thread();
	private RobotState curState;

	/**
	 * Maze behavior and arbitrator
	 */
	private Behavior m1 = new DriveForward();
	private Behavior[] mazeBehavior = { m1 };

	/**
	 * Instantiate an {@code ArbitratorManager}
	 */
	public ArbitratorManager() {
		init();
	}

	/**
	 * Instantiate an {@code ArbitratorManager} with a given {@code RobotState
	 * }.
	 * 
	 * @param state
	 *            given {@code RobotState} to instantiate the
	 *            {@code ArbitratorManager} with
	 */
	public ArbitratorManager(RobotState state) {
		init(state);
	}

	/**
	 * initializes the {@code CustomArbitrator}.
	 */
	private void init() {
		updateArbitrator(Settings.FIRST_LEVEL);
	}

	/**
	 * initializes the {@code CustomArbitrator} with a given {@code RobotState}
	 * 
	 * @param state
	 *            given {@code RobotState} to initialize arbitrator with
	 */
	private void init(RobotState state) {
		updateArbitrator(state);
	}

	/**
	 * Changes the current arbitrator, according to the given {@code RobotState}
	 * .
	 * 
	 * @param state
	 *            given {@code RobotState} to change arbitrator one
	 */
	public void changeState(RobotState state) {
		this.curState = state;
		updateArbitrator(this.curState);
	}

	/**
	 * Update the current arbitrator to a level specific one, depending on the
	 * passed {@code RobotState}.
	 * 
	 * @param state
	 *            given {@code RobotState} to change the arbitrator to
	 */
	private void updateArbitrator(RobotState state) {
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

		// update the thread to run the selected arbitrator
		this.thread = new Thread(this.arbitrator);
		this.thread.start();
	}
}
