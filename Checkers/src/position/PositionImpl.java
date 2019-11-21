package position;

public class PositionImpl implements Position{
	
	private int x, y;
	
	public PositionImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "X: "+ getX()+" Y: "+getY();
	}

	@Override
	public Position between(Position p) {
		return new PositionImpl((x+p.getX())/2, (y+p.getY())/2);
	}
	
}
