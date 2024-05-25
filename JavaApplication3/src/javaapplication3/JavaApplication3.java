package javaapplication3;

import java.io.*;

public class JavaApplication3 { //This one is uses 1 array on DP table so it takes less space.Allowing bigger datas to be processed.
    public static void main(String[] args) {
        // File paths
        String inputFileName = "C:\\File\\location\\File.txt";
        String outputFileName = "C:\\File\\location\\File.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            String line;
            if ((line = reader.readLine()) != null && !line.isEmpty()) {
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
                int totalValue = knapsack(N, K, values, weights);
                System.out.println("Total value: " + totalValue);
                writer.write("Total value: " + totalValue + "\n");
                System.out.println("---");
                writer.write("---\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static int knapsack(int N, int K, int[] values, int[] weights) {
        int[] dp = new int[K + 1];

        // Build the DP table using only one array
        for (int i = 0; i < N; i++) {
            for (int w = K; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }

        return dp[K];
    }
}
