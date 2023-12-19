import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GRASP extends GreedyAlg {
    // -1 will be treated as random between 0 and 1
    private final static double[] alphas = {0.25, 0.5, 0.75, -1};

    public GRASP(Instance instance) {
        super(instance);
    }


    // runs only the first phase, without through local search
    @Override
    public Solution run() {
        this.instance.floydWarshall();
        this.selectFacilities();
        super.assignFacilities();
        return this.solution;
    }

    public Solution run(String improvement){
        this.run();
        if (improvement.equals("FI")){

        } else if (improvement.equals("BI")){

        }
        return this.solution;
    };

    private void selectFacilities() {
        List<Map.Entry<Integer, Double>> candidates = super.getCandidates();
        Random rand = new Random();
        double alpha = alphas[rand.nextInt(alphas.length)];
        if (alpha == -1) {
            alpha = rand.nextInt(1);
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
}
