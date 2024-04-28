package Algorithms;

import Models.Instance;
import Models.Solution;

import java.util.Random;

public class VNS extends GRASP{

    private final static double[] kValues = {0.05, 0.10, 0.20};

    public VNS(Instance instance, Solution solution) {
        super(instance);
        this.solution = solution;
    }

    public Solution run() {
        Solution oldSolution;
        try{
            oldSolution = (Solution) this.solution.clone();
            for (double k : kValues) {
                boolean improved = true;
                while (improved) {
                    improved = false;
                    this.shake(k);
                    super.improve(1);
                    if (this.solution.getObjectiveFunction() < oldSolution.getObjectiveFunction()) {
                        improved = true;
                        oldSolution = (Solution) this.solution.clone();
                    }
                }
            }
        } catch (CloneNotSupportedException e){
            System.err.println("Unable to perform VNS");
            e.printStackTrace();
            return null;
        }
        if (this.solution.getObjectiveFunction() < oldSolution.getObjectiveFunction()){
            return this.solution;
        } else{
            return oldSolution;
        }
    }

    // closes and opens k (%) of facilities at random
    private void shake(double k){
        int nClose = (int) (this.instance.getnFacilities() * k);
        Random rand = new Random();
        for (int i = 0; i<nClose; i++){
            int open = -1, close = -1;
            while (open == -1 || close == -1){
                int node = rand.nextInt(this.instance.getnNodes());
                if (solution.getFacilities().contains(node)){
                    close = node;
                } else{
                    open = node;
                }
            }
            solution.swap(close, open);
        }
        solution.deleteAllocations();
        super.assignFacilities(solution);
    }
}
