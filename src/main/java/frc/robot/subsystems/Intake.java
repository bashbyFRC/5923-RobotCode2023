package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
//import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

public class Intake extends SubsystemBase {
    private WPI_TalonSRX topSegMotor, bottomSegMotor, topArmTalon;
    private WPI_VictorSPX topArmIntakeMotor, topArmVictor, liftMotor;
    private Encoder topEncoder = new Encoder(TOP_ENCODER_PORT_A, TOP_ENCODER_PORT_B);

    private ShuffleboardTab tab;

    public Intake(ShuffleboardTab tab){
        topSegMotor = new WPI_TalonSRX(TOP_SEG_MOTOR_ID);
        bottomSegMotor = new WPI_TalonSRX(BOTTOM_SEG_MOTOR_ID);
        topArmTalon = new WPI_TalonSRX(TOP_ARM_TALON);

        topArmIntakeMotor = new WPI_VictorSPX(TOP_ARM_INTAKE);
        topArmVictor = new WPI_VictorSPX(TOP_ARM_VICTOR);

        liftMotor = new WPI_VictorSPX(LIFT_MOTOR);

        topArmVictor.setInverted(true);
        topArmVictor.follow(topArmTalon);

        this.tab = tab;
        configureShuffleboardData();

        //topEncoder.setDistancePerPulse(TOP_ARM_DISTANCE_PER_PULSE);
    }

    private void configureShuffleboardData() {
        ShuffleboardLayout layout = tab.getLayout("Intake Data", BuiltInLayouts.kGrid).withPosition(0, 3);
        layout.add(this);
        
        layout.addNumber("Top Arm Encoder", () -> topEncoder.getDistance());
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
        topArmIntakeMotor.set(speed);
    }

    //Upper intake arm
    public void move(double speed){
        topArmTalon.set(speed);
    }

    public double getTopEncoderPosition() {
        return topEncoder.getDistance();
    }

    //lifting mechanism on robot
    public void lift(double speed){
        liftMotor.set(speed);
    }
}
