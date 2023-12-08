import java.util.Random;

public class RandomAlg implements Algorithm{
    private Instance instance;
    private Solution solution;

    public RandomAlg(Instance instance) {
        this.instance = instance;
    }

    @Override
    public Solution run() {
        // applies floydWarshall to the original graph
        instance.floydWarshall();
        // selects p facilities at random
        this.selectFacilities();
        this.assignFacilities();
        return this.solution;

    }

    private void selectFacilities(){
        int nNodes = instance.getnNodes();
        int nFacilities = instance.getnFacilities();
        Random rand = new Random();
        this.solution = new Solution(instance);
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

    private void assignFacilities(){
        int alpha = instance.getAlpha();
        int[] candidates = new int[alpha];
        for (int i = 0; i<instance.getnNodes(); i++){
            if (!this.solution.hasFacility(i)){
                for (int k = 0; k<alpha; k++){
                    candidates[k] = -1;
                }
                for (int facility : solution.getFacilities()){
                    for (int j = 0; j<alpha; j++){
                        if (candidates[j]==-1 || instance.getDistance(i, facility) < instance.getDistance(i, candidates[j])){
                            candidates[j] = facility;
                            break;
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
