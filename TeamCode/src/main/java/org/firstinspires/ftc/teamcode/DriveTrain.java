package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import static org.firstinspires.ftc.teamcode.Hardware.leftBack;
import static org.firstinspires.ftc.teamcode.Hardware.leftFront;
import static org.firstinspires.ftc.teamcode.Hardware.rightBack;
import static org.firstinspires.ftc.teamcode.Hardware.rightFront;

public class DriveTrain {

    static double speedx;
    static double speedy;
    static double turn;

    public static void move(double powerx, double powery, double offset) {
        speedx = powerx;
        speedy = powery;
        turn = offset;

        leftFront.setPower(Range.clip(speedy - speedx + offset, -1, 1));
        rightFront.setPower(Range.clip(speedy + speedx - offset, -1, 1));
        leftBack.setPower(Range.clip(speedy + speedx + offset, -1, 1));
        rightBack.setPower(Range.clip(speedy - speedx - offset, -1, 1));
    }

    public void init() {
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

/*
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
*/
    }

    public void stopRobot() {
        move(0, 0, 0);
    }
}
