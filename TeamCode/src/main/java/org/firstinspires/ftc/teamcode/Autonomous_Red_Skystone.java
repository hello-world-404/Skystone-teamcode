package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Autonomous Red Skystone")

public class Autonomous_Red_Skystone extends LinearOpMode{
	
	//声明硬件变量
	public DcMotor frontLeft = null, frontRight = null, backLeft = null, backRight = null;
	public Servo leftStake = null, rightStake = null;
	
	
	public void runOpMode(){
		//Hardware map 初始化变量
		frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
		frontRight = hardwareMap.get(DcMotor.class, "frontRight");
		backLeft = hardwareMap.get(DcMotor.class, "backLeft");
		backRight = hardwareMap.get(DcMotor.class, "backRight");

		leftStake = hardwareMap.get(Servo.class, "leftStake");
		rightStake = hardwareMap.get(Servo.class, "rightStake");
		
		leftStake.setPosition(0.3);
		rightStake.setPosition(0.3);
		
		telemetry.addData("Status", "Initialized");
		telemetry.update();
		
		waitForStart();

		//自动程序开始
		//driveForwardWithEncoders();

	}

	public void driveForwardWithEncoders(double power, int distance){
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