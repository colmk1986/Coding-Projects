package Exam2;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class DroneFlight {
    private int id;
    private Drone drone;
    private List<Waypoint> waypoints;
    private String description;
    private Date startTime;
    private Date endTime;
    private double distance;
    private int currentWaypointIndex = 0;
    
    //utilised for calculating distance between all drones in calcualteDistance()
    private static final double EARTH_RADIUS = 6371; // Approximate radius of the earth in km

    public DroneFlight(int id, Drone drone, List<Waypoint> waypoints, String description, Date startTime, Date endTime, double distance) {
        this.id = id;
        this.drone = drone;
        this.waypoints = waypoints;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double calculateFlightTime() {
        long diff = endTime.getTime() - startTime.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(diff);
    }

    public double calculateAverageSpeed() {
        double flightTime = calculateFlightTime();
        return distance / (flightTime / 60);
    }
    
    public Waypoint getCurrentWaypoint() {
        return waypoints.get(currentWaypointIndex);
    }

    public Waypoint moveToNextWaypoint() {
        currentWaypointIndex++;
        return waypoints.get(currentWaypointIndex);
    }
    
    
    //This implementation takes in two Waypoint objects and calculates the distance between 
    //them using the Haversine formula. The result is returned in kilometers, which is the unit used by the EARTH_RADIUS constant.

    private double calculateDistance(Waypoint currentPosition, Waypoint nextPosition) {
        double lat1 = Math.toRadians(currentPosition.getLatitude());
        double lon1 = Math.toRadians(currentPosition.getLongitude());
        double lat2 = Math.toRadians(nextPosition.getLatitude());
        double lon2 = Math.toRadians(nextPosition.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat/2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon/2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return EARTH_RADIUS * c;
    }
    
    public void move() {
        // get the current position of the drone
        Waypoint currentPosition = drone.getCurrentPosition();
        
        // get the next waypoint
        Waypoint nextWaypoint = waypoints.get(currentWaypointIndex + 1);
        
        // calculate the distance between the current position and the next waypoint
        double distance = calculateDistance(currentPosition, nextWaypoint);
        
        // calculate the time it will take to move to the next waypoint
        int timeToMove = (int) (distance / drone.getSpeed());
        
        // calculate the distance to move each iteration
        double distancePerIteration = distance / timeToMove;
        
        // calculate the latitude and longitude differences between the current position and the next waypoint
        double latDiff = nextWaypoint.getLatitude() - currentPosition.getLatitude();
        double lonDiff = nextWaypoint.getLongitude() - currentPosition.getLongitude();
        
        // calculate the latitude and longitude differences to move each iteration
        double latPerIteration = latDiff / timeToMove;
        double lonPerIteration = lonDiff / timeToMove;
        
        // update the drone's position incrementally over the time to move
        for (int i = 0; i < timeToMove; i++) {
            double newLat = currentPosition.getLatitude() + latPerIteration;
            double newLon = currentPosition.getLongitude() + lonPerIteration;
            currentPosition = new Waypoint(newLat, newLon,0);
            drone.setCurrentPosition(currentPosition);
            System.out.println("Moved to: " + currentPosition);
            try {
                Thread.sleep(1000); // wait 1 second between each iteration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // update the current waypoint index to the next waypoint
        currentWaypointIndex++;
    }



}
