// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  // Instantiate motor controllers:
  private final CANSparkMax left1 = new CANSparkMax(DrivetrainConstants.LEFT_1_DEVICE_ID, DrivetrainConstants.DRIVETRAIN_MOTOR_TYPE);
  private final CANSparkMax left2 = new CANSparkMax(DrivetrainConstants.LEFT_2_DEVICE_ID, DrivetrainConstants.DRIVETRAIN_MOTOR_TYPE);
  private final CANSparkMax right1 = new CANSparkMax(DrivetrainConstants.RIGHT_1_DEVICE_ID, DrivetrainConstants.DRIVETRAIN_MOTOR_TYPE);
  private final CANSparkMax right2 = new CANSparkMax(DrivetrainConstants.RIGHT_2_DEVICE_ID, DrivetrainConstants.DRIVETRAIN_MOTOR_TYPE);

  private final DifferentialDrive drive;

  public Drivetrain() {
    // Motor controller setup:
    left1.setInverted(DrivetrainConstants.IS_LEFT_INVERTED);
    left2.setInverted(DrivetrainConstants.IS_LEFT_INVERTED);
    right1.setInverted(DrivetrainConstants.IS_RIGHT_INVERTED);
    right2.setInverted(DrivetrainConstants.IS_RIGHT_INVERTED);
    
    left2.follow(left1);
    right2.follow(right1);

    drive = new DifferentialDrive(left1, right1);
  }
  
  // Drive methods:
  public void tankDrive(double left, double right) {
    drive.tankDrive(left, right);
  }
  public void arcadeDrive(double left, double right) {
    drive.arcadeDrive(left, right);
  }
  public void stop() {
    tankDrive(0, 0);
  }
}
