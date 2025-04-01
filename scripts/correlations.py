import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler

# Load the dataset
df = pd.read_csv("repositories.csv")

# Data preparation
df["chosen"] = df["chosen"].astype(bool)
df["pushed_at"] = pd.to_datetime(df["pushed_at"], errors="coerce", utc=True)
df = df[df["pushed_at"].dt.year < 2025]
df_chosen = df[df["chosen"]].copy()
df_chosen["year"] = df_chosen["pushed_at"].dt.year

# Handle 'failed' column
if "failed" in df_chosen.columns:
    df_chosen["failed"] = df_chosen["failed"].astype(bool)
else:
    df_chosen["failed"] = False

# Assign coverage = 0 where appropriate
df_chosen.loc[(~df_chosen["failed"]) & (df_chosen["coverage"].isna()), "coverage"] = 0
df_chosen["successful"] = (~df_chosen["failed"]) & (df_chosen["coverage"].notna())

# Filter to successful repositories
df_transformed = df_chosen[df_chosen["successful"]].copy()

# Apply log1p transformation to skewed fields
for col in ['size', 'stars', 'forks', 'watchers', 'commits', 'contributors', 'complexity']:
    if col in df_transformed.columns:
        df_transformed[col] = np.log1p(df_transformed[col])

# Normalize coverage
scaler = MinMaxScaler()
df_transformed["coverage"] = scaler.fit_transform(df_transformed[["coverage"]])

# Select relevant numeric fields
correlation_fields = ['coverage', 'size', 'stars', 'forks', 'watchers', 'commits', 'contributors', 'complexity']
df_corr = df_transformed[correlation_fields].dropna()

# Compute correlation matrices
pearson_matrix = df_corr.corr(method='pearson')
spearman_matrix = df_corr.corr(method='spearman')

# Plot both heatmaps side by side
fig, axes = plt.subplots(1, 2, figsize=(18, 7))

# Pearson
sns.heatmap(pearson_matrix, annot=True, cmap="coolwarm", fmt=".2f",
            linewidths=0.5, ax=axes[0])
axes[0].set_title("Pearson Correlation: Branch Coverage vs Project Characteristics")

# Spearman
sns.heatmap(spearman_matrix, annot=True, cmap="coolwarm", fmt=".2f",
            linewidths=0.5, ax=axes[1])
axes[1].set_title("Spearman Correlation: Branch Coverage vs Project Characteristics")

plt.tight_layout()
plt.show()
