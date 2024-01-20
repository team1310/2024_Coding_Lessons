package frc.robot.operatorInput;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorInputConstants;
import frc.robot.commands.drive.DriveForTimeCommand;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Operator Input
 * <p>
 * This class is used to bind buttons to functions.
 * <p>
 * The operator input class should be passed into any command that requires operator input
 */
public class OperatorInput extends SubsystemBase {

    XboxController driverController = new XboxController(OperatorInputConstants.DRIVER_CONTROLLER_PORT);

    /**
     * Use this method to define bindings of buttons to command
     */
    public void configureBindings(DriveSubsystem driveSubsystem) {

        new Trigger(() -> driverController.getAButton())
            .onTrue(
                new DriveForTimeCommand(2, .2, driveSubsystem));
    }

    /*
     * Any command where operator input is required will need to get functional instructions from the controller
     */
    public double getLeftSpeed() {
        double speed = -driverController.getLeftY();
        return speed;
    }

    public double getRightSpeed() {
        double speed = -driverController.getRightY();
        return speed;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

}
