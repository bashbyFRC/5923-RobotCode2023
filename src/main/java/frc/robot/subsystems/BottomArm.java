// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BottomArm extends SubsystemBase {
  private WPI_TalonSRX topSegMotor, bottomSegMotor;
  private ShuffleboardTab tab;

  /** Creates a new BottomArm. */
  public BottomArm(ShuffleboardTab tab) {
    topSegMotor = new WPI_TalonSRX(TOP_SEG_MOTOR_ID);
    bottomSegMotor = new WPI_TalonSRX(BOTTOM_SEG_MOTOR_ID);

    topSegMotor.setNeutralMode(NeutralMode.Brake);
    bottomSegMotor.setNeutralMode(NeutralMode.Brake);

    this.tab = tab;
    configureShuffleboardData();
  }

  // Lower intake arm
  public void move(double bottomMotorSpeed, double topMotorSpeed) {
    bottomSegMotor.set(bottomMotorSpeed);
    topSegMotor.set(topMotorSpeed);
}

  private void configureShuffleboardData() {
    ShuffleboardLayout layout = tab.getLayout("Bottom Arm", BuiltInLayouts.kList);

    layout.addNumber("Top segment encoder value", () -> topSegMotor.getSelectedSensorPosition());
    layout.addNumber("Bottom segment encoder value", () -> bottomSegMotor.getSelectedSensorPosition());
  }

  public double getBottomSegEncoderPos() { return bottomSegMotor.getSelectedSensorPosition(); }
  public double getTopSegEncoderPos() { return topSegMotor.getSelectedSensorPosition(); }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}