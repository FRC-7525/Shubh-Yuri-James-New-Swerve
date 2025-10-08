package frc.robot;

import edu.wpi.first.math.util.Units;

public class Constants {
    
    public static final class SwerveModuleConstants{
        public static final double WheelDiameterMeters = Units.inchesToMeters(4);
        public static final double DriveMotorGearRatio = 1/5.8462;
        public static final double TurningMotorGearRatio = 1/18.0;
        public static final double DriveEncoderRotationsToMeters = DriveMotorGearRatio * Math.PI * WheelDiameterMeters;
        public static final double TurningEncoderRotationsToMeters = TurningMotorGearRatio * 2 * Math.PI;
        public static final double DriveEncoderRPMToMetersPerSecond = DriveEncoderRotationsToMeters / 60.0;
        public static final double TurningEncoderRPMToRadiansPerSecond = TurningEncoderRotationsToMeters / 60.0;
        public static final double PTurning = 0.5; //need to tune
    }

    public static final class DriveConstants{
        public static final double MaxSpeedMetersPerSecond = Units.metersToFeet(12); //need to tune
        
        public static final int FrontLeftDriveMotorPort = 1;
        public static final int FrontLeftTurningMotorPort = 2;
        public static final boolean FrontLeftDriveMotorReversed = false;
        public static final boolean FrontLeftTurningMotorReversed = false;
        public static final int FrontLeftAbsoluteEncoderPort = 0;
        public static final double FrontLeftAbsoluteEncoderOffsetRad = 0; //need to tune
        public static final boolean FrontLeftAbsoluteEncoderReversed = false;

        public static final int FrontRightDriveMotorPort = 3;
        public static final int FrontRightTurningMotorPort = 4;
        public static final boolean FrontRightDriveMotorReversed = false;
        public static final boolean FrontRightTurningMotorReversed = false;
        public static final int FrontRightAbsoluteEncoderPort = 1;
        public static final double FrontRightAbsoluteEncoderOffsetRad = 0; //need to tune
        public static final boolean FrontRightAbsoluteEncoderReversed = false;

        public static final int BackLeftDriveMotorPort = 5;
        public static final int BackLeftTurningMotorPort = 6;
        public static final boolean BackLeftDriveMotorReversed = false;
        public static final boolean BackLeftTurningMotorReversed = false;
        public static final int BackLeftAbsoluteEncoderPort = 2;
        public static final double BackLeftAbsoluteEncoderOffsetRad = 0; //need to tune
        public static final boolean BackLeftAbsoluteEncoderReversed = false;

        public static final int BackRightDriveMotorPort = 7;
        public static final int BackRightTurningMotorPort = 8;
        public static final boolean BackRightDriveMotorReversed = false;
        public static final boolean BackRightTurningMotorReversed = false;
        public static final int BackRightAbsoluteEncoderPort = 3;
        public static final double BackRightAbsoluteEncoderOffsetRad = 0; //need to tune
        public static final boolean BackRightAbsoluteEncoderReversed = false;
        

    }
}
