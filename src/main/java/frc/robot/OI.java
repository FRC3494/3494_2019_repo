/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.commands.climb.Shift;
import frc.robot.commands.climb.feet.ToggleRearFeet;
import frc.robot.commands.hatch.EjectHatch;
import frc.robot.commands.hatch.RetractHatchEjector;
import frc.robot.subsystems.HatchManipulator;

public class OI {

    private static OI INSTANCE = new OI();
    private Joystick leftFlight;
    private Joystick rightFlight;
    private XboxController xbox;

    private JoystickButton ejectHatch;
    private JoystickButton extendCenter;
    private JoystickButton toggleHatcherExtended;
    private JoystickButton secondLevel;
    private JoystickButton preclimb;

    private JoystickButton floorGet;
    private JoystickButton engageZbar;

    private OI() {
        leftFlight = new Joystick(RobotMap.OI.LEFT_JOY);
        rightFlight = new Joystick(RobotMap.OI.RIGHT_JOY);
        xbox = new XboxController(RobotMap.OI.XBOX);

        Runnable extendHatcher = () -> HatchManipulator.getInstance().setExtended(
                HatchManipulator.getInstance().isExtended() ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
        // Xbox binds
        ejectHatch = new JoystickButton(xbox, RobotMap.OI.EJECT_HATCH);
        extendCenter = new JoystickButton(xbox, RobotMap.OI.EXTEND_CENTER);
        toggleHatcherExtended = new JoystickButton(xbox, RobotMap.OI.EXTEND_HATCHER);
        secondLevel = new JoystickButton(xbox, RobotMap.OI.SECOND_LEVEL_CLIMBER);
        preclimb = new JoystickButton(xbox, 2);

        ejectHatch.whenPressed(new EjectHatch());
        ejectHatch.whenReleased(new RetractHatchEjector());
        secondLevel.whenPressed(new ToggleRearFeet());
        extendCenter.whenPressed(
                new InstantCommand(HatchManipulator.getInstance(),
                        () -> HatchManipulator.getInstance().toggleCenter()));
        toggleHatcherExtended.whenPressed(new InstantCommand(HatchManipulator.getInstance(), extendHatcher));
        // Driver joystick binds
        floorGet = new JoystickButton(leftFlight, 1);
        engageZbar = new JoystickButton(rightFlight, RobotMap.OI.ZBAR_ENGAGE_BUTTON);

        floorGet.whenPressed(new InstantCommand(HatchManipulator.getInstance(), extendHatcher));
        engageZbar.whenPressed(new Shift(DoubleSolenoid.Value.kReverse));
    }

    public static double removeDeadband(double y) {
        if (Math.abs(y) <= .05) {
            return 0;
        } else {
            return y;
        }
    }

    public double getLeftY() {
        return -removeDeadband(leftFlight.getY());
    }

    public double getRightY() {
        return -removeDeadband(rightFlight.getY());
    }

    public double getXboxRightY() {
        return removeDeadband(xbox.getY(GenericHID.Hand.kRight));
    }

    public boolean getXboxLeftBumper() {
        return this.xbox.getBumper(GenericHID.Hand.kLeft);
    }

    public boolean getXboxRightBumper() {
        return this.xbox.getBumper(GenericHID.Hand.kRight);
    }

    public double getXboxLeftTrigger() {
        return this.xbox.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    public double getXboxRightTrigger() {
        return this.xbox.getTriggerAxis(GenericHID.Hand.kRight);
    }

    public boolean getXboxA() {
        return this.xbox.getAButton();
    }

    public boolean getXboxB() {
        return this.xbox.getBButton();
    }

    public boolean cruiseControlCancel() {
        return (this.getLeftY() != 0 || this.getRightY() != 0) ||
                (this.getXboxA() || this.getXboxB()) ||
                (this.getXboxLeftBumper() || this.getXboxRightBumper());
    }

    public static OI getInstance() {
        return INSTANCE;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
