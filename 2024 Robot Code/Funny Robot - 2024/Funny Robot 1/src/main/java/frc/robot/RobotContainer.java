// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.InputConstants;
import frc.robot.subsystems.DrivetrainSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final static int DRIVER_PORT = InputConstants.DRIVER_CONTROLLER_PORT;

  private final InputManager input = new InputManager();
  private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    input.addController(DRIVER_PORT);
    
    input.setDriverController(DRIVER_PORT, drivetrain);
    input.addBind(
      DRIVER_PORT, // Controller port
      InputManager.A, // Button
      InputManager.WHILE_TRUE, // Condition
      null // TODO: Add command
    );
  }

  public DrivetrainSubsystem getDrivetrain() {
    return drivetrain;
  }
}
