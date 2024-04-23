import Algorithms.GRASP;
import Algorithms.GreedyAlg;
import Algorithms.RandomAlg;
import Models.Instance;
import Models.Solution;


public class Main {
    public static void main(String[] args) {
        Instance instance = new Instance("instances/att48_2_20.txt");
        RandomAlg random = new RandomAlg(instance);
        Solution rnSol = random.run();
        System.out.println("RANDOM");
        rnSol.printSolution();
        //GreedyAlg gr = new GreedyAlg(instance);
       // Solution grSol = gr.run();
        //System.out.println("\nGREEDY");
        //grSol.printSolution();
        GRASP grasp = new GRASP(instance);
        Solution graspSolution = grasp.run();
        System.out.println("\nGRASP w/o improv");
        graspSolution.printSolution();
        Solution graspSolutionFI = grasp.run(0);
        System.out.println("\nGRASP FI");
        graspSolutionFI.printSolution();
        Solution graspSolutionBI = grasp.run(1);
        System.out.println("\nGRASP BI");
        graspSolutionBI.printSolution();

    }
}
