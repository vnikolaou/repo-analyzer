import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from scipy.stats import skew

# Set pandas options to display full output
pd.set_option('display.max_rows', None)  # Show all rows
pd.set_option('display.max_columns', None)  # Show all columns
pd.set_option('display.width', 1200)  # Auto-adjust width to fit content
pd.set_option('display.max_colwidth', None)  # Show full column content

# Load the CSV file
df = pd.read_csv('repositories.csv', parse_dates=['pushed_at'])


# Replace NaN with zero for attributes where zero makes sense
df.fillna({'size': 0, 'stars': 0, 'forks': 0, 'watchers': 0, 'commits': 0, 'contributors': 0, 'complexity': 0}, inplace=True)

# For coverage, exclude NaN and zero from statistics since they are non-informative
df_filtered = df[df['coverage'] > 0]

# Select relevant numerical columns
num_cols = ['size', 'stars', 'forks', 'watchers', 'commits', 'contributors', 'complexity', 'coverage']

# Compute summary statistics
summary_stats = df_filtered[num_cols].describe()
print("Summary Statistics for All Attributes:")
print(summary_stats)

# Compute skewness for each attribute
skewness = df_filtered[num_cols].apply(skew, nan_policy='omit')
print("\nSkewness of Attributes:")
print(skewness)

# Percentile analysis
percentiles = df_filtered[num_cols].quantile([0.01, 0.25, 0.50, 0.75, 0.99])
print("\nPercentile Analysis (1%, 25%, 50%, 75%, 99%):")
print(percentiles)

# Plot histograms to visualize distributions
plt.figure(figsize=(12, 12))  # Adjust height for more rows
for i, col in enumerate(num_cols, 1):
    plt.subplot(4, 2, i)  # 4 rows, 2 columns
    sns.histplot(df[col].dropna(), bins=50, kde=True)
    plt.title(col)
plt.tight_layout()
plt.show()


