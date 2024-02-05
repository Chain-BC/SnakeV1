package player;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

public class SnakeHead extends SnakeAbstract {
	
	private ArrayList<SnakeAbstract> snake;
	private SnakeThread gameThread;
	public int score;
	public double speed;
	private int direction;
	private boolean movingHorizontally;
	public String currentDirection = "right";
	public ImageIcon icon = new ImageIcon("assets/SnakeHeadRight.png");
	
	public SnakeHead(JLayeredPane gamePanel, ArrayList<SnakeAbstract> snake, SnakeThread thread) {
		addPiece(gamePanel);
		this.gameThread = thread;
		this.snake = snake;
		this.x = piece.getX();
		this.y = piece.getY();
		this.previousX = piece.getX();
		this.previousX = piece.getY();
		this.speed = 1;
		this.direction = 1;
		this.movingHorizontally = true;
		setSpeed(1);
	}
	
	// Add button to the game panel and get it back
	public void addPiece(JLayeredPane gamePanel) {
		// MAKES A NEW BUTTON, ADDS IT TO THE GAME FRAME!
		piece = new JButton("");
		piece.setContentAreaFilled(false);
		piece.setIcon(icon);
		piece.setBounds(new Rectangle(0, 800, 20, 20));
		piece.setBorder(null);
		gamePanel.setLayer(piece, 0);
		gamePanel.add(piece);
		piece.requestFocus();
	}
	
	public JButton getHead() {
		return piece;
	}
	
	// Sets speed of the snake for future power ups
	public void setSpeed(double s) {
		speed = Math.pow(s, -1);
	}
	
	// Sets the direction of the snake, this function should be called by input
	public void setDirection(String dir) {
		if(dir.equals("up") && !currentDirection.equals("down") && !currentDirection.equals("up")) {
			currentDirection = "up";
			movingHorizontally = false;
			direction = -1;
		}
		else if(dir.equals("down") && !currentDirection.equals("up") && !currentDirection.equals("down")) {
			currentDirection = "down";
			movingHorizontally = false;
			direction = 1;
		}
		else if(dir.equals("left") && !currentDirection.equals("right") && !currentDirection.equals("left")) {
			currentDirection = "left";
			movingHorizontally = true;
			direction = -1;
		}
		else if(dir.equals("right") && !currentDirection.equals("left") && !currentDirection.equals("right")) {
			currentDirection = "right";
			movingHorizontally = true;
			direction = 1;
		}
	}
	
	// Moves the snake
	public void move() {
		delay(100*speed);
		// Basic movement
		setPreviousXY();
		for(int sb = 0; sb < snake.size(); sb++) {
			
			// If it is a body part instead of the head, move it to the previous part's position
			if(snake.get(sb) instanceof SnakeBody) {
				SnakeBody currentPiece = (SnakeBody)snake.get(sb);
				currentPiece.index = sb-1;
				currentPiece.move();
				continue;
			}
			
			// Checks whether snake is moving vertically or horizontally and changes values accordingly
			if(movingHorizontally) {
				x += 20*direction;
			}
			else {
				y += 20*direction;
			}
			
			// Checks the direction and sets the icon of the head accordingly
			if(direction > 0 && movingHorizontally) {
				icon = new ImageIcon("assets/SnakeHeadRight.png");
			}
			else if (direction < 0 && movingHorizontally){
				icon = new ImageIcon("assets/SnakeHeadLeft.png");
			}
			else if(direction > 0 && !movingHorizontally) {
				icon = new ImageIcon("assets/SnakeHeadDown.png");
			}
			else {
				icon = new ImageIcon("assets/SnakeHeadUp.png");
			}
			piece.setIcon(icon);
			
			/**
			 * END GAME CONDITIONS
			 */
			
			// Checks for the walls to end game
			if(x < 0 || x > 800 || y < 0 || y > 800) {
				gameThread.endGame();
				return;
			}
			
			// Checks if the snake hits itself
			for(int i = 1; i < snake.size(); i++) {
				if(x == snake.get(i).x && y == snake.get(i).y) {
					gameThread.endGame();
					return;
				}
			}
			
			// Finally move it if NOTHING goes wrong
			piece.setBounds(x, y, piece.getWidth(), piece.getHeight());
			
		}
		
	}
	
	// The cooler thread sleep
	private void delay(double s) {
		try {
			Thread.sleep((int)s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
