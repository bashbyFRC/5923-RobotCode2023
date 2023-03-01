package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;


public class TopArmManual extends CommandBase {
    private Intake topArm;
    private Supplier<Double> retract, extend;
    private Supplier<Boolean> outtake, intake;

    public TopArmManual(Intake arm, Supplier<Boolean> intake, Supplier<Boolean> outtake, Supplier<Double> retract, Supplier<Double> extend){
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

        if (outtake.get()) { topArm.releaseObject(-0.5); }
        else if (intake.get()) { topArm.releaseObject(0.5); } 
        else { topArm.releaseObject(0); }

        topArm.move(armSpeed);

        /* 
        if(extendIn.get() > 0.0){
            topArm.topArm(1, 0);
        } else if(extendOut.get() > 0.0){
            topArm.topArm(0, -1);
        } else {
            topArm.topArm(0, 0);
        }
        */
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
