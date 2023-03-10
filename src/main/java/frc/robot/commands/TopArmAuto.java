package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TopArm;
import static frc.robot.Constants.*;


public class TopArmAuto extends CommandBase {
    private TopArm topArm;
    private Supplier<Boolean> downPos, upPos;
    private double[] setpoints = {ARM_IN_SETPOINT, ARM_TRANSFER_POINT_SETPOINT, ARM_MID_SETPOINT, ARM_HIGH_SETPOINT};
    private int currentSetpoint;
    private double motorSpeed, error, errorIntegral, dt, previousError, errorDerivative, previousTimestamp;

    public TopArmAuto(TopArm arm, Supplier<Encoder> topEncoder, Supplier<Boolean> downPos, Supplier<Boolean> upPos){
        addRequirements(arm);
        this.topArm = arm;
        this.downPos = downPos;
        this.upPos = upPos;
    }

    @Override
    public void initialize() {
        previousTimestamp = Timer.getFPGATimestamp();
        currentSetpoint = 0;
    }

    @Override
    public void execute() {
        if (downPos.get() && currentSetpoint > 0) { currentSetpoint--; }
        if (upPos.get() && currentSetpoint < setpoints.length - 1) { currentSetpoint++; }
        topArm.feedCurrentSetpoint(setpoints[currentSetpoint]);

        // PID calculations
        error = setpoints[currentSetpoint] - topArm.getTopEncoderPosition();
        dt = Timer.getFPGATimestamp() - previousTimestamp;
        if (Math.abs(error) < 100) { errorIntegral = error * dt; } // integral term only calculated within a radius to minimize oscillation
        errorDerivative = (error - previousError) / dt; // de/dt

        previousError = error; // update value for next iteration
        previousTimestamp = Timer.getFPGATimestamp();

        // PID formula
        motorSpeed = (ARM_KP * error) + (ARM_KI * errorIntegral) + (ARM_KD * errorDerivative);
        topArm.move(motorSpeed);
    }

    public void end(boolean interrupted) {
        topArm.move(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}