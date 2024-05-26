package frc.robot.commands.autonCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutonManager {
  public static DoubleSupplier asDoubleSupplier(double value) {
    return () -> value;
  }

  public static Command getAutonomousCommand(DrivetrainSubsystem drivetrain) {
    return new DefaultAutonomousCommand(drivetrain);
  }
}
