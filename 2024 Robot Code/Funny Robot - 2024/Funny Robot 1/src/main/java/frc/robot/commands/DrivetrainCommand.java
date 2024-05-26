// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DrivetrainCommand extends Command {
  private final DrivetrainSubsystem drivetrainSubsystem;
  private final DoubleSupplier leftSpeedSupplier;
  private final DoubleSupplier rightSpeedSupplier;
  
  public DrivetrainCommand(
    DrivetrainSubsystem drivetrainSubsystem,
    DoubleSupplier leftSpeedSupplier,
    DoubleSupplier rightSpeedSupplier
  ) {
    this.drivetrainSubsystem = drivetrainSubsystem;
    this.leftSpeedSupplier = leftSpeedSupplier;
    this.rightSpeedSupplier = rightSpeedSupplier;
    addRequirements(drivetrainSubsystem);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrainSubsystem.drive(
      leftSpeedSupplier.getAsDouble(), 
      rightSpeedSupplier.getAsDouble()
    );
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrainSubsystem.drive(0, 0);
  }
}
