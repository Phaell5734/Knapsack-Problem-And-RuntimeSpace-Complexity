import pandas as pd
import matplotlib.pyplot as plt
import os

# Define the file path
file_path = r'C:\File\location\output_data.csv'

# Check if the file exists
if not os.path.exists(file_path):
    print("File not found: ", file_path)
else:
    # Load the CSV file
    df = pd.read_csv(file_path)

    # Plotting runtime complexity
    plt.figure(figsize=(10, 6))
    plt.scatter(df['N'], df['Time(ms)'], color='blue', label='Runtime vs N')
    plt.title('Runtime Complexity of Knapsack Solution')
    plt.xlabel('Number of Items (N)')
    plt.ylabel('Execution Time (ms)')
    plt.legend()
    plt.grid(True)
    plt.show()

    # Plotting space complexity if applicable
    plt.figure(figsize=(10, 6))
    plt.scatter(df['K'], df['Total Value'], color='green', label='Space vs K')
    plt.title('Space Complexity of Knapsack Solution')
    plt.xlabel('Knapsack Capacity (K)')
    plt.ylabel('Total Value (Assuming Space Metric)')
    plt.legend()
    plt.grid(True)
    plt.show()
