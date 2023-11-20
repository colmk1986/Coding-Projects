package Exam2;

public class ClosestWaypoint {
    public static Waypoint getClosestWaypoint(Waypoint point, String droneId, boolean isStartingWaypoint) {
        Waypoint closest = null;
        double shortestDistance = Double.MAX_VALUE;

        for (int i = 0; i < DroneFlightPaths.coordinates.length; i++) {
            for (int j = 0; j < DroneFlightPaths.coordinates[i].length; j++) {
                double x = DroneFlightPaths.coordinates[i][j][0];
                double y = DroneFlightPaths.coordinates[i][j][1];
                double z = DroneFlightPaths.coordinates[i][j][2];
                Waypoint waypoint = new Waypoint(x, y, z, droneId, isStartingWaypoint);

                double distance = point.distance(waypoint);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    closest = waypoint;
                }
            }
        }

        return closest;
    }
}

