package frc.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DrivetrainCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class InputManager {
  private final Map<Integer, CommandXboxController> controllerMap = new HashMap<>();

  public void addController(int port) {
    if (controllerMap.put(port, new CommandXboxController(port)) != null) {
      conflictingControllerPorts(port);
    }
  }

  private void conflictingControllerPorts(int port) {
    DriverStation.reportError(
      "Conflicting XBox Controller Port: " + port, 
      null
    );
  }

  public void setDriverController(int port, DrivetrainSubsystem drivetrain) {
    CommandXboxController controller = getController(port);
    
    drivetrain.setDefaultCommand(
      new DrivetrainCommand(
        drivetrain, 
        controller::getLeftY, 
        controller::getRightY
      )
    );
  }

  private CommandXboxController getController(int port) {
    return controllerMap.get(port);
  }
}
