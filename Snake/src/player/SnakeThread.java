package player;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import consumables.DoubleTrouble;
import consumables.Food;

public class SnakeThread extends Thread {
	
	boolean stopThread;
	private ArrayList<SnakeAbstract> snake;
	private Food food;
	public boolean hasMoved = false;
	public JFrame mainFrame;
	public JLayeredPane gamePanel;
	public JLabel lblScore;
	public CardLayout cards;
	boolean potato = true;
	DoubleTrouble DTConsumable;
	
	public SnakeThread(ArrayList<SnakeAbstract> snake, Food food, JFrame mainFrame, JLayeredPane gamePanel, JLabel score) {
		this.snake = snake;
		this.food = food;
		this.mainFrame = mainFrame;
		this.gamePanel = gamePanel;
		this.lblScore = score;
		// Gets all the cards to change between windows
		this.cards = (CardLayout) mainFrame.getContentPane().getLayout();
	}
	
	public void run() {
		while(true) {
			SnakeHead snakeHead = (SnakeHead)snake.get(0);
			
			
			/*
			// Checks whether any of the consumables have been eaten
			if(DTConsumable != null) {
				if(DTConsumable.checkConsumed()) {
					DTConsumable.consumedAlready = true;
				}
			}
			
			
			// For a chance of spawning a random consumable
			double chanceSpawn = Math.random();
			if(chanceSpawn < 0.1) {
				if(DTConsumable == null) {
					DTConsumable = new DoubleTrouble(gamePanel, snake, mainFrame, food);
					DTConsumable.start();
				}
				else if(DTConsumable != null) {
					if(!DTConsumable.consumedAlready && !DTConsumable.isAlive()) {
						DTConsumable = new DoubleTrouble(gamePanel, snake, mainFrame, food);
						DTConsumable.start();
					}
				}
			}
			*/
			

			
			// Checks whether the food has been eaten
			food.checkConsumed();
			lblScore.setText("" + snakeHead.score);

			
			// Moves the snake AND its body (all within the SnakeHead class)
			snakeHead.move();
			hasMoved = false;
				
			// Stops the thread if needed
			if(stopThread) {
				return;
			}
		}
	}
	
	public void endGame() {
		stopThread = true;
		addGameOverFrame(gamePanel, mainFrame);
	}
	
	public void restartGame(JLayeredPane gamePanel) {
		stopThread = true;
		for(int i = 0; i < snake.size(); i++) {
			gamePanel.remove(snake.get(i).piece);
		}
		gamePanel.remove(food.food);
		//gamePanel.remove(DTConsumable.DTConsumable);
		snake.clear();
	}
	
	public void pauseGame() {
		stopThread = true;
		
	}
	
	// Function to add a game over screen
	public void addGameOverFrame(JLayeredPane gamePanel, JFrame mainFrame) {
		JPanel pnlGameOver = new JPanel();
		pnlGameOver.setBounds(225, 225, 350, 350);
		pnlGameOver.setBackground(new Color(255, 255, 255, 100));
		pnlGameOver.setOpaque(false);
		gamePanel.setLayer(pnlGameOver, 1);
		gamePanel.add(pnlGameOver);
		pnlGameOver.setLayout(null);
		
		JLabel lblGameOver = new JLabel("GAME OVER");
		lblGameOver.setForeground(new Color(255, 0, 0));
		lblGameOver.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameOver.setBounds(10, 10, 330, 86);
		pnlGameOver.add(lblGameOver);
		
		JLabel lblEndScore = new JLabel("Score: ");
		lblEndScore.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEndScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndScore.setBounds(10, 60, 330, 86);
		pnlGameOver.add(lblEndScore);
		
		// Sets the score of the end score label
		SnakeHead snakeHead = (SnakeHead)snake.get(0);
		lblEndScore.setText("Score: " + snakeHead.score);
		
		JButton btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restartGame(gamePanel);
				food = new Food(gamePanel, snake, mainFrame);
				SnakeThread snakeMovement = new SnakeThread(snake, food, mainFrame, gamePanel, lblScore);
				snake.add(new SnakeHead(gamePanel, snake, snakeMovement));
				addMovementListener(snake.get(0).piece, snakeMovement);
				snakeMovement.start();
				snake.get(0).piece.requestFocus();
				
				// Remove Game Over
				gamePanel.remove(pnlGameOver);
				mainFrame.repaint();
			}
		});
		btnRestart.setBounds(100, 200, 150, 25);
		pnlGameOver.add(btnRestart);
		
		JButton btnMenu = new JButton("To Menu");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restartGame(gamePanel);
				cards.show(mainFrame.getContentPane(), "game_menu");
				
				// Remove Game Over
				gamePanel.remove(pnlGameOver);
				mainFrame.repaint();
			}
		});
		btnMenu.setBounds(100, 250, 150, 25);
		pnlGameOver.add(btnMenu);
		
		JPanel pnlBackgroundGameOver = new JPanel();
		pnlBackgroundGameOver.setBackground(new Color(255, 255, 255, 100));
		pnlBackgroundGameOver.setBounds(0, 0, 350, 350);
		pnlGameOver.add(pnlBackgroundGameOver);
		
		// Actually makes the game over appear
		mainFrame.repaint();
	}
	
	// Function to add the main movement listener to a button
	public void addMovementListener(JButton snakeButton, SnakeThread newThread) {
		// So I can check for input, bring the player button here and add a key listener
		snakeButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(newThread.hasMoved) {
					return;
				}
				
				// Gets the head of the snake (ALWAYS INDEX 0)
				SnakeHead head = (SnakeHead)snake.get(0);
				// Sets new direction
				if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'w') {
					if(head.currentDirection.equals("down")) {
						e.consume();
					}
					head.setDirection("up");
					newThread.hasMoved = true;
				}
				else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's') {
					if(head.currentDirection.equals("up")) {
						e.consume();
					}
					head.setDirection("down");
					newThread.hasMoved = true;
				}
				else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a') {
					if(head.currentDirection.equals("right")) {
						e.consume();
					}
					head.setDirection("left");
					newThread.hasMoved = true;
				}
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
					if(head.currentDirection.equals("left")) {
						e.consume();
					}
					head.setDirection("right");
					newThread.hasMoved = true;
				}
			}
		});
	}
	
}
