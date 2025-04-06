import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from scipy.stats import skew

# Set pandas options to display full output
pd.set_option('display.max_rows', None)
pd.set_option('display.max_columns', None)
pd.set_option('display.width', 1200)
pd.set_option('display.max_colwidth', None)

# Load the CSV file
df = pd.read_csv('repositories.csv', parse_dates=['pushed_at'])

# Replace NaN with zero where appropriate
df.fillna({
    'size': 0, 'stars': 0, 'forks': 0, 'watchers': 0,
    'commits': 0, 'contributors': 0, 'complexity': 0
}, inplace=True)

# For coverage, exclude NaN and zero from statistics
df_filtered = df[df['coverage'] > 0]

# Select relevant numerical columns
num_cols = ['size', 'stars', 'forks', 'watchers', 'commits', 'contributors', 'complexity', 'coverage']

# Compute and display summary statistics
summary_stats = df_filtered[num_cols].describe()
print("Summary Statistics for All Attributes:")
print(summary_stats)

# Compute and display skewness
skewness = df_filtered[num_cols].apply(skew, nan_policy='omit')
print("\nSkewness of Attributes:")
print(skewness)

# Compute and display percentiles
percentiles = df_filtered[num_cols].quantile([0.01, 0.25, 0.50, 0.75, 0.99])
print("\nPercentile Analysis (1%, 25%, 50%, 75%, 99%):")
print(percentiles)

# Plot histograms with log1p(x), excluding zeros, 2 per row
rows = int(np.ceil(len(num_cols) / 2))
plt.figure(figsize=(14, rows * 4))
for i, col in enumerate(num_cols, 1):
    plt.subplot(rows, 2, i)
    non_zero = df[col][df[col] > 0].dropna()
    transformed_data = np.log1p(non_zero)
    sns.histplot(transformed_data, bins=50, kde=True)
    plt.title(f"log1p({col}) - Zero Excluded")
    plt.xlabel(f"log1p({col})")
    plt.ylabel("Count")

plt.tight_layout()
plt.show()
