// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BottomArm;

import static frc.robot.Constants.*;

public class BottomArmAuto extends CommandBase {
  private BottomArm bottomArm;
  private Supplier<Boolean> extendButton;
  private boolean retractArm, extendArm, feedCone;
  private double bottomSegSpeed, topSegSpeed;
  private int setpointIndex;
  private double[] bottomSetpoints = {BOTTOM_SEG_RETRACTED_SETPOINT, BOTTOM_SEG_EXTENDED_SETPOINT, BOTTOM_SEG_FEED_SETPOINT};
  private double[] topSetpoints = {TOP_SEG_RETRACTED_SETPOINT, TOP_SEG_EXTENDED_SETPOINT, TOP_SEG_FEED_SETPOINT};
  private double error, errorIntegral, dt, previousError, errorDerivative, previousTimestamp;

  /** Creates a new BottomArmAuto. */
  public BottomArmAuto(BottomArm bottomArm, Supplier<Boolean> stickOut) {
    addRequirements(bottomArm);
    this.bottomArm = bottomArm;
    this.extendButton = stickOut;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    previousError = 0;
    previousTimestamp = 0;
    setpointIndex = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (retractArm) { setpointIndex = 0; }
    else if (extendArm) { setpointIndex = 1; }
    else if (feedCone) { setpointIndex = 2; }

    bottomSegSpeed = calculateMotorOutput(bottomArm.getBottomSegEncoderPos(), bottomSetpoints[setpointIndex], BOTTOM_SEG_KP, BOTTOM_SEG_KI, BOTTOM_SEG_KD);
    topSegSpeed = calculateMotorOutput(bottomArm.getTopSegEncoderPos(), topSetpoints[setpointIndex], TOP_SEG_KP, TOP_SEG_KI, TOP_SEG_KD);

    bottomArm.move(bottomSegSpeed, topSegSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  private double calculateMotorOutput(double position, double setpoint, double kP, double kI, double kD) {
    error = setpoint - position;
    dt = Timer.getFPGATimestamp() - previousTimestamp;
    if (Math.abs(error) < 100) { errorIntegral = error * dt; } // integral term only calculated within a radius to minimize oscillation
    errorDerivative = (error - previousError) / dt; // de/dt

    previousError = error; // update value for next iteration
    previousTimestamp = Timer.getFPGATimestamp();

    return (kP * error) + (kI * errorIntegral) + (kD * errorDerivative);
  }
}