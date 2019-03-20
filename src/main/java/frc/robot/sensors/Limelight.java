package frc.robot.sensors;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.RobotMap;

/**
 * Class to represent a Limelight vision system.
 * Automatically retrieves values from {@link NetworkTable Network Tables} and contains wrapper methods
 * for reading the data.
 *
 * @author The Quadrangles 3494
 * @see "http://docs.limelightvision.io/en/latest/networktables_api.html"
 */
public class Limelight {
    /**
     * Constant for use with {@link Limelight#setLEDs(int) setLEDs(int)}.
     */
    public static final int LIMELIGHT_LED_ON = 0;
    /**
     * Constant for use with {@link Limelight#setLEDs(int) setLEDs(int)}.
     */
    public static final int LIMELIGHT_LED_OFF = 1;
    /**
     * Constant for use with {@link Limelight#setLEDs(int) setLEDs(int)}.
     */
    public static final int LIMELIGHT_LED_BLINK = 2;

    /**
     * The {@link NetworkTable} that contains the Limelight vision data.
     */
    private NetworkTable table;

    /**
     * Constructor for a Limelight with the name {@code limelight}.
     */
    public Limelight() {
        this("limelight");
    }

    /**
     * Constructor.
     *
     * @param name The name of the network table to use. By Limelight's default settings, this is {@code limelight}.
     */
    public Limelight(String name) {
        table = NetworkTableInstance.getDefault().getTable(name);
    }

    /**
     * Sets the pipeline to be used for processing.
     *
     * @param pipeline The pipeline number (0-9) to be used.
     */
    public void setPipeline(int pipeline) {
        this.table.getEntry("pipeline").setNumber(pipeline);
    }

    /**
     * Returns the number of the pipeline currently in use.
     *
     * @return int (0-9) id of the pipeline currently in use.
     */
    public int getPipeline() {
        return this.table.getEntry("getpipe").getNumber(0).intValue();
    }


    /**
     * Sets the LED mode on the Limelight.
     * {@code final static int}s are available as part of this class
     * to reduce the amount of magic numbers.
     *
     * @param state The LED state to set to.
     */
    public void setLEDs(int state) {
        this.table.getEntry("ledMode").setNumber(state);
    }

    /**
     * Gets the horizontal distance from the center of the image
     * to the center of the detected object, in degrees.
     *
     * @return The horizontal distance from image center to object center in degrees.
     */
    public double getTargetXAngleDeg() {
        return this.table.getEntry("tx").getDouble(0);
    }

    /**
     * Gets the horizontal distance from the center of the image
     * to the center of the detected object, in radians.
     *
     * @return The horizontal distance from image center to object center in radians.
     */
    public double getTargetXAngleRad() {
        return Math.toRadians(this.table.getEntry("tx").getDouble(0));
    }


    /**
     * Gets the distance between the robot and the target, in feet.
     * @return the distance between the robot and the target, in feet.
     */
    public double getDistance() {
        return this.getTVert() / this.getTHor() * RobotMap.LIMELIGHT.FOCAL_LENGTH;
    }

    /**
     * Returns the difference in angle from the direction the camera is looking and the
     * direction the target faces.
     * A target not "flush" with the camera is skewed.
     * @return the skew angle.
     */
    public double getSkewAngle() {
        return Math.PI / 2 - Math.acos(this.getAspectRatio() / RobotMap.LIMELIGHT.EXPECTED_ASPECT_RATIO);
    }

    /**
     *gets the vertical height of the target in pixels.
     * @return the vertical height of the target in pixels.
     */
    private double getTVert() {
        return this.table.getEntry("tvert").getDouble(0);
    }

    /**
     *gets the horizontal height of the target in pixels.
     * @return the horizontal height of the target in pixels.
     */
    private double getTHor() {
        return this.table.getEntry("thor").getDouble(0);
    }

    private double getAspectRatio() {
        return this.getTHor() / this.getTVert();
    }


    /**
     * Gets the vertical distance from the center of the image
     * to the center of the detected object, in degrees.
     *
     * @return The vertical distance from image center to object center in degrees.
     */
    public double getYTargetAngleDeg() {
        return this.table.getEntry("ty").getDouble(0);
    }

    /**
     * Gets the vertical distance from the center of the image
     * to the center of the detected object, in radians.
     *
     * @return The vertical distance from image center to object center in radians.
     */
    public double getYTargetAngleRad() {
        return Math.toRadians(this.table.getEntry("ty").getDouble(0));
    }

    public boolean hasValidTarget() {
        return this.table.getEntry("tv").getDouble(0) == 1;
    }

    /**
     * Returns the {@link NetworkTable} for more direct access.
     *
     * @return The {@link NetworkTable} for the Limelight.
     */
    public NetworkTable getTable() {
        return table;
    }

    /**
     * Method to refresh the network table. Call if you feel the need, but should not be needed.
     */
    public void tableRefresh() {
        table = NetworkTableInstance.getDefault().getTable(table.getPath());
    }
}
