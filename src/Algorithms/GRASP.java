package Algorithms;

import Models.Instance;
import Models.Solution;

import java.util.*;

public class GRASP extends GreedyAlg {
    // -1 will be treated as random between 0 and 1
    private final static double[] alphas = {0.25, 0.5, 0.75, -1};

    public GRASP(Instance instance) {
        super(instance);
    }


    // runs only the first phase, without improvement through local search
    @Override
    public Solution run() {
        this.instance.floydWarshall();
        this.selectFacilities();
        super.assignFacilities();
        return this.solution;
    }

    // 0 for FI, 1 (or anything else) for BI
    public Solution run(int improvementMode){
        this.run();
        System.out.println("GRASP before improvement");
        solution.printSolution();
        try {
            this.improve2(improvementMode);
            System.out.println("\nGRASP after improvement");
            solution.printSolution();
        } catch (CloneNotSupportedException e) {
            System.err.println("Unable to perform GRASP phase 2");
            e.printStackTrace();
            return null;
        }
        return this.solution;
    };

    private void selectFacilities() {
        List<Map.Entry<Integer, Double>> candidates = super.getCandidates();
        Random rand = new Random();
        double alpha = alphas[rand.nextInt(alphas.length)];
        if (alpha == -1) {
            alpha = rand.nextDouble(1);
        }
        // th = g_min + alpha * (g_max - g_min)
        double threshold = candidates.get(0).getValue() + alpha * (candidates.get(candidates.size() - 1).getValue() - candidates.get(0).getValue());
        List<Integer> RCL = new ArrayList<>();
        int i = 0;
        // build the restricted candidates list using only those nodes whose medium distance is below the threshold
        while (candidates.get(i).getValue() <= threshold) {
            RCL.add(candidates.get(i).getKey());
            i++;
        }

        // choose facilities at random from the RCL
        for (i = 0; i < this.instance.getnFacilities(); i++) {
            int newFacilty = RCL.get(rand.nextInt(RCL.size()));
            if (!solution.hasFacility(newFacilty)) {
                // add a new facility chosen at random
                solution.addFacilitiy(newFacilty);
            } else {
                // if the current node was already a facility, ignore this iteration
                i--;
            }

        }
    }

    /* TODO: problemas de concurrencia si se edita a la vez que se itera this.solution
    si se itera oldSolution mientras se modifica this.solution, a veces se intenta reemplazar una facility que existe en oldSolution
    pero no en this.solution, por lo tanto la hacer el swap se añade una facility más!!! ¿POR QUÉ? no sé, no debería, this.solution
    y oldSolution deberían ser copias idénticas antes de llegar a la línea 88 (si se hace un swap exitoso, oldSolution se convierte en
    una copia de this.solution)
     */
    private void improve(int mode) throws CloneNotSupportedException{
        //System.out.println("GRASP before improvment:");
        //solution.printSolution();
        // saves the previous solution in case we need to restore it
        Solution oldSolution = this.solution;
        this.solution = (Solution) oldSolution.clone();
        double best = oldSolution.getObjectiveFunction();
        int bestFacility = -1;
        Set<Integer> facilities = new HashSet<>(this.solution.getFacilities());
        for (int facility : facilities){
            for (int i = 0; i<instance.getnNodes(); i++){
                if (!facilities.contains(i)){
                    // swaps the old facility with the new one and then recalculates allocations
                    this.solution.swap(facility, i);
                    this.solution.deleteAllocations();
                    super.assignFacilities();
                    if (this.solution.getObjectiveFunction() >= best){
                        this.solution = (Solution) oldSolution.clone();
                    } else{
                        if (mode == 0){
                            System.out.println("GRASP after improvment:");
                            solution.printSolution();
                            return;
                        } else{
                            best = this.solution.getObjectiveFunction();
                            bestFacility = i;
                            this.solution = (Solution) oldSolution.clone();;
                        }
                    }
                }
            }
            this.solution.swap(facility, bestFacility);
            this.solution.deleteAllocations();
            super.assignFacilities();
            //facilities.remove(facility);
            //facilities.add(bestFacility);
            best = -1;
            oldSolution = (Solution) this.solution.clone();
        }
        //System.out.println("GRASP after improvment:");
        //solution.printSolution();
    }

    private void improve2(int mode) throws CloneNotSupportedException {
        double best = this.solution.getObjectiveFunction();
        int bestFacility = -1;
        List<Integer> facilities = new ArrayList<>(this.solution.getFacilities());
        for (int i = 0; i < facilities.size(); i++){
            int facility = facilities.get(i);
            for (int j = 0; j<this.instance.getnNodes(); j++){
                if (!this.solution.getFacilities().contains(j)){
                    Solution newSolution = (Solution) this.solution.clone();
                    newSolution.swap(facility, j);
                    newSolution.deleteAllocations();
                    super.assignFacilities(newSolution);
                    if (newSolution.getObjectiveFunction() < best){
                        // FI
                        if(mode == 0){
                            this.solution = newSolution;
                            return;
                        // BI
                        } else{
                            bestFacility = j;
                            best = newSolution.getObjectiveFunction();
                        }
                    }
                }
            }
            if (bestFacility != -1){
                facilities.remove(i);
                facilities.add(bestFacility);
                solution.getFacilities().remove(facility);
                solution.getFacilities().add(bestFacility);
                solution.deleteAllocations();
                super.assignFacilities();
                bestFacility = -1;
            }
        }
    }
}
