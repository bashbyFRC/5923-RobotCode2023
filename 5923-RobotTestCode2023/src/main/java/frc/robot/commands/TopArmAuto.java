package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import static frc.robot.Constants.*;


public class TopArmAuto extends CommandBase {
    private Intake topArm;
    private Supplier<Boolean> downPos, upPos;
    private PIDController pid;
    private double[] setpoints = {ARM_IN_SETPOINT, ARM_LOW_SETPOINT, ARM_MID_SETPOINT, ARM_HIGH_SETPOINT};
    private int currentSetpoint;

    public TopArmAuto(Intake arm, Supplier<Encoder> topEncoder, Supplier<Boolean> downPos, Supplier<Boolean> upPos){
        addRequirements(arm);
        this.topArm = arm;
        this.downPos = downPos;
        this.upPos = upPos;
        this.pid = new PIDController(ARM_KP, ARM_KI, ARM_KD);
    }

    @Override
    public void initialize() {
        pid.setTolerance(5, 10);
        currentSetpoint = 0;
    }

    @Override
    public void execute() {
        if (downPos.get() && currentSetpoint > 0) { currentSetpoint--; }
        if (upPos.get() && currentSetpoint < setpoints.length - 1) { currentSetpoint++; }
        //topArm.move(pid.calculate(topArm.getTopEncoderPosition(), setpoints[currentSetpoint]));
    }

    public void end(boolean interrupted) {
        topArm.move(0);
    }

    @Override
    public boolean isFinished() {
    return pid.atSetpoint();
    }
}
