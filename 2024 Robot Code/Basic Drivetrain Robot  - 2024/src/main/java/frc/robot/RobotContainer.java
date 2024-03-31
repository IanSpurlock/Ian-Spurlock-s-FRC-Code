// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.InputConstants;
import frc.robot.commands.AutonomousCommand1;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Instantiate Commands, Subsystems, and Controllers:
  private final CommandXboxController driver_controller = new CommandXboxController(InputConstants.DRIVER_CONTROLLER_PORT);

  private final Drivetrain drivetrain = new Drivetrain();

  private final SendableChooser<Command> autonChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    drivetrain.setDefaultCommand(
      new DefaultDriveCommand(drivetrain, driver_controller::getLeftY, driver_controller::getRightY)
    );

    autonChooser.setDefaultOption("No Auto", null);
    autonChooser.addOption("Short Auto", new AutonomousCommand1(drivetrain, 0.6, 0.5));
    autonChooser.addOption("Medium Auto", new AutonomousCommand1(drivetrain, 0.6, 0.8));
    autonChooser.addOption("Long Auto", new AutonomousCommand1(drivetrain, 0.6, 1.25));

    SmartDashboard.putData("Autonomous Selector", autonChooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }
}
