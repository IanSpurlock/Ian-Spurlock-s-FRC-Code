package frc.robot;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.util.Units;

public final class Constants {
    public static class ControlConstants {
        public final static int CONTROLLER_PORT_ID = 0;

        public final static double SPEED_SLEW_RATE_LIMITER = 3; // Clamps the rate of change for speed. (Units per second)
        public final static double ROTATION_SLEW_RATE_LIMITER = 3; // Clamps the rate of change for rotation. (Units per second)
        public final static double JOYSTICK_DEADBAND = 0.02; // Joystick drift or unintentional movement protection.

        public final static boolean FIELD_RELATIVE = true; // Whether robot movement is relative to the field or relative to the robot.
    }

    public static class ModuleConstants {
        public final static double[] DRIVE_PID_VALUES = {1, 0, 0}; // kp, ki, kd
        public final static double[] TURNING_PID_VALUES = {1, 0, 0}; // kp, ki, kd
        public final static double[] DRIVE_FEEDFORWARD_VALUES = {1, 3}; // ks, kv
        public final static double[] TURNING_FEEDFORWARD_VALUES = {1, 0.5}; // ks, kv

        public final static double WHEEL_DIAMETER = Units.inchesToMeters(4); // Actual unit is meters, but conversion is used.
        public final static int ENCODER_RESOLUTION = 4096; // Make sure that this matches encoders used.

        public final static MotorType MOTOR_TYPE = MotorType.kBrushless; // SparkMAX motor type
    }

    public static class DrivetrainConstants {
        public final static double MAX_DRIVE_SPEED = 3; // Meters per second
        public final static double MAX_TURNING_SPEED = Math.PI; // Radians of rotation per second
        public final static double MAX_TURNING_ACCELERATION = 2 * Math.PI; // Radians of rotation per second squared

        /* CAUTION!
        Each of these position arrays contain an x and y value, 
        which represents distances from the center of the robot to the center of each wheel.
        * POSITIVE X values are FORWARD.
        * POSITIVE Y values are LEFT.

              +Y
          ---------
          |BL | FL|
          --------- +X
          |BR | FR|
          ---------
        */
        public final static double[] FRONT_LEFT_WHEEL_POSITION = {Units.inchesToMeters(15), Units.inchesToMeters(15)}; // +, +
        public final static double[] BACK_LEFT_WHEEL_POSITION = {-Units.inchesToMeters(15), Units.inchesToMeters(15)}; // -, +
        public final static double[] BACK_RIGHT_WHEEL_POSITION = {-Units.inchesToMeters(15), -Units.inchesToMeters(15)}; // -, -
        public final static double[] FRONT_RIGHT_WHEEL_POSITION = {Units.inchesToMeters(15), -Units.inchesToMeters(15)}; // +, -
        //

        // Front Left Module:
        public final static int FRONT_LEFT_DRIVE_MOTOR_ID = 2;
        public final static int FRONT_LEFT_TURNING_MOTOR_ID = 3;
        public final static int FRONT_LEFT_DRIVE_ENCODER_CHANNEL_A = 0;
        public final static int FRONT_LEFT_DRIVE_ENCODER_CHANNEL_B = 1;
        public final static int FRONT_LEFT_TURNING_ENCODER_CHANNEL_A = 2;
        public final static int FRONT_LEFT_TURNING_ENCODER_CHANNEL_B = 3;

        // Back Left Module:
        public final static int BACK_LEFT_DRIVE_MOTOR_ID = 4;
        public final static int BACK_LEFT_TURNING_MOTOR_ID = 5;
        public final static int BACK_LEFT_DRIVE_ENCODER_CHANNEL_A = 4;
        public final static int BACK_LEFT_DRIVE_ENCODER_CHANNEL_B = 5;
        public final static int BACK_LEFT_TURNING_ENCODER_CHANNEL_A = 6;
        public final static int BACK_LEFT_TURNING_ENCODER_CHANNEL_B = 7;

        // Back Right Module:
        public final static int BACK_RIGHT_DRIVE_MOTOR_ID = 6;
        public final static int BACK_RIGHT_TURNING_MOTOR_ID = 7;
        public final static int BACK_RIGHT_DRIVE_ENCODER_CHANNEL_A = 8;
        public final static int BACK_RIGHT_DRIVE_ENCODER_CHANNEL_B = 9;
        public final static int BACK_RIGHT_TURNING_ENCODER_CHANNEL_A = 10;
        public final static int BACK_RIGHT_TURNING_ENCODER_CHANNEL_B = 11;

        // Front Right Module:
        public final static int FRONT_RIGHT_DRIVE_MOTOR_ID = 8;
        public final static int FRONT_RIGHT_TURNING_MOTOR_ID = 9;
        public final static int FRONT_RIGHT_DRIVE_ENCODER_CHANNEL_A = 12;
        public final static int FRONT_RIGHT_DRIVE_ENCODER_CHANNEL_B = 13;
        public final static int FRONT_RIGHT_TURNING_ENCODER_CHANNEL_A = 14;
        public final static int FRONT_RIGHT_TURNING_ENCODER_CHANNEL_B = 15;
        
        //
        public final static int GYRO_CHANNEL = 0; // Analog channel (0 or 1)
    } 
}
