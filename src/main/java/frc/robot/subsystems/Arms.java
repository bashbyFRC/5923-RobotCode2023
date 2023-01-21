package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.ArmConstants.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.MathUtil;

//import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

public class Arms extends SubsystemBase {
    private WPI_TalonSRX topSegMotor;
    private WPI_TalonSRX bottomSegMotor;

    private ShuffleboardTab tab;

    public Arms(ShuffleboardTab tab){
        topSegMotor = new WPI_TalonSRX(topSegMotorID);
        bottomSegMotor = new WPI_TalonSRX(bottomSegMotorID);

        this.tab = tab;
        configureShuffleboardData();
    }

    private void configureShuffleboardData() {
        tab.add(this);
    }

    @Override
    public void periodic() {
    }

    public void armIntake(double bAngle, double tAngle){
        bAngle = MathUtil.applyDeadband(bAngle, rotationDeadband);
        tAngle = MathUtil.applyDeadband(tAngle, rotationDeadband);

        topSegMotor.set(tAngle);
        bottomSegMotor.set(bAngle);
    }
}
