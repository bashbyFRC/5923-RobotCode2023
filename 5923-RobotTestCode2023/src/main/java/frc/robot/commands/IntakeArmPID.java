// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Intake;

import static frc.robot.Constants.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeArmPID extends PIDCommand {
  
  /** Creates a new IntakeArmPID. */
  public IntakeArmPID(Intake intake, Encoder encoderMain, double setpoint) {
    super(
        // The controller that the command will use
        new PIDController(INTAKE_KP, INTAKE_KI, INTAKE_KD),
        // This should return the measurement
        () -> encoderMain.getDistance(),
        // This should return the setpoint (can also be a constant)
        () -> setpoint,
        // This uses the output
        output -> {
          intake.move(output, output * MOTOR_SPEED_RATIO);
        });
      addRequirements(intake);

      
    // Configure additional PID options by calling `getController` here.
    getController().enableContinuousInput(-180, 180);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
