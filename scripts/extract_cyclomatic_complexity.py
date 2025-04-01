import pandas as pd

# Load class.csv
df = pd.read_csv("class.csv")

# Select important columns (adjust based on what you need)
important_columns = ["class", "wmc", "loc", "cbo", "rfc", "lcom"]

# Print the first 10 rows of selected columns
print(df[important_columns].head(10))

# Sum the Weighted Methods per Class (wmc)
total_cyclomatic_complexity = df["wmc"].sum()

# Print the result
print(f"Total Cyclomatic Complexity (WMC) for the project: {total_cyclomatic_complexity}")