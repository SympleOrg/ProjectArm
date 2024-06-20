package org.firstinspires.ftc.teamcode.managers;

import java.util.ArrayList;
import java.util.Comparator;

public class ArmManager {
    public static final ArrayList<Line> obstacles = new ArrayList<Line>(){{
       add(new Line(0.3f, 202.5f));
       add(new Line(0.15f, 225f));
       add(new Line(0.25f, 247.5f));
       add(new Line(0.25f, 292.5f));
       add(new Line(0.15f, 315f));
       add(new Line(0.3f, 337.5f));

       this.sort(Comparator.comparingDouble(line -> line.angle));
    }};

    public static double calcMaxArmLengthBetweenAngles(double currentAngle, double goalAngle) {
        double perfectCurrentAngle = sympleModulo(currentAngle, 360);
        double perfectGoalAngle = sympleModulo(goalAngle, 360);

        double finalArmLength = Double.POSITIVE_INFINITY;

        for (Line line : obstacles) {
            if(line.isBetween(perfectCurrentAngle, perfectGoalAngle) && line.length < finalArmLength) finalArmLength = line.length;
        }

        return finalArmLength;
    }


    public static Line[] getSurroundingLines(Line line) {
        Line previousLine = null;

        for (Line ln : obstacles) {
            if(previousLine == null) {
                previousLine = ln;
                continue;
            }

            if(ln == line) continue;

            if(line.isBetween(previousLine, ln)) return new Line[]{previousLine,ln};

            previousLine = ln;
        }

        return new Line[]{};
    }

    public boolean isFinalPositionPossible(Line finalPos, Line[] surroundingLines) {
        double x1 = Math.cos(angle) * length;
        double y1 = Math.sin(angle) * length;

        double x2 = Math.cos(angle) * length;
        double y2 = Math.sin(angle) * length;
    }

    public static double sympleModulo(double x, double y) {
        return ((x%y) + y)%y;
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
            return sympleModulo(this.angle, 360);
        }

        public boolean isBetween(Line l1, Line l2) {
            return isBetween(l1.getModuloedAngle(), l2.getModuloedAngle());
        }

        public boolean isBetween(double a1, double a2) {
            return this.getModuloedAngle() >= a1 && this.getModuloedAngle() <= a2;
        }
    }

}
