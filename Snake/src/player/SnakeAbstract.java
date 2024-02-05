package player;

import javax.swing.JButton;
import javax.swing.JLayeredPane;

public abstract class SnakeAbstract {
	
	public int x;
	public int y;
	public int previousX;
	public int previousY;
	public JButton piece;
	
	public abstract void move();
	
	public abstract void addPiece(JLayeredPane gamePanel);
	
	protected void setPreviousXY() {
		this.previousX = x;
		this.previousY = y;
	}
	
}
