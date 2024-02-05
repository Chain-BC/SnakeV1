package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.util.ArrayList;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Cursor;
import java.awt.CardLayout;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import consumables.Food;
import player.SnakeAbstract;
import player.SnakeHead;
import player.SnakeThread;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class SnakeDriver {

	public SnakeDriver window;
	private JFrame frmSnakeV;
	private CardLayout cards;
	private ArrayList<SnakeAbstract> snake = new ArrayList<SnakeAbstract>();
	private JPanel menu;
	private SnakeThread snakeMovement; // Main thread that will move the snake
	private Food food; // Main thread that will be checking if the food has actually been consumed or not
	private JLayeredPane gamePanel;
	private JLabel lblScore;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SnakeDriver window = new SnakeDriver();
					window.frmSnakeV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SnakeDriver() {
		initialize();
		
		// Gets all the cards to change between windows
		cards = (CardLayout) frmSnakeV.getContentPane().getLayout();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSnakeV = new JFrame();
		frmSnakeV.setTitle("Snake v1.0");
		frmSnakeV.setName("frame");
		frmSnakeV.getContentPane().setBackground(new Color(255, 255, 255));
		frmSnakeV.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frmSnakeV.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frmSnakeV.setResizable(false);
		frmSnakeV.getContentPane().setBounds(new Rectangle(0, 0, 0, 0));
		frmSnakeV.getContentPane().setLayout(new CardLayout(0, 0));
		
		/**
		 * Main Menu
		 */
		menu = new JPanel();
		frmSnakeV.getContentPane().add(menu, "game_menu");
		menu.setLayout(null);
		
		JButton btnPlay = new JButton("PLAY");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Sets up the food thread
				food = new Food(gamePanel, snake, frmSnakeV);
				
				// Sets up player and player movement thread
				snakeMovement = new SnakeThread(snake, food, frmSnakeV, gamePanel, lblScore); 
				snake.add(new SnakeHead(gamePanel, snake, snakeMovement));
				
				// Goes to main game window
				cards.show(frmSnakeV.getContentPane(), "game");
				
				// Starts the movement of the snake
				snakeMovement.start();
				
				// So input starts working!
				snakeMovement.addMovementListener(snake.get(0).piece, snakeMovement);
				snake.get(0).piece.requestFocus();
				//snakeMovement.addGameOverFrame(gamePanel, frmSnakeV); // Just to test score
				frmSnakeV.repaint();
			}
		});
		btnPlay.setBounds(328, 451, 89, 23);
		menu.add(btnPlay);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(328, 492, 89, 23);
		menu.add(btnExit);
		
		frmSnakeV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSnakeV.setBounds(100, 100, (800+36), (800+59));
		
		/**
		 * Main Game Panel
		 */
		gamePanel = new JLayeredPane();
		gamePanel.setBackground(new Color(255, 165, 0));
		frmSnakeV.getContentPane().add(gamePanel, "game");
		gamePanel.setLayout(null);
		
		JPanel pnlBackground = new JPanel();
		pnlBackground.setBounds(0, 0, 820, 820);
		pnlBackground.setBackground(new Color(255, 165, 0));
		gamePanel.setLayer(pnlBackground, -2);
		gamePanel.add(pnlBackground);
		
		lblScore = new JLabel("");
		lblScore.setForeground(new Color(255, 255, 255));
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 300));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		gamePanel.setLayer(lblScore, -1);
		lblScore.setBounds(0, 0, 820, 820);
		gamePanel.add(lblScore);
		
		
	}
}
