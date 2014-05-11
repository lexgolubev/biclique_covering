package by.bsu.binarytree.domain.spttree;

import by.bsu.binarytree.util.SPTreeException;

public enum NodeType {

	EMPTY(0), LEAF(1), SEQ(2), PAR(3);
	private int value;

	NodeType(int v) {
		value = v;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isLeaf() {
		return value == 1;
	}
	
	public static NodeType getByValue(int value) throws SPTreeException {
		switch (value) {
		case 0: return EMPTY;
		case 1: return LEAF;
		case 2: return SEQ;
		case 3: return PAR;
		default: throw new SPTreeException("Node type is out of range");
		}
	}
	
	public static boolean isSP(int value) {
		return (value == 2) || (value == 3);
	}
}
