package by.bsu.binarytree.domain.spttree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import by.bsu.binarytree.alg.Algorithms;
import by.bsu.binarytree.domain.result.ResultNode;
import by.bsu.binarytree.domain.result.ResultRecord;
import by.bsu.binarytree.util.SPTreeException;
import by.bsu.covering.stgraph.STGraph;

public class SPTree {
	private ArrayList<Node> tree = new ArrayList<Node>();
	private ArrayList<ResultRecord> resultRecord = new ArrayList<ResultRecord>();
	private static final int INF = 10000000;
	private static final int NUMBER_OF_PROPERTIES = 6;
	private static final int MAX_MASK = 1 << NUMBER_OF_PROPERTIES;
        private int result = INF;
	private int numberSPNodes = 0;
	private int graphVertexCount = 0;
	private int minCoveringSize;

	private void buildEmptyTree(int size) {
		tree.clear();
		numberSPNodes = 0;
		for (int i = 0; i < size + 1; i++) {
			Node node = new Node();
			node.type = NodeType.EMPTY;
			node.left = -1;
			node.right = -1;
			tree.add(node);
		}
	}

	public void loadFromFile(String fileName) throws IOException,
			SPTreeException {
		Integer prev;
		Integer type;
		if (fileName.equals("")) {
			return;
		}
		// load data from file
		FileReader fin = new FileReader(fileName);
		Scanner inFile = new Scanner(fin);
		if (inFile.hasNextInt()) {
			buildEmptyTree(inFile.nextInt() + 1);
			//tree.get(1).type = NodeType.getByValue(inFile.nextInt());
		} else {
			throw new SPTreeException("Incorrect File Structure");
		}
		int count = 0;
		try {
			while (inFile.hasNext()) {
				count++;
				prev = new Integer(inFile.next());
				type = new Integer(inFile.next());
				tree.get(count).type = NodeType.getByValue(type);
				if (NodeType.isSP(type)) {
					numberSPNodes ++;
				}
				if (prev >= 0) {
					if (tree.get(prev).left == -1) {
						tree.get(prev).left = count;
						// System.out.println(prev + " left " + count);
					} else {
						tree.get(prev).right = count;
						// System.out.println(prev + " right " + count);
					}
				}
			}
		} catch (SPTreeException e) {
			throw new SPTreeException("Node type is out of range");
		} finally {
			fin.close();
			inFile.close();
		}
	}

    public static SPTree generateRandomTree(int edgeAmount) {
        Random rnd = new Random();
        SPTree sptree = new SPTree();
        List<Node> unmerged = new ArrayList<>();
        sptree.tree.add(new Node());
        sptree.tree.add(new Node());
        for (int i = 0; i < edgeAmount; i++) {
            Node node = new Node();
            node.type = NodeType.LEAF;
            node.left = -1;
            node.right = -1;
            node.hasSTEdge = true;
            unmerged.add(node);
            sptree.tree.add(node);
        }
        while (unmerged.size() > 1) {
            int next = rnd.nextInt(unmerged.size() - 1);
            Node node = new Node();
            Node left = unmerged.get(next);
            Node right = unmerged.get(next + 1);
            node.left = sptree.tree.indexOf(left);
            node.right = sptree.tree.indexOf(right);
            if (left.hasSTEdge && right.hasSTEdge) {
                node.type = NodeType.SEQ;
                node.hasSTEdge = false;
            } else {
                if (rnd.nextBoolean()) {
                    node.type = NodeType.SEQ;
                    node.hasSTEdge = false;
                } else {
                    node.type = NodeType.PAR;
                    node.hasSTEdge = left.hasSTEdge || right.hasSTEdge;
                }
            }
            unmerged.remove(left);
            unmerged.remove(right);
            unmerged.add(next, node);
            if (unmerged.size() == 1) {
                sptree.tree.set(1, node);
            } else {
                sptree.tree.add(node);
            }
            sptree.numberSPNodes++;
        }
        return sptree;
    }
    
    public int setFirstAndLastVertices() {
		graphVertexCount = setFirstAndLastVerticesRec(tree.get(1), 1, 2, 2, 0, 0);
		return graphVertexCount;
	}
    
    private int setFirstAndLastVerticesRec(Node node, Integer first, Integer last, Integer usedNumbers,
            Integer x, Integer y) {
        node.first = first;
        node.last = last;
        node.x = x;
        node.y = y;
        if (node.type == NodeType.PAR) {
            usedNumbers = setFirstAndLastVerticesRec(tree.get(node.left), first, last, usedNumbers,
                    x, y);
            usedNumbers = setFirstAndLastVerticesRec(tree.get(node.right), first, last, usedNumbers,
                    x, y + 1);
        } else if (node.type == NodeType.SEQ) {
            int middle = usedNumbers + 1;           
            usedNumbers = setFirstAndLastVerticesRec(tree.get(node.left), first, middle, usedNumbers + 1,
                    x, y);
            usedNumbers = setFirstAndLastVerticesRec(tree.get(node.right), middle, last, usedNumbers,
                    x + 1, y);
        } 
        return usedNumbers;
    }
    
    public void print() {
        printSubtree(1);
    }
    
	private void printSubtree(int nodeNumber) {
	    Node node = tree.get(nodeNumber);
	    if (!node.type.equals(NodeType.LEAF)) {
	        System.out.println(nodeNumber + " " + node.type + "(" + node.left + ", " + node.right + ")");
	        printSubtree(node.left);
	        printSubtree(node.right);
	    }
	}
	
	public int[] generateBC(int nodeNumber) throws SPTreeException {
        int[] hc;

        if (nodeNumber == -1) {
            throw new SPTreeException("Bad node number");
        }

        switch (tree.get(nodeNumber).type) {
        case LEAF:
            hc = leafFunction();
            break;
        case SEQ:
            hc = spFunction('s', nodeNumber);
            break;
        case PAR:
            hc = spFunction('p', nodeNumber);
            break;
        default:
            throw new SPTreeException("Wrong type of vertex number "
                    + tree.get(nodeNumber).type);
        }

        return hc;
    }

	public ArrayList<ResultRecord> bc() throws SPTreeException {
		int[] bc = generateBC(1);
		result = INF;

		int resmask = -1;

		for (int i = 0; i < MAX_MASK; ++i)
			if (!((Algorithms.GB(i, 5) || Algorithms.GB(i, 6)) && !Algorithms
					.GB(i, 1))) {

				if (bc[i] < result) {
					result = bc[i];
					// result = min (result, bc[i]);
					resmask = i;
				}
			}

		//System.out.println("result: " + result);
		//System.out.println("resmask: "+ resmask);
		minCoveringSize = result;
		return resultRecord;
	}

	public int getMinCoveringSize() {
        return minCoveringSize;
    }

    public static int[] leafFunction() {
		int[] hc = new int[MAX_MASK];
		for (int i = 0; i < MAX_MASK; ++i) {
			hc[i] = INF;
		}
		hc[1] = 1;
		hc[2] = 1;
		hc[4] = 1;
		return hc;
	}

	public int[] spFunction(char flag, int nodeNumber) throws SPTreeException {
		int[] ha;
		int[] hb;
		int[] hc;
		ha = generateBC(tree.get(nodeNumber).left);
		hb = generateBC(tree.get(nodeNumber).right);

		hc = new int[MAX_MASK];
		for (int i = 0; i < MAX_MASK; ++i) {

			hc[i] = INF;
		}

		int currw;

		for (int i = 0; i < MAX_MASK; ++i)
			if (ha[i] != INF)
				for (int j = 0; j < MAX_MASK; ++j)
					if (hb[j] != INF)
						for (int k = 0; k < MAX_MASK; ++k) {

							currw = Algorithms.findDiff(flag, i, j, k);

							if (currw >= 0) {
								if (hc[k] > ha[i] + hb[j] - currw) {
									hc[k] = ha[i] + hb[j] - currw;

//									System.out.print(nodeNumber
//											+ " c= "
//											+ Algorithms.gbString(k,
//													NUMBER_OF_PROPERTIES)
//											+ " hc= " + hc[k]);
//									System.out.print(" a= "
//											+ Algorithms.gbString(i,
//													NUMBER_OF_PROPERTIES)
//											+ " ha= " + ha[i]);
//									System.out.println(" b= "
//											+ Algorithms.gbString(j,
//													NUMBER_OF_PROPERTIES)
//											+ " hb= " + hb[j]);
									ResultNode c = new ResultNode(hc[k], Algorithms.gbString(k,
											NUMBER_OF_PROPERTIES));
									ResultNode a = new ResultNode(ha[i], Algorithms.gbString(i,
											NUMBER_OF_PROPERTIES));
									ResultNode b = new ResultNode(hb[j], Algorithms.gbString(j,
											NUMBER_OF_PROPERTIES));
									resultRecord.add(new ResultRecord(nodeNumber,c, a, b));									
								}

								// hc[k] = min(ha[i] + hb[j] - currw, hc[k]);
							}
						}
		return hc;
	}

    public int getResult() {
		return result;
	}

	public int getNumberSPNodes() {
		return numberSPNodes;
	}

        public boolean hasANodeLeaf(int nodeNumber) {
		return tree.get(tree.get(nodeNumber).left).type.isLeaf();
	}
	
	public boolean hasBNodeLeaf(int nodeNumber) {
		return tree.get(tree.get(nodeNumber).right).type.isLeaf();
	}

        public ArrayList<Node> getTree() {
		return tree;
	}

	public int getGraphVertexCount() {
		return graphVertexCount;
	}

	public STGraph generateGraph() {
	    return generateSubgraph(1);
	}
	
	private STGraph generateSubgraph(int nodeNumber) {
        Node node = tree.get(nodeNumber);
        if (node.type.equals(NodeType.LEAF)) {
            return STGraph.createLeafSTGraph();
        } else if (node.type.equals(NodeType.SEQ)) {
            STGraph left = generateSubgraph(node.left);
            STGraph right = generateSubgraph(node.right);
            return left.sequenceOperation(right);
        } else {
            STGraph left = generateSubgraph(node.left);
            STGraph right = generateSubgraph(node.right);
            return left.parallelOperation(right);
        }
    }
}
