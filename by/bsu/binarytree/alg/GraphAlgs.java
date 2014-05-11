package by.bsu.binarytree.alg;

import java.util.ArrayList;

import by.bsu.binarytree.domain.graph.Prototype;
import by.bsu.binarytree.domain.spttree.Node;
import by.bsu.binarytree.domain.spttree.NodeType;

public class GraphAlgs {

	public void generateBicliques(ArrayList<Node> treeNodes) {
		generateBicliquesRec(1, treeNodes);
	}

	private void generateBicliquesRec(int nodeNumber, ArrayList<Node> treeNodes) {
		Node node = treeNodes.get(nodeNumber);
		if (node.type == NodeType.LEAF) {
			generateBiclique(node);
			return;
		}
		Node left = treeNodes.get(node.left);
		Node right = treeNodes.get(node.right);

		generateBicliquesRec(node.left, treeNodes);
		generateBicliquesRec(node.right, treeNodes);
		if (node.type == NodeType.PAR) {
			// node.clearPrototypes();
			/*
			 * node.prototypes1234.addAll(left.prototypes1234);
			 * node.prototypes1234.addAll(right.prototypes1234);
			 */
			node.prototypes5.addAll(left.prototypes5);
			node.prototypes5.addAll(right.prototypes5);
			node.prototypes6.addAll(left.prototypes6);
			node.prototypes6.addAll(right.prototypes6);
			node.biqliques.addAll(left.biqliques);
			node.biqliques.addAll(right.biqliques);
			// if (node.h < left.h + right.h) {
			uniteBicliquesP(node, left, right, nodeNumber);
			// }
		} else if (node.type == NodeType.SEQ) {
			// node.clearPrototypes();
			/*
			 * node.prototypes1234.addAll(left.prototypes1234);
			 * node.prototypes1234.addAll(right.prototypes1234);
			 * node.prototypes5.addAll(left.prototypes5);
			 * node.prototypes5.addAll(right.prototypes5);
			 * node.prototypes6.addAll(left.prototypes6);
			 * node.prototypes6.addAll(right.prototypes6);
			 */
			uniteBicliquesS(node, left, right, nodeNumber);

		}
	}

	private void uniteBicliquesP(Node parent, Node left, Node right,
			int nodeNumber) {
		if (parent.code == 0) {
			Prototype st = !left.prototypes1234.get(1).isEmpty() ? left.prototypes1234
					.get(1)
					: right.prototypes1234.get(1);
		//	if (st.usedByNodeNumber != nodeNumber) {
		//		st.usedByNodeNumber = nodeNumber;
				makeBiclique(st, left.prototypes5, parent);
				makeBiclique(st, right.prototypes5, parent);
				makeBiclique(st, left.prototypes6, parent);
				makeBiclique(st, right.prototypes6, parent);
				parent.prototypes5.clear();
				parent.prototypes6.clear();
		//	}
			return;
		}
		/*
		 * if (GetBit(parent.code, 1)) { Prototype newPr =
		 * left.prototypes1234.get(1) != null ? left.prototypes1234 .get(1) :
		 * right.prototypes1234.get(1); parent.prototypes1234.add(1, newPr); }
		 */
		for (int i = 1; i <= 4; i++) {
			if (GetBit(parent.code, i)) {
				if (!left.prototypes1234.get(i).isEmpty()) {
					parent.prototypes1234.set(i, left.prototypes1234.get(i));
					parent.prototypes1234.get(i).plusP(
							right.prototypes1234.get(i), i);
				} else {
					parent.prototypes1234.set(i, right.prototypes1234.get(i));
				}
			}
		}
	}

	private void uniteBicliquesS(Node parent, Node left, Node right,
			int nodeNumber) {
		parent.biqliques.addAll(left.biqliques);
		parent.biqliques.addAll(right.biqliques);

		if (parent.code == 0) {
			Prototype a3 = left.prototypes1234.get(3);
			Prototype b2 = right.prototypes1234.get(2);
			a3.plusS(b2, 0);
			parent.biqliques.add(a3);
			// return;
		}

		if (GetBit(parent.code, 2)) {
			parent.prototypes1234.set(2, left.prototypes1234.get(2));
			left.prototypes1234.get(2).usedByNodeNumber = nodeNumber;
		}
		if (GetBit(parent.code, 3)) {
			parent.prototypes1234.set(3, right.prototypes1234.get(3));
			right.prototypes1234.get(3).usedByNodeNumber = nodeNumber;
			// TODO:
			/*
			 * if (GetBitCount(parent.code) == 1 &&
			 * !left.prototypes1234.get(1).isEmpty() &&
			 * !right.prototypes1234.get(2).isEmpty()) {
			 * left.prototypes1234.get(1).plusS(right.prototypes1234.get(2), 0);
			 * parent.biqliques.add(left.prototypes1234.get(1)); }
			 */
		}
		if (GetBit(parent.code, 4)) {
			Prototype a1 = left.prototypes1234.get(1);
			Prototype b1 = right.prototypes1234.get(1);
			a1.plusS(b1, 4);
			parent.prototypes1234.set(4, a1);
			a1.usedByNodeNumber = nodeNumber;
			b1.usedByNodeNumber = nodeNumber;
		}
		if (GetBit(parent.code, 5)) {
			Prototype a4 = left.prototypes1234.get(4);
			Prototype b1 = right.prototypes1234.get(1);
			a4.plusS(b1, 5);
			parent.prototypes5.add(a4);
			parent.prototypes5.addAll(left.prototypes5);
			parent.prototypes5.addAll(right.prototypes5);
			a4.usedByNodeNumber = nodeNumber;
			b1.usedByNodeNumber = nodeNumber;
		}
		if (GetBit(parent.code, 6)) {
			Prototype a1 = left.prototypes1234.get(1);
			Prototype b4 = right.prototypes1234.get(4);
			a1.plusS(b4, 6);
			parent.prototypes6.add(a1);
			parent.prototypes6.addAll(left.prototypes6);
			parent.prototypes6.addAll(right.prototypes6);
			a1.usedByNodeNumber = nodeNumber;
			b4.usedByNodeNumber = nodeNumber;
		}

		if (GetBit(left.code, 1) && GetBit(right.code, 2)
		/* parent.code == 0 */) {
			Prototype a1 = left.prototypes1234.get(1);
			Prototype b2 = right.prototypes1234.get(2);
			if (a1.usedByNodeNumber != nodeNumber
					&& b2.usedByNodeNumber != nodeNumber) {
				a1.plusS(b2, 0);
				parent.biqliques.add(a1);
				a1.usedByNodeNumber = nodeNumber;
				b2.usedByNodeNumber = nodeNumber;
			}
		}

		if (GetBit(left.code, 3) && GetBit(right.code, 1)
		/* parent.code == 0 */) {
			Prototype a3 = left.prototypes1234.get(3);
			Prototype b1 = right.prototypes1234.get(1);
			if (a3.usedByNodeNumber != nodeNumber
					&& b1.usedByNodeNumber != nodeNumber) {
				a3.plusS(b1, 0);
				parent.biqliques.add(a3);
				a3.usedByNodeNumber = nodeNumber;
				b1.usedByNodeNumber = nodeNumber;
			}
		}

		
	}

	private void generateBiclique(Node node) {
		node.generatePrototype();
	}

	private static boolean GetBit(int x, int bit) {
		return (x & (1 << (bit - 1))) != 0;
	}

	public static int GetBitCount(int x) {
		int count = 0;
		for (int i = 1; i < (1 << 6); i = i << 1)
			if ((x & i) != 0)
				count++;
		return count;
	}

	private void makeBiclique(Prototype st, ArrayList<Prototype> prototypes,
			Node parent) {
		for (int i = 0; i < prototypes.size(); i++) {
			Prototype prNew = prototypes.get(i);
			prNew.plusP(st, 0);
			parent.biqliques.add(prNew);
		}
	}
}
