package Exam2;

public class Waypoint {
    private double x;
    private double y;
    private double z;
    private String droneId; // New field to store the drone ID
    private boolean isStartingWaypoint; // New field to indicate if it's the starting waypoint

   

    public Waypoint(double x, double y, double z, String droneId, boolean isStartingWaypoint) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.droneId= droneId;
        this.isStartingWaypoint= isStartingWaypoint;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }
    
    public boolean isStartingWaypoint() {
        return isStartingWaypoint;
    }

    public void setisStartingWaypoint(boolean isStartingWaypoint) {
        this.isStartingWaypoint = isStartingWaypoint;
    }
    public double distance(Waypoint other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double getLatitude() {
        // Assuming x represents longitude and y represents latitude
        double lat = y * 180 / Math.PI;
        return lat;
    }

    public double getLongitude() {
        // Assuming x represents longitude and y represents latitude
        double lon = x * 180 / Math.PI;
        return lon;
    }
 // Override the toString() method to print the waypoint with drone ID
    @Override
    public String toString() {
        return "Waypoint (" + x + ", " + y + ", " + z + ") for Drone " + droneId;
    }
}
