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
import frc.robot.commands.hatch.ExtendHatchManipulator;
import frc.robot.commands.hatch.RetractHatchManipulator;

public class OI {

    private static OI INSTANCE = new OI();
    private Joystick leftFlight;
    private Joystick rightFlight;
    private XboxController xbox;
    private JoystickButton extendHatchManipulatorButton;
    private JoystickButton retractHatchManipulatorButton;

    private OI() {

        leftFlight = new Joystick(RobotMap.LEFT_JOY);
        rightFlight = new Joystick(RobotMap.RIGHT_JOY);

        xbox = new XboxController(RobotMap.OI.XBOX_CONTROLLER);
        extendHatchManipulatorButton = new JoystickButton(xbox, RobotMap.OI.EXTEND_HATCH_MANIPULATOR_BUTTON);
        retractHatchManipulatorButton = new JoystickButton(xbox, RobotMap.OI.RETRACT_HATCH_MANIPULATOR_BUTTON);

        extendHatchManipulatorButton.whenPressed(new ExtendHatchManipulator());
        retractHatchManipulatorButton.whenPressed(new RetractHatchManipulator());
    }

    public double removeDeadband(double y) {
        if (Math.abs(y) <= .05) {
            return 0;
        } else {
            return y;
        }
    }


    public double getLeftY() {
        return -this.removeDeadband(leftFlight.getY());
    }


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
