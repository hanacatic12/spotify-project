import pandas as pd

# Load the dataset
data = pd.read_csv('filtered_songs_num.csv')

# Function to categorize artists based on their popularity
def categorize_artists(artists):
    # Split the artists if they are separated by commas
    artist_list = artists.split(',')
    
    # Count how many times each artist appears in the dataset
    count = sum(artist_counts.get(artist.strip(), 0) for artist in artist_list)
    
    # Categorize based on the total appearance count
    if count > 50:
        return 'Very Popular'
    elif count > 20:
        return 'Moderately Popular'
    else:
        return 'Less Popular'

# Count occurrences of each artist in the dataset
artist_counts = data['Artist'].str.split(',').explode().value_counts()

# Apply the categorization function to the dataset
data['Artist_Category'] = data['Artist'].apply(categorize_artists)

# Categorize 'Popularity' into two groups: 'Not Popular' and 'Popular'
data['Popularity_Category'] = data['Popularity'].apply(lambda x: 'N' if x < 50 else 'P')

# Select required columns: 'Popularity_Category', 'Artist_Category', and all others except 'Popularity' and 'Artist'
columns_to_keep = [col for col in data.columns if col not in ['Popularity', 'Artist']]
processed_data = data[columns_to_keep]

# Save the modified dataset to a new CSV file
processed_data.to_csv('processed_songs_final.csv', index=False)

print("File 'processed_songs_with_categories.csv' has been created with the desired format.")
