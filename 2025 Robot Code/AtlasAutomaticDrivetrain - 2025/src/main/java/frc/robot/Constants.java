// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public final class Constants {
  /*
  
   Ensure that the robot's motor controller IDs are set up as shown 
   (the 1 and 2 can be swapped, and the 3 and 4 can be swapped) (i.e. 1/2 is left, 3/4 is right):
   -----F-----
   |[1]   [3]|
   |         |
   |         |
   |[2]   [4]|
   -----B-----

   Set up the following values according to the robot:

  */
  public static class DrivetrainConstants {
    public static final int CONTROLLER_USB_PORT = 0;

    public static final int MOTOR_CONTROLLER_COUNT = 4;

    public static final MotorType MOTOR_TYPE = MotorType.kBrushless;
    public static final IdleMode SPARK_MAX_IDLE_MODE = IdleMode.kBrake;

    public static final boolean IS_TANK_DRIVE = true;
    public static final double BASE_INPUT_MULTIPLIER = 1;
    public static final double INPUT_MULTIPLIER_ADJUSTMENT_INCREMENT = 0.1;

    public static final boolean LEFT_CONTROLLERS_INVERTED = true;
    public static final boolean RIGHT_CONTROLLERS_INVERTED = false;
  }
}
