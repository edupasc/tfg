package Models;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instance implements Cloneable{
    private int nNodes;
    private int nEdges;
    private int nFacilities;
    private double[][] distanceMatrix;
    private int alpha;
    public static double INF = Double.MAX_VALUE;
    private String filepath;

    public Instance(String filepath) {
        this.filepath = filepath;
        this.setAlpha(filepath);
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
            // initialize all distances to infinite (or 0 if origin = destination)
            for (int i = 0; i < this.nNodes; i++){
                for (int j = 0; j < this.nNodes; j++){
                    if (i == j){
                        this.distanceMatrix[i][j] = 0;
                    } else{
                        this.distanceMatrix[i][j] = INF;
                    }
                }
            }
            // assumes non-directed graph
            for(int i = 0; i<nEdges; i++){
                int a = sc.nextInt();
                int b = sc.nextInt();
                double c = sc.nextDouble();
                this.distanceMatrix[a][b]=c;
                this.distanceMatrix[b][a]=c;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Invalid file");
            e.printStackTrace();
        }
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public int getnNodes() {
        return nNodes;
    }


    public int getnFacilities() {
        return nFacilities;
    }

    public void floydWarshall(){
        int i, j, k;

        // for all nodes i, j,k, checks if the distance from i to j is shorter passing through k
        for (k=0; k<this.nNodes; k++){
            for (i=0; i<this.nNodes; i++){
                for (j=0; j<this.nNodes; j++){
                    if (this.distanceMatrix[i][k] + this.distanceMatrix[k][j] < this.distanceMatrix[i][j]){
                        //System.out.println("Swapped distance between " + i + " and " + j + ".\nOld value: " + this.distanceMatrix[i][j] + " New value: " + (this.distanceMatrix[i][k] + this.distanceMatrix[k][j]));
                        this.distanceMatrix[i][j] = this.distanceMatrix[i][k] + this.distanceMatrix[k][j];
                    }
                }
            }
        }
    }

    private void setAlpha(String filename){
        Pattern pattern = Pattern.compile("_(\\d)_");
        Matcher matcher = pattern.matcher(filename);
        if (matcher.find()){
            this.alpha = Integer.parseInt(matcher.group(1));
        } else {
            System.out.println("Assuming alpha = 1");
            this.alpha = 1;
        }

    }

    public int getAlpha() {
        return alpha;
    }

    public double getDistance(int a, int b){
        return this.distanceMatrix[a][b];
    }

    public String getFilepath() {
        return filepath;
    }

    public Object clone() throws CloneNotSupportedException{
        Instance cloned = (Instance) super.clone();
        cloned.distanceMatrix = new double[this.distanceMatrix.length][];
        for (int i = 0; i < this.distanceMatrix.length; i++) {
            cloned.distanceMatrix[i] = Arrays.copyOf(this.distanceMatrix[i], this.distanceMatrix[i].length);
        }
        return cloned;
    }
}
