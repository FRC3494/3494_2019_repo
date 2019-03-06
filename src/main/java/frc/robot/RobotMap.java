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
        public static final int LEFT_MASTER_CHANNEL = 17;
        public static final int LEFT_FOLLOWER_ONE_CHANNEL = 1;
        public static final int LEFT_FOLLOWER_TWO_CHANNEL = 2;
        public static final int RIGHT_MASTER_CHANNEL = 13;
        public static final int RIGHT_FOLLOWER_ONE_CHANNEL = 14;
        public static final int RIGHT_FOLLOWER_TWO_CHANNEL = 15;

        public static final double GEAR_RATIO = 7.58 / 12 / 15;
        public static final double WHEEL_RADIUS_FEET = .25;
        public static final double WHEEL_CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS_FEET;
    }

    public class LIMELIGHT{
        public static final double FOV_DEG = 54;
        public static final double FOV_RAD = 5 * Math.PI / 72;
    }

    public class ARC_DRIVE{
        public static final double TIMEOUT = 15;
        public static final double CARGO_HATCH_TAPE_WIDTH_FEET = 14.63 / 12;
        public static final double CARGO_HATCH_TAPE_HEIGHT_FEET = 5.83 / 12;
        public static final double WIDTH_BETWEEN_ROBOT_WHEELS_FEET = 28 / 12; //find number
        public static final double SURFACE_ANGLE = 5; //find number
        public static final double NORMAL_LOWER = 0;//find
        public static final double NORMAL_CENTER = 0;//find
        public static final double NORMAL_UPPER = 0;//find
        public static final double MAX_SPEED = 0.5;//percent power

        public static final double
                kP = 0.0,
                kI = 0.0,
                kD = 0.0,
                kF = 0.0;
    }

    public class TURN_DRIVE{
        public static final double
                kP = 0.0,
                kI = 0.0,
                kD = 0.0,
                kF = 0.0;
        public static final double TIMEOUT = 5.0;
        public static final double TOLERANCE_RANGE_PID = 1.0;
    }
    //gear ratio is 7.58:12:15

    public class CLIMBER {
        public static final int FRONT_FOOT_FORWARD = 5;
        public static final int FRONT_FOOT_REVERSE = 4;

        public static final int REAR_FEET_FORWARD = 3;
        public static final int REAR_FEET_REVERSE = 2;

        public static final int SHIFTER_FORWARD_CHANNEL = 5;
        public static final int SHIFTER_REVERSE_CHANNEL = 6;
    }

    public class CARGO_ARM {
        public static final int ARM_MOTOR_CHANNEL = 6;

        public static final int DISK_BRAKE = 1;
    }

    public class HATCH_MANIPULATOR {
        public static final int PUSH_FORWARD_CHANNEL = 0;
        public static final int PUSH_REVERSE_CHANNEL = 4;

        public static final int CENTER_FORWARD_CHANNEL = 1;

        public static final int EXTENDER_FORWARD = 2;
        public static final int EXTENDER_REVERSE = 3;
    }

    public class CARGO_MANIPULATOR {
        public static final int LEFT_MOTOR_CHANNEL = 4;
        public static final int RIGHT_MOTOR_CHANNEL = 5;

        public static final double INTAKE_SPEED = 0.5;
        public static final double OUTAKE_SPEED = -INTAKE_SPEED;
    }

    public class OI {
        public static final int LEFT_JOY = 0;
        public static final int RIGHT_JOY = 1;
        public static final int XBOX = 2;

        public static final int ZBAR_ENGAGE_BUTTON = 1;

        public static final int EXTEND_HATCHER = 4; // Y
        public static final int EJECT_HATCH = 6; // right bumper
        public static final int EXTEND_CENTER = 1; // A
        public static final int SECOND_LEVEL_CLIMBER = 3; // X
    }

    public static final int PRESSURE_SENSOR_PORT = 0;

    public static final int PCM_A = 0;
    public static final int PCM_B = 1;
}
