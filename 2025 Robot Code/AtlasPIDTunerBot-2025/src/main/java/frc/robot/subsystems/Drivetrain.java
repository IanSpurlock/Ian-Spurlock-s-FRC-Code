// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  private SparkMax leftSpark1 = new SparkMax(DrivetrainConstants.LEFT_SPARK_1_ID, MotorType.kBrushless);
  private SparkMax leftSpark2 = new SparkMax(DrivetrainConstants.LEFT_SPARK_2_ID, MotorType.kBrushless);
  private SparkMax rightSpark1 = new SparkMax(DrivetrainConstants.RIGHT_SPARK_1_ID, MotorType.kBrushless);
  private SparkMax rightSpark2 = new SparkMax(DrivetrainConstants.RIGHT_SPARK_2_ID, MotorType.kBrushless);

  private RelativeEncoder encoder = leftSpark1.getEncoder();

  private double[] pidParameters;
  private double tolerance = DrivetrainConstants.INITIAL_COMPLETION_TOLERANCE;
  private int selectedParameter = 0;
  private int pidMultiplier = DrivetrainConstants.START_PID_REVERSED ? -1 : 1;
  private PIDController pid;

  public Drivetrain() {
    resetParameters();
  }

  public void setPIDParameter(double delta) {
    pidParameters[selectedParameter] += delta;
    if (pidParameters[selectedParameter] < 0) pidParameters[selectedParameter] = 0;
    displayPIDParameter();
  }
  public void resetParameters() {
    pidParameters = new double[]{
      DrivetrainConstants.INITIAL_KP,
      DrivetrainConstants.INITIAL_KI,
      DrivetrainConstants.INITIAL_KD
    };
    SmartDashboard.putNumber("kP", pidParameters[0]);
    SmartDashboard.putNumber("kI", pidParameters[1]);
    SmartDashboard.putNumber("kD", pidParameters[2]);
  }
  private void displayPIDParameter() {
    SmartDashboard.putNumber(getKString(), pidParameters[selectedParameter]);
  }
  private String getKString() {
    return "k" + (selectedParameter == 0 ? "P" : selectedParameter == 1 ? "I" : "D");
  }

  public void selectNextParameter() {
    if (selectedParameter == 2) selectedParameter = 0;
    else selectedParameter++;

    SmartDashboard.putString("Selected PID Parameter", getKString());
  }

  public void reversePID() {
    pidMultiplier *= -1;
  }

  public void drive(double speed) {
    drive(speed, speed);
  }
  public void drive(double left, double right) {
    leftSpark1.set(-left);
    leftSpark2.set(-left);
    rightSpark1.set(right);
    rightSpark2.set(right);
    SmartDashboard.putNumber("Encoder Value In Dist.", encoder.getPosition() * DrivetrainConstants.RATIO_FROM_DISTANCE_TO_ENCODER);
  }
  
  public Command driveDistance(double targetDistance) {
    if (pid != null) pid.close();
    encoder.setPosition(0);

    pid = new PIDController(pidParameters[0], pidParameters[1], pidParameters[2]);
    pid.setTolerance(tolerance);

    return run(() -> drive(pidMultiplier * pid.calculate(
      encoder.getPosition() * DrivetrainConstants.RATIO_FROM_DISTANCE_TO_ENCODER, 
      targetDistance
    )))
      .until(pid::atSetpoint)
      .andThen(runOnce(() -> drive(0)));
  }

  public Command callPIDParameterChange(double delta) {
    return runOnce(() -> setPIDParameter(delta));
  }
}
