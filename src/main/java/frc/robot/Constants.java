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

    public static double ROTATION_DEADBAND = .25;
    public static double STRAFING_DEADBAND = .25;  //.75
    public static double SPEED_DEADBAND = .3;
    public static double MAX_OUTPUT = .75;

    // Drivetrain motor ID ports
    public static int FRONT_LEFT_TALON_ID = 7;
    public static int REAR_LEFT_VICTOR_ID = 9;

    public static int FRONT_RIGHT_TALON_ID = 1;
    public static int REAR_RIGHT_VICTOR_ID = 3;

    // Intake arm motor ID ports
    public static int TOP_SEG_MOTOR_ID = 7;
    public static int BOTTOM_SEG_MOTOR_ID = 1;

    public static boolean IS_TANKDRIVE_SQUARED = false;
    public static boolean DOES_CHEESYDRIVE_PIVOT = false;
    public static boolean IS_ARCADEDRIVE_SQUARED = true;

    // Joystick buttons
    public static int INTAKE_GRAB_BUTTON = 1;
    public static int INTAKE_LIFT_BUTTON = 2;

    // Intake arm PID constants
    public static double INTAKE_KP = 0;
    public static double INTAKE_KI = 0;
    public static double INTAKE_KD = 0;

    public static double DESTINATION_POSITION = 0;
}
