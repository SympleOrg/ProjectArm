package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PController;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class SetArmSizeCommand extends CommandBase {
    private final double Kp = 0.1f;
    private final ArmSubsystem armSubsystem;
    private final PController pController;
    private final double length;

    /**
     * @param length in meters
     **/
    public SetArmSizeCommand(ArmSubsystem armSubsystem, double length) {
        super();

        addRequirements(armSubsystem);

        this.armSubsystem = armSubsystem;
        this.pController = new PController(Kp);
        this.length = length;

        this.pController.setTolerance(1);
    }

    @Override
    public void initialize() {
        pController.setSetPoint(this.length);
    }

    @Override
    public void execute() {
        double rawPower = pController.calculate(this.armSubsystem.getMotorLengthPosInMeter());

        this.armSubsystem.setMotorLengthPower(rawPower);
    }

    @Override
    public void end(boolean interrupted) {
        this.armSubsystem.setMotorLengthPower(0);
    }

    @Override
    public boolean isFinished() {
        return this.pController.atSetPoint();
    }
}
