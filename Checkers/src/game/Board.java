package game;

import pieces.Piece;
import position.Position;

public class Board {
	
	private Piece[][] board;
	
	//This class stores the board and can be used to retrieve data from it.
	public Board(Piece [][] arr) {
		setBoard(arr);
	}
	
	public Board() {
	}
	
	public void resetBoard() {
		board = new Piece[8][8];
	}
	
	public void setBoard(Piece[][] arr) {
		board = arr.clone();
	}
	
	public Piece getValue(Position p) {
		return board[p.getX()][p.getY()];
	}
	
	public void setValue(Position pos, Piece piece) {
		board[pos.getX()][pos.getY()]=piece;
	}
	
	public void exchangeValues(Position pos1, Position pos2) {//Swaps one element with another
		Piece temp = getValue(pos1);
		setValue(pos1, getValue(pos2));
		setValue(pos2, temp);
	}
	
	public String boardToString() { //creates a string representation of the board
		String str = "";
		for(Piece [] piece1: board) {
			for(Piece piece2:piece1) {
				str += piece2==null ? "[  ]" : "["+piece2+"]";
			}
			str += "\n";
		}
		return str;
	}
}
