package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.studica.frc.AHRS;

public class SwerveSubsystem {
    private final SwerveModule frontLeft = new SwerveModule(
        Constants.DriveConstants.FrontLeftDriveMotorPort,
        Constants.DriveConstants.FrontLeftTurningMotorPort,
        Constants.DriveConstants.FrontLeftDriveMotorReversed,
        Constants.DriveConstants.FrontLeftTurningMotorReversed,
        Constants.DriveConstants.FrontLeftAbsoluteEncoderPort,
        Constants.DriveConstants.FrontLeftAbsoluteEncoderOffsetRad,
        Constants.DriveConstants.FrontLeftAbsoluteEncoderReversed);

    private final SwerveModule frontRight = new SwerveModule(
        Constants.DriveConstants.FrontRightDriveMotorPort,
        Constants.DriveConstants.FrontRightTurningMotorPort,
        Constants.DriveConstants.FrontRightDriveMotorReversed,
        Constants.DriveConstants.FrontRightTurningMotorReversed,
        Constants.DriveConstants.FrontRightAbsoluteEncoderPort,
        Constants.DriveConstants.FrontRightAbsoluteEncoderOffsetRad,
        Constants.DriveConstants.FrontRightAbsoluteEncoderReversed);

    private final SwerveModule backLeft = new SwerveModule(
        Constants.DriveConstants.BackLeftDriveMotorPort,
        Constants.DriveConstants.BackLeftTurningMotorPort,
        Constants.DriveConstants.BackLeftDriveMotorReversed,
        Constants.DriveConstants.BackLeftTurningMotorReversed,
        Constants.DriveConstants.BackLeftAbsoluteEncoderPort,
        Constants.DriveConstants.BackLeftAbsoluteEncoderOffsetRad,
        Constants.DriveConstants.BackLeftAbsoluteEncoderReversed);

    private final SwerveModule backRight = new SwerveModule(
        Constants.DriveConstants.BackRightDriveMotorPort,
        Constants.DriveConstants.BackRightTurningMotorPort,
        Constants.DriveConstants.BackRightDriveMotorReversed,
        Constants.DriveConstants.BackRightTurningMotorReversed,
        Constants.DriveConstants.BackRightAbsoluteEncoderPort,
        Constants.DriveConstants.BackRightAbsoluteEncoderOffsetRad,
        Constants.DriveConstants.BackRightAbsoluteEncoderReversed);
        

     private AHRS gyro = new AHRS(AHRS.NavXComType.kMXP_SPI);

     public SwerveSubsystem() {
     new Thread(() -> {
         try {
             Thread.sleep(1000);
             zeroHeading();
         } catch (Exception e) {
         }
     }).start();
     }

     public void zeroHeading(){
        gyro.reset();
     }

     public double getHeading(){
        return Math.IEEEremainder(gyro.getAngle(), 360);
     }

     public Rotation2d geRotation2d(){
        return Rotation2d.fromDegrees(getHeading());
     }

     @Override
     public void periodic() {
        SmartDashboard.putNumber("Robot Heading", getHeading());
     }

     public void stopModules(){
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
     }

     public void setModuleStates(SwerveModuleState[] desiredStates){
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.DriveConstants.MaxSpeedMetersPerSecond);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[2]);
        backRight.setDesiredState(desiredStates[3]);
     }



    
}
