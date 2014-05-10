package by.golubev.covering;

import by.bsu.binarytree.domain.SPTree;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: lex
 * Date: 4/27/14
 * Time: 9:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class BicliqueCoveringOnSeriesParallelGraph {
    private int size;

    private SPTree tree;

    public BicliqueCoveringOnSeriesParallelGraph(SPTree tree) {
        size = 0;
        this.tree = tree;
        cover();
    }

    private void cover() {

    }

    public int getCoveringSize() {
        return 0;
    }
}
