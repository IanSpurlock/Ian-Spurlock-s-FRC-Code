// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Handling;
import frc.robot.Constants.InputConstants;

public class Drivetrain extends SubsystemBase {
  private double leftInversionFactor = (InputConstants.INVERT_LEFT ? -1 : 1);
  private double rightInversionFactor = (InputConstants.INVERT_RIGHT ? -1 : 1);

  SparkMax leftSpark1 = new SparkMax(1, MotorType.kBrushless);
  SparkMax leftSpark2 = new SparkMax(2, MotorType.kBrushless);
  SparkMax rightSpark1 = new SparkMax(3, MotorType.kBrushless);
  SparkMax rightSpark2 = new SparkMax(4, MotorType.kBrushless);

  public void drive(double leftSpeed, double rightSpeed) {
    double[] adjustedValues = Handling.getAdjustedValues(leftSpeed, rightSpeed);

    leftSpark1.set(adjustedValues[0]);
    leftSpark2.set(adjustedValues[0]);
    rightSpark1.set(adjustedValues[1]);
    rightSpark2.set(adjustedValues[1]);
  }

  public Command command(DoubleSupplier leftSpeedSupplier, DoubleSupplier rightSpeedSupplier) {
    return runEnd(
      () -> drive(
        leftInversionFactor * leftSpeedSupplier.getAsDouble(),
        rightInversionFactor * rightSpeedSupplier.getAsDouble()
      ),
      () -> drive(
        0, 
        0
      )
    );
  }
}
