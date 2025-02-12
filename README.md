# Spotify Top 50 Songs Analysis

## Overview
This project analyzes Spotify's Top 50 Songs playlist data using the Decision Tree Classification model to understand what makes a song popular. The analysis includes various song attributes such as danceability, energy, key, loudness, and other musical features that Spotify provides through its API.

## Dataset
The dataset contains information about songs that have appeared in Spotify's Top 50 playlists, including:
- Musical features (danceability, energy, key, loudness, etc.)
- Track information (name, artist, duration)
- Popularity metrics
- Audio characteristics (tempo, valence, instrumentalness)
The dataset was sourced from [Kaggle](https://www.kaggle.com/datasets/asaniczka/top-spotify-songs-in-73-countries-daily-updated/data).

## Features
- Implementation of Decision Tree model for song popularity prediction
- Analysis of musical features that contribute to a song's success
- Data preprocessing and feature engineering
- Model evaluation and performance metrics

## Requirements
- Python 3.x
- Required Python packages:
  - scikit-learn
  - pandas
  - numpy
  - matplotlib
  - seaborn
- Dataset from [Kaggle](https://www.kaggle.com/datasets/asaniczka/top-spotify-songs-in-73-countries-daily-updated/data)

## Model Performance
The Decision Tree model analyzes various musical features to predict and understand what characteristics make songs popular in Spotify's Top 50 playlists. It performs with accuracy above 60%, due to the nature of the dataset. There is an imbalance in the classes, which can be addressed in the future. However, we have also concluded that song popularity also depends on other factors, such as marketing and promotion, which are not recorded in the dataset.

## Conclusion
There are many factors influencing a songs popularity. The model reveals the factors that influence the most:
- danceability, loudness, and tempo: significantly contribute to a song's popularity
- explicit content and mode: have a minor influence
- valence: songs with high valence tend to be more popular
