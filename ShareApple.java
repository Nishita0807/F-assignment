import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ShareApple {

    static class Person {
        String name;
        int weightShare;
        List<Integer> apples;

        Person(String name, int weightShare) {
            this.name = name;
            this.weightShare = weightShare;
            this.apples = new ArrayList<>();
        }

        void addApple(int weight) {
            apples.add(weight);
        }

        void printApples() {
            System.out.print(name + ": ");
            for (int weight : apples) {
                System.out.print(weight + " ");
            }
            System.out.println();
        }

        int getTotalWeight() {
            return apples.stream().mapToInt(Integer::intValue).sum();
        }

        int getAppleCount() {
            return apples.size();
        }
    }

    static void distributeApples(int[] arr, int ramShare, int shamShare, int rahimShare) {
        List<Integer> appleWeights = new ArrayList<>();
        for (int weight : arr) {
            appleWeights.add(weight);
        }
        Collections.sort(appleWeights, Collections.reverseOrder());

        Person ram = new Person("Ram", ramShare);
        Person sham = new Person("Sham", shamShare);
        Person rahim = new Person("Rahim", rahimShare);

        for (int weight : appleWeights) {
            if (ram.getTotalWeight() + weight <= ram.weightShare) {
                ram.addApple(weight);
            } else if (sham.getTotalWeight() + weight <= sham.weightShare) {
                sham.addApple(weight);
            } else {
                rahim.addApple(weight);
            }
        }

        ram.printApples();
        sham.printApples();
        rahim.printApples();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> appleWeights = new ArrayList<>();

        System.out.println("Enter apple weight in grams (-1 to stop):");
        while (true) {
            int weight = scanner.nextInt();
            if (weight == -1) {
                break;
            }
            appleWeights.add(weight);
        }

        int[] arr = appleWeights.stream().mapToInt(i -> i).toArray();
        int total = 0;
        for (int weight : arr) {
            total += weight;
        }

        // Share of total weight based on proportions
        int ramShare = total * 50 / 100; // Ram's share: 50% of total weight
        int shamShare = total * 30 / 100; // Sham's share: 30% of total weight
        int rahimShare = total - (ramShare + shamShare); // Rahim's share: remaining weight

        // Distribute the apples
        distributeApples(arr, ramShare, shamShare, rahimShare);
        scanner.close();
    }
}
