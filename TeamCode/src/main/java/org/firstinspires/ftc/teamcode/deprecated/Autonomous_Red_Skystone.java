package org.firstinspires.ftc.teamcode.deprecated;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "Autonomous Red Skystone")

public class Autonomous_Red_Skystone extends LinearOpMode {

    //电机力量
    private static double MOTOR_POWER = 0.6;
    //声明硬件变量
    DcMotor frontLeft = null, frontRight = null, backLeft = null, backRight = null;
    Servo leftStake = null, rightStake = null;
    Servo backRoot = null, backGrab = null;
    ColorSensor cSensor;

    @Override
    public void runOpMode() {
        //Hardware map 初始化变量
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        leftStake = hardwareMap.get(Servo.class, "leftStake");
        rightStake = hardwareMap.get(Servo.class, "rightStake");

        backRoot = hardwareMap.get(Servo.class, "backRoot");
        backGrab = hardwareMap.get(Servo.class, "backGrab");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        cSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        backRoot.setDirection(Servo.Direction.REVERSE);

        backRoot.setPosition(0);
        backGrab.setPosition(0);

        leftStake.setPosition(0.3);
        rightStake.setPosition(0.3);

        boolean detectedSkystone = false;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        driveForwardWithEncoders(-0.4, 780);

        waitRobot();

        strafeRightWithEncoders(0.6, 1000);

        waitRobot();

        while (!detectedSkystone) {
            telemetry.addData("Red", cSensor.red());
            telemetry.addData("Green", cSensor.green());
            telemetry.addData("Blue", cSensor.blue());
            telemetry.update();

            if ((cSensor.red() > 0 && cSensor.red() < 15) && (cSensor.green() > 5 && cSensor.green() < 16) && (cSensor.blue() > 0 && cSensor.blue() < 10)) {
                telemetry.addData("Block", "skystone");
                telemetry.update();
                detectedSkystone = true;

                backRoot.setPosition(0.3);
                backGrab.setPosition(0.4);

                strafeRightWithEncoders(0.6, 1000);


            } else {
                driveBackwardWithEncoders(0.3, 150);
                waitRobot();
            }
        }
    }

    public void move(double powerx, double powery, double turn) {
        double speedx = powerx;
        double speedy = powery;
        double offset = turn;
        frontLeft.setPower(Range.clip(speedy - speedx + offset, -1, 1));
        frontRight.setPower(Range.clip(speedy + speedx - offset, -1, 1));
        backLeft.setPower(Range.clip(speedy + speedx + offset, -1, 1));
        backRight.setPower(Range.clip(speedy - speedx - offset, -1, 1));
    }

    public void waitRobot() {
        stopRobot();
        sleep(1800);
    }

    public void strafeLeftWithEncoders(double power, long time) {
        frontLeft.setPower(Range.clip(-power, -1, 1));
        frontRight.setPower(Range.clip(-power, -1, 1));
        backLeft.setPower(Range.clip(power, -1, 1));
        backRight.setPower(Range.clip(power, -1, 1));

        sleep(time);
    }

    public void strafeRightWithEncoders(double power, long time) {
        frontLeft.setPower(Range.clip(-power, -1, 1));
        frontRight.setPower(Range.clip(power, -1, 1));
        backLeft.setPower(Range.clip(power, -1, 1));
        backRight.setPower(Range.clip(-power, -1, 1));

        sleep(time);
    }

    public void turnLeftWithEncoders(double power, long time) {
        frontLeft.setPower(Range.clip(-power, -1, 1));
        frontRight.setPower(Range.clip(power, -1, 1));
        backLeft.setPower(Range.clip(-power, -1, 1));
        backRight.setPower(Range.clip(power, -1, 1));

        sleep(time);
    }

    public void turnRightWithEncoders(double power, long time) {
        frontLeft.setPower(Range.clip(power, -1, 1));
        frontRight.setPower(Range.clip(-power, -1, 1));
        backLeft.setPower(Range.clip(power, -1, 1));
        backRight.setPower(Range.clip(-power, -1, 1));

        sleep(time);
    }

    public void driveBackwardWithEncoders(double power, int distance) {
        resetEncoders();

        setTargetPos(-distance);

        setModeRunToPosition();

        move(0, power, 0);

        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
        }

        stopRobot();

        setModeUseEncoders();
    }

    public void driveForwardWithEncoders(double power, int distance) {
        resetEncoders();

        setTargetPos(distance);

        setModeRunToPosition();

        move(0, power, 0);

        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
        }


        stopRobot();

        setModeUseEncoders();
    }

    public void setModeUseEncoders() {
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stopRobot() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void setModeRunToPosition() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setTargetPos(int distance) {
        frontLeft.setTargetPosition(distance);
        frontRight.setTargetPosition(distance);
        backLeft.setTargetPosition(distance);
        backRight.setTargetPosition(distance);
    }

    public void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


}