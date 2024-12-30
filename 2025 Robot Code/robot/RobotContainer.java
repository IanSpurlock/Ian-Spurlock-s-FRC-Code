// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.InputConstants;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  Drivetrain drivetrain = new Drivetrain();
  CommandXboxController controller = new CommandXboxController(InputConstants.CONTROLLER_PORT);
  
  public RobotContainer() {
    drivetrain.setDefaultCommand(drivetrain.command(
      controller::getLeftY,
      controller::getRightY
    ));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
