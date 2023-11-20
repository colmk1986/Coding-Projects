package Wk2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
	

public class MultipleBouncingBallsP3 extends JPanel{
	
	    private static final long serialVersionUID = 1L;
		private static final int WIDTH = 640;
	    private static final int HEIGHT = 480;
	    private static final int DELAY = 10;
	    private final Object lock = new Object(); // lock object for synchronization
	    private ArrayList<Ball> balls = new ArrayList<Ball>();

	    public MultipleBouncingBallsP3() {
	        setPreferredSize(new Dimension(WIDTH, HEIGHT));
	        setBackground(Color.WHITE);
	    }

	    public void start() {
	        Thread animationThread = new Thread(new AnimationRunnable());
	        animationThread.start();
	    }

	    public void addBall(Ball ball) {
	    	//adding the ball object to the balls Arraylist
	        balls.add(ball);
	    }

	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        for (Ball ball : balls) {
	            g2d.setColor(ball.getColor());
	            g2d.fill(ball.getShape());
	        }
	    }

	    public static void main(String[] args) {
	    	 int numberOfBalls = Integer.parseInt(JOptionPane.showInputDialog("Please enter the number of balls required: "));
	    	 MultipleBouncingBallsP3 MultipleBouncingBalls = new MultipleBouncingBallsP3();
	    	 Random random = new Random();

	    	 //creating array to store colours generated
	    	 Color[] uniqueColors = new Color[numberOfBalls];
	    	 int uniqueColorCount = 0;
	    	

	    	 for (int i = 0; i < numberOfBalls; i++) {
	    	     int x = random.nextInt(WIDTH - Ball.SIZE);
	    	     int y = random.nextInt(HEIGHT - Ball.SIZE);
	    	     Color color;
	    	     boolean colorIsUnique;

	    	     do {
	    	         color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	    	         colorIsUnique = true;
	    	         //check if the color is already in the uniqueColors array by looping through all the colors in the array 
	    	         //If the color is already in the array, we set colorIsUnique to false
	    	         for (int j = 0; j < uniqueColorCount; j++) {
	    	             if (color.equals(uniqueColors[j])) {
	    	                 colorIsUnique = false;
	    	                 break;
	    	             }
	    	         }
	    	     } while (uniqueColorCount < numberOfBalls && !colorIsUnique);

	    	     int dx = random.nextInt(10) - 5;
	    	     int dy = random.nextInt(10) - 5;
	    	     double mass = random.nextDouble() * 10; // Declaring and initialize the mass variable
	    	     Ball ball = new Ball(x, y, dx, dy, mass , color);

	    	     if (uniqueColorCount <numberOfBalls) {
	    	         uniqueColors[uniqueColorCount] = color;
	    	         uniqueColorCount++;
	    	     }

	    	     MultipleBouncingBalls.addBall(ball);
	    	 
	    }
	    	 
	    	 JFrame frame = new JFrame("Part 3: Multiple Bouncing Balls that collide.");
	    	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	 frame.add(MultipleBouncingBalls);
	    	 frame.pack();
	    	 frame.setLocationRelativeTo(null);
	    	 frame.setVisible(true);

	    	 MultipleBouncingBalls.start();
	    }
	    private class AnimationRunnable implements Runnable {
	    	public void run() {
	            for (Ball ball : balls) {
	                Thread thread = new Thread(new BallRunnable(ball, lock));
	                thread.start();
	            }
	        }
	    }
	    
	    private class BallRunnable implements Runnable {
	        private Ball ball;
	        private final Object lock;

	        public BallRunnable(Ball ball, Object lock) {
	            this.ball = ball;
	            this.lock = lock;
	        }

	        public void run() {
	            while (true) {
	                synchronized(lock) {
	                    ball.move(getWidth(), getHeight(), balls);
	                }
	                repaint();
	                try {
	                    Thread.sleep(DELAY);
	                } catch (InterruptedException ex) {}
	            }
	        }
	    }
	    
	    
	}

	class Ball {
		public static final int SIZE = 20; //the size of the ball and is set to 20.
		private double x; //x-coordinate of the ball.
		private double y; //y-coordinate of the ball.
		private double dx;//x-velocity of the ball.
		private double dy; //y-velocity of the ball.
		private Color color;
		private double radius;
	    private final Object lock; // lock used for synchronization in the move method.
	    private double mass;//the mass of the ball, which is used to calculate the new velocities after a collision.
	    
	    //This instance variable represents whether the ball has collided with another ball 
	    private volatile boolean collided; // adding the volatile keyword to ensure changes are visible to all other threads.
	    private boolean isCollided;
	    
	    
	    
	    public Ball(double x, double y, double dx, double dy, double mass, Color color) {
	        this.x = x;
	        this.y = y;
	        this.dx = dx;
	        this.dy = dy;
	        this.color = color;
	        this.radius = SIZE / 2.0;
	        this.mass = mass;
	        this.lock = new Object();
	        this.collided = false;
	    }

	    public Color getColor() {
	        return color;
	    }
	    
	    public void setColor(Color color) {
	        this.color = color;
	    }

	    public void move(int width, int height, ArrayList<Ball> balls) {
	        synchronized(lock) {
	        	collided = false;// ensuring boolean collided is set to false at the beginning
	        	// First loop to detect collisions
	        	 
	        	
	            for (Ball otherBall : balls) {
	                if (this != otherBall && detectCollision(otherBall)) {
	                    // Calculate new velocities for collision
	                    double dx1 = ((dx * (mass - otherBall.getMass())) + (2 * otherBall.getMass() * otherBall.getDx())) / (mass + otherBall.getMass());
	                    double dy1 = ((dy * (mass - otherBall.getMass())) + (2 * otherBall.getMass() * otherBall.getDy())) / (mass + otherBall.getMass());
	                    double dx2 = ((otherBall.getDx() * (otherBall.getMass() - mass)) + (2 * mass * dx)) / (mass + otherBall.getMass());
	                    double dy2 = ((otherBall.getDy() * (otherBall.getMass() - mass)) + (2 * mass * dy)) / (mass + otherBall.getMass());

	                    // Update velocities for both balls
	                    setDx(dx1);
	                    setDy(dy1);
	                    otherBall.setDx(dx2);
	                    otherBall.setDy(dy2);
	                }
	            }
	            
	            // Move the ball
	            if (x + dx < 0 || x + dx > width - SIZE) {
	                dx = -dx;
	            }
	            if (y + dy < 0 || y + dy > height - SIZE) {
	                dy = -dy;
	            }
	            x += dx;
	            y += dy;
	        }
	    }


	    public java.awt.Shape getShape() {
	        return new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
	    }
	    
	    public boolean detectCollision(Ball other) {
	        double dx = x - other.getX();
	        double dy = y - other.getY();
	        double distance = Math.sqrt(dx * dx + dy * dy);
	        double radiiSum = getRadius() + other.getRadius();
	        return distance <= radiiSum;
	    }
	    
	   
	    //added additional getters and setters required for collision detection
	    public double getX() {
	        return x;
	    }

	    public double getY() {
	        return y;
	    }
	    
	    public void setX(double x) {
	        this.x = x;
	    }

	    public void setY(double y) {
	        this.y = y;
	    }
	    
	    public double getDx() {
	        return dx;
	    }

	    public double getDy() {
	        return dy;
	    }
	    
	    public void setDx(double dx) {
	        this.dx = dx;
	    }

	    public void setDy(double dy) {
	        this.dy = dy;
	    }

	    public double getRadius() {
	        return radius;
	    }
	    
	    //This method returns whether the ball has collided with another ball.
	    public boolean isCollided() {
	        return collided;
	    }
	    
	    //This method sets the collided variable to the specified boolean value.
	    public void setCollided(boolean collided) {
	        this.isCollided = collided;
	    }
	    
	    //method returns the mass of the ball.
	    public double getMass() {
	        return mass;
	    }
	}





