package by.golubev.covering.sptree;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;


/**
 * 
 * @author LexLuthor
 * STGraph is representation of sequence-parallel graph
 * s-vertex is 0
 * t-vertex is last vertex 
 *
 */
public class STGraph {
    Graph<Integer, DefaultEdge> g;

    public STGraph() {
        g = new SimpleGraph<>(DefaultEdge.class);
    }
    
    public static STGraph createLeafSTGraph() {
        STGraph result = new STGraph();
        result.g.addVertex(0);
        result.g.addVertex(1);
        result.g.addEdge(0, 1);
        return result;
    }
    
    public STGraph sequenceOperation(STGraph other) {
        STGraph result = new STGraph();
        int n = this.g.vertexSet().size();
        int m = other.g.vertexSet().size();
        //insert verteces
        for (int i = 0; i < n + m - 1; i++) {
            result.g.addVertex(i);
        }
        //insert edges of first graph
        for (DefaultEdge e : this.g.edgeSet()) {
            result.g.addEdge(this.g.getEdgeSource(e), this.g.getEdgeTarget(e));
        }
        //insert edges of second graph exclude edges of s-vertex
        Set<DefaultEdge> edges = new HashSet<>(other.g.edgeSet());
        edges.removeAll(other.g.edgesOf(0));
        for (DefaultEdge e : edges) {
            result.g.addEdge(other.g.getEdgeSource(e) + n - 1, other.g.getEdgeTarget(e) + n - 1);
        }
        //insert edges of second graph from s-vertex
        for (DefaultEdge e : other.g.edgesOf(0)) {
            if (other.g.getEdgeSource(e).equals(0)) {
                result.g.addEdge(n - 1, other.g.getEdgeTarget(e) + n - 1);
            } else {
                result.g.addEdge(n - 1, other.g.getEdgeSource(e) + n - 1);
            }
        }
        return result;
    }
    
    public STGraph parallelOperation(STGraph other) {
        STGraph result = new STGraph();
        int n = this.g.vertexSet().size();
        int m = other.g.vertexSet().size();
        //insert verteces
        for (int i = 0; i < n + m - 2; i++) {
            result.g.addVertex(i);
        }
        Set<DefaultEdge> edges1 = new HashSet<>(this.g.edgeSet());
        edges1.removeAll(this.g.edgesOf(n - 1));
        //insert edges of 1st graph exclude edges of t-vertex
        for (DefaultEdge e : edges1) {
            result.g.addEdge(this.g.getEdgeSource(e), this.g.getEdgeTarget(e));
        }
        //insert edges of 2nd graph exclude edges of s-vertex
        Set<DefaultEdge> edges2 = new HashSet<>(other.g.edgeSet());
        edges2.removeAll(other.g.edgesOf(0));
        for (DefaultEdge e : edges2) {
            result.g.addEdge(other.g.getEdgeSource(e) + n - 2, other.g.getEdgeTarget(e) + n - 2);
        }
        //insert edges of t-vertex 1st graph
        for (DefaultEdge e : this.g.edgesOf(n - 1)) {
            if (other.g.getEdgeSource(e).equals(n - 1)) {
                result.g.addEdge(n + m - 3, this.g.getEdgeTarget(e));
            } else {
                result.g.addEdge(n + m - 3, this.g.getEdgeSource(e));
            }
        }
        //insert edges of s-vertex 2nd graph
        for (DefaultEdge e : other.g.edgesOf(0)) {
            if (other.g.getEdgeSource(e).equals(0)) {
                result.g.addEdge(0, other.g.getEdgeTarget(e) + n - 2);
            } else {
                result.g.addEdge(0, other.g.getEdgeSource(e) + n - 2);
            }
        }
        return result;
    }
    
    public String toString() {
        return g.toString();
    }
    
    public Graph<Integer, DefaultEdge> getGraph() {
        return g;
    }
}
