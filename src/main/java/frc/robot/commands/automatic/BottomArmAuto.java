// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.automatic;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BottomArm;

import static frc.robot.Constants.*;

public class BottomArmAuto extends CommandBase {
  private BottomArm bottomArm;
  private Supplier<Boolean> extendButton, feedButton;
  private boolean feedCone, setpointReached, changeSetpoint;
  private int defaultSetpoint, goalSetpoint, goalSetpointPrevious, currentSetpoint;
  private double[] setpointsInner = {FEED_INNER, RETRACTED_INNER, PIVOT_INNER, EXTENDED_INNER};
  private double[] setpointsOuter = {FEED_OUTER, RETRACTED_OUTER, PIVOT_OUTER, EXTENDED_OUTER};
  private double error, errorIntegral, dt, previousError, errorDerivative, previousTimestamp;

  /** Creates a new BottomArmAuto. */
  public BottomArmAuto(BottomArm bottomArm, Supplier<Boolean> extendButton, Supplier<Boolean> feedButton) {
    addRequirements(bottomArm);
    this.bottomArm = bottomArm;
    this.extendButton = extendButton;
    this.feedButton = feedButton;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    previousError = 0;
    previousTimestamp = Timer.getFPGATimestamp();

    bottomArm.resetEncoders();
    defaultSetpoint = 1;
    goalSetpoint = 1;
    goalSetpointPrevious = goalSetpoint;
    currentSetpoint = 1;
    setpointReached = true;
    feedCone = false;
    changeSetpoint = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (extendButton.get()) {
      goalSetpoint = 3;
      currentSetpoint = 3;
    }
    else{
      goalSetpoint = defaultSetpoint;
    }

    if (feedButton.get()) {
      coneSwitch();
    }

    setpointReached = setpointReached(); // determined from encoder readings

    // when currentSetpoint should change
    if (goalSetpoint != goalSetpointPrevious || setpointReached && currentSetpoint != goalSetpoint) { changeSetpoint = true; }

    if (currentSetpoint < goalSetpoint && changeSetpoint) { currentSetpoint++; }
    else if (currentSetpoint > goalSetpoint && changeSetpoint) { currentSetpoint--; }
    else if (currentSetpoint == goalSetpoint && setpointReached) { bottomArm.move(0, 0); }
    else if (setpointReached == false) {
      double innerOutput = calculateMotorOutput(bottomArm.getInnerSegEncoderPos(), setpointsInner[currentSetpoint], INNER_SEG_KP, INNER_SEG_KI, INNER_SEG_KD);
      double outerOutput = calculateMotorOutput(bottomArm.getOuterSegEncoderPos(), setpointsOuter[currentSetpoint], OUTER_SEG_KP, OUTER_SEG_KI, OUTER_SEG_KD);
      bottomArm.move(innerOutput, outerOutput);
    }

    bottomArm.feedShuffleboardValues(feedCone, currentSetpoint, goalSetpoint, defaultSetpoint, setpointsInner[currentSetpoint], setpointsOuter[currentSetpoint], setpointReached);
    goalSetpointPrevious = goalSetpoint;
    changeSetpoint = false;
  }

  private void coneSwitch() {
    feedCone = !feedCone;
    if (feedCone) {
      defaultSetpoint = 0;
    }
    else {
      defaultSetpoint = 1;
    }
  }

  private boolean setpointReached() {
    return (Math.abs(bottomArm.getInnerSegEncoderPos() - setpointsInner[currentSetpoint]) <= 5) && (Math.abs(bottomArm.getOuterSegEncoderPos() - setpointsOuter[currentSetpoint]) <= 5);
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