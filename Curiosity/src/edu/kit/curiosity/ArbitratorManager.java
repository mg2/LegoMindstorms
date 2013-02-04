package edu.kit.curiosity;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import lejos.util.Delay;
import lejos.util.Timer;
import lejos.util.TimerListener;
import edu.kit.curiosity.behaviors.DriveForward;
import edu.kit.curiosity.behaviors.MotorAStall;
import edu.kit.curiosity.behaviors.ReadCodes;
import edu.kit.curiosity.behaviors.SensorHeadPosition;
import edu.kit.curiosity.behaviors.bridge.*;
import edu.kit.curiosity.behaviors.gate.*;
import edu.kit.curiosity.behaviors.maze.*;
import edu.kit.curiosity.behaviors.race.*;
import edu.kit.curiosity.behaviors.slider.AfterRollFloor;
import edu.kit.curiosity.behaviors.tape.*;

/**
 * This class manages the different arbitrators for all the different levels.
 * 
 * @author Curiosity
 */
public class ArbitratorManager {
	private CustomArbitrator arbitrator;
	private Thread thread = new Thread();
	private RobotState curState;

	/**
	 * Start behavior
	 */
	private Behavior s1 = new DriveForward();
	private Behavior s2 = new ReadCodes();
	private Behavior s3 = new SensorHeadPosition();
	private Behavior s4 = new MotorAStall();
	private Behavior[] startBehavior = { s1, s2, s3, s4 };

	/**
	 * Race behavior and arbitrator
	 */

	private Behavior r1 = new RaceDrive();
	private Behavior r2 = new Race();
	private Behavior r3 = new ReadCodes();
	private Behavior r4 = new SensorHeadPosition();
	private Behavior r5 = new MotorAStall();
	private Behavior[] raceBehavior = { r1, r2, r3, r4, r5 };

	/**
	 * Bridge behavior and arbitrator
	 */
	private Behavior b0 = new DriveForward();
	private Behavior b1 = new DriveUntilAbyss();
	private Behavior b2 = new AbyssDetected();
	private Behavior b3 = new ReachedEndOfBridge();
	private Behavior b4 = new ReadCodes();
	private Behavior b5 = new SensorHeadPosition();
	private Behavior b6 = new MotorAStall();
	private Behavior[] bridgeBehavior = { b0, b1, b2, b3, b4, b5, b6 };

	/**
	 * Maze behavior and arbitrator
	 */
	private Behavior m1 = new DriveForward();
	private Behavior m2 = new FollowWall(12);
	private Behavior m3 = new BeginMaze();
	private Behavior m4 = new HitWall();
	private Behavior m5 = new SensorHeadPosition();
	private Behavior m6 = new SwampDetected();
	private Behavior m7 = new SwampLeft();
	private Behavior m8 = new FoundEndLine();
	private Behavior m9 = new ReadCodes();
	private Behavior m10 = new MotorAStall();
	private Behavior[] mazeBehavior = { m1, m2, m3, m4, m5, m6, m7, m8, m9, m10 };

	/**
	 * Gate behavior and arbitrator
	 */
	private Behavior g1 = new DriveForward();
	private Behavior g2 = new AfterRollFloor();
	private Behavior g3 = new ReadCodes();
	private Behavior g4 = new SensorHeadPosition();
	private Behavior g5 = new MotorAStall();
	private Behavior[] gateBehavior = { g1, g2, g3, g4, g5 };

	/**
	 * Tape behavior and arbitrator
	 */
	private Behavior t1 = new LineFollow();
	private Behavior t2 = new TapeLost();
	private Behavior t3 = new GapFound(); // >130
	private Behavior t4 = new ObstacleFound();
	private Behavior t5 = new ReadCodes();
	private Behavior t6 = new SensorHeadPosition();
	private Behavior t7 = new MotorAStall();
	private Behavior[] tapeBehavior = { t1, t2, t3, t4, t5, t6, t7 };

	
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
		if (state != null && state != RobotState.START) {
			this.arbitrator.stop();
			System.out.println(state.toString() + " mode selected");
		}

		switch (state) {
		case START:
			Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.5);
			Settings.PILOT
					.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 4);
			Settings.motorAAngle = 90;
			this.arbitrator = new CustomArbitrator(startBehavior);
			break;
		case RACE:
			Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed());
			Settings.PILOT
					.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 4);
			Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);

			Button.ENTER.waitForPressAndRelease();
			
			// Not in first row
			if (Settings.SONIC.getDistance() < 40) {
				Settings.inFirstRow = false;

				// In first row
			} else {
				Settings.inFirstRow = true;
			}
			Delay.msDelay(10000);
			this.arbitrator = new CustomArbitrator(raceBehavior);
			break;
		case BRIDGE:
			Settings.LIGHT.setHigh(Settings.light_bridge);
			Settings.LIGHT.setLow(Settings.light_black);
			Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
			Settings.PILOT
					.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 5);
			Settings.PILOT.setTravelSpeed(30);
			Settings.motorAAngle = 0;
			this.arbitrator = new CustomArbitrator(this.bridgeBehavior);
			break;
		case MAZE:
			Settings.PILOT
					.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() / 2);
			Settings.PILOT
					.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 4);
			Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
			Settings.motorAAngle = 0;
			Motor.A.setStallThreshold(10, 1000);
			this.arbitrator = new CustomArbitrator(mazeBehavior);
			break;
		case TAPE:
			Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
			Settings.PILOT
					.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.75);
			Settings.PILOT
					.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() * 0.15);
			Settings.motorAAngle = 90;
			this.arbitrator = new CustomArbitrator(tapeBehavior);
			break;
		case SLIDER:
			Settings.PILOT
					.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() / 1.5);
			Settings.PILOT
					.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 4);
			Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
			Settings.motorAAngle = 90;
			this.arbitrator = new CustomArbitrator(gateBehavior);
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
