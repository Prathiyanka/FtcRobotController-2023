package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous( name = "Autonomous", group = "")
public class Auto extends MasterOpMode {

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
    @Override
    public void runOpMode() {


        LB = hardwareMap.get(DcMotor.class, "LB");
        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        RF = hardwareMap.get(DcMotor.class, "RF");

        L1 = hardwareMap.get(DcMotor.class, "L1");
        L2 = hardwareMap.get(DcMotor.class, "L2");
        rIntake = hardwareMap.get(DcMotor.class, "rIntake");
        lIntake = hardwareMap.get(DcMotor.class, "lIntake");
        LS = hardwareMap.get(Servo.class, "LS");
        Twist = hardwareMap.get(Servo.class, "Twist");

       /* robot has the wheels placed backwards so when you give
       positive power it moves backward so we reverse so this
       is not confusing in the code*/
        LF.setDirection(DcMotorSimple.Direction.REVERSE);
        LB.setDirection(DcMotorSimple.Direction.REVERSE);

        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        L1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        L1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        L1.setTargetPosition(0);
        L1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        L2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        L2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        L2.setTargetPosition(0);
        L2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Twist.setPosition(0.31);
        L1.setTargetPosition(180);
        L1.setPower(0.5);
        L2.setTargetPosition(-60);
        L2.setPower(0.5);
        LS.setPosition(0.7);
        waitForStart();
        if (opModeIsActive()) {
            rIntake.setPower(0.5);
            lIntake.setPower(-0.5);
            forward(22, 0.8);
            pauseMillis(600);
            //pick
            LS.setPosition(0.1);
            pauseMillis(100);
            rIntake.setPower(0);
            lIntake.setPower(-0);
            L2.setTargetPosition(150);
            L2.setPower(0.5);
            L1.setTargetPosition(150);
            L1.setPower(0.5);
            forward(26, 0.9);
            pauseMillis(1250);
            //arm up
            L1.setTargetPosition(1175);
            L1.setPower(0.5);
            L2.setTargetPosition(-840);
            L2.setPower(0.5);
            Twist.setPosition(0.7);
            turnRight(-45, 0.6);
            pauseMillis(900);
            forward(6,0.9);
            pauseMillis(500);

            //drop stuff
            LS.setPosition(0.7);
            pauseMillis(500);
            forward(-6,0.9);
            //pauseMillis(500);
            for(int i = 0; i<2; i++) {
                //setting lower pos
                Twist.setPosition(0.6);
                if(i == 0) {
                    L1.setTargetPosition(200);
                }else{
                    L1.setTargetPosition(100);
                }
                L1.setPower(0.5);
                L2.setTargetPosition(-500);
                L2.setPower(0.5);
                LS.setPosition(0.8);
                turnRight(135, 0.6);
                pauseMillis(1500);
                if (i == 0){
                    forward(14, 0.9);
                } else{
                    forward(15,0.9);
                }
                pauseMillis(750);
                //pick
                LS.setPosition(0.1);
                pauseMillis(500);
                //arm up
                L1.setTargetPosition(1250);
                L1.setPower(0.5);
                L2.setTargetPosition(-840);
                L2.setPower(0.5);
                Twist.setPosition(0.7);
                pauseMillis(750);
                forward (-16,0.9);
                pauseMillis(750);
                turnRight(-135, 0.6);
                pauseMillis(1400);
                forward(7,0.9);
                pauseMillis(500);
                //drop stuff
                LS.setPosition(0.7);
                pauseMillis(500);
                forward(-7,0.9);
                pauseMillis(500);

            }
            turnRight(-50,0.6);
            pauseMillis(900);
        }

    }

}
