package by.bsu.binarytree.domain.spttree;

import java.util.ArrayList;

import by.bsu.binarytree.domain.graph.Prototype;

public class Node {
	public NodeType type;
	public int left;
	public int right;
	public boolean hasSTEdge;
	public int first; 
	public int last;
	public int code = 0;
	public String codeView = "000000";
	public int h = 0;
	public ArrayList<Prototype> prototypes1234 = new ArrayList<Prototype>();
	public ArrayList<Prototype> prototypes5 = new ArrayList<Prototype>();
	public ArrayList<Prototype> prototypes6 = new ArrayList<Prototype>();
	public ArrayList<Prototype> biqliques = new ArrayList<Prototype>();
	
	public int x = 0;
	public int y = 0;

	public Node() {
		{
			for (int i = 0; i < 5; i++) {
				prototypes1234.add(new Prototype());
			}
		}
	}
	
	public void generatePrototype() {
		int prType = (int)Math.round(Math.log(code)/Math.log(2)) + 1;
		Prototype pr = new Prototype(first, last, prType);
		// if prototype type < 5
		if (prType < 5 && prType > 0) {
			prototypes1234.set(prType, pr);			 
		} else if (prType == 5) {
			prototypes5.add(pr);
		} else if (prType == 6) {
			prototypes6.add(pr);
		} else if (prType == 0) {
			biqliques.add(pr);
		}
	}
	
	public void clearPrototypes() {
		prototypes1234.clear();
		prototypes5.clear();
		prototypes6.clear();
	}
	
	public void print() {
		System.out.println("type: " + type + " code: " + codeView + " left: " + left + " right: " + right + " first v: " + first + " last v: " + last + 
				" x: " + x + " y: " + y);
		for (int i = 1; i <= 4; i++) {
			if (!prototypes1234.get(i).isEmpty()) {
				prototypes1234.get(i).print();
			}
		}
		for (int i = 0; i < prototypes5.size(); i++) {
			prototypes5.get(i).print();			
		}
		for (int i = 0; i < prototypes6.size(); i++) {
			prototypes6.get(i).print();			
		}
		
		for (int i = 0; i < biqliques.size(); i++) {
			biqliques.get(i).print();			
		}
	}
}
