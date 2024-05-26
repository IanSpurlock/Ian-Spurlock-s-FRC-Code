// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DrivetrainCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DefaultAutonomousCommand extends SequentialCommandGroup {
  /** Creates a new DefaultAutonomousCommand. */
  public DefaultAutonomousCommand(DrivetrainSubsystem drivetrain) {
    addCommands(
      new DrivetrainCommand(
        drivetrain, 
        AutonManager.asDoubleSupplier(0.45), 
        AutonManager.asDoubleSupplier(0.45)
      ).withTimeout(0.5)
    );
  }
}
