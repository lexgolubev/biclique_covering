package by.bsu.binarytree.view;

import java.io.IOException;

import by.bsu.binarytree.domain.SPTree;
import by.bsu.binarytree.util.SPTreeException;
import by.golubev.covering.BicliqueCovering;

public class Runner {
	public static void main(String[] args) {
        SPTree tree = SPTree.generateRandomTree(13);
        try {
            tree.bc();
            BicliqueCovering bc = new BicliqueCovering(tree.generateGraph().getGraph());
            System.out.println("covering: " + bc.getCoveringSize());
            System.out.println("covering: " + bc.getCovering());
        } catch (SPTreeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tree.print();
    //         SPTree.generateRandomTree(5).print();
	}
}
