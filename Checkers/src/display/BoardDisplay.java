package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import game.Board;
import pieces.Piece;
import pieces.Piece.PieceColor;
import position.Position;
import position.PositionImpl;

public class BoardDisplay{
	
	static private Color evenSquares = new Color(0,200,0);
	static private Color oddSquares = new Color(200,200,200);
	private static int x;
	private static int y;
	
	
	public static void adjustWidthHeight(int width, int height) {
		x=width/8-1;
		y=height/8-1;	
	}
	
	public static Position findClickLocation(int width, int height) {//adjusts pixel clicked to array index
			if(!(width<0||height<0||width>8*x||height>8*y)) {
				return new PositionImpl(width/x, height/y);
			}
			return null;
	}
	
	
	
	public static void drawBoard(Graphics g) {
		//draws Squares
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				setTileColor(i, j, g);
			}
		}
		
		//draws boarder
		g.setColor(Color.BLACK);
		for(int i = 0; i<9; i++) {
			g.drawLine(0, y*i, x*8, y*i);
			g.drawLine(x*i, 0, x*i, y*8);
		}	
	}
	
	public static void drawBoardWithPieces(Board board, Graphics g) {
		drawBoard(g);
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				Piece p = board.getValue(new PositionImpl(i, j));
				if(p != null) 
					p.drawPiece(g);
				
				
			}
		}
	}
	
	public static void attackSquare(Position p, Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(p.getX()*x+1, p.getY()*y+1, x-1, y-1);
	}
	
	public static void selectedSquare(Position p, Board board, Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(p.getX()*x+1, p.getY()*y+1, x-1, y-1);
		Piece piece = board.getValue(p);
		piece.drawPiece(g);
	}
	
	public static void moveSquare(Position p, Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(p.getX()*x+1, p.getY()*y+1, x-1, y-1);
	}
	
	public static void drawMoves(Position[] movesArr, Position selected, Board board, Graphics g) {
		for(Position p: movesArr)
			if(p.distance(selected)>2)
				attackSquare(p, g);
			else
				moveSquare(p, g);
		selectedSquare(selected, board, g);
	}
	
	public static void resetSelectedSquares(Position[] movesArr, Position selected, Board board, Graphics g) {
		setTileColor(selected.getX(), selected.getY(), g);
		board.getValue(selected).drawPiece(g);
		for(Position p:movesArr) 
			setTileColor(p.getX(), p.getY(), g);
	}
	
	private static void setTileColor(int x_, int y_, Graphics g) {
		if((x_+y_)% 2==1)
			g.setColor(evenSquares);
		else
			g.setColor(oddSquares);
		g.fillRect(1+x_*x, 1+y_*y, x-1, y-1);
	}
	
	public static void drawPawn(int x_, int y_, PieceColor p, Graphics g) {
		Color pawnColor = p==PieceColor.WHITE ? Color.WHITE:Color.BLACK;
		Color outLine = p!=PieceColor.WHITE ? Color.WHITE:Color.BLACK;
		
		g.setColor(pawnColor);
		g.fillOval(x_*x+(x/10)+1, y_*y+(y/10)+1, (8*x/10), (8*y/10));
		g.setColor(outLine);
		g.drawOval(x_*x+(x/10), y_*y+(y/10), (8*x/10), (8*y/10));
		
		int centerX = x_*x+(x/2);
		int centerY = y_*y+(y/2);
		
		Polygon ridge = new Polygon();
		
		for(int i = 0; i<18; i++) {//?
			ridge.addPoint(centerX+ (int)((4*x/10) * Math.cos(Math.toRadians(i*20))), 
							centerY + (int)((4*y/10) * Math.sin(Math.toRadians(i*20))));
			ridge.addPoint(centerX+ (int)((3*x/10) * Math.cos(Math.toRadians(i*20+10))), 
					centerY + (int)((3*y/10) * Math.sin(Math.toRadians(i*20+10))));
		}
		g.fillPolygon(ridge);
		g.setColor(pawnColor);
		g.fillOval(x_*x+(2*x/10), y_*y+(2*y/10), (6*x/10), (6*y/10));
		g.setColor(outLine);
		g.drawOval(x_*x+(2*x/10), y_*y+(2*y/10), (6*x/10), (6*y/10));
	}
	
	public static void drawKing(int x_, int y_, PieceColor p, Graphics g) {
		drawPawn(x_,y_,p,g);
		g.setColor(Color.YELLOW);
		
		x_= x_*x;
		y_= y_*y;
		
		Polygon crown = new Polygon();
		
		crown.addPoint(x_+(3*x/10), y_+(7*y/20));
		crown.addPoint(x_+(4*x/10), y_+(9*y/20));
		crown.addPoint(x_+(5*x/10), y_+(7*y/20));
		crown.addPoint(x_+(6*x/10), y_+(9*y/20));
		crown.addPoint(x_+(7*x/10), y_+(7*y/20));
		crown.addPoint(x_+(7*x/10), y_+(13*y/20));
		crown.addPoint(x_+(3*x/10), y_+(13*y/20));
		
		g.fillPolygon(crown);
	}
	
	
}
