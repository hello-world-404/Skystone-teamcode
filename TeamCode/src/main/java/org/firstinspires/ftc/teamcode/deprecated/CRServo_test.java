package org.firstinspires.ftc.teamcode.deprecated;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "CRServo test", group = "Hardware tests")
public class CRServo_test extends LinearOpMode {

    public CRServo linearCraneServo = null;

    @Override
    public void runOpMode() {
        linearCraneServo = hardwareMap.get(CRServo.class, "linearCraneServo");

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Status", "Running");
            telemetry.update();

            if (gamepad2.a) {
                linearCraneServo.setPower(0.8);
            } else if (gamepad2.x) {
                linearCraneServo.setPower(-0.8);
            } else {
                linearCraneServo.setPower(0.0);
            }
        }
    }
}
