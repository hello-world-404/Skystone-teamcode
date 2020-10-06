package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.DriveTrain.move;
import static org.firstinspires.ftc.teamcode.Hardware.leftIntake;
import static org.firstinspires.ftc.teamcode.Hardware.linearCrane;
import static org.firstinspires.ftc.teamcode.Hardware.rightIntake;
import static org.firstinspires.ftc.teamcode.Hardware.riseMotor;
import static org.firstinspires.ftc.teamcode.Servo.pick;
import static org.firstinspires.ftc.teamcode.Servo.rotate;
import static org.firstinspires.ftc.teamcode.Servo.stake;

@TeleOp(name = "TeleOp_Manual_Control Single User")

public class TeleOp_Manual_Ctrl_SG extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DriveTrain driveTrain = new DriveTrain();
    private Intake intake = new Intake();
    private Servo servo = new Servo();
    private Hardware hw = new Hardware();


    //////Records the button currently pressed.//////////
    private String button;

    @Override
    public void runOpMode() {

        hw.init(hardwareMap);
        driveTrain.init();
        intake.init();
        servo.init();


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        runtime.reset();
        while (opModeIsActive()) {


            ////////// Driver control ////////////
            /*
             * driverx, drivery and turn correspond to the power applied by
             * the user from left stick x, left stick y and right stick x.
             */
            double drivex = gamepad1.left_stick_x;
            double drivery = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            //Scale down force values in order to have
            drivex *= 0.8;
            drivery *= 0.8;
            turn *= 0.8;

            move(drivex, drivery, turn);


            /////////Control skystone intake///////////
            /**
             * NOTICE: Spitting out the stone is only for debugging functions.
             * NOT Production.
             */

            if (gamepad1.left_bumper) {
                button = "left bumper";
                leftIntake.setPower(1);
                rightIntake.setPower(1);
            } else if (gamepad1.right_bumper) {
                button = "right bumper";
                leftIntake.setPower(-1);
                rightIntake.setPower(-1);
            } else {
                leftIntake.setPower(0);
                rightIntake.setPower(0);
            }

            ////////Control skystone pick mechanism//////////
            if (gamepad1.dpad_right) {
                button = "dpad right";
                linearCrane.setPower(0.8);
            } else if (gamepad1.dpad_left) {
                button = "dpad left";
                linearCrane.setPower(-0.8);
            } else {
                linearCrane.setPower(0);
            }

            ///////////Controlling lift mechanism///////////
            if (gamepad1.dpad_up) {
                button = "dpad up";
                riseMotor.setPower(1);
            } else if (gamepad1.dpad_down) {
                button = "dpad down";
                riseMotor.setPower(-1);
            } else {
                riseMotor.setPower(0);
            }

            /////////Controlling stake for landfill//////////
            /**
             * NOTICE: Actually, in the coding, button a and b are corresponding to
             * the physical buttons x and y of the gamepads, and x and y in coding to
             * a and b in physical gamepads when Logitech F310 is connected to phones
             */
            if (gamepad1.a) {
                button = "x";
                stake();
            }


            ///////Controlling rotate servo///////////
            if (gamepad1.y) {
                button = "b";
                rotate();
            }

            ///////Controlling pick servo////////////
            if (gamepad1.x) {
                button = "a";
                pick();
            }

            telemetry.addData("Status", "Running...");
            telemetry.addData("Run time", runtime.toString());
            telemetry.addData("Button pressed", button);
            telemetry.update();
        }
    }
}
