// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  private SparkMax leftLeader;
  private SparkMax rightLeader;
  private SparkBaseConfig leftLeaderConfig;
  private SparkBaseConfig rightLeaderConfig;
  private DifferentialDrive differentialDrive;

  private boolean leftHasBeenInverted = false;
  private boolean rightHasBeenInverted = false;
  private boolean isTankDrive = DrivetrainConstants.IS_TANK_DRIVE;
  private double inputMultiplier = DrivetrainConstants.BASE_INPUT_MULTIPLIER;

  /**
   * The drivetrain subsystem.
   */
  public Drivetrain() {
    // SparkMax setup
    for (int i = 0; i < DrivetrainConstants.MOTOR_CONTROLLER_COUNT; i++) {
      int motorControllerHalfPoint = DrivetrainConstants.MOTOR_CONTROLLER_COUNT / 2;
      boolean isLeftMotor = i < motorControllerHalfPoint;
      
      // Intialize configs
      SparkBaseConfig config = new SparkMaxConfig()
        .inverted(
          isLeftMotor ? 
          DrivetrainConstants.LEFT_CONTROLLERS_INVERTED : 
          DrivetrainConstants.RIGHT_CONTROLLERS_INVERTED
        )
        .idleMode(
          DrivetrainConstants.SPARK_MAX_IDLE_MODE
        );

      // Create the SparkMax
      SparkMax spark = new SparkMax(i + 1, DrivetrainConstants.MOTOR_TYPE);

      // Configure follow structure
      if (isLeftMotor) {
        if (i == 0) {
          leftLeader = spark;
          leftLeaderConfig = config;
        } else {
          config.follow(leftLeader);
        }
      } else {
        if (i == motorControllerHalfPoint) {
          rightLeader = spark;
          rightLeaderConfig = config;
        } else {
          config.follow(rightLeader);
        }
      }

      // Configure the sparks
      spark.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    // DifferentialDrive setup
    differentialDrive = new DifferentialDrive(leftLeader, rightLeader);
  }

  /**
   * Sets the configuration of either the left or right motor controllers.
   * @param left whether or not the left side will be configured
   * @param setInverted the flag to set the motor controller inversion to
   */
  public void configureMotorControllers(boolean left, boolean setInverted) {
    (left ? leftLeader : rightLeader).configure(
      (left ? leftLeaderConfig : rightLeaderConfig).inverted(setInverted), 
      ResetMode.kResetSafeParameters, 
      PersistMode.kPersistParameters
    );

    if (left) {
      leftHasBeenInverted = !leftHasBeenInverted;
    } else {
      rightHasBeenInverted = !rightHasBeenInverted;
    }
  }
  public void switchDifferentialDriveType() {
    isTankDrive = !isTankDrive;
  }
  public void addToInputMultiplier(double value) {
    inputMultiplier += value;
    if (value > 1) value = 1;
    else if (value < 0) value = 0;
  }

  public void drive(double left, double right) {
    left *= inputMultiplier;
    right *= inputMultiplier;
    if (isTankDrive) differentialDrive.tankDrive(left, right);
    else differentialDrive.arcadeDrive(left, right);
  }

  public Command tankDrive(DoubleSupplier leftInputSupplier, DoubleSupplier rightInputSupplier) {
    return runEnd(
      () -> drive(leftInputSupplier.getAsDouble(), rightInputSupplier.getAsDouble()),
      () -> drive(0, 0)
    );
  }
  public Command invertSide(boolean left) {
    return runOnce(() -> configureMotorControllers(left, (left ? !leftHasBeenInverted : !rightHasBeenInverted)));
  }
  public Command modifyInputMultiplier(boolean increase) {
    return runOnce(() -> addToInputMultiplier(
      increase ? 
      DrivetrainConstants.INPUT_MULTIPLIER_ADJUSTMENT_INCREMENT : 
      -DrivetrainConstants.INPUT_MULTIPLIER_ADJUSTMENT_INCREMENT
    ));
  }
}
