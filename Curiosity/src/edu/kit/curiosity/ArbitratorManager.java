package edu.kit.curiosity;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.CustomArbitrator;
import lejos.util.Delay;
import edu.kit.curiosity.behaviors.DriveForward;
import edu.kit.curiosity.behaviors.MotorAStall;
import edu.kit.curiosity.behaviors.ReadCodes;
import edu.kit.curiosity.behaviors.SensorHeadPosition;
import edu.kit.curiosity.behaviors.bluetooth.LabyrinthGate;
import edu.kit.curiosity.behaviors.bridge.AbyssDetected;
import edu.kit.curiosity.behaviors.bridge.DriveUntilAbyss;
import edu.kit.curiosity.behaviors.bridge.HitWallBeforeBridge;
import edu.kit.curiosity.behaviors.bridge.ReachedEndOfBridge;
import edu.kit.curiosity.behaviors.colorGate.ButtonPressed;
import edu.kit.curiosity.behaviors.colorGate.ColorFound;
import edu.kit.curiosity.behaviors.colorGate.ColorTapeFollow;
import edu.kit.curiosity.behaviors.colorGate.FirstColor;
import edu.kit.curiosity.behaviors.colorGate.FollowWallColor;
import edu.kit.curiosity.behaviors.colorGate.WallAfterButton;
import edu.kit.curiosity.behaviors.hangingBridge.FollowBridgeFast;
import edu.kit.curiosity.behaviors.maze.BeginMaze;
import edu.kit.curiosity.behaviors.maze.FollowWall;
import edu.kit.curiosity.behaviors.maze.HitWall;
import edu.kit.curiosity.behaviors.race.Race;
import edu.kit.curiosity.behaviors.race.RaceDrive;
import edu.kit.curiosity.behaviors.race.RaceFollowWall;
import edu.kit.curiosity.behaviors.slider.DetectTape;
import edu.kit.curiosity.behaviors.slider.SliderFollowWall;
import edu.kit.curiosity.behaviors.slider.SliderHitWall;
import edu.kit.curiosity.behaviors.slider.StartSlider;
import edu.kit.curiosity.behaviors.swamp.SwampForward;
import edu.kit.curiosity.behaviors.tapefollow.TapeFollow;
import edu.kit.curiosity.behaviors.tapefollow.TapeGapFound;
import edu.kit.curiosity.behaviors.tapefollow.TapeObstacleFound;
import edu.kit.curiosity.behaviors.turntable.TurntableConnect;
import edu.kit.curiosity.behaviors.turntable.TurntablePark;
import edu.kit.curiosity.behaviors.turntable.TurntableRotate;
import edu.kit.curiosity.behaviors.whip.DriveUntilWhipAbyss;
import edu.kit.curiosity.behaviors.whip.EndOfWhip;
import edu.kit.curiosity.behaviors.whip.WaitForWhip;
import edu.kit.curiosity.behaviors.whip.WhipAbyssDetected;
import edu.kit.curiosity.behaviors.whip.WhipDriveForward;
import edu.kit.curiosity.behaviors.whip.WhipIsDown;

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
	 * Start behavior
	 */
	private Behavior s1 = new DriveForward();
	private Behavior s2 = new ReadCodes();
	private Behavior s3 = new SensorHeadPosition();
	private Behavior s4 = new MotorAStall();
	private Behavior[] startBehavior = { s1, s2, s3, s4 };

	/**
	 * Race behavior.
	 */
	private Behavior r1 = new RaceFollowWall(13);
	private Behavior r2 = new Race();
	private Behavior r3 = new ReadCodes();
	private Behavior r4 = new RaceDrive();
	private Behavior r5 = new SensorHeadPosition();
	private Behavior r6 = new MotorAStall();
	private Behavior[] raceBehavior = { r1, r2, r3, r4, r5, r6 };

	/**
	 * Bridge behavior.
	 */
	private Behavior b0 = new DriveForward();
	private Behavior b1 = new HitWallBeforeBridge();
	private Behavior b2 = new DriveUntilAbyss();
	private Behavior b3 = new AbyssDetected();
	private Behavior b4 = new ReachedEndOfBridge();
	private Behavior b5 = new ReadCodes();
	private Behavior b6 = new SensorHeadPosition();
	private Behavior[] bridgeBehavior = { b0, b1, b2, b3, b4, b5, b6 };

	/**
	 * Maze behavior.
	 */
	private Behavior m1 = new DriveForward();
	private Behavior m2 = new FollowWall(12);
	private Behavior m3 = new BeginMaze();
	private Behavior m4 = new HitWall();
	private Behavior m9 = new ReadCodes();
	private Behavior m5 = new SensorHeadPosition();

	private Behavior[] mazeBehavior = { m1, m2, m3, m4, m9, m5 };

	/**
	 * Swamp behavior.
	 */
	private Behavior sw1 = new SwampForward();
	private Behavior sw2 = new ReadCodes();
	private Behavior sw3 = new SensorHeadPosition();
	private Behavior[] swampBehavior = { sw1, sw2, sw3 };

	/**
	 * Bluetooth Gate behavior.
	 */
	private Behavior bt1 = new LabyrinthGate();
	private Behavior bt2 = new ReadCodes();
	private Behavior bt3 = new SensorHeadPosition();
	private Behavior[] btgateBehavior = { bt1, bt2, bt3 };

	/**
	 * Turntable behavior.
	 */
	private Behavior tt1 = new TapeFollow();
	private Behavior tt2 = new TurntablePark();
	private Behavior tt3 = new TurntableRotate();
	private Behavior tt4 = new TurntableConnect();
	private Behavior tt5 = new SensorHeadPosition();
	private Behavior[] turnTableArray = { tt1, tt2, tt3, tt4, tt5 };

	/**
	 * Slider behavior.
	 */
	private Behavior g0 = new TapeFollow();
	private Behavior g1 = new SliderFollowWall(10);
	private Behavior g2 = new SliderHitWall();
	private Behavior g3 = new StartSlider();
	private Behavior g4 = new DetectTape();
	private Behavior g5 = new SensorHeadPosition();
	private Behavior[] gateBehavior = { g0, g1, g2, g3, g4, g5 };

	/**
	 * Seesaw behavior.
	 */
	private Behavior w1 = new TapeFollow();
	private Behavior w2 = new WhipDriveForward();
	private Behavior w3 = new WhipIsDown();
	private Behavior w4 = new DriveUntilWhipAbyss();
	private Behavior w5 = new WhipAbyssDetected();
	private Behavior w6 = new WaitForWhip();
	private Behavior w7 = new EndOfWhip();
	private Behavior w8 = new SensorHeadPosition();
	private Behavior[] seesawArray = { w1, w2, w3, w4, w5, w6, w7, w8 };

	/**
	 * Suspension bridge behavior.
	 */
	private Behavior sb1 = new TapeFollow();
	private Behavior sb2 = new FollowBridgeFast();
	private Behavior sb3 = new SensorHeadPosition();

	private Behavior[] suspensionBridgeArray = { sb1, sb2, sb3 };

	/**
	 * Tape behavior.
	 */
	private Behavior t1 = new TapeFollow();
	private Behavior t2 = new TapeGapFound();
	private Behavior t3 = new TapeObstacleFound();
	private Behavior t4 = new ReadCodes();
	private Behavior t5 = new SensorHeadPosition();
	// private Behavior t6 = new MotorAStall();
	private Behavior[] tapeBehavior = { t1, t2, t3, t4, t5 };

	/**
	 * ColorGate behavior.
	 */
	private Behavior c1 = new ColorTapeFollow();
	private Behavior c2 = new FirstColor();
	private Behavior c3 = new FollowWallColor(12);
	private Behavior c4 = new ColorFound();
	private Behavior c5 = new ButtonPressed();
	private Behavior c6 = new WallAfterButton();
	private Behavior c7 = new SensorHeadPosition();
	// private Behavior tf7 = new MotorAStall();

	private Behavior[] colorGateBehavior = { c1, c2, c3, c4, c5, c6, c7 };

	/**
	 * End Opponent behavior.
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
	 * Changes the current arbitrator, according to the given
	 * {@code RobotState} .
	 * 
	 * @param state
	 *            given {@code RobotState} to change arbitrator one
	 */
	public void changeState(RobotState state) {
		this.curState = state;
		updateArbitrator(this.curState);
	}

	/**
	 * Update the current arbitrator to a level specific one, depending on
	 * the passed {@code RobotState}.
	 * 
	 * @param state
	 *            given {@code RobotState} to change the arbitrator to
	 */
	private void updateArbitrator(RobotState state) {
		if (state != null && state != RobotState.START && state != RobotState.READCODE) {
			this.arbitrator.stop();
		}
		System.out.println(state.toString() + " mode selected");

		switch (state) {
			case READCODE:
				pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.6);
				pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
				Settings.motorAAngle = Settings.SENSOR_FRONT;

				System.out.println("Relocating. Press ENTER to continue.");
				Button.ENTER.waitForPress();
				this.arbitrator = new CustomArbitrator(this.startBehavior);
				break;
			case START:
				pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.6);
				pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
				Settings.motorAAngle = Settings.SENSOR_FRONT;

				this.arbitrator = new CustomArbitrator(startBehavior);
				break;
			case RACE:
				Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.70);
				Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() / 4);
				Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
				Settings.motorAAngle = Settings.SENSOR_RIGHT;
				Settings.atStart = false;

				if (!Settings.raceStarted) {
					System.out.println("Press ENTER");
					Button.ENTER.waitForPressAndRelease();
					// wait 10 seconds before starting the race
					Delay.msDelay(10000);
					Settings.raceStarted = true;
				}

				this.arbitrator = new CustomArbitrator(this.raceBehavior);
				break;
			case BRIDGE:
				Settings.readState = false;
				Settings.LIGHT.setHigh(Settings.light_bridge);
				Settings.LIGHT.setLow(Settings.light_black);
				Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
				pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 5);
				pilot.setTravelSpeed(30);
				Settings.motorAAngle = Settings.SENSOR_RIGHT;

				this.arbitrator = new CustomArbitrator(this.bridgeBehavior);
				break;
			case MAZE:
				pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 2);
				pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
				Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
				Settings.motorAAngle = Settings.SENSOR_RIGHT;
				Motor.A.setStallThreshold(10, 1000);

				this.arbitrator = new CustomArbitrator(this.mazeBehavior);
				break;
			case SWAMP:
				Settings.readState = false;
				pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 2);
				pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
				Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
				Settings.motorAAngle = Settings.SENSOR_FRONT;
				Motor.A.setStallThreshold(10, 1000);

				this.arbitrator = new CustomArbitrator(this.swampBehavior);
				break;
			case BT_GATE:
				pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 2);
				pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
				Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
				Settings.motorAAngle = Settings.SENSOR_FRONT;

				this.arbitrator = new CustomArbitrator(this.btgateBehavior);
				break;
			case TURNTABLE:
				Settings.bluetooth = false;
				Settings.motorAAngle = Settings.SENSOR_FRONT;

				this.arbitrator = new CustomArbitrator(this.turnTableArray);
				break;
			case SLIDER:
				pilot.setTravelSpeed(pilot.getMaxTravelSpeed() / 2);
				pilot.setRotateSpeed(pilot.getMaxRotateSpeed() / 4);
				Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
				Settings.motorAAngle = Settings.SENSOR_FRONT;

				this.arbitrator = new CustomArbitrator(this.gateBehavior);
				break;
			case SEESAW:
				Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.40);
				Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() * 0.15);
				Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
				Settings.motorAAngle = Settings.SENSOR_FRONT;

				this.arbitrator = new CustomArbitrator(this.seesawArray);
				break;
			case SUSPENSION_BRIDGE:
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed());
				Settings.motorAAngle = Settings.SENSOR_FRONT;

				this.arbitrator = new CustomArbitrator(this.suspensionBridgeArray);
				break;
			case LINE_OBSTACLE:
				double speed = pilot.getMaxTravelSpeed() * Settings.tapeFollowSpeed;
				pilot.setTravelSpeed(speed);
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed());
				Settings.motorAAngle = Settings.SENSOR_FRONT;

				this.arbitrator = new CustomArbitrator(this.tapeBehavior);
				break;
			case COLOR_GATE:
				Motor.A.setSpeed(Motor.A.getMaxSpeed() / 5);
				Settings.PILOT.setTravelSpeed(Settings.PILOT.getMaxTravelSpeed() * 0.75);
				Settings.PILOT.setRotateSpeed(Settings.PILOT.getMaxRotateSpeed() * 0.15);
				Settings.motorAAngle = Settings.SENSOR_FRONT;

				this.arbitrator = new CustomArbitrator(this.colorGateBehavior);
				break;
			case END_OPPONENT:
				this.arbitrator = new CustomArbitrator(this.endBehavior);
				break;
			default:
				System.out.println("No arbitrator selected! Error! Error!");
				break;
		}

		// update the thread to run the selected this.arbitrator
		this.thread = new Thread(this.arbitrator);
		this.thread.start();
		Settings.isRunning = true;
	}

	public void stopArbitrator() {
		this.arbitrator.stop();
	}
}
