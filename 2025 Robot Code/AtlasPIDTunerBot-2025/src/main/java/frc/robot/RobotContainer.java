// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private Drivetrain drivetrain = new Drivetrain();
  private CommandXboxController controller = new CommandXboxController(OperatorConstants.CONTROLLER_PORT);
  
  public RobotContainer() {
    drivetrain.setDefaultCommand(Commands.runEnd(
      () -> drivetrain.drive(controller.getLeftY(), controller.getRightY()), 
      () -> drivetrain.drive(0), 
      drivetrain
    ));
    configureBindings();
  }

  private void configureBindings() {
    controller.povRight().onTrue(Commands.runOnce(drivetrain::selectNextParameter, drivetrain));
    controller.povUp().onTrue(drivetrain.callPIDParameterChange(DrivetrainConstants.PID_PARAMETER_INCREMENT));
    controller.povDown().onTrue(drivetrain.callPIDParameterChange(-DrivetrainConstants.PID_PARAMETER_INCREMENT));
    controller.povLeft().onTrue(Commands.runOnce(drivetrain::resetParameters, drivetrain));
    controller.leftBumper().onTrue(Commands.runOnce(drivetrain::reversePID, drivetrain));

    controller.y().onTrue(drivetrain.driveDistance(5));
    controller.rightBumper().onTrue(Commands.runOnce(() -> drivetrain.drive(0), drivetrain));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
