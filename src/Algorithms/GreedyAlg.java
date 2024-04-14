package Algorithms;

import Models.Instance;
import Models.Solution;

import java.util.*;

public class GreedyAlg extends GenericAlg{

    public GreedyAlg(Instance instance) {
        super(instance);
    }


    @Override
    public Solution run() {
        this.instance.floydWarshall();
        this.selectFacilities();
        super.assignFacilities();
        return this.solution;
    }

    private void selectFacilities(){
        List<Map.Entry<Integer, Double>> candidates = getCandidates();
        // selects the p facilities with the lowest average distance to the rest of nodes
        for (int i = 0; i<this.instance.getnFacilities(); i++){
            this.solution.addFacilitiy(candidates.get(i).getKey());
        }
    }

    protected List<Map.Entry<Integer, Double>> getCandidates(){
        List<Map.Entry<Integer, Double>> candidates = new ArrayList<>();
        for (int i = 0; i<this.instance.getnNodes(); i++){
            // greedy criterion is the average distance to the rest of nodes
            double average = 0;
            for (int j = 0; j<this.instance.getnNodes(); j++){
                average+=this.instance.getDistance(i ,j);
            }
            average /= (this.instance.getnNodes() - 1);
            candidates.add(new AbstractMap.SimpleEntry<>(i, average));
        }
        candidates.sort(new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return (int) (o1.getValue() - o2.getValue());
            }
        });
        return candidates;
    }
}
