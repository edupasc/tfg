package Algorithms;

import Models.CandidatesComparator;
import Models.Instance;
import Models.Solution;

import java.util.*;

public class GreedyAlg extends GenericAlg{

    List<Map.Entry<Integer, Double>> candidates;

    public GreedyAlg(Instance instance) {
        super(instance);
    }


    @Override
    public Solution run() {
        long startTime = System.currentTimeMillis();
        this.instance.floydWarshall();
        this.selectFacilities();
        super.assignFacilities();
        long endTime = System.currentTimeMillis() - startTime;
        this.solution.setRuntime(endTime);
        return this.solution;
    }

    private void selectFacilities(){
        getCandidates();
        // selects the p facilities with the lowest average distance to the rest of nodes
        for (int i = 0; i<this.instance.getnFacilities(); i++){
            this.solution.addFacilitiy(getNextCandidate());
        }
    }

    protected void getCandidates(){
        candidates = new LinkedList<>();
        for (int i = 0; i<this.instance.getnNodes(); i++){
            // greedy heuristic is the average distance to the rest of nodes, excluding already open facilities
            double average = this.instance.getAverageDistance(i, this.solution.getFacilities());
            candidates.add(new AbstractMap.SimpleEntry<>(i, average));
        }
        CandidatesComparator comparator = new CandidatesComparator();
        candidates.sort(comparator);
    }


    protected int getNextCandidate(){
        int candidate = this.candidates.get(0).getKey();
        // comment this for the prev version of greedy
        updateCandidateList(candidate);
        return candidate;
    }

    protected void updateCandidateList(int remove){
        int i = 0;
        while (this.candidates.get(i).getKey() != remove){
            i++;
        }
        this.candidates.remove(i);
        for (i = 0; i<this.candidates.size(); i++){
            Map.Entry<Integer, Double> entry = candidates.get(i);
            entry.setValue(this.instance.getAverageDistance(entry.getKey(), this.solution.getFacilities()));
        }
        CandidatesComparator comparator = new CandidatesComparator();
        candidates.sort(comparator);
    }
}
