package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TopArm;


public class IntakeArmManual extends CommandBase {
    private TopArm miniArm;
    private Supplier<Double> bottom, top;

    public IntakeArmManual(TopArm arm, Supplier<Double> top, Supplier<Double> bottom){
        addRequirements(arm);
        this.miniArm = arm;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        miniArm.move(top.get(), bottom.get());
    }

    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
    return false;
    }
}
