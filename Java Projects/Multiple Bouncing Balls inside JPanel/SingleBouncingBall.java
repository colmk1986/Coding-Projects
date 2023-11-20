package Wk2;

	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.RenderingHints;
	import java.util.ArrayList;
	import java.util.Random;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	

public class SingleBouncingBall extends JPanel{
	
	    private static final long serialVersionUID = 1L;
		private static final int WIDTH = 640;
	    private static final int HEIGHT = 480;
	    private static final int DELAY = 10;
	    private ArrayList<Ball> balls = new ArrayList<Ball>();

	    public SingleBouncingBall() {
	        setPreferredSize(new Dimension(WIDTH, HEIGHT));
	        setBackground(Color.WHITE);
	    }

	    public void start() {
	        Thread animationThread = new Thread(new AnimationRunnable());
	        animationThread.start();
	    }

	    public void addBall(Ball ball) {
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
	    	 SingleBouncingBall SingleBouncingBall = new SingleBouncingBall();
	         Random random = new Random();
	         int x = random.nextInt(WIDTH - Ball.SIZE);
	         int y = random.nextInt(HEIGHT - Ball.SIZE);
	         Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	         int dx = random.nextInt(10) - 5;
	         int dy = random.nextInt(10) - 5;
	         Ball ball = new Ball(x, y, color, dx, dy);
	         SingleBouncingBall.addBall(ball);

	         JFrame frame = new JFrame("Single Bouncing Ball");
	         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	         frame.add(SingleBouncingBall);
	         frame.pack();
	         frame.setLocationRelativeTo(null);
	         frame.setVisible(true);

	         SingleBouncingBall.start();
	    }
	    private class AnimationRunnable implements Runnable {
	        public void run() {
	            while (true) {
	                for (Ball ball : balls) {
	                    ball.move(getWidth(), getHeight());
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


