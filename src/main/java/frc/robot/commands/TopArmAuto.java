package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import static frc.robot.Constants.*;


public class TopArmAuto extends CommandBase {
    private Intake topArm;
    private Supplier<Boolean> fullIn, midRung, highRung, low;
    private PIDController pid;

    public TopArmAuto(Intake arm, Supplier<Encoder> topEncoder, Supplier<Boolean> fullIn, Supplier<Boolean> midRung, Supplier<Boolean> topRung, Supplier<Boolean> low){
        addRequirements(arm);
        this.topArm = arm;
        this.fullIn = fullIn;
        this.midRung = midRung;
        this.highRung = topRung;
        this.low = low;
        this.pid = new PIDController(ARM_KP, ARM_KI, ARM_KD);
    }

    @Override
    public void initialize() {
        pid.setTolerance(5, 10);
    }

    //bruh 
    @Override
    public void execute() {
        if (fullIn.get()) {
            topArm.move(pid.calculate(topArm.getTopEncoderPosition(), ARM_IN_SETPOINT));
        }
        else if (low.get()) {
            topArm.move(pid.calculate(topArm.getTopEncoderPosition(), ARM_LOW_SETPOINT));
        }
        else if (midRung.get()) {
            topArm.move(pid.calculate(topArm.getTopEncoderPosition(), ARM_MID_SETPOINT));
        }
        else if (highRung.get()) {
            topArm.move(pid.calculate(topArm.getTopEncoderPosition(), ARM_HIGH_SETPOINT));
        }
    }

    public void end(boolean interrupted) {
        topArm.move(0);
    }

    @Override
    public boolean isFinished() {
    return pid.atSetpoint();
    }
}
