package frc.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DrivetrainCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class InputManager {
  public static final int A = 0;
  public static final int B = 1;
  public static final int X = 2;
  public static final int Y = 3;
  public static final int D_UP = 4;
  public static final int D_DOWN = 5;
  public static final int D_LEFT = 6;
  public static final int D_RIGHT = 7;
  public static final int BUMPER_LEFT = 8;
  public static final int BUMPER_RIGHT = 9;

  public static final int WHILE_TRUE = 0;
  public static final int WHILE_FALSE = 1;
  public static final int ONCE_TRUE = 2;
  public static final int ONCE_FALSE = 3;
  public static final int TOGGLE_ONCE_TRUE = 4;
  public static final int TOGGLE_ONCE_FALSE = 5;
  

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
    if (!controllerExists(controller)) return;

    drivetrain.setDefaultCommand(
      new DrivetrainCommand(
        drivetrain, 
        controller::getLeftY, 
        controller::getRightY
      )
    );
  }

  /**
   * @param button Use InputManager static constants.
   */
  public void addBind(int port, int button, int condition, Command command) {
    CommandXboxController controller = getController(port); 
    if (!controllerExists(controller)) return;
    Trigger trigger = null;
    
    switch (button) {
      case A:
        trigger = controller.a();
        break;
      case B:
        trigger = controller.b();
        break;
      case X:
        trigger = controller.x();
        break;
      case Y:
        trigger = controller.y();
        break;
      case D_UP:
        trigger = controller.povUp();
        break;
      case D_DOWN:
        trigger = controller.povDown();
        break;
      case D_LEFT:
        trigger = controller.povLeft();
        break;
      case D_RIGHT:
        trigger = controller.povRight();
        break;
      case BUMPER_LEFT:
        trigger = controller.leftBumper();
        break;
      case BUMPER_RIGHT:
        trigger = controller.rightBumper();
        break;
    }

    if (trigger == null) {
      DriverStation.reportError(
        "Button " + button + " does not exist", 
        null
      );
      return;
    }
    if (condition < 7) {
      DriverStation.reportError(
        "Condition " + condition + " does not exist", 
        null
      );
      return;
    }

    switch (condition) {
      case WHILE_TRUE:
        trigger.whileTrue(command);
        break;
      case WHILE_FALSE:
        trigger.whileFalse(command);
        break;
      case ONCE_TRUE:
        trigger.onTrue(command);
        break;
      case ONCE_FALSE:
        trigger.onFalse(command);
        break;
      case TOGGLE_ONCE_TRUE:
        trigger.toggleOnTrue(command);
        break;
      case TOGGLE_ONCE_FALSE:
        trigger.toggleOnFalse(command);
        break;
    }
  }

  public CommandXboxController getController(int port) {
    return controllerMap.get(port);
  }

  private boolean controllerExists(CommandXboxController controller) {
    boolean output = controller != null;
    if (!output) {
      DriverStation.reportError(
        "Controller port " + controller.getHID().getPort() + " does not exist", 
        null
      );
    }

    return output;
  }
}
