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
        public static final int leftMasterChannel = 0;
        public static final int leftFollower1Channel = 1;
        public static final int leftFollower2Channel = 2;
        public static final int rightMasterChannel = 13;
        public static final int rightFollower1Channel = 14;
        public static final int rightFollower2Channel = 15;

        public static final double TOLERANCE_AXLE = .01;
        public static final double EXPECTED_FREE_CURRENT = 1.8;
        public static final double TOLERANCE_CURRENT = .05;
    }

    public static final int LEFT_JOY = 0;
    public static final int RIGHT_JOY = 1;
}
