package by.bsu.covering;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import by.bsu.covering.EuristicAlgorithm.Biclique;

public class ModifiedEuristicAlgorithm extends EuristicAlgorithm {

    public ModifiedEuristicAlgorithm(Graph<Integer, DefaultEdge> g) {
        super(g);
        deleteUnnesesaryBicliques();
    }

    private void deleteUnnesesaryBicliques() {
        List<Biclique> unnessecary = new ArrayList<>();
        for (Biclique b1 : this.bicliques) {
            boolean bicliqueIsNessecary = true;
            for (Integer x : b1.x) {
                for (Integer y : b1.y) {
                    boolean edgeIsNessecary = true;
                    for (Biclique b2 : this.bicliques) {
                        if (b1 != b2) {
                            if ((b2.x.contains(x) && b2.y.contains(y)) || (b2.y.contains(x) && b2.x.contains(y))) {
                                edgeIsNessecary = false;
                                break;
                            }
                        }
                    }
                    if (edgeIsNessecary) {
                        bicliqueIsNessecary = true;
                        break;
                    }
                }
                if (bicliqueIsNessecary) {
                    break;
                }
            }
            if (!bicliqueIsNessecary) {
                unnessecary.add(b1);
            }
        }
        System.out.println("deleted " + unnessecary.size());
        this.bicliques.removeAll(unnessecary);
    }

}
