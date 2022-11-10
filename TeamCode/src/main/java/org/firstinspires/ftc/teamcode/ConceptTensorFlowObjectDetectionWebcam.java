/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * This 2022-2023 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine which image is being presented to the robot.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "AutonomousRight", group = "")
public class ConceptTensorFlowObjectDetectionWebcam extends MasterOpMode {

    /*
     * Specify the source for the Tensor Flow Model.
     * If the TensorFlowLite object model is included in the Robot Controller App as an "asset",
     * the OpMode must to load it using loadModelFromAsset().  However, if a team generated model
     * has been downloaded to the Robot Controller's SD FLASH memory, it must to be loaded using loadModelFromFile()
     * Here we assume it's an Asset.    Also see method initTfod() below .
     */
    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";
    // private static final String TFOD_MODEL_FILE  = "/sdcard/FIRST/tflitemodels/CustomTeamModel.tflite";


    private static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            " AWktGC7/////AAABmWKXUU093URljMcDhIGHTBIugYP4MeuPxsZOHvXTJ1485UQh3wRflKKg00bzx405aB2YMMJEyw29YyMVwy1etB2GP4f+fyxFB2awpLUn/DJvGqg0XWq1UHhTx3bYVeeUknjDzyEl6Dgnvcht4W+xPwP0lFE4bMs636PtqZD3JCrFi3a6x8KFb5V3SXqaM6xwTeUuzzm/BeFMFYYJinA2POsyLEg/SWwf3mM/KmFiuIT+fvutHpthZ7+YPDgGbKnqa89ErtbbmKyrl8Lwlro9l2akeip/lJiXEcTIPmbVj5rxZbdLqS3q0Ltu6avyfLQGY2z30CMh4QFZMlFNWT2FPQ1GIg2HqZ19IVzc6Y9Yhg4Z";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;
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
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can increase the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1.0, 16.0/9.0);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

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
        LS.setPosition(0.1);
        waitForStart();

        if (opModeIsActive()) {
            long Start = System.currentTimeMillis();
            long Current = 0;
            String label = "3 Panel";
            while (Current < 1000) {
                Current = System.currentTimeMillis() - Start;
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Objects Detected", updatedRecognitions.size());

                        // step through the list of recognitions and display image position/size information for each one
                        // Note: "Image number" refers to the randomized image orientation/number
                        for (Recognition recognition : updatedRecognitions) {
                            double col = (recognition.getLeft() + recognition.getRight()) / 2 ;
                            double row = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                            double width  = Math.abs(recognition.getRight() - recognition.getLeft()) ;
                            double height = Math.abs(recognition.getTop()  - recognition.getBottom()) ;

                            telemetry.addData(""," ");
                            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100 );
                            telemetry.addData("- Position (Row/Col)","%.0f / %.0f", row, col);
                            telemetry.addData("- Size (Width/Height)","%.0f / %.0f", width, height);
                            label = recognition.getLabel();
                        }
                        telemetry.update();
                    }
                }
            }
            tfod.deactivate();
            rIntake.setPower(-1);
            lIntake.setPower(1);
            L2.setTargetPosition(150);
            L2.setPower(0.5);
            L1.setTargetPosition(150);
            L1.setPower(0.5);
            forward(22, 0.8);
            pauseMillis(600);
            //pick
            //LS.setPosition(0.1);
            //pauseMillis(100);
            //rIntake.setPower(0);
            //lIntake.setPower(-0);

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
            rIntake.setPower(0);
            lIntake.setPower(0);
            //pauseMillis(500);
            for(int i = 0; i<2; i++) {
                //setting lower pos
                Twist.setPosition(0.6);
                if(i == 0) {
                    L1.setTargetPosition(150);
                }else{
                    L1.setTargetPosition(50);
                }
                L1.setPower(0.5);
                L2.setTargetPosition(-500);
                L2.setPower(0.5);
                LS.setPosition(0.8);
                turnRight(135, 0.6);
                pauseMillis(1500);
                if (i == 0){
                    forward(15, 0.9);
                } else{
                    forward(16.5,0.9);
                }
                pauseMillis(750);
                //pick
                LS.setPosition(0.1);
                pauseMillis(500);
                //arm up
                L1.setTargetPosition(1265);
                L1.setPower(0.5);
                L2.setTargetPosition(-840);
                L2.setPower(0.5);
                Twist.setPosition(0.7);
                pauseMillis(750);
                if(i == 0) {
                    forward(-17, 0.9);
                }else{
                    forward(-17.5,0.9);
                }
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
            L1.setTargetPosition(-250);
            L1.setPower(0.5);
            L2.setTargetPosition(-500);
            L2.setPower(0.5);
            if (label.equals("1 Bolt")){
                forward(20,0.9);
            }else if(label.equals("2 Bulb")){
                forward(-3, 0.9);
            }else{
                forward(-24, 0.9);
            }
            pauseMillis(2000);
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "camera");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        // tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
    }
}
