package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous( name = "Autonomous", group = "")
public class Auto extends MasterOpMode {

    public DcMotor RF;
    public DcMotor LF;
    public DcMotor RB;
    public DcMotor LB;
    @Override
    public void runOpMode() {


        LB = hardwareMap.get(DcMotor.class, "LB");
        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        RF = hardwareMap.get(DcMotor.class, "RF");

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

        waitForStart();
        if (opModeIsActive()) {
            forward(15, 0.8);
            //pick
            pauseMillis(400);
            forward(32.5, 0.9);
            pauseMillis(1250);
            turnRight(-45, 0.5);
            pauseMillis(750);
            forward(7,0.9);
            pauseMillis(500);
            //drop stuff
            forward(-7,0.9);
            pauseMillis(500);
            for(int i = 0; i<5; i++) {
                turnRight(130, 0.5);
                pauseMillis(1500);
                forward(24, 0.9);
                pauseMillis(1000);
                //pick
                forward(-24, 0.9);
                pauseMillis(850);
                turnRight(-128, 0.5);
                pauseMillis(1500);
                forward(7,0.9);
                pauseMillis(500);
                //drop stuff
                forward(-7,0.9);
                pauseMillis(500);

            }

        }

    }

}

