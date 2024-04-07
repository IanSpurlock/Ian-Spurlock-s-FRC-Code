// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.TrajectoryConstants;

public class Drivetrain extends SubsystemBase {
  // Instantiate motor controllers:
  private final CANSparkMax left1 = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax left2 = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax right1 = new CANSparkMax(4, MotorType.kBrushless);
  private final CANSparkMax right2 = new CANSparkMax(5, MotorType.kBrushless);

  // Instantiate encoders:
  private final RelativeEncoder leftEncoder = left1.getEncoder();
  private final RelativeEncoder rightEncoder = right1.getEncoder();

  // Instantiate gyroscope:
  private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  // Instantiate trajectory stuff:
  private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(TrajectoryConstants.TRACK_WIDTH);
  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), leftEncoder.getPosition(), rightEncoder.getPosition());

  public Drivetrain() {
    // Motor controller setup:
    left2.follow(left1);
    right2.follow(right1);

    left1.setInverted(DrivetrainConstants.IS_LEFT_INVERTED);
    right1.setInverted(DrivetrainConstants.IS_RIGHT_INVERTED);
  }

  @Override
  public void periodic() {
    odometry.update(null, null); // TODO: Update code
  }
}
