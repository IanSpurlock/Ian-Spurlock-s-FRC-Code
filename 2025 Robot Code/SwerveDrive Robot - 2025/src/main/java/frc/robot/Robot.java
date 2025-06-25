// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.ControlConstants;
import frc.robot.Constants.DrivetrainConstants;

public class Robot extends TimedRobot {
  private final XboxController m_controller = new XboxController(ControlConstants.CONTROLLER_PORT_ID);
  private final Drivetrain m_swerve = new Drivetrain();

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(ControlConstants.SPEED_SLEW_RATE_LIMITER);
  private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(ControlConstants.SPEED_SLEW_RATE_LIMITER);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(ControlConstants.ROTATION_SLEW_RATE_LIMITER);

  @Override
  public void autonomousPeriodic() {
    driveWithJoystick(true);
    m_swerve.updateOdometry();
  }

  @Override
  public void teleopPeriodic() {
    driveWithJoystick(ControlConstants.FIELD_RELATIVE);
  }

  private void driveWithJoystick(boolean fieldRelative) {
    final double xSpeed = getAdjustedSpeed(m_xspeedLimiter, m_controller::getLeftY, DrivetrainConstants.MAX_DRIVE_SPEED);
    final double ySpeed = getAdjustedSpeed(m_yspeedLimiter, m_controller::getLeftX, DrivetrainConstants.MAX_DRIVE_SPEED);
    final double rotation = getAdjustedSpeed(m_rotLimiter, m_controller::getRightX, DrivetrainConstants.MAX_TURNING_SPEED);

    m_swerve.drive(xSpeed, ySpeed, rotation, fieldRelative, getPeriod());
  }

  private double getAdjustedSpeed(SlewRateLimiter speedLimiter, DoubleSupplier speedSource, double maxSpeed) {
    return maxSpeed * -speedLimiter.calculate(MathUtil.applyDeadband(speedSource.getAsDouble(), ControlConstants.JOYSTICK_DEADBAND));
  }
}
