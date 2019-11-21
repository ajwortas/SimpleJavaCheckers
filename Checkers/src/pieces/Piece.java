package pieces;

import java.awt.Graphics;

import game.Board;
import position.Position;

public interface Piece {
	
	public enum PieceColor{WHITE,BLACK};
	
	public PieceColor getColor();
	public Position getPosition();
	public Position[] getMoves(Board board);
	public boolean hasAttack(Board board);
	public Position[] getAttacks(Board board);
	public boolean hasMoves(Board board);
	public void setPosition(Position p);
	public void drawPiece(Graphics g);
		
}
