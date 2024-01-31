// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

    /*
     * Drive Motors
     */
    private final TalonSRX  leftMotor   = new TalonSRX(DriveConstants.LEFT_MOTOR_PORT);
    private final TalonSRX  leftMotor2  = new TalonSRX(DriveConstants.LEFT_MOTOR_PORT + 1);
    private final TalonSRX  rightMotor  = new TalonSRX(DriveConstants.RIGHT_MOTOR_PORT);
    private final VictorSPX rightMotor2 = new VictorSPX(DriveConstants.RIGHT_MOTOR_PORT + 1);

    /*
     * Gyro
     */
    private AHRS            navXGyro    = new AHRS();

    /**
     * Place all initialization code in the constructor
     */
    public DriveSubsystem() {
        /*
         * One of the sides always needs to be inverted
         */
        leftMotor.setInverted(true);
        leftMotor2.setInverted(true);

        leftMotor.setNeutralMode(NeutralMode.Brake);
        leftMotor2.setNeutralMode(NeutralMode.Brake);
        rightMotor.setNeutralMode(NeutralMode.Brake);
        rightMotor2.setNeutralMode(NeutralMode.Brake);
    }


    public void setDriveSpeed(double leftSpeed, double rightSpeed) {

        leftMotor.set(ControlMode.PercentOutput, leftSpeed);
        leftMotor2.set(ControlMode.PercentOutput, leftSpeed);

        rightMotor.set(ControlMode.PercentOutput, rightSpeed);
        rightMotor2.set(ControlMode.PercentOutput, rightSpeed);
    }

    /**
     * Get the current heading from the Gyro
     */
    public double getHeading() {

        double yaw = navXGyro.getYaw();

        // The navX yaw is between -180 and +180.
        // FIXME what should we return to get a number in the range 0-360?
        return yaw;
    }

    public double getHeadingError(double desiredHeading, double currentHeading) {

        double error = (desiredHeading - currentHeading) % 360;
        if (error > 180) {
            error -= 360;
        }
        return error;
        // FIXME: calcuate the heading error based on the desired heading
        // by convention, when calculating the error for a PID feedback
        // loop the formula for error is:
        // error = desiredValue (setpoint) - actualValue (measurement)
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Gyro", getHeading());
    }



}
