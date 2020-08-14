package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Grab test")

public class Grab_Test extends LinearOpMode {

    Servo backRoot = null, backGrab = null;

    @Override
    public void runOpMode() {
        ElapsedTime runtime = new ElapsedTime();

        backRoot = hardwareMap.get(Servo.class, "backRoot");
        backGrab = hardwareMap.get(Servo.class, "backGrab");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        backRoot.setPosition(0);
        backGrab.setPosition(0);

        waitForStart();
        runtime.reset();

        //当驾驶员按下“开始”按钮后
        while (opModeIsActive()) {


            if (gamepad1.dpad_right) {
                backGrab.setPosition(0.2);
            } else if (gamepad1.dpad_left) {
                backGrab.setPosition(0);
            }

            telemetry.addData("Run time", runtime.toString());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}