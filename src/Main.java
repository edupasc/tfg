import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input filepath: \n");
        String filepath = sc.nextLine();
        Instance instance = new Instance(filepath);
    }
}
