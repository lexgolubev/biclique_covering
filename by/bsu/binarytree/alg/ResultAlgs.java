package by.bsu.binarytree.alg;

import java.util.ArrayList;
import by.bsu.binarytree.domain.result.ResultNode;
import by.bsu.binarytree.domain.result.ResultRecord;
import by.bsu.binarytree.domain.spttree.Node;
import by.bsu.binarytree.domain.spttree.NodeType;

public class ResultAlgs {
	private ArrayList<ResultRecord> resultRecords = new ArrayList<ResultRecord>();
	private ArrayList<Node> treeNodes;

	public ArrayList<Node> generateResultTree(
			ArrayList<ResultRecord> resultRecord, int resultNumber,
			ArrayList<Node> treeNodes) {
		this.resultRecords = resultRecord;
		this.treeNodes = treeNodes;
		ResultRecord first = getFirstRecord(resultNumber);		
		treeNodes.get(1).code = Integer.valueOf(first.c.getCode().reverse().toString());
		treeNodes.get(1).codeView = first.c.getCode().reverse().toString();
		treeNodes.get(1).h = first.c.getH();
		System.out.println("Result");

		generateResultTreeRec1(1, first);
		printResult();
		return treeNodes;
	}

	private ResultRecord getFirstRecord(int resultNumber) {
		if (resultRecords.size() == 0) {
			return null;
		}
		for (ResultRecord rr : resultRecords) {
			if (rr.nodeNumber == 1
//					&& rr.c.getCode().toString().equals("000000")
					&& rr.c.getH() == resultNumber) {
				System.out.println("first record: " + rr);
				return rr;
			}
		}
		return null;
	}

	private void storeResult(Node node, ResultNode result) {
		node.code = Integer.valueOf(result.getCode().reverse().toString(), 2);
		node.codeView = result.getCode().reverse().toString();
		node.h = result.getH();
	}

	private void generateResultTreeRec1(int nodeNumber, ResultRecord parent) {
		
		Node treeNode = treeNodes.get(nodeNumber);
		storeResult(treeNode, parent.c);

		int leftChildNumber = treeNodes.get(nodeNumber).left;
		int rightChildNumber = treeNodes.get(nodeNumber).right;

		Node leftChildNode = treeNodes.get(treeNode.left);
		Node rightChildNode = treeNodes.get(treeNode.right);

		if (leftChildNode.type == NodeType.LEAF) {
			storeResult(leftChildNode, parent.a);
		}

		if (rightChildNode.type == NodeType.LEAF) {
			storeResult(rightChildNode, parent.b);
		}

		for (ResultRecord record : resultRecords) {
			if (record.nodeNumber == leftChildNumber
					&& parent.isAParent(record)) {
				System.out.println(record);
				generateResultTreeRec1(leftChildNumber, record);
			} else if (record.nodeNumber == rightChildNumber
					&& parent.isBParent(record)) {
				System.out.println(record);
				generateResultTreeRec1(rightChildNumber, record);
			}
		}
	}

	private void printResult() {
		for (int i = 1; i < treeNodes.size(); i++) {
			Node node = treeNodes.get(i);
			System.out
					.println(i + "  c = " + node.codeView + "  h = " + node.h);
		}
	}

}
