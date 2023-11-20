package Wk2;

//Starting Conway’s Game of Life
//The main application class (single instance)
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.Random; 
import java.awt.Rectangle;

//imports for file handling
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

	public class ConwaysLife extends JFrame implements Runnable, MouseListener {

		// member data
		private BufferStrategy strategy;
		private Graphics offscreenBuffer;
		private boolean gameState[][] = new boolean[40][40];
		
		//game states
		private boolean playing;
		
		//creating an array called buttons to hold the buttons in
		//this will be used by the mouseListener to check for a button press
		private Rectangle[] buttons;
		
		// constructor
			public ConwaysLife () {
					//Display the window, centered on the screen
				Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				
				int x = screensize.width/2 - 400;
				int y = screensize.height/2 - 400;
				setBounds(x, y, 800, 800);
				setVisible(true);
				this.setTitle("Conway's game of life");

				// initialise double-buffering
				createBufferStrategy(2);
				strategy = getBufferStrategy();
				offscreenBuffer = strategy.getDrawGraphics();
				
				//Create 4 buttons for Start, Random, Load and Save options
				buttons = new Rectangle[4];
		        buttons[0] = new Rectangle(20, 20, 100, 50);
		        buttons[1] = new Rectangle(140, 20, 100, 50);
		        buttons[2] = new Rectangle(260, 20, 100, 50);
		        buttons[3] = new Rectangle(380, 20, 100, 50);
		        
				// register the JFrame itself to receive mouse events
				addMouseListener(this);

				// initialise the game state
				 playing = false;
				
				
				for (int i = 0; i < 40; i++) {
				    for (int j = 0; j < 40; j++) {
				        int liveNeighbors = 0;

				        // count the live neighbors of cell [x][y][0]
				        for (int xx = -1; xx <= 1; xx++) {
				            for (int yy = -1; yy <= 1; yy++) {
				                if (xx != 0 || yy != 0) {
				                    // calculate the coordinates of the neighbor cell
				                    int nx = i + xx;
				                    int ny = j + yy;

				                    // check if the neighbor cell is within the board boundaries
				                    if (nx >= 0 && nx < 40 && ny >= 0 && ny < 40) {
				                        // check if the neighbor cell is alive
				                        if (gameState[nx][ny]) {
				                            liveNeighbors++;
				                        }
				                    }
				                }
				            }
				        }
				    
				

				        // determine if cell [x][y][0] should be alive or dead
				        if (gameState[i][j]) {
				            if (liveNeighbors < 2 || liveNeighbors > 3) {
				                gameState[i][j] = false; // cell dies
				            }
				        } else {
				            if (liveNeighbors == 3) {
				                gameState[i][j] = true; // cell becomes alive
				            }
				        }
				    }
				}
				// create and start our animation thread
				Thread t = new Thread(this);
				t.start();
			}
			
			// thread's entry point
			public void run() {
			    while (true) {
			        // 1: sleep for 1/5 sec
			        try {
			            Thread.sleep(200);
			        } catch (InterruptedException e) {
			        }
			        //if statement to control whether the game plays or not 
			      //this links to the toggle functionality of the start button
			        if (playing) {
			            boolean newState[][] = new boolean[40][40];
			            // compute the new state of the board
			            for (int i = 0; i < 40; i++) {
			                for (int j = 0; j < 40; j++) {
			                    // compute the number of live neighbors of cell (i, j)
			                    int liveNeighbors = 0;
			                    for (int x = i - 1; x <= i + 1; x++) {
			                        for (int y = j - 1; y <= j + 1; y++) {
			                            if (x >= 0 && x < 40 && y >= 0 && y < 40 && !(x == i && y == j)) {
			                                if (gameState[x][y]) {
			                                    liveNeighbors++;
			                                }
			                            }
			                        }
			                    }
			                    // apply the rules of the game
			                    if (gameState[i][j]) {
			                        if (liveNeighbors < 2 || liveNeighbors > 3) {
			                            newState[i][j] = false;
			                        } else {
			                            newState[i][j] = true;
			                        }
			                    } else {
			                        if (liveNeighbors == 3) {
			                            newState[i][j] = true;
			                        } else {
			                            newState[i][j] = false;
			                        }
			                    }
			                }
			            }

			            // update the game state
			            gameState = newState;
			        }
			        // 3: force an application repaint
			        // initialise double-buffering
			        createBufferStrategy(2);
			        strategy = getBufferStrategy();
			        // create a graphics object
			        Graphics g = strategy.getDrawGraphics();
			        // clear the buffer
			        g.setColor(Color.black);
			        g.fillRect(0, 0, getWidth(), getHeight());

			        
			        // draw the game state
			        g.setColor(Color.white);
			        for (int i = 0; i < 40; i++) {
			            for (int j = 0; j < 40; j++) {
			                if (gameState[i][j]) {
			                    g.fillRect(i * 20, j * 20, 20, 20);
			                }
			            }
			        }
			        //draw 4 buttons required
			        drawButtons(g);

			        // dispose the graphics object
			        g.dispose();

			        // show the buffer
			        strategy.show();
			    }
			}
			
			
			// mouse events which must be implemented for MouseListener
			//mousePressed detects when the mouse is clicked and held
			public void mousePressed(MouseEvent e) {
				 int x = e.getX();
				    int y = e.getY();
				    
				    for (int i = 0; i < buttons.length; i++) {
				        if (buttons[i].contains(x, y)) {
				            switch (i) {
				                case 0:
				                    System.out.println("Mouse pressed Start");
				                    playing = !playing; // toggle between playing and not playing
				                    break;
				                case 1:
				                    System.out.println("Mouse pressed Random");
				                    // Initialise the game state randomly
				                    for (int i1 = 0; i1 < 40; i1++) {
				                        for (int j = 0; j < 40; j++) {
				                            Random random = new Random();
				                            gameState[i1][j] = random.nextBoolean();
				                        }
				                    }
				                    break;
				                case 2:
				                	System.out.println("Mouse pressed Load");
				                	loadGame();
				                    break;
				                case 3:
				                    System.out.println("Mouse pressed Save");
				                    try {
				                        FileWriter writer = new FileWriter(new File("gameState.txt"));
				                        for (int j = 0; j < 40; j++) {
				                            for (int k = 0; k < 40; k++) {
				                                writer.write(gameState[j][k] ? "1" : "0");
				                                writer.write(" ");
				                            }
				                            //I put a space between each element as it was printed to the file to assist with loading the file
				                            writer.write("\n");
				                        }
				                        writer.close();
				                    } catch (IOException ex) {
				                        ex.printStackTrace();
				                    }
				                    break;
				            }
				            return;
				        }
				    }
				    
				    // Only toggle cells when the game is playing
				    if (playing) {
				        // determine which cell of the gameState array was clicked on
				        int gameStateX = x / 20;
				        int gameStateY = y / 20;
				        
				        // toggle the state of the cell
				        gameState[gameStateX][gameStateY] = !gameState[gameStateX][gameStateY];
				    }
				}
			//event is triggered when the mouse button is released
			public void mouseReleased(MouseEvent e) { 
			    // determine which cell of the gameState array was clicked on
			    int x = e.getX()/20;
			    int y = e.getY()/20;
			    // clear the state of the cell
			    gameState[x][y] = false;
			    
			    
			    this.repaint();
			}

			
			//detects when mouse enters the program
			public void mouseEntered(MouseEvent e) { 
			    System.out.println("Mouse entered the program");
			}
			
			//detects when mouse exits the program
			public void mouseExited(MouseEvent e) { 
			    System.out.println("Mouse exited the program");
			}

			
			//mouse clicked detects when the mouse is clicked normally
			public void mouseClicked(MouseEvent e) { 
			    // determine which cell of the gameState array was clicked on
			    int x = e.getX()/20;
			    int y = e.getY()/20;
			    // select the state of the cell
			    gameState[x][y] = true;
			    
			    
			    this.repaint();
			}

			//
			// application's paint method
			public void paint(Graphics g) {
				
				super.paint(g);;
				g = offscreenBuffer; // draw to offscreen buffer
				
				// clear the canvas with a big black rectangle
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 800);
				
				g.setColor(Color.GREEN);
		        for (Rectangle button : buttons) {
		            g.fillRect(button.x, button.y, button.width, button.height);
		        }

		        // add labels to the buttons
		        g.setColor(Color.WHITE);
		        g.drawString("Start", buttons[0].x + 30, buttons[0].y + 30);
		        g.drawString("Random", buttons[1].x + 30, buttons[1].y + 30);
		        g.drawString("Load", buttons[2].x + 30, buttons[2].y + 30);
		        g.drawString("Save", buttons[3].x + 30, buttons[3].y + 30);
				
				// redraw all game objects
				g.setColor(Color.WHITE);
				 for (int x=0;x<40;x++) {
					 for (int y=0;y<40;y++) {
						 	if (gameState[x][y]) {
						 		g.fillRect(x*20, y*20, 20, 20);
				 }
			}
		}
				// flip the buffers
				strategy.show();
	}
			
			public void drawButtons(Graphics g) {
			    // set the font and color for the buttons
			    g.setFont(new Font("Arial", Font.BOLD, 14));
			    g.setColor(Color.GREEN);

			    // draw each button
			    for (int i = 0; i < buttons.length; i++) {
			        g.drawRect(buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height);
			        g.drawString(getButtonText(i), buttons[i].x + 10, buttons[i].y + 30);
			    }
			}
			
			public String getButtonText(int index) {
			    switch (index) {
			        case 0:
			            return "Start";
			        case 1:
			            return "Random";
			        case 2:
			            return "Load";
			        case 3:
			            return "Save";
			        default:
			            return "";
			    }
			}
			public void loadGame() {
			    try {
			        BufferedReader reader = new BufferedReader(new FileReader("gameState.txt"));
			        boolean[][] newGameState = new boolean[40][40];
			        String line;
			        int row = 0;
			        while ((line = reader.readLine()) != null && row < 40) {
			            String[] values = line.trim().split("\\s+");
			            for (int col = 0; col < 40 && col < values.length; col++) {
			                newGameState[row][col] = (values[col].equals("1"));
			            }
			            row++;
			        }
			        reader.close();
			        //put loaded values from the newGameState array into the gameState array to override the contents of the screen
			        gameState = newGameState;
			    } catch (IOException ex) {
			        ex.printStackTrace();
			    }
			}


			// application entry point
			public static void main(String[] args) {
				ConwaysLife w = new ConwaysLife();
			}
}
