package schach;

public class Position {
	
	int x,y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position) {
			return ((Position)obj).x == x && ((Position)obj).y == y;
		}
		return false;
	}
	
}
