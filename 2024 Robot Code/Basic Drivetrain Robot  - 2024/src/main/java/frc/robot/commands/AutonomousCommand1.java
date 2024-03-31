// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html

/**
 * Autonomously drives the robot in a straight line.
 * @param drivetrain Drivetrain subsystem.
 * @param speed Speed given to the drivetrain (between -1 and 1).
 * @param time Time, in seconds, to drive for.
 */
public class AutonomousCommand1 extends SequentialCommandGroup {
  public AutonomousCommand1(Drivetrain drivetrain, double speed, double time) {
    addCommands(
      new DefaultDriveCommand(drivetrain, () -> speed, () -> speed).withTimeout(time)
    );
  }
}
