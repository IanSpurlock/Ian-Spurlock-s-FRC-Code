// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.ModuleConstants;

public class SwerveModule {
  private final TalonFX m_driveMotor;
  private final TalonFX m_turningMotor;
  private final Encoder m_driveEncoder;
  private final Encoder m_turningEncoder;

  private final PIDController m_drivePIDController = new PIDController(
    ModuleConstants.DRIVE_PID_VALUES[0],
    ModuleConstants.DRIVE_PID_VALUES[1],
    ModuleConstants.DRIVE_PID_VALUES[2]
  );
  private final ProfiledPIDController m_turningPIDController =
      new ProfiledPIDController(
          ModuleConstants.TURNING_PID_VALUES[0],
          ModuleConstants.TURNING_PID_VALUES[1],
          ModuleConstants.TURNING_PID_VALUES[2],
          new TrapezoidProfile.Constraints(DrivetrainConstants.MAX_TURNING_SPEED, DrivetrainConstants.MAX_TURNING_ACCELERATION)
      );
  private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(
    ModuleConstants.DRIVE_FEEDFORWARD_VALUES[0],
    ModuleConstants.DRIVE_FEEDFORWARD_VALUES[1]
  );
  private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(
    ModuleConstants.TURNING_FEEDFORWARD_VALUES[0],
    ModuleConstants.TURNING_FEEDFORWARD_VALUES[1]
  );

  /**
   * Constructs a SwerveModule with a drive motor, turning motor, drive encoder and turning encoder.
   *
   * @param driveMotorID CAN ID for the drive motor.
   * @param turningMotorID CAN ID for the turning motor.
   * @param driveEncoderChannelA DIO input for the drive encoder channel A
   * @param driveEncoderChannelB DIO input for the drive encoder channel B
   * @param turningEncoderChannelA DIO input for the turning encoder channel A
   * @param turningEncoderChannelB DIO input for the turning encoder channel B
   */
  public SwerveModule(
      int driveMotorID,
      int turningMotorID,
      int driveEncoderChannelA,
      int driveEncoderChannelB,
      int turningEncoderChannelA,
      int turningEncoderChannelB
  ) {
    m_driveMotor = new TalonFX(driveMotorID);
    m_turningMotor = new TalonFX(turningMotorID);

    m_driveEncoder = new Encoder(driveEncoderChannelA, driveEncoderChannelB);
    m_turningEncoder = new Encoder(turningEncoderChannelA, turningEncoderChannelB);
    m_driveEncoder.setDistancePerPulse(Math.PI * ModuleConstants.WHEEL_DIAMETER / ModuleConstants.ENCODER_RESOLUTION);
    m_turningEncoder.setDistancePerPulse(2 * Math.PI / ModuleConstants.ENCODER_RESOLUTION);

    m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(m_driveEncoder.getRate(), new Rotation2d(m_turningEncoder.getDistance()));
  }
  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(m_driveEncoder.getDistance(), new Rotation2d(m_turningEncoder.getDistance()));
  }

  public void setDesiredState(SwerveModuleState desiredState) {
    var encoderRotation = new Rotation2d(m_turningEncoder.getDistance());
    SwerveModuleState state = new SwerveModuleState();

    state.optimize(encoderRotation);
    // Scale speed based on wheel alignment.
    state.speedMetersPerSecond *= state.angle.minus(encoderRotation).getCos();

    final double driveOutput = m_drivePIDController.calculate(m_driveEncoder.getRate(), state.speedMetersPerSecond);
    final double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);

    final double turnOutput = m_turningPIDController.calculate(m_turningEncoder.getDistance(), state.angle.getRadians());
    final double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);

    m_driveMotor.set(driveOutput + driveFeedforward);
    m_turningMotor.set(turnOutput + turnFeedforward);
  }

  public static SwerveModuleState[] createStates(
    SwerveDriveKinematics kinematics, 
    AnalogGyro gyro,
    double xSpeed, 
    double ySpeed, 
    double rot, 
    boolean fieldRelative, 
    double periodSeconds
  ) {
    return kinematics.toSwerveModuleStates(
      ChassisSpeeds.discretize(
        fieldRelative 
          ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, gyro.getRotation2d()) 
          : new ChassisSpeeds(xSpeed, ySpeed, rot),
        periodSeconds
      )
    );
  }
}
