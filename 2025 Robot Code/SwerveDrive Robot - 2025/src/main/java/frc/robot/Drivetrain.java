// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogGyro;
import frc.robot.Constants.DrivetrainConstants;

/** Represents a swerve drive style drivetrain. */
public class Drivetrain {
  private final Translation2d m_frontLeftLocation = new Translation2d(
    DrivetrainConstants.FRONT_LEFT_WHEEL_POSITION[0],
    DrivetrainConstants.FRONT_LEFT_WHEEL_POSITION[1]
  );
  private final Translation2d m_frontRightLocation = new Translation2d(
    DrivetrainConstants.FRONT_RIGHT_WHEEL_POSITION[0],
    DrivetrainConstants.FRONT_RIGHT_WHEEL_POSITION[1]
  );
  private final Translation2d m_backLeftLocation = new Translation2d(
    DrivetrainConstants.BACK_LEFT_WHEEL_POSITION[0],
    DrivetrainConstants.BACK_LEFT_WHEEL_POSITION[1]
  );
  private final Translation2d m_backRightLocation = new Translation2d(
    DrivetrainConstants.BACK_RIGHT_WHEEL_POSITION[0],
    DrivetrainConstants.BACK_RIGHT_WHEEL_POSITION[1]
  );

  private final SwerveModule m_frontLeft = new SwerveModule(
    DrivetrainConstants.FRONT_LEFT_DRIVE_MOTOR_ID, 
    DrivetrainConstants.FRONT_LEFT_TURNING_MOTOR_ID, 
    DrivetrainConstants.FRONT_LEFT_DRIVE_ENCODER_CHANNEL_A, 
    DrivetrainConstants.FRONT_LEFT_DRIVE_ENCODER_CHANNEL_B, 
    DrivetrainConstants.FRONT_LEFT_TURNING_ENCODER_CHANNEL_A, 
    DrivetrainConstants.FRONT_LEFT_TURNING_ENCODER_CHANNEL_B
  );
  private final SwerveModule m_frontRight = new SwerveModule(
    DrivetrainConstants.FRONT_RIGHT_DRIVE_MOTOR_ID, 
    DrivetrainConstants.FRONT_RIGHT_TURNING_MOTOR_ID, 
    DrivetrainConstants.FRONT_RIGHT_DRIVE_ENCODER_CHANNEL_A, 
    DrivetrainConstants.FRONT_RIGHT_DRIVE_ENCODER_CHANNEL_B, 
    DrivetrainConstants.FRONT_RIGHT_TURNING_ENCODER_CHANNEL_A, 
    DrivetrainConstants.FRONT_RIGHT_TURNING_ENCODER_CHANNEL_B
  );
  private final SwerveModule m_backLeft = new SwerveModule(
    DrivetrainConstants.BACK_LEFT_DRIVE_MOTOR_ID, 
    DrivetrainConstants.BACK_LEFT_TURNING_MOTOR_ID, 
    DrivetrainConstants.BACK_LEFT_DRIVE_ENCODER_CHANNEL_A, 
    DrivetrainConstants.BACK_LEFT_DRIVE_ENCODER_CHANNEL_B, 
    DrivetrainConstants.BACK_LEFT_TURNING_ENCODER_CHANNEL_A, 
    DrivetrainConstants.BACK_LEFT_TURNING_ENCODER_CHANNEL_B
  );
  private final SwerveModule m_backRight = new SwerveModule(
    DrivetrainConstants.BACK_RIGHT_DRIVE_MOTOR_ID, 
    DrivetrainConstants.BACK_RIGHT_TURNING_MOTOR_ID, 
    DrivetrainConstants.BACK_RIGHT_DRIVE_ENCODER_CHANNEL_A, 
    DrivetrainConstants.BACK_RIGHT_DRIVE_ENCODER_CHANNEL_B, 
    DrivetrainConstants.BACK_RIGHT_TURNING_ENCODER_CHANNEL_A, 
    DrivetrainConstants.BACK_RIGHT_TURNING_ENCODER_CHANNEL_B
  );
  private final SwerveModule[] swerveModules = new SwerveModule[]{
    m_frontLeft, 
    m_frontRight, 
    m_backLeft, 
    m_backRight
  };
  
  private final AnalogGyro m_gyro = new AnalogGyro(DrivetrainConstants.GYRO_CHANNEL);
  private final SwerveDriveKinematics m_kinematics =
      new SwerveDriveKinematics(
          m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);
  private final SwerveDriveOdometry m_odometry =
      new SwerveDriveOdometry(
          m_kinematics,
          m_gyro.getRotation2d(),
          getPositions()
      );

  public Drivetrain() {
    m_gyro.reset();
  }

  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative, double periodSeconds) {
    SwerveModuleState[] swerveModuleStates = SwerveModule.createStates(
      m_kinematics, m_gyro, xSpeed, ySpeed, rot, fieldRelative, periodSeconds
    );

    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DrivetrainConstants.MAX_DRIVE_SPEED);
    for (int s = 0; s < 4; s++) {
      swerveModules[s].setDesiredState(swerveModuleStates[s]);
    }
  }

  public void updateOdometry() {
    m_odometry.update(m_gyro.getRotation2d(), getPositions());
  }

  private SwerveModulePosition[] getPositions() {
    SwerveModulePosition[] positions = new SwerveModulePosition[4];
    for (int p = 0; p < 4; p++) {
      positions[p] = swerveModules[p].getPosition();
    }
    return positions;
  }
}
