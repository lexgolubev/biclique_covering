package by.bsu.binarytree.domain.graph;

public class Point {
	public int x = 0;
	public int y = 0;
	public double count = 0.0;

	public Point(int x, int y, double count) {
		this.x = x;
		this.y = y;
		this.count = count;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
		this.count = point.count;
	}
}
