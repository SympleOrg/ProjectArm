package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.managers.ArmManager;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class SafeMoveArmToPositionCommand extends SequentialCommandGroup {
    private final ArmSubsystem armSubsystem;
    private final ArmManager.Line line;
    private boolean isPossible = true;

    /**
     * @param point meters and deg
     **/
    public SafeMoveArmToPositionCommand(ArmSubsystem armSubsystem, ArmManager.Line point) {
        super();

        this.armSubsystem = armSubsystem;
        this.line = point;
    }

    /**
     * @param angle deg
     * @param length meters
     **/
    public SafeMoveArmToPositionCommand(ArmSubsystem armSubsystem, double angle, double length) {
        this(armSubsystem, new ArmManager.Line(angle, length));
    }

    @Override
    public void initialize() {
        isPossible = ArmManager.isFinalPositionPossible(this.line);

        if (isPossible) {
            double currentAngle = this.armSubsystem.getMotorAnglePos();

            double finalLength = ArmManager.calcMaxArmLengthBetweenAngles(currentAngle, line.getAngle());

            addCommands(
                    new SetArmSizeCommand(this.armSubsystem, finalLength),
                    new RotateArmToPositionCommand(this.armSubsystem, currentAngle),
                    new SetArmSizeCommand(this.armSubsystem, line.getLength())
            );
        }

        super.initialize();
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || !isPossible;
    }
}
