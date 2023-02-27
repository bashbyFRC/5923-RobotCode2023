/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MecanumDrivetrain;


public class DriveMecanum extends CommandBase {
  /*
   * Creates a new DriveMecanum.
   */

  private MecanumDrivetrain driveTrain;
  private Supplier<Double>  x, y, z, angle;
  private Supplier<Rotation2d> r;

  public DriveMecanum(MecanumDrivetrain drivetrain, Supplier<Double> forward, Supplier<Double> strafe, Supplier<Double> zRotation,
   Supplier<Rotation2d> rAngle, Supplier<Double> theta) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    this.driveTrain = drivetrain;
    this.x = forward;
    this.y = strafe;
    this.z = zRotation;
    this.r = rAngle;
    this.angle = theta;
  }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double xSpeed = -x.get();
    double ySpeed = y.get();
    double zRotation = z.get();
    double theta = angle.get();
    Rotation2d gyroAngle = r.get();
    
    //driveTrain.driveCartesian(xSpeed, ySpeed, zRotation);
    driveTrain.driveCartesian(xSpeed, ySpeed, zRotation, gyroAngle.times(-1));
    //driveTrain.homeBrewMecanum(xSpeed, ySpeed, zRotation, theta);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //driveTrain.driveCartesian(0, 0, 0);
    driveTrain.driveCartesian(0.0, 0.0, 0.0, r.get());	
    //driveTrain.homeBrewMecanum(0, 0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
