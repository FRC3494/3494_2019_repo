/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    //// joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    private static OI INSTANCE = new OI();
    // declare buttons, joysticks here
    private Joystick leftFlight;
    private Joystick rightFlight;

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

    private OI() {
        // construct joysticks, buttons here and bind buttons to actions
        leftFlight = new Joystick(RobotMap.LEFT_JOY);
        rightFlight = new Joystick(RobotMap.RIGHT_JOY);
    }

    public static OI getInstance() {
        return INSTANCE;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
