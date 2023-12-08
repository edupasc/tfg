

public class Main {
    public static void main(String[] args) {
        Instance instance = new Instance("instances/att48_2_40.txt");
        Algorithm random = new RandomAlg(instance);
        Solution solution = random.run();
        solution.printSolution();

    }
}
