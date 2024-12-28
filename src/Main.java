import Algorithms.GRASP;
import Algorithms.GreedyAlg;
import Algorithms.RandomAlg;
import Algorithms.VNS;
import Models.Instance;
import Models.Solution;

import java.io.File;


public class Main {
    public static void main(String[] args) {
        File dir = new File("instances/");
        File[] dirList = dir.listFiles();
        double alpha = 0.75;
        int i = 0;
        for (File file : dirList){
            i++;
                Instance instance = new Instance(file.getPath());
                GreedyAlg greedyAlg = new GreedyAlg(instance);
            //for (double alpha : alphas){
                    try{
                        Solution sol = greedyAlg.run();
                        sol.print();
                    } catch (IllegalArgumentException e){
                        Solution sol = greedyAlg.run();
                        sol.print();
                    }
                }
            }
}
    //}


    /*
    public static void main(String[] args) {
        Instance instance = new Instance("instances/att48_1_40.txt");
        GRASP grasp = new GRASP(instance);
        Solution grSol = grasp.run(0, 0.75);
        System.out.println("GRASP");
        grSol.printVerbose();
        VNS vns = new VNS(instance, grSol);
        Solution sol = vns.run();
        System.out.println("\nVNS");
        sol.printVerbose();
    }
     */
//}
