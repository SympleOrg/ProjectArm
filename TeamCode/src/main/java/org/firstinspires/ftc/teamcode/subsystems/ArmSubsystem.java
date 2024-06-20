package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.maps.MotorMap;
import org.firstinspires.ftc.teamcode.util.MathUtil;

public class ArmSubsystem extends SubsystemBase {

    public static final double WHEEL_RADIUS = 0.045f;
    public static final double METERS_PER_REV = (Math.PI * 2) * WHEEL_RADIUS;

    private final MotorEx motorLength;
    private final MotorEx motorAngle;

    public ArmSubsystem(final HardwareMap hMap) {
        super();
        this.motorLength = hMap.get(MotorEx.class, MotorMap.ARM_LENGTH.getId());
        this.motorAngle = hMap.get(MotorEx.class, MotorMap.ARM_ANGLE.getId());
    }

    public void setMotorLengthPower(double power) {
        motorLength.set(power);
    }

    public void setMotorAnglePower(double power) {
        motorAngle.set(power);
    }

    public double getMotorAnglePos() {
        return MathUtil.countsToDeg(this.motorAngle.getCurrentPosition(), MotorMap.ARM_ANGLE.getTicksPerRev());
    }

    public double getMotorLengthPosInDeg() {
        return MathUtil.countsToDeg(this.motorLength.getCurrentPosition(), MotorMap.ARM_LENGTH.getTicksPerRev());
    }

    public double getMotorLengthPosInMeter() {
        return getMotorLengthPosInDeg() * METERS_PER_REV;
    }
}
