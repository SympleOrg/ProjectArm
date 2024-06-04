package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PController;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class MoveArmToPosition extends CommandBase {
    private final double Kp = 0;
    private final ArmSubsystem armSubsystem;
    private final PController pController;
    private final double point;

    public MoveArmToPosition(ArmSubsystem armSubsystem, double point) {
        super();

        addRequirements(armSubsystem);

        this.armSubsystem = armSubsystem;
        this.pController = new PController(Kp);
        this.point = point;

        this.pController.setTolerance(1);
    }

    @Override
    public void initialize() {
        pController.setSetPoint(this.point);
    }

    @Override
    public void execute() {
        double rawPower = pController.calculate(this.armSubsystem.getMotorAnglePos());

        this.armSubsystem.setMotorAngle(rawPower);
    }
}
