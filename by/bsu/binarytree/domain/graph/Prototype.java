package by.bsu.binarytree.domain.graph;

import java.util.ArrayList;

public class Prototype {
	private int type = 0;
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	private Integer firstVertex = -1;
	private Integer lastVertex = -1;
	private boolean isEmpty = true;
	public int usedByNodeNumber = -1;

	public Prototype() {		
	}
	
	public Prototype(Integer firstVertex, Integer lastVertex, int type) {
		this.firstVertex = firstVertex;
		this.lastVertex = lastVertex;
		this.type = type;
		edges.add(new Edge(firstVertex, lastVertex));
		isEmpty = false;
	}

	public boolean isEmpty() {
		return isEmpty;
	}
	
	public void plusS(Prototype right, int type) {
		if (right == null) {
			return;
		}
		if (lastVertex != right.firstVertex) {
			System.out.println("Error: plusS" + right.firstVertex + " "
					+ lastVertex);
		}
		this.type = type;
		edges.addAll(right.edges);
		lastVertex = right.lastVertex;
	}

	public void plusP(Prototype parallel, int type) {
		if (parallel == null) {
			return;
		}
		if (firstVertex != parallel.firstVertex
				&& lastVertex != parallel.lastVertex) {
			System.out.println("Error: plusP" + parallel.firstVertex + " "
					+ firstVertex + " " + parallel.lastVertex + " "
					+ lastVertex);
		}
		this.type = type;
		edges.addAll(parallel.edges);
	}

	public void print() {
		System.out.println("type: " + type );
		for (int i = 0; i < edges.size(); i++) {
			System.out.println(edges.get(i));
		}
		System.out.println("--");
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}
}
