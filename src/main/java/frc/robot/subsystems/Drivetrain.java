/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new Drivetrain.
   */
  private WPI_TalonSRX leftMasterMotor;
  private WPI_TalonSRX leftSlaveMotor;
  private WPI_TalonSRX rightMasterMotor;
  private WPI_TalonSRX rightSlaveMotor;
  

  private DifferentialDrive drive;
  private ShuffleboardTab tab;

  public Drivetrain(ShuffleboardTab tab) {
     //set up drive motors
    leftMasterMotor = new WPI_TalonSRX(FRONT_LEFT_TALON_ID);
    leftSlaveMotor = new WPI_TalonSRX(REAR_LEFT_TALON_ID);
    
    leftSlaveMotor.follow(leftMasterMotor);

    rightMasterMotor = new WPI_TalonSRX(FRONT_RIGHT_TALON_ID);
    rightSlaveMotor = new WPI_TalonSRX(REAR_RIGHT_TALON_ID);

    rightMasterMotor.setInverted(true);
    rightSlaveMotor.setInverted(true);

    rightSlaveMotor.follow(rightMasterMotor);

    drive = new DifferentialDrive(leftMasterMotor, rightMasterMotor);

    this.tab = tab;

    configureShuffleboardData();
  }

  private void configureShuffleboardData() {
    ShuffleboardLayout layout = tab.getLayout("Drivetrain Data", BuiltInLayouts.kGrid).withPosition(8, 0);
    layout.add(this);
    layout.add("Drive Base", drive);

    layout.addNumber("Left Encoder Pos", () -> getLeftEncoderPosition());
    layout.addNumber("Left Encoder Vel", () -> getLeftEncoderVelocity());
    layout.addNumber("Right Encoder Pos", () -> getRightEncoderPosition());
    layout.addNumber("Right Encoder Vel", () -> getRightEncoderVelocity());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    drive.feed();
  }

  public void resetEncoderPositions() {
    leftMasterMotor.setSelectedSensorPosition(0);
    rightMasterMotor.setSelectedSensorPosition(0);
  }

  public double getLeftEncoderPosition() {
    return leftMasterMotor.getSelectedSensorPosition();
  }

  public double getRightEncoderPosition() {
    return rightMasterMotor.getSelectedSensorPosition();
  }

  public double getLeftEncoderVelocity() {
    return leftMasterMotor.getSelectedSensorVelocity();
  }

  public double getRightEncoderVelocity() {
    return rightMasterMotor.getSelectedSensorVelocity();
  }

  public void tankDrive(double left, double right) {
    drive.tankDrive(left, right, IS_TANKDRIVE_SQUARED);
  }

  public void arcadeDrive(double speed, double rotation) {

    speed = (Math.abs(speed) < SPEED_DEADBAND)?
            0 : (speed < 0)?
                (speed + SPEED_DEADBAND) / (1 - SPEED_DEADBAND):
                (speed - SPEED_DEADBAND) / (1 - SPEED_DEADBAND);
    
    rotation = (Math.abs(rotation) < ROTATION_DEADBAND)?
               0 : (rotation < 0)?
                   (rotation + ROTATION_DEADBAND) / (1 - ROTATION_DEADBAND):
                   (rotation - ROTATION_DEADBAND) / (1 -ROTATION_DEADBAND);

    drive.arcadeDrive(speed, rotation, IS_ARCADEDRIVE_SQUARED);
  }

  public void cheesyDrive(double speed, double rotation) {

    speed = (Math.abs(speed) < SPEED_DEADBAND)?
            0 : (speed < 0)?
            (speed + SPEED_DEADBAND) / (1 - SPEED_DEADBAND):
            (speed - SPEED_DEADBAND) / (1 - SPEED_DEADBAND);

    rotation = (Math.abs(rotation) < ROTATION_DEADBAND)?
               0 : (rotation < 0)?
               (rotation + ROTATION_DEADBAND) / (1 - ROTATION_DEADBAND):
               (rotation - ROTATION_DEADBAND) / (1 - ROTATION_DEADBAND);

    drive.curvatureDrive(speed, rotation, DOES_CHEESYDRIVE_PIVOT);
  }
}