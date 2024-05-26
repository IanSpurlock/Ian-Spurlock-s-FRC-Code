// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class DrivetrainSubsystem extends SubsystemBase {
  private final CANSparkMax frontLeftMotorController = new CANSparkMax(
    DrivetrainConstants.FRONT_LEFT_MOTOR_CONTROLLER_ID, 
    DrivetrainConstants.MOTOR_TYPE
  );
  private final CANSparkMax backLeftMotorController = new CANSparkMax(
    DrivetrainConstants.BACK_LEFT_MOTOR_CONTROLLER_ID, 
    DrivetrainConstants.MOTOR_TYPE
  );
  private final CANSparkMax frontRightMotorController = new CANSparkMax(
    DrivetrainConstants.FRONT_RIGHT_MOTOR_CONTROLLER_ID, 
    DrivetrainConstants.MOTOR_TYPE
  );
  private final CANSparkMax backRightMotorController = new CANSparkMax(
    DrivetrainConstants.BACK_RIGHT_MOTOR_CONTROLLER_ID, 
    DrivetrainConstants.MOTOR_TYPE
  );

  private final DifferentialDrive differentialDrive = new DifferentialDrive(
    frontLeftMotorController,
    frontRightMotorController
  );
  
  public DrivetrainSubsystem() {
    backLeftMotorController.follow(frontLeftMotorController);
    backRightMotorController.follow(frontRightMotorController);
  }

  public void drive(double leftSpeed, double rightSpeed) {
    differentialDrive.tankDrive(leftSpeed, rightSpeed);
  }
}
