/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// TO DO (12/13/2022):
// Increase motor power while strafing
// Gyro zeroing button (B)
// Command for pivoting to 0/90/180/270 using D-pad

package frc.robot;

import java.util.Map;

import com.kauailabs.navx.frc.AHRS;
import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  /*
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureInitialDefaultCommands();
    configureButtonBindings();
    configureShuffleboardData();
  }

  // The robot's subsystems and commands are defined here...
  /// SHUFFLEBOARD TAB ///
  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Competition Robot");
  private final SendableChooser<Command> m_chooser = new SendableChooser<Command>();
  

  /// SUBSYSTEMS ///
  private final MecanumDrivetrain mecanumDrivetrain = new MecanumDrivetrain(m_tab);
  //private final Drivetrain drivetrain = new Drivetrain(m_tab);
  private final Intake arms = new Intake(m_tab);
  private static final AHRS ahrs = new AHRS(SPI.Port.kMXP);


  /// OI DEVICES ///
  private final XboxController xbox = new XboxController(0);
  private final Joystick stick = new Joystick(1);


  /// COMMANDS ///
  //private final AutoDriveTimed m_autoDriveTimedForward = new AutoDriveTimed(mecanumDrivetrain,
   //0.5, 0.5 , 6.5, ahrs.getRotation2d(), 0.0);
  //private final DriveTank driveTank = new DriveTank(drivetrain, () -> xbox.getLeftY(), () -> xbox.getRightY());
  private final DriveMecanum fieldDrive = new DriveMecanum(mecanumDrivetrain, () -> xbox.getLeftX() + stick.getX(), ()-> xbox.getLeftY() + stick.getY(),
    ()-> xbox.getRightX() + stick.getTwist(), ()-> ahrs.getRotation2d());

  private final IntakeArm miniArm = new IntakeArm(arms, () -> xbox.getLeftY(), () -> xbox.getRightY());

  /// JOYSTICK BUTTONS ///
  JoystickButton intakeGrab = new JoystickButton(stick, INTAKE_GRAB_BUTTON);
  
  /// SHUFFLEBOARD METHODS ///
  /**
   * Use this command to define {@link Shuffleboard} buttons using a
   * {@link ShuffleboardTab} and its add() function. You can put already defined
   * Commands,
   */
  private void configureShuffleboardData() {
    Shuffleboard.selectTab(m_tab.getTitle());
    
    //m_chooser.setDefaultOption("First Course", m_autoDriveTimedForward);
    //m_chooser.addOption("Drive Reverse", m_autoDriveTimedReverse);
    m_chooser.addOption("Nothing", null);

    ShuffleboardLayout drivingStyleLayout = m_tab.getLayout("driving styles", BuiltInLayouts.kList)
    .withPosition(0, 0).withSize(2, 2)
    .withProperties(Map.of("label position", "BOTTOM"));

    drivingStyleLayout.add("Joystick Field Drive",
    new InstantCommand(() -> mecanumDrivetrain.setDefaultCommand(fieldDrive), mecanumDrivetrain));

    ShuffleboardLayout mecanumSensor = m_tab.getLayout("NavX", BuiltInLayouts.kGrid)
    .withPosition(2, 0).withSize(1, 3)
    .withProperties(Map.of("lable psition", "BOTTOM"));

    mecanumSensor.addNumber("Gyro", ()-> ahrs.getAngle()).withWidget(BuiltInWidgets.kDial);

    mecanumSensor.add("Reset",
    new InstantCommand(()-> ahrs.zeroYaw()));

    mecanumSensor.add("Calibrate",
    new InstantCommand(()-> ahrs.calibrate()));

    ShuffleboardLayout controllerLayout = m_tab.getLayout("xbox", BuiltInLayouts.kGrid)
    .withPosition(4, 0).withSize(2, 6)
    .withProperties(Map.of("label position", "BOTTOM"));
    controllerLayout.addNumber("left y", () -> -xbox.getLeftY())
    .withPosition(0, 0).withSize(2, 1).withWidget(BuiltInWidgets.kNumberBar);
    controllerLayout.addNumber("left x", () -> xbox.getLeftX())
    .withPosition(0, 1).withSize(2, 1).withWidget(BuiltInWidgets.kNumberBar);
    controllerLayout.addNumber("left trigger", () -> xbox.getLeftTriggerAxis())
    .withPosition(0, 2).withSize(2, 1).withWidget(BuiltInWidgets.kNumberBar);
    controllerLayout.addNumber("right y", () -> -xbox.getRightY())
    .withPosition(2, 0).withSize(2, 1).withWidget(BuiltInWidgets.kNumberBar);
    controllerLayout.addNumber("right x", () -> xbox.getRightX())
    .withPosition(2, 1).withSize(2, 1).withWidget(BuiltInWidgets.kNumberBar);
    controllerLayout.addNumber("right trigger", () -> xbox.getRightTriggerAxis())
    .withPosition(2, 2).withSize(2, 1).withWidget(BuiltInWidgets.kNumberBar);

    m_tab.add("Auto Chooser", m_chooser)
    .withPosition(0, 6).withSize(5, 2)
    .withWidget(BuiltInWidgets.kSplitButtonChooser);   
  }
  /**
   * Use this method to define the default commands of subsystems. 
   * Default commands are ran whenever no other commands are using a specific subsystem.
   */
  private void configureInitialDefaultCommands() {
    mecanumDrivetrain.setDefaultCommand(fieldDrive);
    arms.setDefaultCommand(miniArm);
  }
  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }

  public void displayValues() {
  SmartDashboard.putData(mecanumDrivetrain);
  }
}