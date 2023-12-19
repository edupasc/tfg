

public class Main {
    public static void main(String[] args) {
        Instance instance = new Instance("instances/att48_2_10.txt");
        Algorithm random = new RandomAlg(instance);
        Solution solution = random.run();
        System.out.println("RANDOM");
        solution.printSolution();
        Algorithm greedy = new GreedyAlg(instance);
        solution = greedy.run();
        System.out.println("\nGREEDY");
        solution.printSolution();
        Algorithm GRASP = new GRASP(instance);
        solution = GRASP.run();
        System.out.println("\nGRASP");
        solution.printSolution();

    }
}
