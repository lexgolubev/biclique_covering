package by.bsu.covering;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

public class EuristicAlgorithm {
    protected Graph<Integer, DefaultEdge> g;

    protected List<Biclique> bicliques;

    public EuristicAlgorithm(Graph<Integer, DefaultEdge> g) {
        this.g = g;
        this.bicliques = new LinkedList<>();
        this.cover();
    }

    private void cover() {
        int n = g.vertexSet().size();
        for (int i = 0; i < n; i++) {
            Set<Integer> w = new HashSet<>();
            for (int j = 0; j < i; j++) {
                if (g.containsEdge(i, j)) {
                    w.add(j);
                }
            }
            if (w.isEmpty()) {
                Biclique b = new Biclique();
                b.x.add(i);
                bicliques.add(b);
            } else {
                Set<Integer> u = new HashSet<>();
                for (Biclique b : bicliques) {
                    if (w.containsAll(b.x) && !b.x.isEmpty()) {
                        b.y.add(i);
                        u.addAll(b.x);
                    } else if (w.containsAll(b.y) && !b.y.isEmpty()) {
                        b.x.add(i);
                        u.addAll(b.y);
                    }
                    if (w.isEmpty()) {
                        break;
                    }
                }
                w.removeAll(u);
                if (!w.isEmpty()) {
                    Biclique b = new Biclique();
                    b.x.add(i);
                    b.y.addAll(w);
                    bicliques.add(b);
                }
            }
        }
    }
    
    public List<Biclique> getCovering() {
        return bicliques;
    }

    public int getCoveringSize() {
        return bicliques.size();
    }

    class Biclique {
        List<Integer> x;

        List<Integer> y;

        public Biclique() {
            x = new ArrayList<>();
            y = new ArrayList<>();
        }
        public String toString() {
            return "[X = " + x + ", y = " + y + "]";
        }
    }
    
}
