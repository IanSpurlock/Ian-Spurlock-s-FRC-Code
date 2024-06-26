// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DefaultDriveCommand extends Command {
  private final Drivetrain drivetrain;
  private final DoubleSupplier left;
  private final DoubleSupplier right;
  
  public DefaultDriveCommand(Drivetrain drivetrain, DoubleSupplier left, DoubleSupplier right) {
    this.drivetrain = drivetrain;
    this.left = left;
    this.right = right;
    addRequirements(this.drivetrain);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.tankDrive(left.getAsDouble(), right.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }
}
