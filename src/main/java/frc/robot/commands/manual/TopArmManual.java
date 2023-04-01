package frc.robot.commands.manual;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TopArm;


public class TopArmManual extends CommandBase {
    private TopArm topArm;
    private Supplier<Double> retract, extend;
    private Supplier<Boolean> cubeIntake, coneIntake, cubeIntakeReleased, coneIntakeReleased;
    private boolean holdCone, holdCube, holdToggle;

    public TopArmManual(TopArm arm, Supplier<Boolean> coneIntake, Supplier<Boolean> cubeIntake,
    Supplier<Double> retract, Supplier<Double> extend,
    Supplier<Boolean> coneIntakeReleased, Supplier<Boolean> cubeIntakeReleased){
        addRequirements(arm);
        this.topArm = arm;
        this.cubeIntake = cubeIntake;
        this.coneIntake = coneIntake;
        this.retract = retract;
        this.extend = extend;
        this.coneIntakeReleased = coneIntakeReleased;
        this.cubeIntakeReleased = cubeIntakeReleased;
    }

    @Override
    public void initialize() {
        holdCone = false;
        holdCube = false;
        holdToggle = false;
    }

    @Override
    public void execute() {
        double armSpeed = extend.get() - retract.get();

        if (cubeIntake.get()) {
            topArm.releaseObject(-.75);
            holdCone = false;
            holdCube = false;
        }
        else if (coneIntake.get()) {
            topArm.releaseObject(.75);
            holdCone = false;
            holdCube = false;
        }
        else if (coneIntakeReleased.get()) {
            holdToggle = !holdToggle;
            holdCone = true;
        }
        else if (cubeIntakeReleased.get()) {
            holdToggle = !holdToggle;
            holdCube = true;
        }

        if (holdCube && holdToggle) {
            topArm.releaseObject(-.5);
        }
        else if (holdCone && holdToggle) {
            topArm.releaseObject(.65);
        }
        else {
            topArm.releaseObject(0);
        }

        /*
        else {
            topArm.releaseObject(0);
        }
        */

        topArm.move(armSpeed * .5);
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
