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

    //lift motor
    public static int LIFT_MOTOR = 0;
    
    // Top arm motor ID ports
    public static int TOP_ARM_INTAKE = 9;
    public static int TOP_ARM_TALON = 5;
    public static int TOP_ARM_VICTOR = 7;

    // Intake arm motor ID ports
    public static int TOP_SEG_MOTOR_ID = 6;
    public static int BOTTOM_SEG_MOTOR_ID = 8;

    // Drivetrain motor ID ports
    public static int FRONT_LEFT_TALON_ID = 4;
    public static int BACK_LEFT_TALON_ID = 1;

    public static int FRONT_RIGHT_TALON_ID = 3;
    public static int BACK_RIGHT_TALON_ID = 2;

    // Encoder ports
    public static int TOP_ENCODER_PORT_A = 0;
    public static int TOP_ENCODER_PORT_B = 1;

    public static int BOTTOM_LOW_ENCODER_PORT_A = 2;
    public static int BOTTOM_LOW_ENCODER_PORT_B = 3;

    public static int BOTTOM_HIGH_ENCODER_PORT_A = 4;
    public static int BOTTOM_HIGH_ENCODER_PORT_B = 5;

    // Drivetrain multipliers
    public static double ROTATION_DEADBAND = .15;   //.25
    public static double STRAFING_DEADBAND = .15;  //.75
    public static double SPEED_DEADBAND = .15; //.3
    public static double MAX_OUTPUT = .75;

    // Drivetrain logic
    public static boolean IS_TANKDRIVE_SQUARED = false;
    public static boolean DOES_CHEESYDRIVE_PIVOT = false;
    public static boolean IS_ARCADEDRIVE_SQUARED = true;

    // Auto rotate PID constants
    public static int ROTATE_KP = 0;
    public static int ROTATE_KI = 0;
    public static int ROTATE_KD = 0;

    // Bottom arm constants
    public static double BOTTOM_ARM_SPEED = 0.5;

    public static double BOTTOM_SEG_RETRACTED_SETPOINT = 0;
    public static double TOP_SEG_RETRACTED_SETPOINT = 0;

    public static double BOTTOM_SEG_EXTENDED_SETPOINT = 0;
    public static double TOP_SEG_EXTENDED_SETPOINT = 0;

    public static double BOTTOM_SEG_FEED_SETPOINT = 0;
    public static double TOP_SEG_FEED_SETPOINT = 0;

    public static double BOTTOM_SEG_KP = 0;
    public static double BOTTOM_SEG_KI = 0;
    public static double BOTTOM_SEG_KD = 0;

    public static double TOP_SEG_KP = 0;
    public static double TOP_SEG_KI = 0;
    public static double TOP_SEG_KD = 0;

    // Intake arm measurements
    public static double TOP_ARM_DISTANCE_PER_PULSE = 1/256;

    // Top arm PID constants
    public static double ARM_KP = 0;
    public static double ARM_KI = 0;
    public static double ARM_KD = 0;

    public static double ARM_IN_SETPOINT = 0;
    public static double ARM_TRANSFER_POINT_SETPOINT = 534;
    public static double ARM_MID_SETPOINT = 1550;
    public static double ARM_HIGH_SETPOINT = 2185;

    // Top arm constants
    public static double TOP_INTAKE_SPEED = .5;

    // Joystick buttons
    public static int INTAKE_GRAB_BUTTON = 1;
    public static int INTAKE_LIFT_BUTTON = 2;
}