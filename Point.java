package pi;


public class Point {
	private long x;
	private long y;

	public Point(long x, long y) {
		setX(x);
		setY(y);
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

}
