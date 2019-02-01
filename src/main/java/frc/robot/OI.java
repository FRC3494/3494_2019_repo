/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private static OI INSTANCE = new OI();
    // declare buttons, joysticks here
    private Joystick leftFlight;
    private Joystick rightFlight;

    private OI() {
        // construct joysticks, buttons here and bind buttons to actions
        leftFlight = new Joystick(RobotMap.LEFT_JOY);
        rightFlight = new Joystick(RobotMap.RIGHT_JOY);

    }

    public double removeDeadband(double y) {
        if (Math.abs(y) <= .05) {
            return 0;
        } else {
            return y;
        }
    }

    /**
     * Returns the Y value of the left joystick.
     *
     * @return The value of the stick Y axis. Should be in [-1, 1].
     */
    public double getLeftY() {
        return -this.removeDeadband(leftFlight.getY());
    }

    /**
     * Returns the Y value of the right joystick.
     *
     * @return The value of the stick Y axis. Should be in [-1, 1].
     */
    public double getRightY() {
        return -this.removeDeadband(rightFlight.getY());
    }

    public static OI getInstance() {
        return INSTANCE;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
