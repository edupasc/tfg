import java.util.Random;

public class RandomAlg implements Algorithm{
    private Solution solution;

    @Override
    public void run(Instance instance) {
        // applies floydWarshall to the original graph
        instance.floydWarshall();
        // selects p facilities at random
        this.selectFacilities(instance);
        this.assignFacilities(instance);

    }

    private void selectFacilities(Instance instance){
        int nNodes = instance.getnNodes();
        int nFacilities = instance.getnFacilities();
        Random rand = new Random();
        Solution solution = new Solution(instance);
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

    private void assignFacilities(Instance instance){
        int alpha = instance.getAlpha();
        int[] candidates = new int[alpha];
        for (int i = 0; i<alpha; i++){
            candidates[i] = -1;
        }
        for (int i = 0; i<instance.getnNodes(); i++){
            if (!this.solution.hasFacility(i)){
                for (int facility : solution.getFacilities()){
                    for (int j = 0; j<alpha; j++){
                        if (candidates[j]==-1 || instance.getDistance(i, facility) < instance.getDistance(i, candidates[j])){
                            candidates[j] = facility;
                        }
                    }
                }
                for (int j = 0; j<alpha; j++){
                    this.solution.allocate(i, candidates[j], j);
                }
            }
        }
    }
}
