package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Hardware.leftStake;
import static org.firstinspires.ftc.teamcode.Hardware.pickServo;
import static org.firstinspires.ftc.teamcode.Hardware.rightStake;
import static org.firstinspires.ftc.teamcode.Hardware.rotateServo;

public class Servo {
    static boolean stakePosition1;
    static boolean rotatePosition1;
    static boolean pickPosition1;

    public static void stake() {
        if (stakePosition1) {
            leftStake.setPosition(0);
            rightStake.setPosition(0);
            stakePosition1 = false;
        } else {
            leftStake.setPosition(0.1);
            rightStake.setPosition(0.1);
            stakePosition1 = true;
        }
    }

    public static void rotate() {
        if (rotatePosition1) {
            rotateServo.setPosition(0);
            rotatePosition1 = false;
        } else {
            rotateServo.setPosition(0.1);
            rotatePosition1 = true;
        }
    }

    public static void pick() {
        if (pickPosition1) {
            pickServo.setPosition(0);
            pickPosition1 = false;
        } else {
            pickServo.setPosition(0.1);
            pickPosition1 = true;
        }
    }

    public void init() {
        leftStake.setPosition(0);
        rightStake.setPosition(0);
        rotateServo.setPosition(0);
        pickServo.setPosition(0);
    }
}
