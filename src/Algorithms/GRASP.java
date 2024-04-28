package Algorithms;

import Models.Instance;
import Models.Solution;

import java.util.*;

public class GRASP extends GreedyAlg {
    // -1 will be treated as random between 0 and 1
    private final static double[] alphas = {0.25, 0.5, 0.75, -1};
    private double alpha = -2;

    public GRASP(Instance instance) {
        super(instance);
    }


    // runs only the first phase, without improvement through local search
    @Override
    public Solution run() {
        long startTime = System.currentTimeMillis();
        if (this.alpha == -2){
            this.setAlpha();
        }
        this.solution.reset();
        this.instance.floydWarshall();
        this.selectFacilities();
        super.assignFacilities();
        long endTime = System.currentTimeMillis() - startTime;
        this.solution.setRuntime(endTime);
        return this.solution;
    }

    public Solution run(double alpha){
        this.setAlpha(alpha);
        this.run();
        return this.solution;
    }

    // 0 for FI, 1 (or anything else) for BI
    public Solution run(int improvementMode){
        this.run();
        //System.out.println("GRASP before improvement");
        //solution.printSolution();
        try {
            this.improve(improvementMode);
            //System.out.println("\nGRASP after improvement");
            //solution.printSolution();
        } catch (CloneNotSupportedException e) {
            System.err.println("Unable to perform GRASP phase 2");
            e.printStackTrace();
            return null;
        }
        return this.solution;
    };

    public Solution run(int improvementMode, double alpha){
        long startTime = System.currentTimeMillis();
        this.setAlpha(alpha);
        this.run(improvementMode);
        long endTime = System.currentTimeMillis() - startTime;
        this.solution.setRuntime(endTime);
        return this.solution;
    }

    private void selectFacilities() {
        super.getCandidates();
        List<Map.Entry<Integer, Double>> candidates = super.candidates;
        double alpha = this.alpha;
        Random rand = new Random();
        // choose facilities at random from the RCL
        for (int i = 0; i < this.instance.getnFacilities(); i++) {
            double threshold = this.getThreshold(alpha, candidates);
            List<Integer> RCL = this.buildRCL(candidates, threshold);
            int newFacilty = RCL.get(rand.nextInt(RCL.size()));
            // add a new facility chosen at random
            solution.addFacilitiy(newFacilty);
            super.updateCandidateList(newFacilty);
        }
    }

    private void setAlpha(){
        Random rand = new Random();
        double alpha = alphas[rand.nextInt(alphas.length)];
        this.setAlpha(alpha);
    }

    private void setAlpha(double alpha){
        if (alpha == -1){
            Random rand = new Random();
            alpha = rand.nextDouble(1);
        }
        this.alpha = alpha;
    }

    public double getAlpha(){
        return this.alpha;
    }

    private double getThreshold(double alpha, List<Map.Entry<Integer, Double>> candidates){
        // th = g_min + alpha * (g_max - g_min)
        double threshold = candidates.get(0).getValue() + alpha * (candidates.get(candidates.size() - 1).getValue() - candidates.get(0).getValue());
        return threshold;
    }

    private List<Integer> buildRCL(List<Map.Entry<Integer, Double>> candidates, double threshold){
        List<Integer> RCL = new ArrayList<>();
        int i = 0;
        // build the restricted candidates list using only those nodes whose average distance is below the threshold
        while (i < candidates.size() && candidates.get(i).getValue() <= threshold) {
            RCL.add(candidates.get(i).getKey());
            i++;
        }
        return RCL;
    }



    protected void improve(int mode) throws CloneNotSupportedException {
        boolean improved;
        do{
            improved = false;
            double best = solution.getObjectiveFunction();
            Solution bestSolution = solution;
            // facility to close
            int close = solution.getWorstFacilty();
            int client = solution.getWorstClient();
            // set of clients that are closer (or at equal distance) to client than close
            Set<Integer> alts = this.instance.getClientsInRadius(client, best, solution.getFacilities());
            for (int newFacility : alts){
                // swap close for newFacility, then recalculate allocations
                Solution newSolution = this.buildNewSolution(close, newFacility);
                if (newSolution.getObjectiveFunction() < best){
                    bestSolution = newSolution;
                    improved = true;
                    // when mode == 0 -> FI, stick to the first improving switch found
                    if (mode==0){
                        break;
                    }
                    best = newSolution.getObjectiveFunction();
                }
            }
            this.solution = bestSolution;
        } while (improved);
    }

    private Solution buildNewSolution(int close, int newFacility) throws CloneNotSupportedException {
        Solution newSolution = (Solution) this.solution.clone();
        newSolution.swap(close, newFacility);
        newSolution.deleteAllocations();
        super.assignFacilities(newSolution);
        return newSolution;
    }
}
