/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;

    public class DRIVETRAIN {
        public static final int LEFT_MASTER_CHANNEL = 0;
        public static final int LEFT_FOLLOWER_ONE_CHANNEL = 1;
        public static final int LEFT_FOLLOWER_TWO_CHANNEL = 2;
        public static final int RIGHT_MASTER_CHANNEL = 13;
        public static final int RIGHT_FOLLOWER_ONE_CHANNEL = 14;
        public static final int RIGHT_FOLLOWER_TWO_CHANNEL = 15;

        public static final double GEAR_RATIO = 7.58 / 12 / 15;
        public static final double WHEEL_RADIUS_FEET = .25;
        public static final double WHEEL_CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS_FEET;
    }
    //gear ratio is 7.58:12:15

    public class CLIMBER {
        public static final int SHIFTER_FORWARD_CHANNEL = 0;
        public static final int SHIFTER_REVERSE_CHANNEL = 1;
    }

    public class CARGO_ARM {
        public static final int CHANNEL_ONE = 0;//I don't know what channels to input these numbers are filler
        public static final int CHANNEL_TWO = 1;

        public static final int ARM_MOTOR_CHANNEL = 0;//also filler
    }

    public static final int LEFT_JOY = 0;
    public static final int RIGHT_JOY = 1;
    public static final int XBOX = 2;

    public static final int SHIFT_ENGAGE_BUTTON = 5;
    public static final int SHIFT_DISENGAGE_BUTTON = 11;

    public static final int PRESSURE_SENSOR_PORT = 0;
}
