package javaapplication5;

import java.io.*;
import java.util.Random;

public class JavaApplication5 { //This one is for Runtime&Space & This output_data.csv is used in python code
    public static void main(String[] args) {
        String baseFilePath = "C:\\File\\location\\";
        String[] fileNames = {"File1", "File2", "File3", "File4", "File5"};
        String outputFileName = baseFilePath + "output_data.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            // Write the header for CSV file
            writer.write("N,K,Total Value,Time(ms)\n");

            for (String fileName : fileNames) {
                String inputFileName = baseFilePath + fileName + ".txt";
                processDataset(inputFileName, writer);
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private static void processDataset(String inputFileName, BufferedWriter writer) throws IOException {
        Random rand = new Random();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            // Read the first line to get N and K
            String firstLine = reader.readLine();
            String[] parts = firstLine.split(" ");
            int N = Integer.parseInt(parts[0]);
            int K = Integer.parseInt(parts[1]);

            int[] values = new int[N];
            int[] weights = new int[N];

            for (int i = 0; i < N; i++) {
                values[i] = rand.nextInt(100) + 1;  // Values between 1 and 100
                weights[i] = rand.nextInt(Math.max(1, K)) + 1;  // Ensure weights are positive
            }

            // Measure time taken to solve the knapsack problem
            long startTime = System.nanoTime();
            int totalValue = knapsack(N, K, values, weights);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000;  // Convert to milliseconds

            // Write data to CSV
            writer.write(N + "," + K + "," + totalValue + "," + duration + "\n");
            System.out.println("Processed N=" + N + " K=" + K + " Time (ms)=" + duration);
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        }
    }

    private static int knapsack(int N, int K, int[] values, int[] weights) {
        int[] dp = new int[K + 1];
        for (int i = 0; i < N; i++) {
            for (int w = K; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
        return dp[K];
    }
}
