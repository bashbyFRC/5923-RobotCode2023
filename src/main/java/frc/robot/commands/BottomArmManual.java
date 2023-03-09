package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BottomArm;


public class BottomArmManual extends CommandBase {
    private BottomArm bottomArm;
    private Supplier<Double> bottom, top;

    public BottomArmManual(BottomArm bottomArm, Supplier<Double> top, Supplier<Double> bottom){
        addRequirements(bottomArm);
        this.bottomArm = bottomArm;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        bottomArm.move(top.get(), bottom.get());
    }

    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
    return false;
    }
}
