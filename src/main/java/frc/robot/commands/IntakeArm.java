package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arms;


public class IntakeArm extends CommandBase {
    private Arms miniArm;
    private Supplier<Double> bottom, top;

    public IntakeArm(Arms arm, Supplier<Double> bottomSeg, Supplier<Double> topSeg){
        addRequirements(arm);
        this.miniArm = arm;
        this.bottom = bottomSeg;
        this.top = topSeg;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double bottomRotate = bottom.get();
        double topRotate = top.get();

        miniArm.armIntake(bottomRotate, topRotate);
    }

    @Override
    public void end(boolean interrupted) {
        miniArm.armIntake(0, 0);
    }

    @Override
    public boolean isFinished() {
    return false;
    }
}
