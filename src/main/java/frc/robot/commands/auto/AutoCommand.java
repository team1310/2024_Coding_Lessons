// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveOnHeadingCommand;
import frc.robot.subsystems.DriveSubsystem;

public final class AutoCommand extends SequentialCommandGroup {

    public AutoCommand(DriveSubsystem driveSubsystem) {

        System.out.println("Auto Command scheduled");
        addCommands(new DriveOnHeadingCommand(0, 1, 0.5, driveSubsystem)
            .andThen(
                new DriveOnHeadingCommand(315, 1.4, -0.5, driveSubsystem))
            .andThen(
                new DriveOnHeadingCommand(0, 1, 0.5, driveSubsystem)));
    }
}
