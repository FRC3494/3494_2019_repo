package frc.robot.sensors;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

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

    private static final Limelight INSTANCE = new Limelight();

    /**
     * Constructor for a Limelight with the name {@code limelight}.
     */
    private Limelight() {
        this("limelight");
    }

    /**
     * Constructor.
     *
     * @param name The name of the network table to use. By Limelight's default settings, this is {@code limelight}.
     */
    private Limelight(String name) {
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
    public double getXDistance() {
        return this.table.getEntry("tx").getDouble(0);
    }

    /**
     * Gets the vertical distance from the center of the image
     * to the center of the detected object, in degrees.
     *
     * @return The vertical distance from image center to object center in degrees.
     */
    public double getYDistance() {
        return this.table.getEntry("ty").getDouble(0);
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

    public static Limelight getInstance() {
        return INSTANCE;
    }
}
