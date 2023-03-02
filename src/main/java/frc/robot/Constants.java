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
    public static int LIFT_MOTOR = 10;
    
    // Top arm motor ID ports
    public static int TOP_ARM_INTAKE = 9;
    public static int TOP_ARM_TALON = 5;
    public static int TOP_ARM_VICTOR = 7;

    // Intake arm motor ID ports
    public static int TOP_SEG_MOTOR_ID = 6;
    public static int BOTTOM_SEG_MOTOR_ID = 8;

    // Drivetrain motor ID ports
    public static int FRONT_LEFT_TALON_ID = 4;
    public static int REAR_LEFT_TALON_ID = 1;

    public static int FRONT_RIGHT_TALON_ID = 3;
    public static int REAR_RIGHT_TALON_ID = 2;

    // Encoder ports
    public static int TOP_ENCODER_PORT_1 = 0;
    public static int TOP_ENCODER_PORT_2 = 1;

    public static int BOTTOM_LEFT_ENCODER_PORT_1 = 2;
    public static int BOTTOM_LEFT_ENCODER_PORT_2 = 3;

    public static int BOTTOM_RIGHT_ENCODER_PORT_1 = 4;
    public static int BOTTOM_RIGHT_ENCODER_PORT_2 = 5;

    // Drivetrain multipliers
    public static double ROTATION_DEADBAND = .15;   //.25
    public static double STRAFING_DEADBAND = .15;  //.75
    public static double SPEED_DEADBAND = .15; //.3
    public static double MAX_OUTPUT = .25;

    // Drivetrain logic
    public static boolean IS_TANKDRIVE_SQUARED = false;
    public static boolean DOES_CHEESYDRIVE_PIVOT = false;
    public static boolean IS_ARCADEDRIVE_SQUARED = true;

    // Auto rotate PID constants
    public static int MECANUM_KP = 0;
    public static int MECANUM_KI = 0;
    public static int MECANUM_KD = 0;

    // Intake arm PID constants
    public static double INTAKE_KP = 0;
    public static double INTAKE_KI = 0;
    public static double INTAKE_KD = 0;

    public static double DESTINATION_POSITION = 0;
    public static double MOTOR_SPEED_RATIO = 1;

    // Intake arm measurements
    public static double INTAKE_TOP_LENGTH = 24;
    public static double INTAKE_BOTTOM_LENGTH = 12;
    public static double TOP_UNITS_PER_METER = 0; 

    // Top arm PID constants
    public static double ARM_KP = 0;
    public static double ARM_KI = 0;
    public static double ARM_KD = 0;

    public static double ARM_IN_SETPOINT = 0;
    public static double ARM_LOW_SETPOINT = 0;
    public static double ARM_MID_SETPOINT = 0;
    public static double ARM_HIGH_SETPOINT = 0;

    // Top arm constants
    public static double TOP_INTAKE_SPEED = .5;

    // Joystick buttons
    public static int INTAKE_GRAB_BUTTON = 1;
    public static int INTAKE_LIFT_BUTTON = 2;
}