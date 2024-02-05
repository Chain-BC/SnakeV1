package consumables;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import player.SnakeAbstract;
import player.SnakeHead;

public class DoubleTrouble extends ConsumableAbstract{
	
	public JLayeredPane gamePanel;
	public ArrayList<SnakeAbstract> snake;
	public JFrame mainFrame;
	public JButton DTConsumable;
	public Food food;
	public boolean consumedAlready;
	
	public DoubleTrouble(JLayeredPane gamePanel, ArrayList<SnakeAbstract> snake, JFrame mainFrame, Food food) {
		x = ((int)(Math.random()*40))*20;
		y = ((int)(Math.random()*40))*20;
		DTConsumable = new JButton("");
		DTConsumable.setContentAreaFilled(false);
		DTConsumable.setIcon(new ImageIcon("assets/DoubleTrouble.png"));
		DTConsumable.setBounds(new Rectangle(x, y, 20, 20));
		DTConsumable.setBorder(null);
		DTConsumable.setFocusable(false);
		gamePanel.setLayer(DTConsumable, 0);
		gamePanel.add(DTConsumable);
		this.gamePanel = gamePanel;
		this.snake = snake;
		this.mainFrame = mainFrame;
		this.food = food;
		consumedAlready = false;
	}
	
	public void run() {
		delay(10000);
		gamePanel.remove(DTConsumable);
		x = -1;
		y = -1;
		mainFrame.repaint();
	}
	
	public boolean checkConsumed() {
		SnakeHead snakeHead = (SnakeHead)snake.get(0);
		// If the snake head is on top of this consumable
		if(snake.get(0).x == x && snake.get(0).y == y) {
			// Remove this from the game panel
			gamePanel.remove(DTConsumable);
			mainFrame.repaint();
			// Set the speed and point multiplier
			snakeHead.setSpeed(2);
			snake.set(0, snakeHead);
			food.scoreMultiplier = 1.5;	
			return true;
		}
		return false;
	}

}
