package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;


public abstract class MasterOpMode extends LinearOpMode {
    public static int targetticks;
    // Motors
    public DcMotor RF;
    public DcMotor LF;
    public DcMotor RB;
    public DcMotor LB;
    public DcMotor L1;
    public DcMotor L2;
    public DcMotor rIntake;
    public DcMotor lIntake;
    public Servo LS;
    public Servo Twist;
    public BNO055IMU imu;
    public double count = 0;
    public double target;
    public void Initialize() {
        LB = hardwareMap.get(DcMotor.class, "LB");
        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        RF = hardwareMap.get(DcMotor.class, "RF");
       /*imu = hardwareMap.get(BNO055IMU.class, "imu");
       BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
       imu.initialize(parameters);
       */
        L1 = hardwareMap.get(DcMotor.class, "L1");
        L2 = hardwareMap.get(DcMotor.class, "L2");
        rIntake = hardwareMap.get(DcMotor.class, "rIntake");
        lIntake = hardwareMap.get(DcMotor.class, "lIntake");
        LS = hardwareMap.get(Servo.class, "LS");
        Twist = hardwareMap.get(Servo.class, "Twist");
    }
    public void forward(double driveInches, double power) {
        Initialize();
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double TotalTicks = 560 * driveInches / 12.566;

        targetticks = (int) TotalTicks;
        LF.setTargetPosition(targetticks);
        LB.setTargetPosition(targetticks);
        RF.setTargetPosition(targetticks);
        RB.setTargetPosition(targetticks);
        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        count = 0;
       /*while(count<power&& Math.abs(LF.getCurrentPosition())+100 < Math.abs(targetticks)){
           LF.setPower(power);
           LB.setPower(power);
           RF.setPower(power);
           RB.setPower(power);
       }*/
        while(count < power && Math.abs(targetticks) > Math.abs(LF.getCurrentPosition()) + 100){
            RF.setPower(count);
            RB.setPower(count);
            LF.setPower(count);
            LB.setPower(count);
            count +=0.02;
            telemetry.addData("count",count);
            telemetry.update();
        }
       /*while(Math.abs(targetticks)>Math.abs(LF.getCurrentPosition())) {
           if (targetticks > 0) {
               if (LF.getCurrentPosition()*2 > targetticks) {
                   while (count > 0.1 && Math.abs(targetticks)>Math.abs(LF.getCurrentPosition())) {
                       LF.setPower(count);
                       LB.setPower(count);
                       RF.setPower(count);
                       RB.setPower(count);
                       count -= 0.01;
                   }
               }
           } else if (targetticks < 0) {
               if (LF.getCurrentPosition()*2 < targetticks) {
                   while (count > 0.1&& Math.abs(targetticks)>Math.abs(LF.getCurrentPosition())) {
                       LF.setPower(count);
                       LB.setPower(count);
                       RF.setPower(count);
                       RB.setPower(count);
                       count -= 0.02;
                   }
               }
           }
       }*/
        telemetry.update();
       /*while (RB.isBusy() || LB.isBusy() || LF.isBusy() || RF.isBusy()) {
           telemetry.addData("TotalTicks", TotalTicks);
           telemetry.addData("LF", LF.getCurrentPosition());
           telemetry.addData("RF", RF.getCurrentPosition());
           telemetry.addData("LB", LB.getCurrentPosition());
           telemetry.addData("RB", RB.getCurrentPosition());
           telemetry.update();
       }*/

        //pauseMillis(10000);
    }


    public void testForward(double driveInches, double power, boolean foward) {
        double leftSpeed, rightSpeed;
        Initialize();
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double TotalTicks = 560 * driveInches / 12.566;

        targetticks = (int) TotalTicks;
        LF.setTargetPosition(targetticks);
        LB.setTargetPosition(targetticks);
        RF.setTargetPosition(targetticks);
        RB.setTargetPosition(targetticks);
        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        L1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        L1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        L1.setTargetPosition(0);
        L1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        L2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        L2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        L2.setTargetPosition(0);
        L2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        count = 0;

        while(Math.abs(targetticks) > Math.abs(LF.getCurrentPosition()) + 50){
            if (count > power){
                count = power;
            }
            double correction = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
            if (foward) {
                leftSpeed = count + (correction - target) / 15;
                rightSpeed = count - (correction - target) / 15;
            }else {
                leftSpeed = count - (correction - target) / 15;
                rightSpeed = count + (correction - target) / 15;
            }
            leftSpeed = Range.clip(leftSpeed, -1, 1);
            rightSpeed = Range.clip(rightSpeed, -1, 1);

            RF.setPower(rightSpeed);
            RB.setPower(rightSpeed);
            LF.setPower(leftSpeed);
            LB.setPower(leftSpeed);

            count +=0.02;
            telemetry.addData("target: ",target);
            telemetry.addData("correction",correction);
            telemetry.addData("targetticks: ",targetticks);
            telemetry.addData("count",count);
            telemetry.update();
        }
    }


    public void turnRight(double turnAngle, double power) {
        Initialize();
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double TotalTicks = 11.5 * turnAngle;
        count = 0;
        targetticks = (int) TotalTicks;
        LF.setTargetPosition(targetticks);
        LB.setTargetPosition(targetticks);
        RF.setTargetPosition(-targetticks);
        RB.setTargetPosition(-targetticks);
        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LF.setPower(power);
        LB.setPower(power);
        RF.setPower(power);
        RB.setPower(power);


       /*while(count<power&& Math.abs(LF.getCurrentPosition())+100 < Math.abs(targetticks)){
           LF.setPower(count);
           LB.setPower(count);
           RF.setPower(count);
           RB.setPower(count);
           count +=0.02;
       }*/

       /*while (RB.isBusy() || LB.isBusy() || LF.isBusy() || RF.isBusy()) {
           telemetry.addData("TotalTicks", TotalTicks);
           telemetry.addData("LF", LF.getCurrentPosition());
           telemetry.addData("RF", RF.getCurrentPosition());
           telemetry.addData("LB", LB.getCurrentPosition());
           telemetry.addData("RB", RB.getCurrentPosition());
           telemetry.update();
       }*/
        telemetry.update();
        //pauseMillis(10000);
    }
    public void strafeRight(double driveInches, double power) {
        Initialize();
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double TotalTicks = 50 * driveInches;

        targetticks = (int) TotalTicks;
        LF.setTargetPosition(targetticks);
        LB.setTargetPosition(-targetticks);
        RF.setTargetPosition(-targetticks);
        RB.setTargetPosition(targetticks);
        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LF.setPower(power);
        LB.setPower(power);
        RF.setPower(power);
        RB.setPower(power);
       /*while (RB.isBusy() || LB.isBusy() || LF.isBusy() || RF.isBusy()) {
           telemetry.addData("TotalTicks", TotalTicks);
           telemetry.addData("LF", LF.getCurrentPosition());
           telemetry.addData("RF", RF.getCurrentPosition());
           telemetry.addData("LB", LB.getCurrentPosition());
           telemetry.addData("RB", RB.getCurrentPosition());
           telemetry.update();
       }*/
        telemetry.update();
    }
    public void pauseMillis(double time) {
        double startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < time && opModeIsActive()) {
            idle();
        }
    }

}

