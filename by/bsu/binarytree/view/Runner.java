package by.bsu.binarytree.view;

import by.bsu.binarytree.domain.spttree.SPTree;
import by.bsu.binarytree.util.SPTreeException;
import by.bsu.covering.EuristicAlgorithm;

public class Runner {
	public static void main(String[] args) {
	    double summaryError = 0;
        int length = 100;
	    for (int i = 0; i < length; i++) {
            SPTree tree = SPTree.generateRandomTree(100);
            try {
                EuristicAlgorithm bc = new EuristicAlgorithm(tree.generateGraph().getGraph());
                int size1 = bc.getCoveringSize();
                System.out.println("Aproximated covering size: " + size1);
    			
                tree.bc();
                int size2 = tree.getMinCoveringSize();
                System.out.println("Minimum covering size: " + size2 + "\n");
                
                summaryError += (double)(size1 - size2) / size2;
            } catch (SPTreeException e) {
                e.printStackTrace();
            }
	    }
	    System.out.println("Average related error: " + summaryError / length);
	}
}
