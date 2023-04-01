/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // Amp limits
    public static int PEAK_LIMIT = 40;
    public static int ENABLE_LIMIT = 30;

    // Scissor lift motor
    public static int LIFT_MOTOR = 0;

    // MEASUREMENTS
        // Drivetrain measurements
        public static double CENTER_TO_WHEEL_X = 21/2/39.37; // 21 in. to m
        public static double CENTER_TO_WHEEL_Y = 19/2/39.37; // 19 in. to m
        public static double COUNTS_PER_ROTATION = 4096;
        public static double WHEEL_DIAMETER = 6;
        public static double DISTANCE_PER_PULSE = ((Math.PI * WHEEL_DIAMETER) / COUNTS_PER_ROTATION) / 39.37;

        // Bottom arm
        public static double INNER_SEG_DIAMETER = 6; //in
        public static double OUTER_SEG_DIAMETER = 16; //in

    // PID CONSTANTS
        // Bottom arm
        public static double INNER_SEG_KP = 3 * 0.001;
        public static double INNER_SEG_KI = 0 * 0.001;
        public static double INNER_SEG_KD = 0;

        public static double OUTER_SEG_KP = 5 * 0.001;
        public static double OUTER_SEG_KI = 0 * 0.001;
        public static double OUTER_SEG_KD = 0;

        public static double FEED_INNER = 26;
        public static double FEED_OUTER = 5.5;

        public static double RETRACTED_INNER = 0;
        public static double RETRACTED_OUTER = 0;

        public static double PIVOT_INNER = 17.75;
        public static double PIVOT_OUTER = 62.75;

        public static double EXTENDED_INNER = 45.25;
        public static double EXTENDED_OUTER = 89.75;

    // MOTOR ID PORTS
        // Top arm motor ID ports
        public static int TOP_ARM_INTAKE = 9;
        public static int TOP_ARM_TALON = 5;
        public static int TOP_ARM_VICTOR = 7;
        // Bottom arm motor ID ports
        public static int OUTER_SEG_MOTOR_ID = 6;
        public static int INNER_SEG_MOTOR_ID = 8;
        // Drivetrain motor ID ports
        public static int FRONT_LEFT_TALON_ID = 1;
        public static int BACK_LEFT_TALON_ID = 4;
        public static int FRONT_RIGHT_TALON_ID = 3;
        public static int BACK_RIGHT_TALON_ID = 2;

    // ENCODER PORTS
        // Top arm
        public static int TOP_ENCODER_PORT_A = 0; // hex bore
        public static int TOP_ENCODER_PORT_B = 1;
        // Bottom Arm
        public static int BOTTOM_INNER_ENCODER_PORT_A = 4; // cimcoders
        public static int BOTTOM_INNER_ENCODER_PORT_B = 5; //left side
        public static int BOTTOM_OUTER_ENCODER_PORT_A = 2;
        public static int BOTTOM_OUTER_ENCODER_PORT_B = 3; //right side

    // Drivetrain multipliers
    public static double ROTATION_DEADBAND = .15;   //.25
    public static double STRAFING_DEADBAND = .15;  //.75
    public static double SPEED_DEADBAND = .15; //.3
    public static double BOOST_MULTIPLIER = .65;

    // Drivetrain logic
    public static boolean IS_TANKDRIVE_SQUARED = false;
    public static boolean DOES_CHEESYDRIVE_PIVOT = false;
    public static boolean IS_ARCADEDRIVE_SQUARED = true;

    // Auto rotate PID constants
    public static int ROTATE_KP = 0;
    public static int ROTATE_KI = 0;
    public static int ROTATE_KD = 0;

    // Autonomous drivetrain PID
    public static double AUTON_KP = 0;
    public static double AUTON_KI = 0;
    public static double AUTON_KD = 0;
    public static double AUTON_DISTANCE_SETPOINT = 3; // feet

    public static double COLLISION_THRESHOLD = 0;

    // Intake arm measurements
    public static double TOP_ARM_DISTANCE_PER_PULSE = 1/256;

    // Top arm PID constants
    public static double ARM_KP = 5 * 0.0001;
    public static double ARM_KI = 3.5 * 0.0001;
    public static double ARM_KD = 0;
    public static double ARM_INTEGRAL_TOLERANCE = 400;

    public static double ARM_IN_SETPOINT = 0;
    public static double ARM_TRANSFER_POINT_SETPOINT = 534 - 242;
    public static double ARM_MID_SETPOINT = 1550 - 242;
    public static double ARM_HIGH_SETPOINT = 2185 - 242;

    // Top arm constants
    public static double TOP_INTAKE_SPEED = .5;

    // Joystick buttons
    public static int INTAKE_GRAB_BUTTON = 1;
    public static int INTAKE_LIFT_BUTTON = 2;

    public static double BOTTOM_ARM_SPEED = 0.5;
}