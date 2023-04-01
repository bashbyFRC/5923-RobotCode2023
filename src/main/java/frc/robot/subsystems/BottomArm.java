// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.FloatArraySerializer;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BottomArm extends SubsystemBase {
  private WPI_TalonSRX outerSegMotor, innerSegMotor;
  private ShuffleboardTab tab;
  private int goalSetpoint, currentSetpoint, defaultSetpoint;
  private double innerSetpoint, outerSetpoint;
  private boolean setpointReached;

  private Encoder innerSegEncoder = new Encoder(BOTTOM_INNER_ENCODER_PORT_A, BOTTOM_INNER_ENCODER_PORT_B, true, EncodingType.k2X);
  private Encoder outerSegEncoder = new Encoder(BOTTOM_OUTER_ENCODER_PORT_A, BOTTOM_OUTER_ENCODER_PORT_B, false, EncodingType.k2X);
  private boolean feedCone;

  /** Creates a new BottomArm. */
  public BottomArm(ShuffleboardTab tab) {
    outerSegMotor = new WPI_TalonSRX(OUTER_SEG_MOTOR_ID);
    innerSegMotor = new WPI_TalonSRX(INNER_SEG_MOTOR_ID);

    innerSegMotor.setInverted(true);

    innerSegEncoder.setDistancePerPulse(1 / (INNER_SEG_DIAMETER * Math.PI));
    outerSegEncoder.setDistancePerPulse(1 / (OUTER_SEG_DIAMETER * Math.PI));

    outerSegMotor.setNeutralMode(NeutralMode.Brake);
    innerSegMotor.setNeutralMode(NeutralMode.Brake);

    outerSegMotor.configOpenloopRamp(.6);
    outerSegMotor.configClosedloopRamp(1.5);

    innerSegMotor.configOpenloopRamp(.6);
    innerSegMotor.configClosedloopRamp(1.5);
    
    this.tab = tab;
    configureShuffleboardData();
  }

  // Lower intake arm
  public void move(double innerSpeed, double outerSpeed) {
    innerSegMotor.set(innerSpeed);
    outerSegMotor.set(outerSpeed);
}
  public void resetEncoders() {
    innerSegEncoder.reset();
    outerSegEncoder.reset();
  }

  private void configureShuffleboardData() {
    ShuffleboardLayout layout = tab.getLayout("Bottom Arm", BuiltInLayouts.kList);

    layout.addNumber("Inner segment encoder value", () -> getInnerSegEncoderPos());
    layout.add("Reset inner segment encoder", new InstantCommand(() -> innerSegEncoder.reset()));
    layout.addNumber("Outer segment encoder value", () -> getOuterSegEncoderPos());
    layout.add("Reset outer segment encoder", new InstantCommand(() -> outerSegEncoder.reset()));
    layout.addBoolean("Feed Cone", () -> feedCone);
    layout.addInteger("currentSetpoint", () -> currentSetpoint);
    layout.addInteger("goalSetpoint", () -> goalSetpoint);
    layout.addInteger("defaultSetpoint", () -> defaultSetpoint);
    layout.addDouble("Inner setpoint", () -> innerSetpoint);
    layout.addDouble("Outer setpoint", () -> outerSetpoint);
    layout.addBoolean("setpointReached", () -> setpointReached);
  }

  public void feedShuffleboardValues(boolean feedCone, int currentSetpoint, int goalSetpoint, int defaultSetpoint, double innerSetpoint, double outerSetpoint, boolean setpointReached) {
    this.feedCone = feedCone;
    this.currentSetpoint = currentSetpoint;
    this.goalSetpoint = goalSetpoint;
    this.defaultSetpoint = defaultSetpoint;
    this.innerSetpoint = innerSetpoint;
    this.outerSetpoint = outerSetpoint;
    this.setpointReached = setpointReached;
  }

  public double getInnerSegEncoderPos() { return innerSegEncoder.getDistance(); }
  public double getOuterSegEncoderPos() { return outerSegEncoder.getDistance(); }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    innerSegMotor.feed();
    outerSegMotor.feed();
  }
}