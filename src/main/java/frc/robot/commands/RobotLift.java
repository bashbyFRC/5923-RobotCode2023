package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;


public class RobotLift extends CommandBase {
    private Intake lift;
    private Supplier<Boolean> up, down;

    public RobotLift(Intake arm, Supplier<Boolean> up, Supplier<Boolean> down){
        addRequirements(arm);
        this.lift = arm;
        this.up = up;
        this.down = down;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        if(up.get()) {lift.lift(.5); }
        if(down.get()) {lift.lift(-.5); }
        if(up.get() == false && down.get() == false) { lift.lift(0.0); }
    }

    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
    return false;
    }
}
