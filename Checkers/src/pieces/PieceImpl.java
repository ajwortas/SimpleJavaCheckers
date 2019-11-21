package pieces;

import java.util.ArrayList;

import game.Board;
import position.Position;

public abstract class PieceImpl implements Piece{

	protected PieceColor color;
	protected Position position;
	
	public PieceImpl(Position p, PieceColor c) {
		color = c;
		position = p;
	}
	
	@Override
	public PieceColor getColor() {
		return color;
	}

	@Override
	public Position getPosition() {
		return position;
	}
	
	@Override
	public void setPosition(Position p) {
		position = p;
	}

	@Override
	abstract public Position[] getMoves(Board b);

	@Override
	public Position[] getAttacks(Board board) {
		ArrayList<Position> atks = new ArrayList<Position>();
		Position [] moves = getMoves(board);
		for(Position move:moves) {
			if(move.distance(getPosition())>2) {
				atks.add(move);
			}
		}
		Position[] attacks = new Position[atks.size()];
		return atks.toArray(attacks);
	}

	@Override
	public boolean hasMoves(Board board) {
		return getMoves(board).length>0;
	}
	
	@Override
	public boolean hasAttack(Board board) {
		return getAttacks(board).length>0;
	}
	
	@Override
	public abstract String toString();
	
	
}
