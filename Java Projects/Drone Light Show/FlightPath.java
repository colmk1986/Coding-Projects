package Exam2;

import java.awt.Point;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FlightPath {
	private long startTime;
	 private String droneID;
	 private int currentWaypointIndex;
	 private ArrayList<Waypoint> waypoints;
	 //require this list to contain all the drones so i can check for collisions later
	 private List<Drone> droneList;
	 
	 // I attempted to use this to pause the start simulation in the hoverInPlace method but it never worked.
	 private static boolean pauseSimulation = true;
	    

	    public FlightPath(Drone drone, DroneWayPoints droneWaypoints, List<Drone> droneList) {
	        this.droneID = drone.getName();
	        this.currentWaypointIndex = 0;
	        this.waypoints = new ArrayList<>();
	        this.droneList = droneList;
	        this.startTime = System.currentTimeMillis(); 
	        
	    }
	    public Waypoint getCurrentWaypoint() {
	        if (this.waypoints.size() > 0 && this.currentWaypointIndex < this.waypoints.size()) {
	            return this.waypoints.get(this.currentWaypointIndex);
	        } else {
	            return null;
	        }
	    }

	    public void moveToNextWaypoint(Drone drone) {
	        this.currentWaypointIndex += 1;
	        if (this.currentWaypointIndex >= this.waypoints.size()) {
	            // Reset index to zero and start from the beginning
	            this.currentWaypointIndex = 0;
	        }

	        if (!waypoints.isEmpty()) {
	            Waypoint nextWaypoint = waypoints.remove(0);
	            drone.setCurrentPosition(nextWaypoint); // Set the current position of the drone to the next waypoint
	            System.out.println("Drone moved to waypoint " + nextWaypoint);
	        } else {
	            System.out.println("No more waypoints to move to.");
	        }
	    }

	    public ArrayList<Waypoint> getWaypoints() {
	        return waypoints;
	    }

	    public void setWaypoints(List<Waypoint> waypoints) {
	        this.waypoints = new ArrayList<>(waypoints);
	    }

	    public int getCurrentWaypointIndex() {
	        return currentWaypointIndex;
	    }

	    public void setCurrentWaypointIndex(int currentWaypointIndex) {
	        this.currentWaypointIndex = currentWaypointIndex;
	    }

	    public boolean isCompleted() {
	        return currentWaypointIndex >= waypoints.size();
	    }
    
	    public void setDrones(List<Drone> droneList) {
	        this.droneList = droneList;
	    }
    public static void main(String[] args) {
    	//Scanner to read in the user input
    	Scanner scanner = new Scanner(System.in);
    	//exit to exit the switch statement
    	 boolean exit = false;
    	// Prompt the user for input and display the menu options
        System.out.println("Welcome to the Drone Light Management System.");
        System.out.println("Please select an option:");
        System.out.println("1. Start simulation.");
        System.out.println("2. Hover in Place.");
        System.out.println("3. Land in Place. ");
        System.out.println("4. Return to Home.");
        System.out.println("5. Exit.");
        System.out.println("Enter your choice (1-5):");

        int choice = scanner.nextInt();
   	    scanner.nextLine(); // Consume newline character
   	    //utilising executorSerivce as i need to have the main simulation running but also be able to 
   	    //listen for menu inputs for additional functions for options 2,3,4
   	    ExecutorService executorService = Executors.newSingleThreadExecutor();

        while (choice!=5) {
        	//choice = scanner.nextInt();
        	switch (choice) {
            case 1:
            	executorService.submit(FlightPath::startSimulation);
        break;
    case 2:
       hoverInPlace();
        break;
    case 3:
       landInPlace();
        break;
    case 4:
    	// Interrupt all simulation threads
        executorService.shutdownNow();

        // Wait for all tasks to complete
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.err.println("Error occurred while waiting for tasks to complete: " + e.getMessage());
        }

        // Print the message after all drones have returned home
        System.out.println("All drones have returned home.");
       returnToHome();
        break;
    case 5:
    	// Exit the program
        System.out.println("Exiting the Flight Path Management System...");
        exit = true;
        break;
    default:
        System.out.println("Invalid choice!");
        	}
        	 // Prompt the user for the next choice
            System.out.println("Please select an option:");
            System.out.println("1. Start simulation.");
            System.out.println("2. Hover in Place.");
            System.out.println("3. Land in Place. ");
            System.out.println("4. Return to Home.");
            System.out.println("5. Exit.");
            System.out.println("Enter your choice (1-5):");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
        }
        // Close the scanner
        scanner.close();
        // Exiting the Flight Path Management System
        System.out.println("Exiting the Flight Path Management System...");
    }





    private static void startSimulation() {
    	
    	
List<Drone> droneList = new ArrayList<>();
    	
        // Create 5 droneWayPoint objects  A-E with the five different flight paths from the file DroneFlightPaths class

        DroneWayPoints droneWayPointsA = new DroneWayPoints(0); 
        DroneWayPoints droneWayPointsB = new DroneWayPoints(1); 
        DroneWayPoints droneWayPointsC = new DroneWayPoints(2); 
        DroneWayPoints droneWayPointsD = new DroneWayPoints(3); 
        DroneWayPoints droneWayPointsE = new DroneWayPoints(4); 
      

        Waypoint initialPositionA = new Waypoint(50, 38, 0, "Drone A", true); //starting point for the drone
        Drone droneA = new Drone("Drone A", initialPositionA, 10.0); // Create an instance for the first drone
        
        Waypoint initialPositionB = new Waypoint(50, 44, 0, "Drone B", true);
        Drone droneB = new Drone("Drone B", initialPositionB, 10.0);
        
        Waypoint initialPositionC = new Waypoint(50, 50, 0, "Drone C", true);
        Drone droneC = new Drone("Drone C", initialPositionC, 10.0);
        
        Waypoint initialPositionD = new Waypoint(50, 56, 0, "Drone D", true);
        Drone droneD = new Drone("Drone D", initialPositionD, 10.0);
        
        Waypoint initialPositionE = new Waypoint(50, 62, 0, "Drone E", true);
        Drone droneE = new Drone("Drone E", initialPositionE, 10.0);
        
        
        droneList.add(droneA);
        droneList.add(droneB);
        droneList.add(droneC);
        droneList.add(droneD);
        droneList.add(droneE);

        FlightPath flightPathA = new FlightPath(droneA, droneWayPointsA, droneList);
        FlightPath flightPathB = new FlightPath(droneB, droneWayPointsB, droneList);
        FlightPath flightPathC = new FlightPath(droneC, droneWayPointsC, droneList);
        FlightPath flightPathD = new FlightPath(droneD, droneWayPointsD, droneList);
        FlightPath flightPathE = new FlightPath(droneE, droneWayPointsE, droneList);

        
        droneA.setFlightPath(flightPathA);
        droneB.setFlightPath(flightPathB);
        droneC.setFlightPath(flightPathC);
        droneD.setFlightPath(flightPathD);
        droneE.setFlightPath(flightPathE);

        Thread threadA = new Thread(() -> {
        	flightPathA.moveDrone(droneA, droneWayPointsA);
        });

        Thread threadB = new Thread(() -> {
        	flightPathB.moveDrone(droneB, droneWayPointsB);
        });

        Thread threadC = new Thread(() -> {
        	flightPathC.moveDrone(droneC, droneWayPointsC);
        });

        Thread threadD = new Thread(() -> {
        	flightPathD.moveDrone(droneD, droneWayPointsD);
        });

        Thread threadE = new Thread(() -> {
        	flightPathE.moveDrone(droneE, droneWayPointsE);
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        threadE.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
            threadE.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         
	
  }
	//The moveDrone() method contains the logic for iterating through the waypoints of 
    //each drone's flight path and updating their positions accordingly. Without calling 
    //this method, the drones won't actually move along their designated paths.
    
    public void moveDrone(Drone drone, DroneWayPoints droneWayPoints) {
    	//putting the drones already checked into an Array to avoid repitition
    	List<Drone> checkedDrones = new ArrayList<>();
        int[][][] waypoints = droneWayPoints.getWaypoints();
        
        List<Drone> collidedDrones = new ArrayList<>();

        double speed = 5.0; // Speed in meters per second
        
        System.out.println("Simulation started");
        
        for (int i = 0; i < waypoints.length; i++) {
            for (int j = 0; j < waypoints[i].length; j++) {
                int[] waypoint = waypoints[i][j];
                int x = waypoint[0];
                int y = waypoint[1];
                int z = waypoint[2];
                double distance = calculateDistance(drone.getX(), drone.getY(), drone.getZ(), x, y, z);
                // Calculate time in seconds
                long time = Math.round(distance / speed);
               

                System.out.println(drone.getName() + " moved to waypoint " + (j + 1) + " in set " + (i + 1) +
                        ": x=" + x + ", y=" + y + ", z=" + z + " (Time: " + time + " seconds)");

             // Delay for the calculated time between waypoints
                try {
                    TimeUnit.SECONDS.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Check for collisions
                if (checkCollision(drone, waypoints,checkedDrones)) {
                    // Store collided drones
                    collidedDrones.add(drone);
                }
            }
            long elapsedTime = (System.currentTimeMillis() - startTime) / 10000;
            // Print the details of the collided drones
            if (!collidedDrones.isEmpty()) {
                System.out.println("Potential collisions detected for drone: " + drone.getName());
                for (Drone collidedDrone : collidedDrones) {
                    System.out.println("Potential collision with drone: " + collidedDrone.getName() +
                            ", Coordinates: (" + collidedDrone.getX() + ", " + collidedDrone.getY() + ", " +
                            collidedDrone.getZ() + "), Elapsed time: " + elapsedTime + " seconds");
                }
            }
            System.out.println("Simulation ended");
        }
    }
        
   

    public boolean checkCollision(Drone drone, int[][][] waypoints, List<Drone> checkedDrones) {
        double currentX = drone.getX();
        double currentY = drone.getY();
        double currentZ = drone.getZ();
        boolean collisionDetected = false;

        for (int i = 0; i < waypoints.length; i++) {
            for (int j = 0; j < waypoints[i].length; j++) {
                int[] waypoint = waypoints[i][j];
                int x = waypoint[0];
                int y = waypoint[1];
                int z = waypoint[2];

                // Calculate distance between current position and waypoint
                double distance = calculateDistance(currentX, currentY, currentZ, x, y, z);

                if (distance < 6) {
                    // Collision detected
                    collisionDetected = true;
                    Drone collidedDroneInstance = getDroneByCoordinates(x, y, z);

                    if (collidedDroneInstance != null && !collidedDroneInstance.equals(drone) &&
                            !checkedDrones.contains(collidedDroneInstance)) {
                    	// Calculate time in seconds
                        long time = (long) (distance / 1.0); // Assuming speed of 1 meter per second
                        
                    	System.out.println("Potential collision detected for drone " + drone.getName() +
                    		    " with drone " + collidedDroneInstance.getName() +
                    		    " (Coordinates: " + currentX + ", " + currentY + ", " + currentZ + " - " +
                    		    collidedDroneInstance.getX() + ", " + collidedDroneInstance.getY() + ", " +
                    		    collidedDroneInstance.getZ() + ")" + "with a collision time of" + time + "seconds");

                        System.out.println("Drone " + collidedDroneInstance.getName() + ": Coordinates (" +
                                collidedDroneInstance.getX() + ", " + collidedDroneInstance.getY() + ", " +
                                collidedDroneInstance.getZ() + ")");

                        checkedDrones.add(drone);
                        checkedDrones.add(collidedDroneInstance);
                     
                    }
                    }
                }
            }
        


        if (!collisionDetected) {
            System.out.println("No collision detected for drone " + drone.getName());
        }
        return collisionDetected;
    }





    public double calculateDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
    	double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) + Math.pow((z2 - z1), 2));
        return distance;
    }
  
    public Drone getDroneByCoordinates(double x, double y, double z) {
        for (Drone drone : droneList) {
            if (drone.getX() == x && drone.getY() == y && drone.getZ() == z) {
                return drone;
            }
        }
        return null; // If no drone is found with the given coordinates
    }
    
    private static void hoverInPlace() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hover in Place");
        System.out.println("Press 1 to start/continue the simulation");

        int userInput = scanner.nextInt();
        if (userInput == 1) {
            synchronized (FlightPath.class) {
            	//Unable to pause the simulation as startSimulation() is started with an executor service . this have no pause feature only a shutdown
            	//i was unable to get the boolean to correctly pause and restart the program.
                pauseSimulation = false; // Pause the simulation
            }

          

            System.out.println("Simulation paused. Press 1 to resume.");
            userInput = scanner.nextInt();

            if (userInput == 1) {
                synchronized (FlightPath.class) {
                	pauseSimulation = true; // Resume the simulation
                }
            } else {
                System.out.println("Invalid input. Simulation still paused.");
            }
        } else {
            System.out.println("Invalid input. Simulation canceled.");
        }

        scanner.close();
    }
    private static void returnToHome() {
        System.out.println("Returning to home...");
        
    }
    private static void landInPlace() {  
        System.out.println("Landing in place...");
    }
}

