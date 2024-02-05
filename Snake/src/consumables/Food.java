package consumables;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import player.SnakeAbstract;
import player.SnakeBody;
import player.SnakeHead;

public class Food extends ConsumableAbstract {
	
	public JButton food;
	public JLayeredPane gamePanel;
	public ArrayList<SnakeAbstract> snake;
	public JFrame mainFrame;
	public int defaultScoreAddition;
	public double scoreMultiplier;
	public double speed = 1.0;
	
	public Food(JLayeredPane gamePanel, ArrayList<SnakeAbstract> snake, JFrame mainFrame) {
		x = ((int)(Math.random()*40))*20;
		y = ((int)(Math.random()*40))*20;
		JButton piece = new JButton("");
		piece.setContentAreaFilled(false);
		piece.setIcon(new ImageIcon("assets/Food.png"));
		piece.setBounds(new Rectangle(x, y, 20, 20));
		piece.setBorder(null);
		piece.setFocusable(false);
		gamePanel.setLayer(piece, 0);
		gamePanel.add(piece);
		food = piece;
		this.snake = snake;
		this.gamePanel = gamePanel;
		this.mainFrame = mainFrame;
		this.defaultScoreAddition = 10;
		this.scoreMultiplier = 1;
	}

	@Override
	public boolean checkConsumed() {
		// If the SnakeHead is on top of the food
		if(snake.get(0).x == x && snake.get(0).y == y) {
			// Moves the food somewhere else since it is unnecessary to completely remove it
			x = ((int)(Math.random()*40))*20;
			y = ((int)(Math.random()*40))*20;
			food.setBounds(new Rectangle(x, y, food.getWidth(), food.getHeight()));
			SnakeHead snakeHead = (SnakeHead)snake.get(0);
			snakeHead.score += (int)(defaultScoreAddition*scoreMultiplier);
			
			// Adds SPEED
			speed += 0.1;
			snakeHead.setSpeed(speed);
			
			// Adds a new snake part
			SnakeBody newSnakePiece = new SnakeBody(snake);
			newSnakePiece.addPiece(gamePanel);
			return true;
		}
		return false;
	}

}
