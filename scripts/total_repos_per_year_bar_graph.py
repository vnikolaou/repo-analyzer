#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

# Load the CSV file
df = pd.read_csv('repository_csv.csv')

# Ensure 'chosen' is treated as boolean
df['chosen'] = df['chosen'].astype(bool)

# Convert 'pushed_at' to datetime format
df['pushed_at'] = pd.to_datetime(df['pushed_at'], errors='coerce', utc=True)

# Exclude rows with pushed_at in 2025
df = df[df['pushed_at'].dt.year < 2025]

# Filter for chosen repositories
df_chosen = df[df['chosen']]

# Extract the year from 'pushed_at'
df_chosen['year'] = df_chosen['pushed_at'].dt.year

# Count the number of chosen repositories per year
yearly_counts = df_chosen['year'].value_counts().sort_index()

print(yearly_counts.sum())

# Bar Plot
plt.figure(figsize=(10, 6))
sns.barplot(x=yearly_counts.index, y=yearly_counts.values, palette="Set2")

# Customize

plt.title('Number of Chosen Repositories Per Year', fontsize=14)
plt.xlabel('Year', fontsize=12)
plt.ylabel('Number of Repositories', fontsize=12)
plt.xticks(rotation=45)
plt.grid(axis='y', linestyle='--', alpha=0.7)

# Show the plot
plt.show()