package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class MecanumDrivetrain extends SubsystemBase {
  private WPI_TalonSRX frontRightMotor, rearRightMotor, rearLeftMotor, frontLeftMotor;

  private MecanumDrive mDrive;
  private ShuffleboardTab tab;

  public MecanumDrivetrain(ShuffleboardTab tab) {
    //Mecanum Drive motors
    frontLeftMotor = new WPI_TalonSRX(FRONT_LEFT_TALON_ID);
    rearLeftMotor = new WPI_TalonSRX(REAR_LEFT_TALON_ID);
    frontRightMotor = new WPI_TalonSRX(FRONT_RIGHT_TALON_ID);
    rearRightMotor = new WPI_TalonSRX(REAR_RIGHT_TALON_ID);

    frontLeftMotor.setInverted(false);
    rearLeftMotor.setInverted(false);
    frontRightMotor.setInverted(true);
    rearRightMotor.setInverted(true);

    mDrive = new MecanumDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

    this.tab = tab;

    configureShuffleboardData();
  }

  private void configureShuffleboardData() {
    ShuffleboardLayout layout = tab.getLayout("Drivetrain Data", BuiltInLayouts.kGrid).withPosition(10, 0);
    layout.add(this);
    layout.add("Mecanum Drive Base", mDrive);
    //
    layout.addNumber("Front Left Encoder Pos", () -> getFrontLeftEncoderPosition());
    layout.addNumber("Front Left Encoder Vel", () -> getFrontLeftEncoderVelocity());
    //
    layout.addNumber("Rear Left Encoder Pos", () -> getRearLeftEncoderPosition());
    layout.addNumber("Rear Left Encoder Vel", () -> getRearLeftEncoderVelocity());
    //
    layout.addNumber("Front Right Encoder Pos", () -> getFrontRightEncoderPosition());
    layout.addNumber("Front Right Encoder Vel", () -> getFrontRightEncoderVelocity());   
    //
    layout.addNumber("Rear Right Encoder Pos", () -> getRearRightEncoderPosition());
    layout.addNumber("Rear Right Encoder Vel", () -> getRearRightEncoderVelocity());

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

  public double getFrontLeftEncoderPosition() { return frontLeftMotor.getSelectedSensorPosition(); }
  public double getRearLeftEncoderPosition() { return rearLeftMotor.getSelectedSensorPosition(); }
  public double getFrontRightEncoderPosition() { return frontRightMotor.getSelectedSensorPosition(); }
  public double getRearRightEncoderPosition() { return rearRightMotor.getSelectedSensorPosition(); }
  public double getFrontLeftEncoderVelocity() { return frontLeftMotor.getSelectedSensorVelocity(); }
  public double getFrontRightEncoderVelocity() { return frontRightMotor.getSelectedSensorVelocity(); }
  public double getRearLeftEncoderVelocity() { return rearLeftMotor.getSelectedSensorVelocity(); }
  public double getRearRightEncoderVelocity() { return rearRightMotor.getSelectedSensorVelocity(); }

  // Robot orientated drive I think
  public void driveCartesian(double xSpeed, double ySpeed, double zRotation, Rotation2d gyroAngle){
    xSpeed = MathUtil.applyDeadband(xSpeed, SPEED_DEADBAND);
    ySpeed = MathUtil.applyDeadband(ySpeed, SPEED_DEADBAND);
    zRotation = MathUtil.applyDeadband(zRotation, ROTATION_DEADBAND);

    mDrive.driveCartesian(xSpeed, ySpeed, zRotation, gyroAngle);
  }

  //Bot orientated
  public void driveCartesian(double xSpeed, double ySpeed, double zRotation){
    xSpeed = MathUtil.applyDeadband(xSpeed, SPEED_DEADBAND);
    ySpeed = MathUtil.applyDeadband(ySpeed, SPEED_DEADBAND);
    zRotation = MathUtil.applyDeadband(zRotation, ROTATION_DEADBAND);

    mDrive.driveCartesian(xSpeed, ySpeed, zRotation);
  }

  public void homeBrewMecanum(double xSpeed, double ySpeed, double zRotation, double theta){
    xSpeed = MathUtil.applyDeadband(xSpeed, SPEED_DEADBAND);
    ySpeed = MathUtil.applyDeadband(ySpeed, SPEED_DEADBAND);
    zRotation = MathUtil.applyDeadband(zRotation, ROTATION_DEADBAND);
    theta = Math.atan2(ySpeed, xSpeed);

    frontLeftMotor.set(  zRotation);
    frontRightMotor.set(  zRotation);
    rearLeftMotor.set(  zRotation);
    rearRightMotor.set( zRotation);
    }
  }
