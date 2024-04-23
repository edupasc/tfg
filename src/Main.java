import Algorithms.GRASP;
import Algorithms.GreedyAlg;
import Algorithms.RandomAlg;
import Models.Instance;
import Models.Solution;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Instance instance = new Instance("instances/att48_2_20.txt");
        GRASP grasp = new GRASP(instance);
        Solution graspSolution = grasp.run(1);
        graspSolution.printSolution();

    }
}
