// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  Drivetrain drivetrain = new Drivetrain();
  CommandXboxController controller = new CommandXboxController(DrivetrainConstants.CONTROLLER_USB_PORT);

  public RobotContainer() {
    drivetrain.setDefaultCommand(drivetrain.tankDrive(
      controller::getLeftY, 
      controller::getRightY
    ));
    
    configureBindings();
  }

  private void configureBindings() {
    controller.povLeft().onTrue(drivetrain.invertSide(true));
    controller.povRight().onTrue(drivetrain.invertSide(false));
    controller.povUp().onTrue(drivetrain.modifyInputMultiplier(true));
    controller.povDown().onTrue(drivetrain.modifyInputMultiplier(false));

    controller.rightBumper().onTrue(Commands.runOnce(
      () -> drivetrain.switchDifferentialDriveType(), 
      drivetrain
    ));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
