package by.bsu.binarytree.domain.graph;

public class Edge {
	public Integer firstVertex;
	public Integer lastVertex;

	public Edge(Integer firstVertex, Integer lastVertex) {
		this.firstVertex = firstVertex;
		this.lastVertex = lastVertex;
	}

	@Override
	public String toString() {
		return firstVertex + " " + lastVertex;
	}
}
