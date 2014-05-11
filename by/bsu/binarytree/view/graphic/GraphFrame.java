package by.bsu.binarytree.view.graphic;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import by.bsu.binarytree.alg.GraphAlgs;
import by.bsu.binarytree.alg.ResultAlgs;
import by.bsu.binarytree.domain.graph.Point;
import by.bsu.binarytree.domain.spttree.Node;
import by.bsu.binarytree.domain.spttree.SPTree;
import by.bsu.binarytree.util.SPTreeException;
import by.bsu.binarytree.view.graph.GraphViewAlgs;

public class GraphFrame extends JFrame {

	private static final int WIDTH = 900;
	private static final int HEIGHT = 500;
	private GridBagConstraints c;
	private Container contentPane;
	private GraphPanel graphPanel = null;

	public GraphFrame() {
		super("Bicliques");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		contentPane = this.getContentPane();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		performSearch();
		contentPane.add(graphPanel, c);
		graphPanel.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent me) {
				System.out.println(me);
				graphPanel.onPressed(me);
				graphPanel.repaint();
			}

			public void mouseReleased(MouseEvent me) {
				graphPanel.onReleased(me);
				graphPanel.repaint();
			}
		});
	}

	public static void main(String[] args) {
		GraphFrame graphFrame = new GraphFrame();
		graphFrame.setVisible(true);
	}

	private void performSearch() {
		SPTree tree = new SPTree();
		ResultAlgs ra = new ResultAlgs();
		GraphAlgs ga = new GraphAlgs();
		GraphViewAlgs gva = new GraphViewAlgs();
		try {
			//tree.loadFromFile("input.txt");
		    try {
                tree.loadFromFile("input.txt");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//SPTree.generateRandomTree(10);
			tree.setFirstAndLastVertices();

			ra.generateResultTree(tree.bc(), tree.getResult(), tree.getTree());
			ga.generateBicliques(tree.getTree());
			tree.print();

			ArrayList<Point> graphPoints = gva.generateGraphNumberView(tree
					.getTree(), tree.getGraphVertexCount());
			graphPanel = new GraphPanel(this);
			graphPanel.loadGraphView(graphPoints, gva.getMaxPoint().x, gva
					.getMaxPoint().y, tree.getTree().get(1).biqliques);
		} catch (SPTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
