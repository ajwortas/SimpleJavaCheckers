package pieces;

import java.awt.Graphics;
import java.util.ArrayList;

import display.BoardDisplay;
import game.Board;
import position.Position;
import position.PositionImpl;

public class Pawn extends PieceImpl implements Piece{

	public Pawn(Position p, PieceColor c) {
		super(p, c);
	}

	@Override
	public Position[] getMoves(Board board) {
		ArrayList<Position> moves = new ArrayList<Position>();
		
		int x = getPosition().getX();
		int y = getPosition().getY();
		
		
		Position [] possibleMoves= new Position [2];
		if(getColor() == PieceColor.WHITE) {
			possibleMoves[0] = new PositionImpl(x+1, y+1);
			possibleMoves[1] = new PositionImpl(x-1, y+1);
		}else {
			possibleMoves[0] = new PositionImpl(x+1, y-1);
			possibleMoves[1] = new PositionImpl(x-1, y-1);
		}
		
		
		for(Position potential : possibleMoves) {
			if(!potential.isOutOfBounds()) {
				Piece p = board.getValue(potential);
				if(p==null)
					moves.add(potential);
				else if(p.getColor() != getColor()) {
					int xChange = p.getPosition().getX()-x;
					int yChange = p.getPosition().getY()-y;
					Position moveAtk = new PositionImpl(x+xChange*2,y+yChange*2);
					if(!moveAtk.isOutOfBounds() && board.getValue(moveAtk)==null) {
						moves.add(moveAtk);
					}
				}	
			}
		}
		Position[] moveArr = new Position[moves.size()];
		moveArr = moves.toArray(moveArr);
		return moveArr;
	}
	
	public Piece upGrade() {
		return new King(getPosition(), getColor());
	}

	public String toString() {
		return getColor() == PieceColor.WHITE ? "WP" : "BP";
	}

	@Override
	public void drawPiece(Graphics g) {
		BoardDisplay.drawPawn(getPosition().getX(), getPosition().getY(), getColor(), g);
	}
}
