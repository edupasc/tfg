import Algorithms.GRASP;
import Algorithms.GreedyAlg;
import Algorithms.RandomAlg;
import Models.Instance;
import Models.Solution;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Instance instance = new Instance("instances/ch150_2_10.txt");
        RandomAlg rn = new RandomAlg(instance);
        Solution rnSol = rn.run();
        System.out.println("\nRANDOM");
        rnSol.printSolution();
        GreedyAlg gr = new GreedyAlg(instance);
        Solution grSol = gr.run();
        System.out.println("\nGREEDY");
        grSol.printSolution();
        GRASP grasp = new GRASP(instance);
        Solution grSOL = grasp.run();
        System.out.println("\nGRASP w/o local search");
        grSOL.printSolution();
        Solution fiSol = grasp.run(0);
        System.out.println("\nGRASP w/ FI");
        fiSol.printSolution();
        Solution biSol = grasp.run(1);
        System.out.println("\nGRASP w/ BI");
        biSol.printSolution();
    }
}
