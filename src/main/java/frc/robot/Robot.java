/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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
  private Compressor comp = new Compressor(7);
  private Solenoid ledlight = new Solenoid(7,3);
  private Solenoid ledlights = new Solenoid(7,2);
  public NetworkTableEntry yaw;
  public NetworkTable cameraTable;
  public NetworkTableEntry IsDriverMode;
  int lightsource = 0;
  double rotationAjust;

  @Override
  public void robotInit() {
    leftrear.setInverted(false);
    leftfront.setInverted(false);
    leftrear.follow(leftfront);
    rightrear.follow(rightfront);
    leftrear.setNeutralMode(NeutralMode.Brake);
    rightrear.setNeutralMode(NeutralMode.Brake);
    leftfront.setNeutralMode(NeutralMode.Brake);
    rightfront.setNeutralMode(NeutralMode.Brake);
    m_myRobot = new DifferentialDrive(leftfront, rightfront);
    m_stick = new XboxController(0);
    NetworkTableInstance table = NetworkTableInstance.getDefault();
    cameraTable = table.getTable("chameleon-vision").getSubTable("Microsoft LifeCam HD-3000");
    IsDriverMode = cameraTable.getEntry("driver_mode");
  }

  @Override
  public void teleopPeriodic() {
    double rotationAjust = (0.0);
    yaw = cameraTable.getEntry("yaw");
    SmartDashboard.putNumber("Joystick_1", -1*m_stick.getRawAxis(1));
    SmartDashboard.putNumber("Joystick_4", m_stick.getRawAxis(4));
    SmartDashboard.putBoolean("Y Button", m_stick.getYButton());
    SmartDashboard.putNumber("yaw", yaw.getDouble(0.0));
    SmartDashboard.putBoolean("A Button", m_stick.getAButton());
    if (m_stick.getAButton()){
      double turnvalue = yaw.getDouble(0.0);
      SmartDashboard.putNumber("turnvalue", turnvalue);
      if (turnvalue > 0.1){
        rotationAjust = (-.01*turnvalue) + .05;
        SmartDashboard.putNumber("rotation", rotationAjust);
        m_myRobot.arcadeDrive(-1*m_stick.getRawAxis(1), rotationAjust);
      }else if (turnvalue < -0.1){
        rotationAjust = (-.01*turnvalue) - .05;
        m_myRobot.arcadeDrive(-1*m_stick.getRawAxis(1), rotationAjust);
      }else{
        m_myRobot.arcadeDrive(-1*m_stick.getRawAxis(1), 0);
      }
    }else{
      m_myRobot.arcadeDrive(-1*m_stick.getRawAxis(1), m_stick.getRawAxis(4));

    }
      if (m_stick.getYButtonPressed()){
        if (lightsource == 0){
          ledlights.set(true);
          lightsource = 1;
        }else{
          ledlights.set(false);
          lightsource = 0;
        }
      }
    }
  }