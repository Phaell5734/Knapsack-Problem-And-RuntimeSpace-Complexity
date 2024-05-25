package javaapplication6;

import java.io.*;

public class JavaApplication6 { //This one is for little sized datas that gives output of weight/value/and the items that are used
    public static void main(String[] args) {
        // File paths
        String inputFileName = "C:\\File\\location\\File.txt";
        String outputFileName = "C:\\File\\location\\File.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                // Read N and K
                String[] firstLine = line.split(" ");
                int N = Integer.parseInt(firstLine[0]);
                int K = Integer.parseInt(firstLine[1]);

                int[] values = new int[N];
                int[] weights = new int[N];

                for (int i = 0; i < N; i++) {
                    line = reader.readLine();
                    if (line == null) {  // Check if the line is null to avoid NullPointerException
                        break;
                    }
                    String[] parts = line.trim().split("\\s+");
                    values[i] = Integer.parseInt(parts[0]);
                    weights[i] = Integer.parseInt(parts[1]);
                }

                // Solve the knapsack problem
                int[] results = knapsack(N, K, values, weights);
                System.out.println("Total value: " + results[0]);
                writer.write("Total value: " + results[0] + "\nItems taken (1: selected, 0: not selected):\n");

                int totalWeight = 0;
                System.out.print("Items taken: ");
                for (int i = 1; i < results.length; i++) {
                    System.out.print(results[i] + " ");
                    writer.write(results[i] + " ");
                    if (results[i] == 1) {
                        totalWeight += weights[i - 1];
                    }
                }
                System.out.println("\nTotal weight of selected items: " + totalWeight);
                writer.write("\nTotal weight of selected items: " + totalWeight + "\n");
                System.out.println("---");
                writer.write("---\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static int[] knapsack(int N, int K, int[] values, int[] weights) {
        int[][] dp = new int[N + 1][K + 1];

        // Build the DP table
        for (int i = 1; i <= N; i++) {
            for (int w = 1; w <= K; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], values[i - 1] + dp[i - 1][w - weights[i - 1]]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Trace back to find the items included
        int[] result = new int[N + 1];
        int totalValue = dp[N][K];
        result[0] = totalValue;

        int w = K;
        for (int i = N; i > 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {
                result[i] = 1; // Item i-1 is included
                w -= weights[i-1];
            } else {
                result[i] = 0; // Item i-1 is not included
            }
        }

        return result;
    }
}
