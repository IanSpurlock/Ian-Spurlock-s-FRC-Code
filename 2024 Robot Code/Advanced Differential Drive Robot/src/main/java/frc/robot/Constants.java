// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class InputConstants {
    public static final int DRIVER_CONTROLLER_PORT = 0;
  }

  /**
   * Constants for trajectory tracking (kinematics and odometry), not basic drivetrain functionality.
   */
  public static class TrajectoryConstants {
    public static final double TRACK_WIDTH = Units.inchesToMeters(28); // Distance between the wheels
  }

  /**
   * Constants for basic drivetrain functionality, not trajectory tracking.
   */
  public static class DrivetrainConstants {
    public static final int LEFT_1_DEVICE_ID = 2;
    public static final int LEFT_2_DEVICE_ID = 3;
    public static final int RIGHT_1_DEVICE_ID = 4;
    public static final int RIGHT_2_DEVICE_ID = 5;

    public static final MotorType DRIVETRAIN_MOTOR_TYPE = MotorType.kBrushless;

    public static final Boolean IS_LEFT_INVERTED = true;
    public static final Boolean IS_RIGHT_INVERTED = false;
  }
}
