// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.spikes2212.command.drivetrains.TankDrivetrain;
import com.spikes2212.command.drivetrains.commands.DriveArcade;
import com.spikes2212.dashboard.RootNamespace;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import java.util.function.Supplier;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    DriveArcade driveArcade;
    TankDrivetrain drivetrain;

    RootNamespace namespace = new RootNamespace("root namespace");
    private final Supplier<Double> leftCorrection = namespace.addConstantDouble("left correction 1", 1);
    private final Supplier<Double> rightCorrection = namespace.addConstantDouble("right correction 1", 1);

    WPI_TalonSRX leftTalon1;
    WPI_TalonSRX leftTalon2;
    WPI_TalonSRX rightTalon1;
    WPI_TalonSRX rightTalon2;
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

    private void stopRobot() {
        leftMCG.stopMotor();
        rightMCG.stopMotor();
    }

    @Override
    public void robotInit() {
        leftEncoder = new Encoder(0, 1);
        rightEncoder = new Encoder(2, 3);

        OI oi = new OI();
        leftTalon1 = new WPI_TalonSRX(RobotMap.CAN.DRIVETRAIN_LEFT_TALON_ONE);
        leftTalon2 = new WPI_TalonSRX(RobotMap.CAN.DRIVETRAIN_LEFT_TALON_TWO);
        rightTalon1 = new WPI_TalonSRX(RobotMap.CAN.DRIVETRAIN_RIGHT_TALON_ONE);
        rightTalon2 = new WPI_TalonSRX(RobotMap.CAN.DRIVETRAIN_RIGHT_TALON_TWO);
        leftMCG = new BustedMotorControllerGroup(leftCorrection, leftTalon1, leftTalon2);
        rightMCG = new BustedMotorControllerGroup(rightCorrection, rightTalon1, rightTalon2);

        drivetrain = new TankDrivetrain(leftMCG, rightMCG);
        driveArcade = new DriveArcade(drivetrain, oi::getLeftY, oi::getRightX);

        namespace.putData("Run left", new InstantCommand(() -> leftMCG.set(0.5)));
        namespace.putData("Run right", new InstantCommand(() -> rightMCG.set(0.5)));
        namespace.putData("Run robot", new DriveArcade(drivetrain, 0.5, 0.5));

        namespace.putNumber("right encoder", rightEncoder::get);
        namespace.putNumber("left encoder", leftEncoder::get);
        namespace.putData("reset encoders", new InstantCommand(this::resetEncoders) {
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
