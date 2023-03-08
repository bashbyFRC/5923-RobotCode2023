package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TopArm;


public class TopArmManual extends CommandBase {
    private TopArm topArm;
    private Supplier<Double> retract, extend;
    private Supplier<Boolean> outtake, intake;

    public TopArmManual(TopArm arm, Supplier<Boolean> intake, Supplier<Boolean> outtake, Supplier<Double> retract, Supplier<Double> extend){
        addRequirements(arm);
        this.topArm = arm;
        this.outtake = outtake;
        this.intake = intake;
        this.retract = retract;
        this.extend = extend;

        // CONTROLS: (Intake [LB]) - (Outtake [RB]) - (Retract [LT]) - (Extend [RT])
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double armSpeed = extend.get() - retract.get();

        if (outtake.get()) { topArm.releaseObject(-0.75); }
        else if (intake.get()) { topArm.releaseObject(0.75); } 
        else { topArm.releaseObject(0); }

        topArm.move(armSpeed);
    }

    public void end(boolean interrupted) {
        topArm.move(0);
        topArm.releaseObject(0);
    }

    @Override
    public boolean isFinished() {
    return false;
    }
}
