package Algorithms;

import Models.Instance;
import Models.Solution;

import java.util.Random;

public class RandomAlg extends GenericAlg{

    public RandomAlg(Instance instance) {
        super(instance);
    }

    @Override
    public Solution run() {
        // applies floydWarshall to the original graph
        instance.floydWarshall();
        // selects p facilities at random
        this.selectFacilities();
        super.assignFacilities();
        return this.solution;

    }

    private void selectFacilities(){
        int nNodes = instance.getnNodes();
        int nFacilities = instance.getnFacilities();
        Random rand = new Random();
        for (int i = 0; i<nFacilities; i++){
            int newFacilty = rand.nextInt(nNodes);
            if (!solution.hasFacility(newFacilty)){
                // add a new facility chosen at random
                solution.addFacilitiy(newFacilty);
            } else{
                // if the current node was already a facility, ignore this iteration
                i--;
            }
        }
    }


}
