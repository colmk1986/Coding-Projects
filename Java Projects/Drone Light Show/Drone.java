package Exam2;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class Drone implements Runnable {
    private String name, droneId;
    private double x, y, z;  // current position
    private double vx, vy, vz;  // current velocity
    private FlightPath flightPath;
    private static List<Drone> drones = new ArrayList<>();
    private double speed;
    private boolean completed= false;
    private boolean isStartingWaypoint; 

    //declare currentPosition as a Waypoint object
    private Waypoint currentPosition;
    
    public Drone(String name, Waypoint initialPosition, double speed) {
        this.name = name;
        this.x = initialPosition.getX();
        this.y = initialPosition.getY();
        this.z = initialPosition.getZ();
        this.speed = speed;
        drones.add(this); // add this drone to the list of all drones
    
    }

    public void setFlightPath(FlightPath flightPath) {
        this.flightPath = flightPath;
    }

    public FlightPath getFlightPath() {
        return flightPath;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    @Override
    public void run() {
        while (!flightPath.isCompleted()) {
            Waypoint currentWaypoint = flightPath.getCurrentWaypoint();
            double dx = currentWaypoint.getX() - x;
            double dy = currentWaypoint.getY() - y;
            double dz = currentWaypoint.getZ() - z;

            if (dx == 0 && dy == 0 && dz == 0) {
                // reached the current waypoint, hover for 10 seconds
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flightPath.moveToNextWaypoint(this);
                continue;
            }

            // calculate the distance to the next waypoint
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

            if (distance <= 6) {
                // near miss or collision detected, hover for 10 seconds
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            // calculate the unit vector in the direction of the next waypoint
            double ux = dx / distance;
            double uy = dy / distance;
            double uz = dz / distance;

            // update the drone's velocity based on the direction of the next waypoint
            vx = ux;
            vy = uy;
            vz = uz;

            // move the drone towards the next waypoint
            x += vx;
            y += vy;
            z += vz;

         // update the current position of the drone
            currentPosition = new Waypoint(x, y, z, droneId, isStartingWaypoint);

            
            try {
                Thread.sleep(1000);  // move 1 meter per second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        completed = true;
    }
    public Waypoint getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Waypoint waypoint) {
        if (currentPosition != null) {
            // Check if any axis has changed
            if (waypoint.getX() != currentPosition.getX()) {
                System.out.println("Drone " + name + " - x-axis changed: " + currentPosition.getX() + " -> " + waypoint.getX());
            }
            if (waypoint.getY() != currentPosition.getY()) {
                System.out.println("Drone " + name + " - y-axis changed: " + currentPosition.getY() + " -> " + waypoint.getY());
            }
            if (waypoint.getZ() != currentPosition.getZ()) {
                System.out.println("Drone " + name + " - z-axis changed: " + currentPosition.getZ() + " -> " + waypoint.getZ());
            }
        }

        currentPosition = waypoint;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public String getName() {
        return this.name;
    }

   
    public void moveToCurrentWaypoint() {
        Waypoint currentWaypoint = flightPath.getCurrentWaypoint();
        double dx = currentWaypoint.getX() - x;
        double dy = currentWaypoint.getY() - y;
        double dz = currentWaypoint.getZ() - z;

        if (dx == 0 && dy == 0 && dz == 0) {
            // reached the current waypoint, hover for 10 seconds
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flightPath.moveToNextWaypoint(this); // Pass the current Drone object to the method
            return;
        }

        // calculate the distance to the current waypoint
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (distance <= 6) {
            // near miss or collision detected, hover for 10 seconds
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        // calculate the unit vector in the direction of the current waypoint
        double ux = dx / distance;
        double uy = dy / distance;
        double uz = dz / distance;

        // update the drone's velocity based on the direction of the current waypoint
        vx = ux * speed;
        vy = uy * speed;
        vz = uz * speed;

        // move the drone towards the current waypoint
        x += vx;
        y += vy;
        z += vz;

        try {
            Thread.sleep(1000);  // move 1 meter per second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
