// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int CONTROLLER_PORT = 0;
  }

  public static class DrivetrainConstants {
    public static final int LEFT_SPARK_1_ID = 1;
    public static final int LEFT_SPARK_2_ID = 2;
    public static final int RIGHT_SPARK_1_ID = 3;
    public static final int RIGHT_SPARK_2_ID = 4;

    public static final double INITIAL_KP = 0;
    public static final double INITIAL_KI = 0;
    public static final double INITIAL_KD = 0;

    public static final double INITIAL_COMPLETION_TOLERANCE = 0.01;
    public static final double RATIO_FROM_DISTANCE_TO_ENCODER = 0;

    public static final double PID_PARAMETER_INCREMENT = 0.05;

    public static final boolean START_PID_REVERSED = false;
  }
}
