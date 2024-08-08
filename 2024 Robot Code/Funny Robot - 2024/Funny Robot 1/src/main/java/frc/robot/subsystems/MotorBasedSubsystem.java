// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorBasedSubsystem extends SubsystemBase {
  public static final int SINGULAR = 0;
  public static final int COUPLED = 1;
  public static final int SPLIT = 2;

  private final List<CANSparkMax> motorControllers = new ArrayList<>();
  
  public MotorBasedSubsystem(MotorType motorType, boolean isInverted, int... deviceIDs) {
    int motorControllerCount = deviceIDs.length;
    int splitIndex = motorControllerCount / 2;
    
    for (int i = 0; i < motorControllerCount; i++) {
      CANSparkMax spark = new CANSparkMax(deviceIDs[i], motorType);
      motorControllers.add(spark);
      spark.setInverted(isInverted);

      if (i == 0) {
        firstMotorController = c;
      } else {
        if (linkType != MotorControllerLinkType.Singular) {
          if (linkType == MotorControllerLinkType.Coupled) {
            c.follow(firstMotorController);
          } else if (i != splitIndex) {
            if (i < splitIndex) {
              c.follow(firstMotorController);
            } else {
              c.setInverted(!isInverted);
              c.follow(splitMotorController);
            }
          } else {
            c.setInverted(!isInverted);
            splitMotorController = c;
          }
        }
      }
      // Implement same system as the AnyBot
    }
  }

  
}
