import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import classification_report, confusion_matrix, roc_auc_score
from imblearn.over_sampling import SMOTE

# Load data
df = pd.read_csv('creditcard.csv')  # from Kaggle

# Separate features and target
X = df.drop('Class', axis=1)
y = df['Class']

# Train-test split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, stratify=y, random_state=42)

# Feature scaling
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Handle imbalance with SMOTE
sm = SMOTE(random_state=42)
X_train_res, y_train_res = sm.fit_resample(X_train_scaled, y_train)

# OPTIONAL: Reduce size for quicker training during testing
X_train_res = X_train_res[:10000]
y_train_res = y_train_res[:10000]

# Train model (faster)
model = RandomForestClassifier(n_estimators=10, n_jobs=-1, random_state=42)
model.fit(X_train_res, y_train_res)

# Predict
y_pred = model.predict(X_test_scaled)

# Evaluate
print("Confusion Matrix\n",confusion_matrix(y_test, y_pred))
print("Classification Report\n",classification_report(y_test, y_pred))

print("ROC AUC Score:", roc_auc_score(y_test, model.predict_proba(X_test_scaled)[:, 1]))
