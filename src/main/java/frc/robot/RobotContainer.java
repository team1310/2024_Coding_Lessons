// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.auto.AutoCommand;
import frc.robot.commands.drive.DefaultDriveCommand;
import frc.robot.operatorInput.OperatorInput;
import frc.robot.subsystems.DriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    // Declare the operator input class
    private final OperatorInput  operatorInput  = new OperatorInput();

    // Declare all subsystems.
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        // Set the default commands for all subsystems
        driveSubsystem.setDefaultCommand(
            new DefaultDriveCommand(operatorInput, driveSubsystem));

        // Configure the operator input button bindings.
        operatorInput.configureBindings(driveSubsystem);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new AutoCommand(driveSubsystem);
    }
}
