package game;

import java.awt.Graphics;

import display.BoardDisplay;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.PieceColor;
import position.Position;
import position.PositionImpl;

public class Game {
	
	private boolean gameStateOne = true; //tests for piece selection or move selection
	private PieceColor currentTurn = PieceColor.WHITE; //keeps track of turn order
	private boolean gameStateTwo = false; //allows for multiple jumps
	private Board board; //the game board
	private Position [] moves; //a stored list of moves for the selected piece
	private Piece selected; //the current piece selected by the user
	
	public Game() {//constructor creates then sets a checkers board
		Piece[][] board = new Piece[8][8];
		for(int x = 0; x<8; x++) {
			for(int y = 0; y<8; y++) {
				if((x+y)%2 == 1) {
					if(y<=2) 
						board[x][y]=new Pawn(new PositionImpl(x, y), PieceColor.WHITE);
					if(y>=5)
						board[x][y]=new Pawn(new PositionImpl(x, y), PieceColor.BLACK);
				}
			}
		}
		this.board = new Board(board);
	}
	
	public Game(Board board) {
		this.board = board;
	}
	
	public Game(Piece[][] board) {
		this.board = new Board(board);
	}

	
	public void ProcessInput(Position p, Graphics g) { 
		if(gameStateOne) { //used when a new piece is selected
			if(board.getValue(p)!=null && board.getValue(p).hasMoves(board) && board.getValue(p).getColor()==currentTurn) {
				ProcessFirstInput(p, g);
			}
		}else { //used to process the move of a selected piece
			for(Position move: moves) {
				if(move.equals(p)||p.equals(selected.getPosition())) {//checking for valid move
					ProcessPlayerMove(p, g);
					break;
				}
			}	
		}
	}
	
	private void ProcessFirstInput(Position p, Graphics g) {//saves selected piece for later
		selected = board.getValue(p);
		moves = selected.getMoves(board);
		BoardDisplay.drawMoves(moves, selected.getPosition(), board, g);
		gameStateOne=false;
	}
	
	private void ProcessPlayerMove(Position p, Graphics g) {
		if(!gameStateTwo) {
			if(p.equals(selected.getPosition()))
				gameStateOne = true;
			else {
				boolean attacked = false;
				
				if(selected.getPosition().distance(p) != 2) {
					board.setValue(selected.getPosition().between(p), null);
					attacked = true;
				}
				
				board.exchangeValues(selected.getPosition(), p);
				selected.setPosition(p);
				
				if(attacked && selected.hasAttack(board)) { //checks if player attacked and has attacks
					gameStateTwo = true;
					moves = selected.getMoves(board);
				}
				else 
					EndTurn();
			}
			BoardDisplay.drawBoardWithPieces(board, g);
			if(gameStateTwo) //draws board for follow up attacks / input 
				BoardDisplay.drawMoves(selected.getAttacks(board), selected.getPosition(), board, g);
		}else {
			if(p.equals(selected.getPosition())) 
				EndTurn();
			else {
				board.exchangeValues(selected.getPosition(), p);
				board.setValue(selected.getPosition().between(p), null);
				selected.setPosition(p);
				if(!selected.hasAttack(board)) {
					EndTurn();
					BoardDisplay.drawBoardWithPieces(board, g);
				}
				else {
					BoardDisplay.drawBoardWithPieces(board, g);
					BoardDisplay.drawMoves(selected.getAttacks(board), selected.getPosition(), board, g);
					moves = selected.getAttacks(board);
				}	
			}
		}
	}
	
	private void EndTurn() { //resets to standard defaults for turns then changes the active player
		gameStateOne = true; 
		currentTurn = currentTurn == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK;
		gameStateTwo = false;
		checkUpGrade();
	}
	
	private void checkUpGrade() { //sees if a pawn can be upgraded to a king
		if(selected instanceof Pawn &&
		((selected.getColor() == PieceColor.BLACK && selected.getPosition().getY()==0) || 
		(selected.getColor() == PieceColor.WHITE && selected.getPosition().getY()==8))) {
			board.setValue(selected.getPosition(), ((Pawn) selected).upGrade());
		}
	}
	
	public Board getBoard() {
		return board;
	}
}
