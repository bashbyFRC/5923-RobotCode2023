package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

public class Intake extends SubsystemBase {
    private WPI_TalonSRX topSegMotor, bottomSegMotor, topArmTalon;
    private WPI_VictorSPX topArmIntakeMotor, topArmVictor;
    private Encoder encoder = new Encoder(TOP_ENCODER_PORT_1, TOP_ENCODER_PORT_2);

    private ShuffleboardTab tab;

    public Intake(ShuffleboardTab tab){
        topSegMotor = new WPI_TalonSRX(TOP_SEG_MOTOR_ID);
        bottomSegMotor = new WPI_TalonSRX(BOTTOM_SEG_MOTOR_ID);
        topArmTalon = new WPI_TalonSRX(TOP_ARM_TALON);

        topArmIntakeMotor = new WPI_VictorSPX(TOP_ARM_INTAKE);
        topArmVictor = new WPI_VictorSPX(TOP_ARM_VICTOR);

        topArmVictor.setInverted(true);
        topArmVictor.follow(topArmTalon);

        this.tab = tab;
        configureShuffleboardData();
    }

    private void configureShuffleboardData() {
        tab.add(this);
    }

    @Override
    public void periodic() {
    }

    // Lower intake arm
    public void move(double topMotorSpeed, double bottomMotorSpeed) {
        topSegMotor.set(topMotorSpeed);
        bottomSegMotor.set(bottomMotorSpeed);
    }

    public void releaseObject(double speed){
        topArmIntakeMotor.set(TOP_INTAKE_SPEED);
    }

    //Upper intake arm
    public void move(double speed){
        topArmTalon.set(speed);
    }

    public double getTopEncoderPosition() {
        return encoder.getDistance();
    }

    
    
}
