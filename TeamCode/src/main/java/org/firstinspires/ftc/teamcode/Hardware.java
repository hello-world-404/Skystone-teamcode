package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {
    public static DcMotor leftFront, rightFront, leftBack, rightBack;
    public static DcMotor leftIntake, rightIntake, riseMotor;

    public static Servo leftStake, rightStake;
    public static CRServo linearCrane;
    public static Servo rotateServo, pickServo;


    public void init(HardwareMap map) {

        //Drive motors
        leftFront = map.get(DcMotor.class, "leftFront");
        rightFront = map.get(DcMotor.class, "rightFront");
        leftBack = map.get(DcMotor.class, "leftBack");
        rightBack = map.get(DcMotor.class, "rightBack");


        //Intake and rise motors
        leftIntake = map.get(DcMotor.class, "leftIntake");
        rightIntake = map.get(DcMotor.class, "rightIntake");

        //Servos
        leftStake = map.get(Servo.class, "leftStake");
        rightStake = map.get(Servo.class, "rightStake");

        //Linear crane servo
        linearCrane = map.get(CRServo.class, "linearCrane");

        //Rotate and pick servos
        rotateServo = map.get(Servo.class, "rotateServo");
        pickServo = map.get(Servo.class, "pickServo");
    }
}
