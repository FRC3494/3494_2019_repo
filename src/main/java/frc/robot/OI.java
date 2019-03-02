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
import frc.robot.commands.climb.feet.SetFrontFoot;
import frc.robot.commands.climb.feet.SetRearFeet;
import frc.robot.commands.climb.groups.LevelThree;
import frc.robot.commands.hatch.EjectHatch;
import frc.robot.commands.hatch.RetractHatchEjector;
import frc.robot.commands.hatch.SetHatchExtender;
import frc.robot.subsystems.HatchManipulator;

public class OI {

    private static OI INSTANCE = new OI();
    private Joystick leftFlight;
    private Joystick rightFlight;
    private XboxController xbox;

    private JoystickButton ejectHatch;
    private JoystickButton extendCenter;
    private JoystickButton extendHatcher;
    private JoystickButton retractHatcher;

    private JoystickButton engageZbar;
    private JoystickButton disengageZbar;
    private JoystickButton retractAllFeet;
    private JoystickButton engageRearFeet;

    private OI() {
        leftFlight = new Joystick(RobotMap.OI.LEFT_JOY);
        rightFlight = new Joystick(RobotMap.OI.RIGHT_JOY);
        xbox = new XboxController(RobotMap.OI.XBOX);

        // Xbox binds
        ejectHatch = new JoystickButton(xbox, RobotMap.OI.EJECT_HATCH);
        extendCenter = new JoystickButton(xbox, RobotMap.OI.EXTEND_CENTER);
        extendHatcher = new JoystickButton(xbox, RobotMap.OI.EXTEND_HATCHER);
        retractHatcher = new JoystickButton(xbox, RobotMap.OI.RETRACT_HATCHER);
        ejectHatch.whenPressed(new EjectHatch());
        ejectHatch.whenReleased(new RetractHatchEjector());
        extendCenter.whenPressed(
                new InstantCommand(HatchManipulator.getInstance(),
                        () -> HatchManipulator.getInstance().toggleCenter()));
        extendHatcher.whenPressed(new SetHatchExtender(true));
        retractHatcher.whenPressed(new SetHatchExtender(false));
        // Driver joystick binds
        disengageZbar = new JoystickButton(leftFlight, RobotMap.OI.ZBAR_DISENGAGE_BUTTON);
        engageZbar = new JoystickButton(leftFlight, RobotMap.OI.ZBAR_ENGAGE_BUTTON);
        engageRearFeet = new JoystickButton(leftFlight, RobotMap.OI.ENGAGE_REAR_FEET);
        retractAllFeet = new JoystickButton(leftFlight, RobotMap.OI.DISENGAGE_ALL_FEET);

        engageRearFeet.whenPressed(new SetRearFeet(DoubleSolenoid.Value.kForward));
        retractAllFeet.whenPressed(new SetFrontFoot(DoubleSolenoid.Value.kReverse));
        retractAllFeet.whenPressed(new SetRearFeet(DoubleSolenoid.Value.kReverse));

        disengageZbar.whenPressed(new LevelThree.UNREADY());
        engageZbar.whenPressed(new LevelThree.READY());
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

    public double getXboxRightX() {
        return removeDeadband(xbox.getX(GenericHID.Hand.kRight));
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
