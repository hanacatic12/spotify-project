import pandas as pd
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix

def load_data(file_path):
    try:
        return pd.read_csv(file_path)
    except FileNotFoundError:
        print(f"Error: File '{file_path}' not found.")
        exit()


def preprocess_data(data):
    data = data.loc[:, ~data.columns.str.contains('Song ID|Song Name')]
    data['Popularity_Category'] = data['Popularity'].apply(lambda x: 'Not Popular' if x < 50 else 'Popular')
    X = pd.get_dummies(data.drop(columns=['Popularity', 'Popularity_Category']))
    y = data['Popularity_Category']
    return X, y

def split_data(X, y):
    X_temp, X_test, y_temp, y_test = train_test_split(X, y, test_size=0.15, random_state=42)
    X_train, X_val, y_train, y_val = train_test_split(X_temp, y_temp, test_size=0.1765, random_state=42)
    return X_train, X_val, X_test, y_train, y_val, y_test

def train_and_evaluate_model(X_train, X_val, y_train, y_val, X_test, y_test):
    model = DecisionTreeClassifier(random_state=42)
    cv_scores = cross_val_score(model, X_train, y_train, cv=5, scoring='accuracy')
    print(f"Cross-validation scores: {cv_scores}")
    print(f"Mean cross-validation accuracy: {cv_scores.mean()}")
    
    model.fit(X_train, y_train)
    print(f"Validation Accuracy: {accuracy_score(y_val, model.predict(X_val))}")
    print("Classification Report on Validation Set:")
    print(classification_report(y_val, model.predict(X_val)))
    
    y_test_pred = model.predict(X_test)
    print(f"Test Accuracy: {accuracy_score(y_test, y_test_pred)}")
    print("Confusion Matrix on Test Set:")
    print(confusion_matrix(y_test, y_test_pred))
    print("Classification Report on Test Set:")
    print(classification_report(y_test, y_test_pred))

if __name__ == "__main__":
    data = load_data('filtered_songs.csv')
    X, y = preprocess_data(data)
    X_train, X_val, X_test, y_train, y_val, y_test = split_data(X, y)
    train_and_evaluate_model(X_train, X_val, y_train, y_val, X_test, y_test)
