

public class Main {
    public static void main(String[] args) {
        Instance instance = new Instance("instances/att48_1_10TEST.txt");
        double matrix[][] = instance.getDistanceMatrix();
        AllPairShortestPath.floydWarshall(matrix, instance.getnNodes());
    }
}
