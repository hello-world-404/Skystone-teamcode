package org.firstinspires.ftc.teamcode.deprecated;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Teleop manual control")

public class Teleop_Manual_Control extends LinearOpMode {


	//声明硬件变量
	public DcMotor frontLeft = null, frontRight = null, backLeft = null, backRight = null;
	public DcMotor leftIntake = null, rightIntake = null;
	public DcMotor riseMotor = null;



	public CRServo linearCraneServo = null;
	public Servo rotateServo = null, pickServo = null;
	public Servo leftStake = null, rightStake = null;

	boolean isRotate, isStake, isPickup;

	String button;

	@Override
	public void runOpMode() {
		ElapsedTime runtime = new ElapsedTime();

		//Hardware map 初始化变量
		frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
		frontRight = hardwareMap.get(DcMotor.class, "frontRight");
		backLeft = hardwareMap.get(DcMotor.class, "backLeft");
		backRight = hardwareMap.get(DcMotor.class, "backRight");

		leftIntake = hardwareMap.get(DcMotor.class, "leftIntake");
		rightIntake = hardwareMap.get(DcMotor.class, "rightIntake");
		
		riseMotor = hardwareMap.get(DcMotor.class, "riseMotor");
		
		leftStake = hardwareMap.get(Servo.class, "leftStake");
		rightStake = hardwareMap.get(Servo.class, "rightStake");
		
		linearCraneServo = hardwareMap.get(CRServo.class, "linearCraneServo");
		
		rotateServo = hardwareMap.get(Servo.class, "rotateServo");
		pickServo = hardwareMap.get(Servo.class, "pickServo");
		
		
		//设置吸取电机转向
		leftIntake.setDirection(DcMotor.Direction.FORWARD);
		rightIntake.setDirection(DcMotor.Direction.REVERSE);
		
		//设置升降电机转向
		riseMotor.setDirection(DcMotor.Direction.REVERSE);
		
		//设置抓取舵机转向
		pickServo.setDirection(Servo.Direction.REVERSE);
		
		
		//设置电机/舵机初始位置
		leftIntake.setPower(0);
		rightIntake.setPower(0);

		riseMotor.setPower(0);

		leftStake.setPosition(0.3);
		rightStake.setPosition(0.3);

		rotateServo.setPosition(0);
		pickServo.setPosition(0);

		telemetry.addData("Status", "Initialized");
		telemetry.update();

		waitForStart();
		runtime.reset();

		//当驾驶员按下“开始”按钮后
		while (opModeIsActive()) {


			//计算电机动力
			calculateDrivePower();

			//控制上升机构
			rise();

			//控制吸取机构
			intake();

            //控制前后移动电机（横向杆）
            operateLCrane();

            stake();

            rotate();

            pick();

            telemetry.addData("gamepad1.left_stick_x", gamepad1.left_stick_x);
            telemetry.addData("gamepad1.left_stick_y", gamepad1.left_stick_y);
            telemetry.addData("gamepad2.right_stick_x", gamepad1.right_stick_x);
            telemetry.addData("Button pressed", button);
            telemetry.addData("Run time", runtime.toString());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
	}
	
	/**
	 * @status 已决定
	 * @return none
	 * 
	 * @do 控制地基舵机
	 * @operator Gamepad1
	 * @control Gamepad1 --> gamepad1.a (要按X) --> 检测是否卡住地基 --> 卡住地基: 松开地基 没卡住地基：卡住地基
	 * @remarks 需要测试
	 **/

	public void stake() {
		if (gamepad1.a) {
			button = "x";
			if (isStake) {
				leftStake.setPosition(0);
				rightStake.setPosition(0);
				isStake = false;
			} else {
				leftStake.setPosition(0.3);
				rightStake.setPosition(0.3);
				isStake = true;
			}
		}
	}

	
	
	/**
	 * @status 已决定
	 * @return none
	 * 
	 * @do 控制抓手旋转电机
	 * @operator Gamepad2 
	 * @control Gamepad2 --> gamepad2.x (要按A) --> 检测是否旋转至1 --> 已经旋转至1：旋转回0  未旋转至1: 旋转到 val
	 * @remarks val需要更改，需要实际测量位置
	 **/
	public void rotate() {
		if (gamepad2.x) {
			button = "a";
			if (isRotate) {
				rotateServo.setPosition(0.0);
				isRotate = false;
			} else {
				rotateServo.setPosition(0.35);
				isRotate = true;
			}
		}
	}

	/**
	 * @return none
	 * @status 已决定
	 * @do 控制抓取电机
	 * @operator Gamepad2
	 * @control Gamepad2 --> gamepad2.b (按Y) --> 检测是否抓取 --> 已抓取：松开 未抓取：抓取
	 **/

	public void pick() {
		if (gamepad2.b) {
			button = "y";
			if (isPickup) {
				pickServo.setPosition(0.0);
				isPickup = false;
			} else {
				pickServo.setPosition(0.1);
				isPickup = true;
			}
		}
	}
	
	
	/**
	 * @status 已决定
	 * @return none
	 * 
	 * @do 控制前后移动电机
	 * @operator Gamepad2
	 * @control Gamepad2 --> dpad_right 往前移动（车前方），Gamepad2 --> dpad_left 往后移动（车辆后方放置石块）
	 * @remarks LCraneServo在实际操作的时候并不稳定，需要寻找原因。
	 **/

    public void operateLCrane() {
		if (gamepad2.dpad_left) {
            button = "dpad_left";
            linearCraneServo.setPower(0.8);
            sleep(1000);
        } else if (gamepad2.dpad_right) {
            button = "dpad_right";
            linearCraneServo.setPower(-0.8);
            sleep(1000);
        } else {
			linearCraneServo.setPower(0.0);
		}
	}


    /**
	 *@status 谁来控制还有待争议。 
	 * @return none
	 * @do 控制吸取电机
	 * @operator Gamepad1
	 * @control Gamepad1 --> left_bumper 吸取, Gamepad1 --> right_bumper (吐出)
	 * @remarks 吐出只是为了防止方块卡在中间而设计的。Removed during production
	 **/
	 public void intake() {
		 if (gamepad1.left_bumper) {
			 button = "leftBumper";
			 leftIntake.setPower(-1.0);
			 rightIntake.setPower(-1.0);
		 } else if (gamepad1.right_bumper) {
			 button = "rightBumper";
			 leftIntake.setPower(1.0);
			 rightIntake.setPower(1.0);
		 } else {
			 leftIntake.setPower(0.0);
			 rightIntake.setPower(0.0);
		 }

	 }


	/**
	 * @return none
	 * @do 控制上升电机
	 * @operator Gamepad1 / Gamepad2 操作人员有待争议。
	 * @control Gamepad1/2 --> dpad_up 上升电机，Gamepad1/2 --> dpad_down 下降电机
	 * @others 考虑是否要在电机进行上升的时候抓取方块。
	 **/
	public void rise() {
		if (gamepad2.dpad_down) {
			button = "dpad_down";
			riseMotor.setPower(1.0);
		} else if (gamepad2.dpad_up) {
			button = "dpad_up";
			riseMotor.setPower(-1.0);
		} else {
			riseMotor.setPower(0.0);
		}
	}

	/**
	 * @return none
	 * @do 计算电机电力并将动力传至电机
	 * @operator Gamepad1
	 **/
	 public void calculateDrivePower(){
		float y_raw = gamepad1.left_stick_y * 0.8f;
		float x_raw = gamepad1.left_stick_x * 0.8f;
		float z_raw = gamepad1.right_stick_x * 0.8f;
		float xscale = (float) 0.75;
		float yscale = (float) 0.75;
		float zscale = (float) 0.65;
		float x = (xscale * (float) Math.pow(x_raw, 7.0) + (1- xscale) * x_raw);
		float y = -(yscale * (float) Math.pow(y_raw, 7.0) + (1 - yscale) * y_raw);
		float z = (zscale * (float) Math.pow(z_raw, 7.0) + (1 - zscale) * z_raw);

		//Mapping joystick values on motors
		float fl = x + y + z;
		float fr = x - y + z;
		float bl = -x + y + z;
		float br = -x -y + z;

		//Initialize motor values

		float[] joystickValues = new float[]{fr, bl, fl, br};
		float Max = Math.abs(fr);
		for(int index = 1; index < 4; index++){
			if(Math.abs(joystickValues[index]) > Max){
				Max = Math.abs(joystickValues[index]);
			}
		}

		if(Max > 1){
			fr /= Max;
			bl /= Max;
			fl /= Max;
			br /= Max;
		}

		frontLeft.setPower(fl);
		frontRight.setPower(fr);
		backLeft.setPower(bl);
		backRight.setPower(br);
	}
}