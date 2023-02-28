package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;


public class TopArmManual extends CommandBase {
    private Intake topArm;
    private Supplier<Double> extendIn, extendOut;
    private Supplier<Boolean> intakeF, intakeB;

    public TopArmManual(Intake arm, Supplier<Boolean> intakeB, Supplier<Boolean> intakeF, Supplier<Double> extendIn, Supplier<Double> extendOut){
        addRequirements(arm);
        this.topArm = arm;
        this.intakeF = intakeF;
        this.intakeB = intakeB;
        this.extendIn = extendIn;
        this.extendOut = extendOut;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(intakeF.get() == true) {
            topArm.topArmIntake(-0.5);
        } else 
        if(intakeB.get() == true) {
            topArm.topArmIntake(0.5);
        } else {
            topArm.topArmIntake(0);
        }

        topArm.topArm(-extendIn.get(), extendOut.get());

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
        
    }

    @Override
    public boolean isFinished() {
    return false;
    }
}
