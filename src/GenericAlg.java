public abstract class GenericAlg implements Algorithm{
    Instance instance;
    Solution solution;

    public GenericAlg(Instance instance){
        this.instance = instance;
        this.solution = new Solution(instance);
    }

    protected void assignFacilities(){
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
