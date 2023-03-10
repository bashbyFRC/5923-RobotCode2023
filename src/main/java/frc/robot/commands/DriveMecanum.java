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
  private Supplier<Double>  x, y, z;
  private Supplier<Boolean> rotate0, rotate180;
  private boolean homingMode;
  private double error, dt, previousTimestamp, previousError, errorIntegral, errorDerivative;
  private Supplier<Rotation2d> r;

  public DriveMecanum(MecanumDrivetrain drivetrain, Supplier<Double> forward, Supplier<Double> strafe, Supplier<Double> zRotation, Supplier<Rotation2d> rAngle, Supplier<Boolean> rotate0, Supplier<Boolean> rotate180) {
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
    this.x = forward;
    this.y = strafe;
    this.z = zRotation;
    this.r = rAngle;
  }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {
    homingMode = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double xSpeed = -x.get();
    double ySpeed = y.get();
    double zRotation = z.get();
    double angleSetpoint = 0;
    Rotation2d gyroAngle = r.get();

    if (rotate0.get()) {
      homingMode = !homingMode;
      angleSetpoint = 0;
    }
    else if (rotate180.get()) {
      homingMode = !homingMode;
      angleSetpoint = 180;
    }

    if (homingMode) {
      zRotation = calculateRotationSpeed(gyroAngle.getDegrees(), angleSetpoint, ROTATE_KP, ROTATE_KI, ROTATE_KD);
    }
    
    drivetrain.driveCartesian(xSpeed, ySpeed, zRotation, gyroAngle.times(-1));
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