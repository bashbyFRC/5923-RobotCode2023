package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class MecanumDrivetrain extends SubsystemBase {
  private WPI_TalonSRX frontRightMotor, rearRightMotor, rearLeftMotor, frontLeftMotor;
  private boolean type;

  private MecanumDrive mDrive;
  private ShuffleboardTab tab;

  private double cpr = 4096;
  private double whd = 6;
  private double distancePerPulse = ((Math.PI * whd) / cpr) / 12;

  public MecanumDrivetrain(ShuffleboardTab tab) {
    // Motor controllers
    frontLeftMotor = new WPI_TalonSRX(FRONT_LEFT_TALON_ID);
    rearLeftMotor = new WPI_TalonSRX(BACK_LEFT_TALON_ID);
    frontRightMotor = new WPI_TalonSRX(FRONT_RIGHT_TALON_ID);
    rearRightMotor = new WPI_TalonSRX(BACK_RIGHT_TALON_ID);

    frontLeftMotor.setInverted(false);
    rearLeftMotor.setInverted(false);
    frontRightMotor.setInverted(true);
    rearRightMotor.setInverted(true);
    
    toggleMotorMode(false);
    type = false;

    // voltage comp
    frontLeftMotor.configVoltageCompSaturation(12); // "full output" will now scale to 11 Volts for all control modes when enabled.
    frontLeftMotor.enableVoltageCompensation(true); // turn on/off feature

    rearLeftMotor.configVoltageCompSaturation(12); 
    rearLeftMotor.enableVoltageCompensation(true); 

    frontRightMotor.configVoltageCompSaturation(12); 
    frontRightMotor.enableVoltageCompensation(true); 

    rearRightMotor.configVoltageCompSaturation(12); 
    rearRightMotor.enableVoltageCompensation(true); 

    //amp limits
    frontLeftMotor.configPeakCurrentLimit(peakLimit);
    frontRightMotor.configPeakCurrentLimit(peakLimit);
    rearRightMotor.configPeakCurrentLimit(peakLimit);
    rearLeftMotor.configPeakCurrentLimit(peakLimit);

    frontLeftMotor.configPeakCurrentDuration(100);
    frontRightMotor.configPeakCurrentDuration(100);
    rearRightMotor.configPeakCurrentDuration(100);
    rearLeftMotor.configPeakCurrentDuration(100);

    frontLeftMotor.configContinuousCurrentLimit(enableLimit);
    frontRightMotor.configContinuousCurrentLimit(enableLimit);
    rearRightMotor.configContinuousCurrentLimit(enableLimit);
    rearLeftMotor.configContinuousCurrentLimit(enableLimit);

    frontLeftMotor.enableCurrentLimit(true);
    frontRightMotor.enableCurrentLimit(true);
    rearLeftMotor.enableCurrentLimit(true);
    rearRightMotor.enableCurrentLimit(true);


    mDrive = new MecanumDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

    this.tab = tab;

    configureShuffleboardData();
  }

  public void toggleMotorMode(boolean typeSwitch) {
    if (typeSwitch) {
      type = !type;
    }
    if (type) {//coast mode
      frontLeftMotor.setNeutralMode(NeutralMode.Coast);
      frontRightMotor.setNeutralMode(NeutralMode.Coast);
      rearLeftMotor.setNeutralMode(NeutralMode.Coast);
      rearRightMotor.setNeutralMode(NeutralMode.Coast);
    }
    else {
      frontLeftMotor.setNeutralMode(NeutralMode.Brake);
      frontRightMotor.setNeutralMode(NeutralMode.Brake);
      rearLeftMotor.setNeutralMode(NeutralMode.Brake);
      rearRightMotor.setNeutralMode(NeutralMode.Brake);
    }
  }

  private void configureShuffleboardData() {
    ShuffleboardLayout layout = tab.getLayout("Encoder Vals", BuiltInLayouts.kGrid).withPosition(10, 0);
    layout.add(this);
    //layout.add("Mecanum Drive Base", mDrive);
    //
    layout.addNumber("Front Left Encoder Pos", () -> getFrontLeftEncoderPosition());
    //layout.addNumber("Front Left Encoder Vel", () -> getFrontLeftEncoderVelocity());
    //
    layout.addNumber("Rear Left Encoder Pos", () -> getRearLeftEncoderPosition());
    //layout.addNumber("Rear Left Encoder Vel", () -> getRearLeftEncoderVelocity());
    //
    layout.addNumber("Front Right Encoder Pos", () -> getFrontRightEncoderPosition());
    //layout.addNumber("Front Right Encoder Vel", () -> getFrontRightEncoderVelocity());   
    //
    layout.addNumber("Rear Right Encoder Pos", () -> getRearRightEncoderPosition());
    //layout.addNumber("Rear Right Encoder Vel", () -> getRearRightEncoderVelocity());

    layout.addBoolean("Brake Mode", () -> type);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    mDrive.feed();
  }

  public void resetEncoderPositions() {
    frontLeftMotor.setSelectedSensorPosition(0);
    frontRightMotor.setSelectedSensorPosition(0);
    rearLeftMotor.setSelectedSensorPosition(0);
    rearRightMotor.setSelectedSensorPosition(0);
  }

  public double getFrontLeftEncoderPosition() { return frontLeftMotor.getSelectedSensorPosition() * distancePerPulse; }
  public double getRearLeftEncoderPosition() { return rearLeftMotor.getSelectedSensorPosition() * distancePerPulse; }
  public double getFrontRightEncoderPosition() { return frontRightMotor.getSelectedSensorPosition() * distancePerPulse; }
  public double getRearRightEncoderPosition() { return rearRightMotor.getSelectedSensorPosition() * distancePerPulse; }
  //public double getFrontLeftEncoderVelocity() { return frontLeftMotor.getSelectedSensorVelocity(); }
  //public double getFrontRightEncoderVelocity() { return frontRightMotor.getSelectedSensorVelocity(); }
  //public double getRearLeftEncoderVelocity() { return rearLeftMotor.getSelectedSensorVelocity(); }
  //public double getRearRightEncoderVelocity() { return rearRightMotor.getSelectedSensorVelocity(); }

  public void driveCartesian(double forward, double side, double zRotation, Rotation2d gyroAngle) {
    forward = MathUtil.applyDeadband(forward, SPEED_DEADBAND);
    side = MathUtil.applyDeadband(side, SPEED_DEADBAND);
    zRotation = MathUtil.applyDeadband(zRotation, ROTATION_DEADBAND);

    mDrive.driveCartesian(forward, side, zRotation, gyroAngle);
  }

  //Bot-oriented
  public void driveCartesian(double xSpeed, double ySpeed, double zRotation){
    xSpeed = MathUtil.applyDeadband(xSpeed, SPEED_DEADBAND);
    ySpeed = MathUtil.applyDeadband(ySpeed, SPEED_DEADBAND);
    zRotation = MathUtil.applyDeadband(zRotation, ROTATION_DEADBAND);

    mDrive.driveCartesian(xSpeed, ySpeed, zRotation);
  }
}