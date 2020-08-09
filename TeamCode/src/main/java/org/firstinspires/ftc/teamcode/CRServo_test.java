package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "CRServo test", group = "Hardware tests")
public class CRServo_test extends LinearOpMode {

    public CRServo linearCraneServo = null;

    @Override
    public void runOpMode() {
        linearCraneServo = hardwareMap.get(CRServo.class, "linearCraneServo");

        linearCraneServo.setPower(0.5);

        sleep(300);

        linearCraneServo.setPower(-0.5);

        sleep(300);


        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Status", "Running");
            telemetry.update();

            if (gamepad2.dpad_left) {
                linearCraneServo.setPower(0.8);
            } else if (gamepad2.dpad_right) {
                linearCraneServo.setPower(-0.8);
            } else {
                linearCraneServo.setPower(0.0);
            }
        }
    }
}
