// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.spikes2212.command.drivetrains.TankDrivetrain;
import com.spikes2212.command.drivetrains.commands.DriveArcade;
import com.spikes2212.dashboard.RootNamespace;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    DriveArcade driveArcade;
    TankDrivetrain drivetrain;

    RootNamespace namespace;

    WPI_VictorSPX rightVictor1;
    WPI_VictorSPX leftVictor1;
    WPI_VictorSPX rightVictor2;
    WPI_VictorSPX leftVictor2;
    MotorControllerGroup leftMCG;
    MotorControllerGroup rightMCG;
    Encoder leftEncoder;
    Encoder rightEncoder;

    private void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    private void runRobot() {
        leftMCG.set(0.5);
        rightMCG.set(0.5);
    }

    private void runLeft1() {
        leftVictor1.set(0.7);
    }

    private void stopRobot() {
        leftMCG.stopMotor();
        rightMCG.stopMotor();
    }

    @Override
    public void robotInit() {
        namespace = new RootNamespace("root");

        leftEncoder = new Encoder(0, 1);
        rightEncoder = new Encoder(2, 3);

        OI oi = new OI();
        int leftPort1 = 3, leftPort2 = 1, rightPort1 = 2, rightPort2 = 4;
        leftVictor1 = new WPI_VictorSPX(leftPort1);
        rightVictor1 = new WPI_VictorSPX(rightPort1);
        leftVictor2 = new WPI_VictorSPX(leftPort2);
        rightVictor2 = new WPI_VictorSPX(rightPort2);
        leftMCG = new MotorControllerGroup(leftVictor1, leftVictor2);
        rightMCG = new MotorControllerGroup(rightVictor1, rightVictor2);

        drivetrain = new TankDrivetrain(leftMCG, rightMCG);
        driveArcade = new DriveArcade(drivetrain, oi::getLeftY, oi::getRightX);

        namespace.putData("Run left", new InstantCommand(() -> leftMCG.set(0.5)));
        namespace.putData("Run right", new InstantCommand(() -> rightMCG.set(0.5)));
        namespace.putData("Run robot", new DriveArcade(drivetrain, 0.5, 0.5));

        namespace.putNumber("right encoder", rightEncoder::get);
        namespace.putNumber("left encoder", leftEncoder::get);
        namespace.putData("reset encoders", new InstantCommand(this::resetEncoders){
            @Override
            public boolean runsWhenDisabled() {
                return true;
            }
        });

        drivetrain.setDefaultCommand(driveArcade);
    }

    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
        namespace.update();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {

    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {

    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}
