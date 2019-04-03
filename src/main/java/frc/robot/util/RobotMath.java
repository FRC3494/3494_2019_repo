package frc.robot.util;

public class RobotMath {
    public static double powerCurve(double x) {
        // https://www.desmos.com/calculator/g07ukjj7bl
        double curve = (0.5D * (Math.atan(Math.PI * (Math.abs(x) - 0.5D)))) + 0.5D;
        return Math.copySign(curve, x);
    }

    public static void normalize(double[] array) {
        double max = Math.abs(array[0]);
        boolean normFlag = max > 1;

        for (int i = 1; i < array.length; i++) {
            if (Math.abs(array[i]) > max) {
                max = Math.abs(array[i]);
                normFlag = max > 1;
            }
        }

        if (normFlag) {
            for (int i = 0; i < array.length; i++) {
                array[i] /= max;
            }
        }
    }
}
