package Wk2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
	

public class MultipleBouncingBalls extends JPanel{
	
	    private static final long serialVersionUID = 1L;
		private static final int WIDTH = 640;
	    private static final int HEIGHT = 480;
	    private static final int DELAY = 10;
	    private ArrayList<Ball> balls = new ArrayList<Ball>();

	    public MultipleBouncingBalls() {
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
	    	 MultipleBouncingBalls MultipleBouncingBalls = new MultipleBouncingBalls();
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
	    	         //the color is already in the uniqueColors array by looping through all the colors in the array 
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
	    	     Ball ball = new Ball(x, y, color, dx, dy);

	    	     if (uniqueColorCount <numberOfBalls) {
	    	         uniqueColors[uniqueColorCount] = color;
	    	         uniqueColorCount++;
	    	     }

	    	     MultipleBouncingBalls.addBall(ball);
	    	 
	    }
	    	 
	    	 JFrame frame = new JFrame("Part 2: Multiple Bouncing Balls");
	    	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	 frame.add(MultipleBouncingBalls);
	    	 frame.pack();
	    	 frame.setLocationRelativeTo(null);
	    	 frame.setVisible(true);

	    	 MultipleBouncingBalls.start();
	    }
	    private class AnimationRunnable implements Runnable {
	        public void run() {
	            ArrayList<Thread> threads = new ArrayList<>();
	            for (Ball ball : balls) {
	                Thread thread = new Thread(new BallRunnable(ball));
	                threads.add(thread);
	                thread.start();
	            }
	            for (Thread thread : threads) {
	                try {
	                    thread.join();
	                } catch (InterruptedException ex) {}
	            }
	        }
	    }
	    
	    private class BallRunnable implements Runnable {
	        private Ball ball;

	        public BallRunnable(Ball ball) {
	            this.ball = ball;
	        }

	        public void run() {
	            while (true) {
	                ball.move(getWidth(), getHeight());
	                repaint();
	                try {
	                    Thread.sleep(DELAY);
	                } catch (InterruptedException ex) {}
	            }
	        }
	    }
	    
	    
	}

	class Ball {
	    public static final int SIZE = 20;
	    private int x;
	    private int y;
	    private Color color;
	    private int dx;
	    private int dy;

	    public Ball(int x, int y, Color color, int dx, int dy) {
	        this.x = x;
	        this.y = y;
	        this.color = color;
	        this.dx = dx;
	        this.dy = dy;
	    }

	    public Color getColor() {
	        return color;
	    }

	    public void move(int width, int height) {
	        if (x + dx < 0 || x + dx > width - SIZE) {
	            dx = -dx;
	        }
	        if (y + dy < 0 || y + dy > height - SIZE) {
	            dy = -dy;
	        }
	        x += dx;
	        y += dy;
	    }

	    public java.awt.Shape getShape() {
	        return new java.awt.geom.Ellipse2D.Float(x, y, SIZE, SIZE);
	    }
	}






 
