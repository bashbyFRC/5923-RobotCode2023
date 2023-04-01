package frc.robot.commands.manual;

import static frc.robot.Constants.*;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BottomArm;

public class BottomArmManual extends CommandBase {
    private BottomArm bottomArm;
    private Supplier<Integer> dPadAngle;

    public BottomArmManual(BottomArm bottomArm, Supplier<Integer> povAngle){
        addRequirements(bottomArm);
        this.bottomArm = bottomArm;
        this.dPadAngle = povAngle;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        int input = dPadAngle.get();
        if (input != -1) { input /= 45; }
        switch (input) {
            default: bottomArm.move(0, 0); break; // no input
            case 0: bottomArm.move(0, BOTTOM_ARM_SPEED); break; // up
            case 1: bottomArm.move(BOTTOM_ARM_SPEED, BOTTOM_ARM_SPEED); break; // up-right
            case 2: bottomArm.move(BOTTOM_ARM_SPEED, 0); break; // right
            case 3: bottomArm.move(BOTTOM_ARM_SPEED, -BOTTOM_ARM_SPEED); break; // down-right
            case 4: bottomArm.move(0, -BOTTOM_ARM_SPEED); break; // down
            case 5: bottomArm.move(-BOTTOM_ARM_SPEED, -BOTTOM_ARM_SPEED); break; // down-left
            case 6: bottomArm.move(-BOTTOM_ARM_SPEED, 0); break; // left
            case 7: bottomArm.move(-BOTTOM_ARM_SPEED, BOTTOM_ARM_SPEED); break; // up-left
        }
    }

    public void end(boolean interrupted) {
        bottomArm.move(0, 0);
    }

    @Override
    public boolean isFinished() {
    return false;
    }
}
