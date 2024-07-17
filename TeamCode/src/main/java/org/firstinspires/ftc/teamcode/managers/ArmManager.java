package org.firstinspires.ftc.teamcode.managers;

import org.firstinspires.ftc.teamcode.util.MathUtil;

import java.util.ArrayList;
import java.util.Comparator;

public class ArmManager {
    public static final ArrayList<Line> obstacles = new ArrayList<Line>() {{
        add(new Line(0.3f, 202.5f));
        add(new Line(0.15f, 225f));
        add(new Line(0.25f, 247.5f));
        add(new Line(0.25f, 292.5f));
        add(new Line(0.15f, 315f));
        add(new Line(0.3f, 337.5f));

        this.sort(Comparator.comparingDouble(line -> line.angle));
    }};

    public static double calcMaxArmLengthBetweenAngles(double currentAngle, double goalAngle) {
        double perfectCurrentAngle = MathUtil.sympleModulo(currentAngle, 360);
        double perfectGoalAngle = MathUtil.sympleModulo(goalAngle, 360);

        double finalArmLength = Double.POSITIVE_INFINITY;

        for (Line line : obstacles) {
            if (line.isBetween(perfectCurrentAngle, perfectGoalAngle) && line.getLength() < finalArmLength)
                finalArmLength = line.getLength();
        }

        return finalArmLength;
    }


    public static Line[] getSurroundingLines(Line line) {
        Line previousLine = null;

        for (Line ln : obstacles) {
            if (previousLine == null) {
                previousLine = ln;
                continue;
            }

            if (ln == line) continue;

            if (line.isBetween(previousLine, ln)) return new Line[]{previousLine, ln};

            previousLine = ln;
        }

        return new Line[0];
    }

    public static boolean isFinalPositionPossible(Line finalPos) {
        Line[] surroundingLines = getSurroundingLines(finalPos);

        if (surroundingLines.length < 2) return true;

        double x1 = Math.cos(surroundingLines[0].getAngle()) * surroundingLines[0].getLength();
        double y1 = Math.sin(surroundingLines[0].getAngle()) * surroundingLines[0].getLength();

        double x2 = Math.cos(surroundingLines[1].getAngle()) * surroundingLines[1].getLength();
        double y2 = Math.sin(surroundingLines[1].getAngle()) * surroundingLines[1].getLength();

        double alpha = MathUtil.sympleModulo(finalPos.getAngle(), 360);

        double m = (y1 - y2) / (x1 - x2);
        double b = y1 - (m * x1);

        double x3 = b / (Math.tan(alpha) - m);
        double y3 = Math.tan(alpha) * x3;

        double dist = Math.sqrt(x3 * x3 + y3 * y3);

        return dist >= finalPos.getLength();
    }


    public static class Line {
        private final double length;
        private final double angle;

        public Line(double length, double angle) {
            this.length = length;
            this.angle = angle;
        }

        public double getAngle() {
            return angle;
        }

        public double getLength() {
            return length;
        }

        public double getModuloedAngle() {
            return MathUtil.sympleModulo(this.angle, 360);
        }

        public boolean isBetween(Line l1, Line l2) {
            return isBetween(l1.getModuloedAngle(), l2.getModuloedAngle());
        }

        public boolean isBetween(double a1, double a2) {
            return this.getModuloedAngle() >= a1 && this.getModuloedAngle() <= a2;
        }
    }

}
