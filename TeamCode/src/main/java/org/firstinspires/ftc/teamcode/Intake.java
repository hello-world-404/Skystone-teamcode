package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.teamcode.Hardware.leftIntake;
import static org.firstinspires.ftc.teamcode.Hardware.rightIntake;

public class Intake {

    public void init() {
        leftIntake.setDirection(DcMotor.Direction.REVERSE);
        rightIntake.setDirection(DcMotor.Direction.FORWARD);

        leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftIntake.setPower(0);
        rightIntake.setPower(0);
    }

    public void intake() {
        leftIntake.setPower(1);
        rightIntake.setPower(1);
    }

    public void stop() {
        leftIntake.setPower(1);
        rightIntake.setPower(1);
    }
}
