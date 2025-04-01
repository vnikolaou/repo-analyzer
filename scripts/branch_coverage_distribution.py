import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler
import seaborn as sns

# Load the CSV file
df = pd.read_csv("repositories.csv")

# Ensure 'chosen' is treated as boolean
df["chosen"] = df["chosen"].astype(bool)

# Convert 'pushed_at' to datetime format
df["pushed_at"] = pd.to_datetime(df["pushed_at"], errors="coerce", utc=True)

# Exclude rows with pushed_at in 2025
df = df[df["pushed_at"].dt.year < 2025]

# Filter for chosen repositories
df_chosen = df[df["chosen"]].copy()

# Extract the year from 'pushed_at'
df_chosen["year"] = df_chosen["pushed_at"].dt.year

# Ensure 'failed' is boolean (if exists)
if "failed" in df_chosen.columns:
    df_chosen["failed"] = df_chosen["failed"].astype(bool)
else:
    df_chosen["failed"] = False  # Assume all succeeded if the column is missing

# Assign coverage = 0 to chosen repositories that have not failed and have null coverage
df_chosen.loc[(~df_chosen["failed"]) & (df_chosen["coverage"].isna()), "coverage"] = 0

# Identify successful repositories (failed=False) with coverage ≥ 0
df_chosen["successful"] = (~df_chosen["failed"]) & (df_chosen["coverage"].notna())

# Apply log transformation where necessary
df_transformed = df_chosen.copy()
for col in ['size', 'stars', 'forks', 'watchers', 'commits', 'contributors', 'complexity']:
    if col in df_transformed.columns:
        df_transformed[col] = np.log1p(df_transformed[col])

# Apply Min-Max scaling to coverage
scaler = MinMaxScaler()
df_transformed["coverage"] = scaler.fit_transform(df_transformed[["coverage"]])

# Filter for successful repositories only
df_successful = df_transformed[df_transformed["successful"]].copy()

# Prepare datasets
df_all = df_successful.copy()
df_nonzero = df_successful[df_successful["coverage"] > 0].copy()

# Create side-by-side boxplots
fig, axes = plt.subplots(1, 2, figsize=(14, 6), sharey=True)

# Boxplot 1: All successful repositories
sns.boxplot(data=df_all, x="year", y="coverage", ax=axes[0], color="#1f77b4", showmeans=True,
            meanprops={"marker": "o", "markerfacecolor": "red", "markeredgecolor": "black"})
axes[0].set_title("Coverage Distribution (All Successful Repositories)")
axes[0].set_xlabel("Year")
axes[0].set_ylabel("Normalized Branch Coverage")
axes[0].grid(axis="y", linestyle="--", alpha=0.7)

# Boxplot 2: Repositories with Coverage > 0
sns.boxplot(data=df_nonzero, x="year", y="coverage", ax=axes[1], color="#2ca02c", showmeans=True,
            meanprops={"marker": "o", "markerfacecolor": "red", "markeredgecolor": "black"})
axes[1].set_title("Coverage Distribution (Coverage > 0 Only)")
axes[1].set_xlabel("Year")
axes[1].set_ylabel("")  # Avoid repeating y-axis label
axes[1].grid(axis="y", linestyle="--", alpha=0.7)

plt.suptitle("Comparison of Normalized Branch Coverage Distributions (2020–2024)", fontsize=14)
plt.tight_layout(rect=[0, 0.03, 1, 0.95])
plt.show()
