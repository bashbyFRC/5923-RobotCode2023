package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;


public class IntakeArm extends CommandBase {
    private Intake miniArm;
    private Supplier<Double> bottom, top;

    public IntakeArm(Intake arm){
        addRequirements(arm);
        this.miniArm = arm;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double bottomRotate = bottom.get();
        double topRotate = top.get();
    }
    
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
    return false;
    }
}
