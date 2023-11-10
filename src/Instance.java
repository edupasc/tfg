import java.io.*;
import java.util.List;
import java.util.Locale;
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
            sc.useLocale(Locale.US);
            this.nNodes = sc.nextInt();
            this.nEdges = sc.nextInt();
            this.nFacilities = sc.nextInt();
            this.distanceMatrix = new double[this.nNodes][this.nNodes];
            for(int i = 0; i<nEdges; i++){
                int a = sc.nextInt();
                int b = sc.nextInt();
                double c = sc.nextDouble();
                this.distanceMatrix[a][b]=c;
                this.distanceMatrix[b][a]=c;
            }
            System.out.println("done");

        } catch (FileNotFoundException e) {
            System.out.println("Invalid file");
            e.printStackTrace();
        }
    }
}
