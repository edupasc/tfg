import Algorithms.GRASP;
import Algorithms.GreedyAlg;
import Algorithms.RandomAlg;
import Models.Instance;
import Models.Solution;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Instance instance = new Instance("instances/rat575_3_50.txt");
        /*RandomAlg rn = new RandomAlg(instance);
        Solution rnSol = rn.run();
        System.out.println("RANDOM\n");
        rnSol.printSolution();
        GreedyAlg gr = new GreedyAlg(instance);
        Solution grSol = gr.run();
        System.out.println("GREEDY\n");
        grSol.printSolution();
        Solution grSOL = grasp.run();
        System.out.println("\nGRASP w/o local search");
        grSOL.printSolution();
        Solution fiSol = grasp.run(0);
        System.out.println("GRASP w/ FI");
        fiSol.printSolution();*/
        GRASP grasp = new GRASP(instance);
        Solution biSol = grasp.run(1);
        //System.out.println("GRASP w/ BI");
        //biSol.printSolution();

    }
}
