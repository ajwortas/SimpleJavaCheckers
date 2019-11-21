package position;

public interface Position {
	
	
	public int getX();
	public int getY();
	
	default public boolean equals(Position p) {
		return (getX() == p.getX()) && (getY() == p.getY());
	}
	default public int distance(Position p) {
		return Math.abs(getX()-p.getX())+Math.abs(getY()-p.getY());
	}
	default public boolean isOutOfBounds() {
		return getX()>=8||getX()<0||getY()>=8||getY()<0;
	}
	
	public Position between(Position p);
}
