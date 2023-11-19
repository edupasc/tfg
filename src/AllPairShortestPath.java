public class AllPairShortestPath {

    public static void floydWarshall(double[][] matrix, int v){
        int i, j, k;

        for (k=0; k<v; k++){
            for (i=0; i<v; i++){
                for (j=0; j<v; j++){
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j]){
                        //System.out.println("Swapped distance between " + i + " and " + j + ".\nOld value: " + matrix[i][j] + " New value: " + (matrix[i][k] + matrix[k][j]));
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }
    }
}
