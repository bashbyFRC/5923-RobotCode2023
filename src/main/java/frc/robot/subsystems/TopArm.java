package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

public class TopArm extends SubsystemBase {
    private WPI_TalonSRX topArmTalon;
    private WPI_VictorSPX topArmIntakeMotor, topArmVictor, liftMotor;
    private DigitalInput topChannelA = new DigitalInput(TOP_ENCODER_PORT_A);
    private DigitalInput topChannelB = new DigitalInput(TOP_ENCODER_PORT_B);
    private Encoder topEncoder = new Encoder(topChannelA, topChannelB, false, EncodingType.k4X);

    private ShuffleboardTab tab;

    public TopArm(ShuffleboardTab tab){
        topArmTalon = new WPI_TalonSRX(TOP_ARM_TALON);

        topArmIntakeMotor = new WPI_VictorSPX(TOP_ARM_INTAKE);
        topArmVictor = new WPI_VictorSPX(TOP_ARM_VICTOR);

        liftMotor = new WPI_VictorSPX(LIFT_MOTOR);

        topArmVictor.setInverted(true);
        topArmVictor.follow(topArmTalon);

        this.tab = tab;
        configureShuffleboardData();

        topEncoder.setDistancePerPulse(TOP_ARM_DISTANCE_PER_PULSE/256.);
        topEncoder.setMinRate(0.1);
    }

    private void configureShuffleboardData() {
        ShuffleboardLayout layout = tab.getLayout("Top arm encoder", BuiltInLayouts.kGrid).withPosition(0, 3).withSize(1,1);
        layout.add(this);

        layout.addNumber("Top Arm Encoder", () -> getTopEncoderPosition());
        layout.add("Reset encoder", new InstantCommand(() -> topEncoder.reset()));
    }

    @Override
    public void periodic() {
    }

    public void releaseObject(double speed) {
        topArmIntakeMotor.set(speed);
    }

    //Upper intake arm
    public void move(double speed) {
        topArmTalon.set(speed);
    }

    public double getTopEncoderPosition() {
        return topEncoder.get();
    }
    
    //lifting mechanism on robot
    public void lift(double speed) {
        liftMotor.set(speed);
    }
}
