package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.lang.Math;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "TeleOps Code", group = "")
public class TeleOpsProgram extends LinearOpMode {

    public DcMotor RF;
    public DcMotor LF;
    public DcMotor RB;
    public DcMotor LB;
    public DcMotor L1;
    public DcMotor L2;
    public Servo RS;
    public Servo LS;
    public Servo Twist;


    //private Robot robot;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {


        LB = hardwareMap.get(DcMotor.class, "LB");
        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        RF = hardwareMap.get(DcMotor.class, "RF");

        L1 = hardwareMap.get(DcMotor.class, "L1");
        L2 = hardwareMap.get(DcMotor.class, "L2");
        RS = hardwareMap.get(Servo.class, "RS");
        LS = hardwareMap.get(Servo.class, "LS");
        Twist = hardwareMap.get(Servo.class, "Twist");

        LF.setDirection(DcMotorSimple.Direction.REVERSE);
        LB.setDirection(DcMotorSimple.Direction.REVERSE);
        L1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        if (opModeIsActive()) {

            while (opModeIsActive()) {



                // Mecanum drive is controlled with three axes: drive (front-and-back),
                // strafe (left-and-right), and twist (rotating the whole chassis).
                double drive  = gamepad1.left_stick_y * -1;
                double strafe = gamepad1.left_stick_x;
                double twist  = gamepad1.right_stick_x;

                /*
                 * If we had a gyro and wanted to do field-oriented control, here
                 * is where we would implement it.
                 *
                 * The idea is fairly simple; we have a robot-oriented Cartesian (x,y)
                 * coordinate (strafe, drive), and we just rotate it by the gyro
                 * reading minus the offset that we read in the init() method.
                 * Some rough pseudocode demonstrating:
                 *
                 * if Field Oriented Control:
                 *     get gyro heading
                 *     subtract initial offset from heading
                 *     convert heading to radians (if necessary)
                 *     new strafe = strafe * cos(heading) - drive * sin(heading)
                 *     new drive  = strafe * sin(heading) + drive * cos(heading)
                 *
                 * If you want more understanding on where these rotation formulas come
                 * from, refer to
                 * https://en.wikipedia.org/wiki/Rotation_(mathematics)#Two_dimensions
                 */

                // You may need to multiply some of these by -1 to invert direction of
                // the motor.  This is not an issue with the calculations themselves.
                double[] speeds = {
                        (drive + strafe + twist),
                        (drive - strafe - twist),
                        (drive - strafe + twist),
                        (drive + strafe - twist)
                };

                // Because we are adding vectors and motors only take values between
                // [-1,1] we may need to normalize them.

                // Loop through all values in the speeds[] array and find the greatest
                // *magnitude*.  Not the greatest velocity.
                double max = Math.abs(speeds[0]);
                for(int i = 0; i < speeds.length; i++) {
                    if ( max < Math.abs(speeds[i]) ) max = Math.abs(speeds[i]);
                }

                // If and only if the maximum is outside of the range we want it to be,
                // normalize all the other speeds based on the given speed value.
                if (max > 1) {
                    for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
                }

                // apply the calculated values to the motors.
                LF.setPower(speeds[0]);
                RF.setPower(speeds[1]);
                LB.setPower(speeds[2]);
                RB.setPower(speeds[3]);
                if(gamepad2.left_stick_y>0.1||gamepad2.left_stick_y<-0.1){
                    if(gamepad2.left_stick_y>0.1){
                        L1.setTargetPosition((int)(L1.getCurrentPosition() + 10));
                        L1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        L1.setPower(0.5);
                    }
                    if(gamepad2.left_stick_y<-0.1){
                        L1.setTargetPosition((int)(L1.getCurrentPosition() + 10));
                        L1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        L1.setPower(0.5);
                    }
                } else{
                    L1.setTargetPosition(L1.getCurrentPosition());
                    L1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    L1.setPower(0.5);
                }

                if(gamepad2.a){
                    LS.setPosition(0.6);
                    RS.setPosition(0);
                }
                if(gamepad2.x){
                    LS.setPosition(0);
                    RS.setPosition(0.5);

                }
                if(gamepad2.right_stick_y>0.1||gamepad2.right_stick_y<-0.1){
                    if(gamepad2.right_stick_y>0.1){
                        Twist.setPosition(Twist.getPosition()+0.001);
                    }
                    if(gamepad2.right_stick_y<-0.1){
                        Twist.setPosition(Twist.getPosition()-0.001);
                    }
                }
                telemetry.addData("RS:",RS.getPosition());
                telemetry.addData("LS:",LS.getPosition());
                telemetry.update();

            }
        }

        telemetry.update();
    }

}

