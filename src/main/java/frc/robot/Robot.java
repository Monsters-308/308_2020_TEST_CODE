/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private XboxController m_stick;
  private WPI_TalonSRX leftrear = new WPI_TalonSRX(1);
  private WPI_TalonSRX leftfront = new WPI_TalonSRX(2);
  private WPI_TalonSRX rightfront = new WPI_TalonSRX(3);
  private WPI_TalonSRX rightrear = new WPI_TalonSRX(4);
  

  @Override
  public void robotInit() {
    leftrear.setInverted(true);
    leftfront.setInverted(true);
    leftrear.follow(leftfront);
    rightrear.follow(rightfront);
    leftrear.setNeutralMode(NeutralMode.Brake);
    rightrear.setNeutralMode(NeutralMode.Brake);
    leftfront.setNeutralMode(NeutralMode.Brake);
    rightfront.setNeutralMode(NeutralMode.Brake);
    m_myRobot = new DifferentialDrive(leftfront, rightfront);
    m_stick = new XboxController(0);
  }

  @Override
  public void teleopPeriodic() {
    m_myRobot.arcadeDrive(-1*m_stick.getRawAxis(2), m_stick.getRawAxis(4));
  }
}
