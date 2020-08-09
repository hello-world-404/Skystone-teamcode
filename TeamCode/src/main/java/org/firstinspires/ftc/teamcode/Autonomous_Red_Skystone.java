package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Autonomous Red Skystone")

public class Autonomous_Red_Skystone extends LinearOpMode {

	//声明硬件变量
	DcMotor frontLeft = null, frontRight = null, backLeft = null, backRight = null;
	Servo leftStake = null, rightStake = null;

	//电机力量
	private static double MOTOR_POWER = 0.6;

	ColorSensor cSensor;
	/*
	DistanceSensor sensorDistance;
	private DistanceSensor sensorRange;
	 */

	@Override
	public void runOpMode() {
		//Hardware map 初始化变量
		frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
		frontRight = hardwareMap.get(DcMotor.class, "frontRight");
		backLeft = hardwareMap.get(DcMotor.class, "backLeft");
		backRight = hardwareMap.get(DcMotor.class, "backRight");

		leftStake = hardwareMap.get(Servo.class, "leftStake");
		rightStake = hardwareMap.get(Servo.class, "rightStake");

		frontLeft.setDirection(DcMotor.Direction.FORWARD);
		frontRight.setDirection(DcMotor.Direction.FORWARD);
		backLeft.setDirection(DcMotor.Direction.FORWARD);
		backRight.setDirection(DcMotor.Direction.FORWARD);

		frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


		cSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

		/*
		distance = hardwareMap.get(DistanceSensor.class, "");
		 */

		leftStake.setPosition(0.3);
		rightStake.setPosition(0.3);

		/*

		// hsvValues is an array that will hold the hue, saturation, and value information.
		float hsvValues[] = {0F, 0F, 0F};

		// values is a reference to the hsvValues array.
		final float values[] = hsvValues;

		// sometimes it helps to multiply the raw RGB values with a scale factor
		// to amplify/attentuate the measured values.
		final double SCALE_FACTOR = 255;
		 */


		telemetry.addData("Status", "Initialized");
		telemetry.update();

		waitForStart();

		//自动程序开始

		driveForwardWithEncoders(MOTOR_POWER, 1000);

		driveBackwardWithEncoders(MOTOR_POWER, 1000);

		turnLeftWithEncoders(MOTOR_POWER, 1000);

		turnRightWithEncoders(MOTOR_POWER, 1000);





		/*
		sensorRange = hardwareMap.get(DistanceSensor.class, "sensor_range");
		// you can also cast this to a Rev2mDistanceSensor if you want to use added
		// methods associated with the Rev2mDistanceSensor class.
		Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensorRange;

		telemetry.addData(">>", "Press start to continue");
		telemetry.update();

		// loop and read the RGB and distance data.
		// Note we use opModeIsActive() as our loop condition because it is an interruptible method.
		while (opModeIsActive()) {
			// convert the RGB values to HSV values.
			// multiply by the SCALE_FACTOR.
			// then cast it back to int (SCALE_FACTOR is a double)
			Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
					(int) (sensorColor.green() * SCALE_FACTOR),
					(int) (sensorColor.blue() * SCALE_FACTOR),
					hsvValues);

			// send the info back to driver station using telemetry function.
			telemetry.addData("Distance (cm)", String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
			telemetry.addData("Alpha", sensorColor.alpha());
			telemetry.addData("Red  ", sensorColor.red());
			telemetry.addData("Green", sensorColor.green());
			telemetry.addData("Blue ", sensorColor.blue());
			telemetry.addData("Hue", hsvValues[0]);

			telemetry.addData("deviceName",sensorRange.getDeviceName() );
			telemetry.addData("range", String.format("%.01f mm", sensorRange.getDistance(DistanceUnit.MM)));
			telemetry.addData("range", String.format("%.01f cm", sensorRange.getDistance(DistanceUnit.CM)));
			telemetry.addData("range", String.format("%.01f m", sensorRange.getDistance(DistanceUnit.METER)));
			telemetry.addData("range", String.format("%.01f in", sensorRange.getDistance(DistanceUnit.INCH)));

			// Rev2mDistanceSensor specific methods.
			telemetry.addData("ID", String.format("%x", sensorTimeOfFlight.getModelID()));
			telemetry.addData("did time out", Boolean.toString(sensorTimeOfFlight.didTimeoutOccur()));

			//Test the which kind of block it is


			// change the background color to match the color detected by the RGB sensor.
			// pass a reference to the hue, saturation, and value array as an argument
			// to the HSVToColor method.
			relativeLayout.post(new Runnable() {
				public void run() {
					relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
				}
			});

			telemetry.update();
		}

		// Set the panel back to the default color
		relativeLayout.post(new Runnable() {
			public void run() {
				relativeLayout.setBackgroundColor(Color.WHITE);
			}
		});


		 */
	}

	public void strafeLeftWithEncoders(double power, int distance) {
		resetEncoders();

		setTargetPos(distance);

		setModeUseEncoders();

		frontLeft.setPower(-power);
		frontRight.setPower(power);
		backLeft.setPower(power);
		backRight.setPower(-power);

		stopRobot();

		setModeUseEncoders();
	}

	public void strafeRightWithEncoders(double power, int distance) {
		resetEncoders();

		setTargetPos(distance);

		setModeRunToPosition();

		frontLeft.setPower(power);
		frontRight.setPower(-power);
		backLeft.setPower(-power);
		backRight.setPower(power);

		stopRobot();

		setModeUseEncoders();
	}

	public void turnLeftWithEncoders(double power, int distance) {
		resetEncoders();

		setTargetPos(distance);

		setModeRunToPosition();

		frontLeft.setPower(-power);
		frontRight.setPower(power);
		backLeft.setPower(-power);
		backRight.setPower(power);

		stopRobot();

		setModeUseEncoders();
	}

	public void turnRightWithEncoders(double power, int distance) {
		resetEncoders();

		setTargetPos(distance);

		setModeRunToPosition();

		frontLeft.setPower(power);
		frontRight.setPower(-power);
		backLeft.setPower(power);
		backRight.setPower(-power);

		while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
		}

		stopRobot();

		setModeUseEncoders();
	}

	public void driveBackwardWithEncoders(double power, int distance) {
		resetEncoders();

		setTargetPos(distance);

		setModeRunToPosition();

		frontLeft.setPower(-power);
		frontRight.setPower(-power);
		backLeft.setPower(-power);
		backRight.setPower(-power);

		while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
		}

		stopRobot();

		setModeUseEncoders();
	}

	public void driveForwardWithEncoders(double power, int distance) {
		resetEncoders();

		setTargetPos(distance);

		setModeRunToPosition();

		frontLeft.setPower(power);
		frontRight.setPower(power);
		backLeft.setPower(power);
		backRight.setPower(power);

		while(frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){}


		stopRobot();

		setModeUseEncoders();
	}
	
	public void setModeUseEncoders(){
		frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}
	
	public void stopRobot(){
		frontLeft.setPower(0);
		frontRight.setPower(0);
		backLeft.setPower(0);
		backRight.setPower(0);
	}
	
	public void setModeRunToPosition(){
		frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}
	
	public void setTargetPos(int distance){
		frontLeft.setTargetPosition(distance);
		frontRight.setTargetPosition(distance);
		backLeft.setTargetPosition(distance);
		backRight.setTargetPosition(distance);
	}
	
	public void resetEncoders(){
		frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
	}
	
	
}