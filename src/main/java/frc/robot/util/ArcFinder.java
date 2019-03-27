package frc.robot.util;

import edu.wpi.first.wpilibj.drive.Vector2d;
import frc.robot.RobotMap;

public class ArcFinder {
    private Vector2d directionIntoTarget;
    private double arcRadius;
    private double initialAngleToTargetCenter;
    private double angleBetweenCenterAndEdge;
    private double initialDistToTargetCenter;
    private double initialDistToTargetEdge;
    private double initialAngleToTargetEdge;
    private double kiteLegLength;
    private double cornerToTargetDist;
    private double initialDrivingDistance;
    private double initialDistToCorner;
    private double targetSkewAngle;
    private double distToTargetAfterArc;
    private double arcAngle;
    private boolean isRightOfTarget;
    private double leftToRightRatio;
    private boolean targetFacesRight;


    private static ArcFinder INSTANCE;

    public static ArcFinder getInstance() {
        return INSTANCE;
    }

    public void calculate(ArcConfig config) {
        this.directionIntoTarget = config.getDirectionIntoTarget();
        this.targetSkewAngle = config.getTargetSkewAngle();

        this.initialDistToTargetCenter = config.getInitialDistToTargetCenter();
        this.initialAngleToTargetCenter = config.getInitialAngleToTargetCenter();

        this.setInitialDistToCorner();

        this.initialAngleToTargetEdge = config.getInitialAngleToTargetEdge();
        this.isRightOfTarget = config.getIsRightOfTarget();



        this.angleBetweenCenterAndEdge = config.getAngleBetweenCenterAndEdge();
        this.initialDistToTargetEdge = config.getInitialDistToTargetEdge();

        this.setInitialDrivingDistance();

        this.setKiteLegLength();
        this.setCornerToTargetDist();
        this.setDistToTargetAfterArc();

        this.setArcRadius();
        this.setArcAngle();
        this.setLeftToRightRatio();
    }

    public double getArcRadius(){
        return this.arcRadius;
    }

    public double getArcAngle(){
        return this.arcAngle;
    }

    public boolean shouldTurn() {
        double angleToTurn;

        double yDist = this.initialDistToCorner * Math.sin(this.initialAngleToTargetCenter);
        double xDist = this.initialDistToCorner * Math.cos(this.initialAngleToTargetCenter);

        if (isRightOfTarget) {
            angleToTurn = this.initialAngleToTargetEdge - RobotMap.LIMELIGHT.FOV_DEG / 2;
        } else {
            angleToTurn = -this.initialAngleToTargetEdge + RobotMap.LIMELIGHT.FOV_DEG / 2;
        }

        return Math.sqrt(Math.pow(yDist / Math.tan(this.targetSkewAngle), 2)
                + Math.pow(xDist, 2)) >
                (-yDist / Math.tan(targetSkewAngle) +
                        xDist - this.initialDrivingDistance);
    }

    /**uses Newton's method to approximate the smallest angle the robot would need to turn to make the path possible
     * @return x, the angle the robot should turn in radians
     */
    public double getAngleToTurn(double guessAngle){
        double x = guessAngle, xNew;
        double yDist = this.initialDistToCorner * Math.sin(this.initialAngleToTargetCenter);
        double xDist = this.initialDistToCorner * Math.cos(this.initialAngleToTargetCenter);
        double y;
        double yPrime;

        for(int i = 0; i< 15; i++){
            y = Math.sqrt(Math.pow(yDist / Math.tan(x), 2) +
                        Math.pow(xDist, 2)) -
                        (-yDist / Math.tan(x) +
                            xDist - this.initialDrivingDistance);
            yPrime = -yDist / Math.pow(Math.cos(x) * Math.tan(x), 2) -
                            Math.pow(yDist / Math.cos(x),2) / (Math.pow(Math.tan(x), 3) *
                                    Math.sqrt(Math.pow(yDist / Math.tan(x), 2) + Math.pow(xDist, 2)));
            x = -y/yPrime + x;
        }
        return x;
    }

    public int getDirectionToTurn(){
        return (int)Math.signum(this.cornerToTargetDist - this.kiteLegLength) * (this.targetFacesRight ? 1 : -1);
    }

    private void setInitialDrivingDistance() {
        this.initialDrivingDistance = (this.initialDistToTargetEdge * Math.cos(RobotMap.LIMELIGHT.FOV_RAD / 2)
                - this.initialDistToTargetEdge * Math.sin(RobotMap.LIMELIGHT.FOV_RAD / 2)) / Math.tan(RobotMap.LIMELIGHT.FOV_RAD / 2);
    }

    private void setArcAngle() {
        this.arcAngle = Math.acos(1
                - (Math.pow(this.initialDistToTargetCenter * Math.cos(this.initialAngleToTargetCenter)
                + this.directionIntoTarget.x * this.distToTargetAfterArc - this.initialDrivingDistance, 2)
                + Math.pow(this.initialDistToTargetCenter * Math.sin(this.initialAngleToTargetCenter)
                + this.directionIntoTarget.y * this.distToTargetAfterArc, 2))
                / (2 * Math.pow(this.arcRadius, 2)));
    }

    //this.directionIntoTarget.rotate() takes degrees and rotates counterclockwise
    private Vector2d getRotatedDirectionIntoTarget() {
        Vector2d rotatedDirection = new Vector2d(this.directionIntoTarget.x, this.directionIntoTarget.y);
        rotatedDirection.rotate(90);
        return rotatedDirection;
    }

    private void setDistToTargetAfterArc() {
        this.distToTargetAfterArc = this.cornerToTargetDist - this.kiteLegLength;
    }

    //equation: sqrt((d_ix - L_x)^2 + (d_iy)^2))
    private void setCornerToTargetDist() {
        this.cornerToTargetDist = Math.sqrt(Math.pow(
                this.initialDistToTargetCenter * Math.cos(this.initialAngleToTargetCenter) - this.initialDistToCorner, 2))
                + (Math.pow(this.initialDistToTargetCenter * Math.sin(this.initialAngleToTargetCenter), 2));
    }

    private void setKiteLegLength() {
        this.kiteLegLength = this.initialDistToCorner - this.initialDrivingDistance;
    }

    private void setInitialDistToCorner() {
        this.initialDistToCorner = -(this.initialDistToTargetCenter * Math.sin(this.initialAngleToTargetCenter)) / Math.tan(this.targetSkewAngle)
                + (this.initialDistToTargetCenter * Math.cos(this.initialAngleToTargetCenter));
    }

    public boolean isPathPossible() {
        return (this.cornerToTargetDist > this.kiteLegLength);
    }

    private void setArcRadius() {
        Vector2d rotatedDirection = getRotatedDirectionIntoTarget();
        this.arcRadius = rotatedDirection.y / rotatedDirection.x * (this.initialDistToTargetCenter * Math.cos(this.initialAngleToTargetCenter)
                + this.directionIntoTarget.x * this.distToTargetAfterArc + this.initialDrivingDistance)
                + this.initialDistToTargetCenter * Math.sin(this.initialAngleToTargetCenter)
                + this.directionIntoTarget.y * this.distToTargetAfterArc;
    }

    private void setLeftToRightRatio(){
        this.leftToRightRatio = (this.arcRadius - RobotMap.ARC_DRIVE.WIDTH_BETWEEN_ROBOT_WHEELS_FEET / 2)
                / (this.arcRadius + RobotMap.ARC_DRIVE.WIDTH_BETWEEN_ROBOT_WHEELS_FEET / 2);
    }

    public double getLeftToRightRatio(){
        return this.leftToRightRatio;
    }
}
