package edu.kit.curiosity;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import lejos.util.Delay;
import edu.kit.curiosity.behaviors.DriveBackward;
import edu.kit.curiosity.behaviors.DriveForward;
import edu.kit.curiosity.behaviors.MotorAStall;
import edu.kit.curiosity.behaviors.ReadCodes;
import edu.kit.curiosity.behaviors.SensorHeadPosition;
import edu.kit.curiosity.behaviors.bridge.AbyssDetected;
import edu.kit.curiosity.behaviors.bridge.DriveUntilAbyss;
import edu.kit.curiosity.behaviors.bridge.ReachedEndOfBridge;
import edu.kit.curiosity.behaviors.maze.BeginMaze;
import edu.kit.curiosity.behaviors.maze.FollowWall;
import edu.kit.curiosity.behaviors.maze.HitWall;
import edu.kit.curiosity.behaviors.race.Race;
import edu.kit.curiosity.behaviors.race.RaceDrive;
import edu.kit.curiosity.behaviors.slider.AfterRollFloor;
import edu.kit.curiosity.behaviors.tapefollow.TapeFollow;
import edu.kit.curiosity.behaviors.tapefollow.TapeGapFound;
import edu.kit.curiosity.behaviors.tapefollow.TapeObstacleFound;
import edu.kit.curiosity.behaviors.turntable.StallMotor;

/**
 * This class manages the different arbitrators for all the different levels.
 * 
 * @author Curiosity
 */
public class ArbitratorManager {
	private CustomArbitrator arbitrator;
	private Thread thread = new Thread();
	private RobotState curState;
	private DifferentialPilot pilot = Settings.PILOT;

	/**
	 * Test behavior
	 */
	private Behavior test1 = new DriveBackward();
	private Behavior test2 = new StallMotor();

	private Behavior[] testBehavior = { test1, test2 };

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
	private Behavior m9 = new ReadCodes();
	private Behavior m10 = new MotorAStall();
	private Behavior[] mazeBehavior = { m1, m2, m3, m4, m5, m9, m10 };

	/**
	 * Swamp behavior and arbitrator
	 */
	private Behavior sw1 = new DriveForward();
	private Behavior sw2 = new ReadCodes();
	private Behavior sw3 = new SensorHeadPosition();
	private Behavior[] swampBehavior = { sw1, sw2, sw3 };

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
	private Behavior t1 = new TapeFollow();
	private Behavior t2 = new TapeGapFound();
	private Behavior t3 = new TapeObstacleFound();
	private Behavior t4 = new ReadCodes();
	private Behavior t5 = new SensorHeadPosition();
	private Behavior t6 = new MotorAStall();
	private Behavior[] tapeBehavior = { t1, t2, t3, t4, t5, t6 };

	/**
	 * End Opponent behavior and arbitrator
	 */
	private Behavior e1 = new DriveForward();
	private Behavior[] endBehavior = { e1 };

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
		if (state != null && state != RobotState.START
				&& state != RobotState.TEST) {
			this.arbitrator.stop();
			System.out.println(state.toString() + " mode selected");
		}

		switch (state) {
		case TEST:
			pilot.setTravelSpeed(10);

			this.arbitrator = new CustomArbitrator(testBehavior);
			break;
		case START:
			pilot.setTravelSpeed(pilot.getMaxTravelSpeed());
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
			Settings.motorAAngle = 0;

			this.arbitrator = new CustomArbitrator(startBehavior);
			break;
		case RACE:
			pilot.setTravelSpeed(pilot.getMaxTravelSpeed());
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
			Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);

			// Not in first row
			if (Settings.SONIC.getDistance() < 40) {
				Settings.inFirstRow = false;

				// In first row
			} else {
				Settings.inFirstRow = true;
			}

			Button.ENTER.waitForPressAndRelease();
			// wait 10 seconds before starting the race
			Delay.msDelay(10000);

			this.arbitrator = new CustomArbitrator(raceBehavior);
			break;
		case BRIDGE:
			Settings.LIGHT.setHigh(Settings.light_bridge);
			Settings.LIGHT.setLow(Settings.light_black);
			Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 5);
			pilot.setTravelSpeed(30);
			Settings.motorAAngle = -90;

			this.arbitrator = new CustomArbitrator(this.bridgeBehavior);
			break;
		case MAZE:
			pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 2);
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
			Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
			Settings.motorAAngle = -90;
			Motor.A.setStallThreshold(10, 1000);

			this.arbitrator = new CustomArbitrator(mazeBehavior);
			break;
		case SWAMP:
			pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 2);
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
			Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
			Settings.motorAAngle = -90;
			Motor.A.setStallThreshold(10, 1000);

			this.arbitrator = new CustomArbitrator(swampBehavior);
			break;
		case TAPE:
			double speed = pilot.getMaxTravelSpeed() * Settings.tapeFollowSpeed;
			pilot.setTravelSpeed(speed);
			pilot.setRotateSpeed(pilot.getRotateMaxSpeed());
			Settings.motorAAngle = 0;

			this.arbitrator = new CustomArbitrator(tapeBehavior);
			break;
		case SLIDER:
			pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 1.5);
			pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
			Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
			Settings.motorAAngle = 0;

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
