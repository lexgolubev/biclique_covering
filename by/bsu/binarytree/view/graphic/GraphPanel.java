package by.bsu.binarytree.view.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import by.bsu.binarytree.domain.graph.Edge;
import by.bsu.binarytree.domain.graph.Point;
import by.bsu.binarytree.domain.graph.Prototype;
import by.bsu.binarytree.domain.spttree.Node;

public class GraphPanel extends JPanel {

	private class VertexPaint {
		public int x = 0;
		public int y = 0;
		public int number = 0;

		public VertexPaint(int x, int y, int number) {
			this.x = x;
			this.y = y;
			this.number = number;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + number;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final VertexPaint other = (VertexPaint) obj;
			if (number != other.number)
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}

	private class EdgePaint {
		public VertexPaint first;
		public VertexPaint second;
		public int offset = 0;
		public Color color;

		public EdgePaint(VertexPaint first, VertexPaint second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((first == null) ? 0 : first.hashCode());
			result = prime * result
					+ ((second == null) ? 0 : second.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final EdgePaint other = (EdgePaint) obj;
			if (first == null) {
				if (other.first != null)
					return false;
			} else if (!first.equals(other.first))
				return false;
			if (second == null) {
				if (other.second != null)
					return false;
			} else if (!second.equals(other.second))
				return false;
			return true;
		}
	}

	private static final int WIDTH = 880;
	private static final int HEIGHT = 454;
	private static final int X_MARGIN = 40;
	private static final int Y_MARGIN = 40;
	private static final int RADIUS = 20;

	private ArrayList<VertexPaint> vertices;
	private ArrayList<EdgePaint> edges;
	private VertexPaint currentVertex;

	public GraphPanel(GraphFrame frm) {
		// this.frm = frm;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// this.setMinimumSize(new Dimension(100, 100));
		setBackground(Color.lightGray);
		repaint();
	}

	public void loadGraphView(ArrayList<Point> graphPoints, int xMax, int yMax,
			ArrayList<Prototype> bicliques) {
		loadVertices(graphPoints, xMax, yMax);
		loadEdges(bicliques);
	}

	private void loadVertices(ArrayList<Point> graphPoints, int xMax, int yMax) {
		vertices = new ArrayList<VertexPaint>();
		int xStep = Math.round((WIDTH - X_MARGIN * 2) / (xMax + 1));
		int yStep = Math.round((HEIGHT - Y_MARGIN * 2) / (yMax + 1));
		for (int i = 0; i < graphPoints.size(); i++) {
			Point point = graphPoints.get(i);
			VertexPaint vertex = new VertexPaint(point.x * xStep + X_MARGIN, point.y
					* yStep + Y_MARGIN, i);
			vertices.add(vertex);
		}
	}

	private void loadEdges(ArrayList<Prototype> bicliques) {
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.black);		
		colors.add(Color.green);
		colors.add(Color.magenta);
		colors.add(Color.orange);
		colors.add(Color.pink);
		colors.add(Color.red);		
		edges = new ArrayList<EdgePaint>();
		int count = 0;
		for (Prototype biclique : bicliques) {			
			Color color;
			if (count < 7) {
				color = colors.get(count++);
			} else {
				color = Color.getHSBColor(count * 130,
						count * 120, (count ++ ) * 100);
			}			
			for (Edge edge : biclique.getEdges()) {
				EdgePaint edgePaint = new EdgePaint(vertices
						.get(edge.firstVertex), vertices.get(edge.lastVertex));
				edgePaint.color = color;
				if (edges.contains(edgePaint)) {
					edgePaint.offset = getCount(edges, edgePaint)* 3;
				}
				edges.add(edgePaint);
			}
		}
	}

	private int getCount(ArrayList<EdgePaint> edges, EdgePaint edgePaint) {
		int count = 0;
		if (edgePaint == null) {
			return 0;
		}
		for (EdgePaint edge : edges) {
			if (edgePaint.equals(edge)) {
				count++;
			}
		}
		return count;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.blue);
		for (EdgePaint edge : edges) {
			g.setColor(edge.color);
			g.drawLine(edge.first.x + edge.offset, edge.first.y + edge.offset,
					edge.second.x + edge.offset, edge.second.y + edge.offset);
		}
	    for (int i = 1; i < vertices.size(); i++) {
	    	VertexPaint vertex = vertices.get(i);
			g.setColor(Color.blue);
			g.fillOval(vertex.x - RADIUS, vertex.y
					- RADIUS, 2 * RADIUS, 2 * RADIUS);
			g.setColor(Color.white);
			g.fillOval(vertex.x - RADIUS + 3, vertex.y
					- RADIUS + 3, 2 * RADIUS - 6, 2 * RADIUS - 6);
			g.setColor(Color.black);
			if (vertex.number < 10) {
				g.drawString(String.valueOf(vertex.number), vertex.x
						- RADIUS + 17, vertex.y - RADIUS + 23);
			} else {
				g.drawString(String.valueOf(vertex.number), X_MARGIN + vertex.x
						- RADIUS + 13, Y_MARGIN + vertex.y - RADIUS + 23);
			}
		}
	}

	private VertexPaint getVertex(int x, int y) {
		for (int i = 1; i < vertices.size(); i++) {
			VertexPaint tmp = vertices.get(i);
			if ((tmp.x - x) * (tmp.x - x) + (tmp.y - y) * (tmp.y - y) < RADIUS
					* RADIUS) {
				return tmp;
			}
		}
		return null;
	}
	
	public void onPressed(MouseEvent me) {
		if (me.getButton() == 1) {
			currentVertex = getVertex(me.getX(), me.getY());
		}		
	}

	public void onReleased(MouseEvent me) {
			if (me.getButton() == 1) {
				if (currentVertex != null) {
					currentVertex.x = me.getX();
					currentVertex.y = me.getY();
				}
			}
		
	}
}
