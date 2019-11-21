package display;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import game.Game;
import position.Position;
import position.PositionImpl;

public class Main extends Applet implements MouseListener{

	public void init() {
		setSize(808,808);
		addMouseListener(this);
	}
	
	Game game = new Game();
	Graphics g;
	int mouseX, mouseY;
	boolean hasBeenClicked = false;
	
	public void paint(Graphics g) {
		BoardDisplay.adjustWidthHeight(this.getWidth(), this.getHeight());
		BoardDisplay.drawBoardWithPieces(game.getBoard(), g);
		if(hasBeenClicked) {
			Position clickPosition = BoardDisplay.findClickLocation(mouseX, mouseY);
			//System.out.println(clickPosition.getX() +" "+ clickPosition.getY());
			if(!(clickPosition==null))
				game.ProcessInput(clickPosition, g);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		hasBeenClicked = true;
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
		
	}
}
