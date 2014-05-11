package by.bsu.binarytree.view.graph;

import java.util.ArrayList;

import by.bsu.binarytree.domain.graph.Point;
import by.bsu.binarytree.domain.spttree.Node;
import by.bsu.binarytree.domain.spttree.NodeType;

public class GraphViewAlgs {
	
	private ArrayList<Point> graphVertexNumber= null; 
	private ArrayList<Node> treeNodes = null;
	private Point endPoint = null;
	private Point maxPoint = new Point(0,0);
	
	public ArrayList<Point> generateGraphNumberView(ArrayList<Node> treeNodes, int graphVertexCount) {
		graphVertexNumber = new ArrayList<Point>();
		for (int i = 0; i <= graphVertexCount; i++) {
			graphVertexNumber.add(new Point(0,0));
		}
		graphVertexNumber.set(1, new Point(0, 0));
	    this.treeNodes = treeNodes;
		endPoint = new Point(1, 0);
		generateGraphNumberViewRec(treeNodes.get(1), NodeType.EMPTY);
		
		for (int i = 1; i < graphVertexNumber.size(); i++) {
			System.out.println(i + " : x=" + graphVertexNumber.get(i).x + 
					" y=" + graphVertexNumber.get(i).y);
		}
		return graphVertexNumber;
	}	
	
	private void generateGraphNumberViewRec(Node treeNode, NodeType parentType) {
		
		if (treeNode.type == NodeType.LEAF) {
			Point b = new Point(endPoint);
			Point e = new Point(endPoint.x + 1, endPoint.y);
			graphVertexNumber.set(treeNode.first, b);
			graphVertexNumber.set(treeNode.last, e);
			maxPoint.x = Math.max(maxPoint.x, endPoint.x + 1);
			maxPoint.y = Math.max(maxPoint.y, endPoint.y);
			return;
		}
		
		Node leftChildNode = treeNodes.get(treeNode.left);
		Node rightChildNode = treeNodes.get(treeNode.right);

		generateGraphNumberViewRec(leftChildNode, treeNode.type);
		
		if (treeNode.type == NodeType.PAR) {
			endPoint.y++;
			endPoint.x = graphVertexNumber.get(treeNode.first).x;
		}

		if (treeNode.type == NodeType.SEQ) {
			endPoint.x++; 
		}
		
		generateGraphNumberViewRec(rightChildNode, treeNode.type);
		
	}

	public Point getMaxPoint() {
		return maxPoint;
	}
	
}
