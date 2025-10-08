package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;


public class SwerveModule {
    private final CANSparkMax driveMotor;
    private final CANSparkMax turningMotor;

    private final CANEncoder driveEncoder;
    private final CANEncoder turningEncoder;

    private final PIDController turningPIDController;

    private final AnalogInput absoluteEncoder;
    private final boolean absoluteEncoderReversed;
    private final double absoluteEncoderOffsetRad;

    public SwerveModule(int driveMotorID, int turningMotorID, boolean driveMotorReversed,
     boolean turningMotorReversed, int absoluteEncoderID, double absoluteEncoderOffset, boolean absoluteEncoderReversed) {
        this.absoluteEncoderOffsetRad = absoluteEncoderOffset;
        this.absoluteEncoderReversed = absoluteEncoderReversed;
        absoluteEncoder = new AnalogInput(absoluteEncoderID);

        driveMotor = new CANSparkMax(driveMotorID, CANSparkMax.MotorType.kBrushless);
        turningMotor = new CANSparkMax(turningMotorID, CANSparkMax.MotorType.kBrushless);

        driveMotor.setInverted(driveMotorReversed);
        turningMotor.setInverted(turningMotorReversed);

        driveEncoder = driveMotor.getEncoder();
        turningEncoder = turningMotor.getEncoder();

        driveEncoder.setPositionConversionFactor(Constants.SwerveModuleConstants.DriveEncoderRotationsToMeters);
        driveEncoder.setVelocityConversionFactor(Constants.SwerveModuleConstants.DriveEncoderRPMToMetersPerSecond); 
        turningEncoder.setPositionConversionFactor(Constants.SwerveModuleConstants.TurningEncoderRotationsToMeters); // Radians
        turningEncoder.setVelocityConversionFactor(Constants.SwerveModuleConstants.TurningEncoderRPMToRadiansPerSecond); // Radians per second

        turningPIDController = new PIDController(Constants.SwerveModuleConstants.PTurning, 0, 0); //need to tune
        turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
         
        resetEncoders();
     }

     public double getDrivePosition(){
        return driveEncoder.getPosition();
     }

    public double getTurningPosition(){
        return turningEncoder.getPosition();
    }

    public double getDriveVelocity(){
        return driveEncoder.getVelocity();
    }

    public double getTurningVelocity(){
        return turningEncoder.getVelocity();
    }

    public double getAbsoluteEncoderRad(){
        double angle = absoluteEncoder.getVoltage() / RobotController.getVoltage5V();
        angle *= 2.0 * Math.PI;
        angle -= absoluteEncoderOffsetRad;
        return angle * (absoluteEncoderReversed ? -1.0 : 1.0);
    }

    public void resetEncoders(){
        driveEncoder.setPosition(0);
        turningEncoder.setPosition(getAbsoluteEncoderRad());
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurningPosition()));
    }

    public void setDesiredState(SwerveModuleState state){
        if (Math.abs(state.speedMetersPerSecond) < 0.001){
            stop();
            return;
        }
        state = SwerveModuleState.optimize(state, getState().angle);
        driveMotor.set(state.speedMetersPerSecond / Constants.DriveConstants.MaxSpeedMetersPerSecond);
        turningMotor.set(turningPIDController.calculate(getTurningPosition(), state.angle.getRadians()));
        SmartDashboard.putString("Swerve[" + absoluteEncoder.getChannel() + "] State", state.toString());
    }

    public void stop(){
        driveMotor.set(0);
        turningMotor.set(0);
    }

}


