import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler

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
    df_chosen["failed"] = False

# Assign coverage = 0 to chosen repositories that have not failed and have null coverage
df_chosen.loc[(~df_chosen["failed"]) & (df_chosen["coverage"].isna()), "coverage"] = 0

# Identify successful repositories
df_chosen["successful"] = (~df_chosen["failed"]) & (df_chosen["coverage"].notna())

# Apply log transformation where necessary
df_transformed = df_chosen.copy()
for col in ['size', 'stars', 'forks', 'watchers', 'commits', 'contributors', 'complexity']:
    if col in df_transformed.columns:
        df_transformed[col] = np.log1p(df_transformed[col])

# Apply Min-Max scaling to coverage
scaler = MinMaxScaler()
df_transformed["coverage"] = scaler.fit_transform(df_transformed[["coverage"]])

# Count total processed vs. successful repositories per year
success_counts = df_transformed.groupby("year")["successful"].value_counts().unstack()

# Calculate percentage of successful repositories
success_percentage = (success_counts[True] / success_counts.sum(axis=1)) * 100

# Filter only successfully analyzed repositories
df_successful = df_transformed[df_transformed["successful"]].copy()

# Categorize repositories: Zero vs. Nonzero coverage
df_successful["has_coverage"] = df_successful["coverage"] > 0

# Count repositories per year by coverage category
coverage_counts = df_successful.groupby("year")["has_coverage"].value_counts().unstack()

# Compute percentage of repositories with nonzero coverage
coverage_percentage = (
    (coverage_counts[True] / (coverage_counts[True] + coverage_counts[False])) * 100
).fillna(0)

# Create updated figure layout with three plots in 2x2 layout (last cell empty)
fig, axes = plt.subplots(2, 2, figsize=(14, 12))

# 1st Plot: Total vs. Successful Repositories
ax1 = success_counts.plot(kind="bar", stacked=True, color=["#1f77b4", "#2ca02c"], ax=axes[0, 0])
ax1.set_xlabel("Year")
ax1.set_ylabel("Number of Repositories")
ax1.set_title("Successfully vs. Failed Analyzed Repositories")
ax1.legend(["Failed", "Successful"])
ax1.grid(axis="y", linestyle="--", alpha=0.7)
for container in ax1.containers:
    ax1.bar_label(container, label_type="center", fontsize=10, color="blue", fmt="%d")

# 2nd Plot: Percentage of Successful Repositories
ax2 = success_percentage.plot(kind="bar", color="#2ca02c", ax=axes[0, 1])
ax2.set_xlabel("Year")
ax2.set_ylabel("Percentage (%)")
ax2.set_title("Percentage of Successfully Analyzed Repositories")
ax2.grid(axis="y", linestyle="--", alpha=0.7)
for container in ax2.containers:
    ax2.bar_label(container, label_type="edge", fontsize=10, color="blue", fmt="%.1f%%")

# 3rd Plot: Zero vs. Nonzero Coverage
ax3 = coverage_counts.plot(kind="bar", stacked=True, color=["#1f77b4", "#ff7f0e"], ax=axes[1, 0])
ax3.set_xlabel("Year")
ax3.set_ylabel("Number of Repositories")
ax3.set_title("Repositories with Zero vs. Nonzero Coverage")
ax3.legend(["Zero Coverage", "Nonzero Coverage"])
ax3.grid(axis="y", linestyle="--", alpha=0.7)
for container in ax3.containers:
    ax3.bar_label(container, label_type="center", fontsize=10, color="blue", fmt="%d")

# 4th Plot: Percentage with Nonzero Coverage
ax4 = coverage_percentage.plot(kind="bar", color="#ff7f0e", ax=axes[1, 1])
ax4.set_xlabel("Year")
ax4.set_ylabel("Percentage (%)")
ax4.set_title("Percentage of Repositories with Nonzero Coverage")
ax4.grid(axis="y", linestyle="--", alpha=0.7)
for container in ax4.containers:
    ax4.bar_label(container, label_type="edge", fontsize=10, color="blue", fmt="%.1f%%")

plt.tight_layout()
plt.show()

