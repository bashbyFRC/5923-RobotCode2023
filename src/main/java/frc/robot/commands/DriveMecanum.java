/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MecanumDrivetrain;

import static frc.robot.Constants.*;

public class DriveMecanum extends CommandBase {
  /*
   * Creates a new DriveMecanum.
   */

  private MecanumDrivetrain drivetrain;
  private Supplier<Double>  y, x, z;
  private Supplier<Boolean> motorToggle, applyBoost;
  private double error, dt, previousTimestamp, previousError, errorIntegral, errorDerivative;
  private double multiplier;
  private Supplier<Rotation2d> r;

  public DriveMecanum(MecanumDrivetrain drivetrain, Supplier<Double> forward, Supplier<Double> strafe, Supplier<Double> zRotation, Supplier<Rotation2d> rAngle, Supplier<Boolean> motorToggle, Supplier<Boolean> applyBoost) {
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
    this.y = forward;
    this.x = strafe;
    this.z = zRotation;
    this.r = rAngle;
    this.motorToggle = motorToggle;
    this.applyBoost = applyBoost;
  }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double ySpeed = -y.get();
    double xSpeed = x.get();
    double zRotation = z.get();
    Rotation2d gyroAngle = r.get();

    if (applyBoost.get()) {
      multiplier = BOOST_MULTIPLIER;
    }
    else {
      multiplier = 1;
    }

    if (motorToggle.get()) {
      drivetrain.toggleMotorMode(true);
    }
    
    drivetrain.driveCartesian(ySpeed * multiplier, xSpeed, zRotation, gyroAngle.times(-1));
    //drivetrain.driveCartesian(xSpeed, ySpeed, zRotation); // bot-oriented drive
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {;
    drivetrain.driveCartesian(0.0, 0.0, 0.0, r.get());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  private double calculateRotationSpeed(double angle, double setpoint, double kP, double kI, double kD) {
    error = setpoint - angle;
    dt = Timer.getFPGATimestamp() - previousTimestamp;
    if (Math.abs(error) < 100) { errorIntegral = error * dt; } // integral term only calculated within a radius to minimize oscillation
    errorDerivative = (error - previousError) / dt; // de/dt

    previousError = error; // update value for next iteration
    previousTimestamp = Timer.getFPGATimestamp();

    return (kP * error) + (kI * errorIntegral) + (kD * errorDerivative);
  }
}