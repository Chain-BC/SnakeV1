package player;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

public class SnakeBody extends SnakeAbstract {
	
	public ArrayList<SnakeAbstract> snake;
	public int index;
	
	public SnakeBody(ArrayList<SnakeAbstract> snake) {
		this.snake = snake;
	}
	
	public void move() {
		setPreviousXY();
		x = snake.get(index).previousX;
		y = snake.get(index).previousY;
		piece.setBounds(new Rectangle(x, y, piece.getWidth(), piece.getHeight()));
	}

	@Override
	public void addPiece(JLayeredPane gamePanel) {
		// Set previous snake XY to new
		x = snake.get(index).previousX;
		y = snake.get(index).previousY;
		// MAKES A NEW BUTTON, ADDS IT TO THE GAME FRAME!
		piece = new JButton("");
		piece.setContentAreaFilled(false);
		piece.setIcon(new ImageIcon("assets/SnakeBody.png"));
		piece.setBounds(new Rectangle(x, y, 20, 20));
		piece.setBorder(null);
		piece.setFocusable(false);
		gamePanel.setLayer(piece, 0);
		gamePanel.add(piece);
		snake.add(this);
	}


}
