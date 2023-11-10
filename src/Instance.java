import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Instance {
    private int nNodes;
    private int nEdges;
    private int nFacilities;
    private double[][] distanceMatrix;

    public Instance(String filepath) {
        this.loadFiles(filepath);
    }

    public void loadFiles(String filepath){
        File file = new File(filepath);
        try {
            Scanner sc = new Scanner(file);
            this.nNodes = sc.nextInt();
            this.nEdges = sc.nextInt();
            this.nFacilities = sc.nextInt();
            this.distanceMatrix = new double[this.nNodes][this.nNodes];
            while (sc.hasNextLine()){
                int origin = sc.nextInt();
                int dest = sc.nextInt();
                double distance = sc.nextDouble();
                System.out.println(origin + " " + dest + " " + distance);
//                this.distanceMatrix[sc.nextInt()][sc.nextInt()]=sc.nextLong();
            }
            System.out.println("done");

        } catch (FileNotFoundException e) {
            System.out.println("Invalid file");
            e.printStackTrace();
        }
    }
}
