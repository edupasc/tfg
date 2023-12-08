

public class Main {
    public static void main(String[] args) {
        Instance instance = new Instance("instances/att48_2_10.txt");
        Algorithm greedy = new GreedyAlg(instance);
        Solution solution = greedy.run();
        solution.printSolution();

    }
}
