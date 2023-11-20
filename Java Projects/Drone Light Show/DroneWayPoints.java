package Exam2;


public class DroneWayPoints {
    private int[][][] waypoints;

    public DroneWayPoints(int droneIndex) {
        waypoints = new int[][][] { DroneFlightPaths.coordinates[droneIndex] };
    }

    public int[][][] getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(int[][][] waypoints) {
        this.waypoints = waypoints;
    }
}


