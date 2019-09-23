'''
    We import the necessary libraries for the task.
    1. Plotting different attributes
    2. Collaborative Filtering using ALS
    3. Clustering using KMeans

    Dataset
    We use the HypeMachine User Favorites for Recommender Systems dataset from Kaggle for the purpose.
    Link: https://www.kaggle.com/skihikingkevin/hypemachine-user-favorites-for-recommender-systems
'''
import pandas as pd
import matplotlib.pyplot as plt
from pyspark import SparkConf, SparkContext
from pyspark.sql.functions import col, isnan, count, when
from pyspark.sql import SQLContext, SparkSession
from pyspark.mllib.recommendation import ALS
from pyspark.mllib.classification import LabeledPoint, LogisticRegressionWithLBFGS
from pyspark.ml.clustering import KMeans
from pyspark.ml.feature import VectorAssembler
from pyspark.ml.evaluation import ClusteringEvaluator
import numpy as np
from sklearn import preprocessing
import findspark
from sklearn.metrics import mean_squared_error 
from sklearn.model_selection import train_test_split

findspark.init("/opt/spark")

# setting up PySpark
conf = SparkConf().setAppName('MyApp').setMaster('local')
sc = SparkContext(conf=conf)
spark = SparkSession(sc)

# read the dataset
data = pd.read_csv('/Users/Janjua/Desktop/Freelance/meta_tracks.csv')

# print the rows and columns
rows, columns = data.shape
print("Number of rows: ", rows)
print("Number of columns: ", columns)

# drop the null values and print the head
data = data.dropna()
print(data.head())

# Encode the labels to numerics
labelEncoder = preprocessing.LabelEncoder()
# plot 3 columns
track_id = list(data['track_id'])
favorites = list(data['favorites'])
artist = list(data['artist'])
song = list(data['song'])
# making a copy
songc = song
artistc = artist

labelEncoder.fit(artist)
artist = labelEncoder.transform(artist)
labelEncoder.fit(song)
song = labelEncoder.transform(song)
data['artist'] = artist
data['song'] = song

#artist = [x.split(' ')[0] for x in artist]
#song = [x.split(' ')[0] for x in song]

# Plot # 01
plt.bar(track_id[:20], favorites[:20])
plt.xlabel('Track_ID')
plt.ylabel('Favorites')
plt.xticks(rotation=90)
plt.title('Track_ID vs Favorites')
plt.show()

# Plot # 02
plt.bar(songc[:20], favorites[:20])
plt.xlabel('Song')
plt.ylabel('Favorites')
plt.xticks(rotation=90)
plt.title('Song vs Favorites')
plt.show()

# Plot # 03
plt.bar(artistc[:20], favorites[:20])
plt.xlabel('Artist')
plt.ylabel('Favorites')
plt.xticks(rotation=90)
plt.title('Artist vs Favorites')
plt.show()

# Recommendation Engine
train = [(x["artist"], x["song"], x["favorites"]) for i, x in data.iterrows()]
test = [(x["artist"], x["song"]) for i, x in data.iterrows()]

# Training
xtrain = sc.parallelize(train)
model = ALS.train(xtrain, 2, seed=200)

# Testing/Predicting
xtest = sc.parallelize(test)
y_pred = model.predictAll(xtest)

preds = spark.createDataFrame(y_pred)
preds.createOrReplaceTempView("predictions")
predicted_ratings = list(preds.toPandas()["rating"])

# Evaluating using Mean Squared Error
mse = 0
for x in range(len(predicted_ratings)):
    diff = (predicted_ratings[x] - float(train[x][2])) ** 2
    mse = mse + diff
mse = mse / len(predicted_ratings)
print("MSE is: ", mse)


'''
    For the task of classification and clustering, we play around with another dataset.
    We use the Titanic dataset from the Kaggle dataset store.
'''
# read the dataset
data = pd.read_csv('/Users/Janjua/Desktop/Freelance/train.csv')

# print the rows and columns
rows, columns = data.shape
print("Number of rows: ", rows)
print("Number of columns: ", columns)

# drop the null values and print the head
data = data.dropna()
print(data.head())

x = list(data[['Pclass', '0ex', 'Age', '0ib0p', 'Parch', 'Embarked']])
y = list(data[['0urvived']])

class_names, class_counts = np.unique(list(data["0urvived"]), return_counts=True)
print(class_names)
xtrain = []
xtest = []

for idx, row in data.iterrows():
    features = [row["Pclass"], row["0ex"], row["Age"], row["0ib0p"], row["Parch"], row["Embarked"]]
    label = row["0urvived"]
    xtrain.append(LabeledPoint(label, features))
    xtest.append(features)

# Training
trainer = sc.parallelize(xtrain)
model = LogisticRegressionWithLBFGS.train(trainer, iterations=10, numClasses=len(class_names))

# Testing and predicting
tester = sc.parallelize(xtest)
y_pred = model.predict(tester)

predictions = y_pred.map(lambda x: x).collect()

# Evaluating Model using Accuracy
count = 0
for x in range(len(xtrain)):
    if int(xtrain[x].label) == int(predictions[x]):
        count += 1
print("Accuracy is: ", (count / len(xtrain)))

dataset = spark.read.csv("/Users/Janjua/Desktop/Freelance/train.csv", inferSchema=True, header=True)
dataset.na.drop()
dataset.show(5)
# Now we perform the clustering.
train_cols = ['Pclass', '0ex', '0ib0p', 'Parch']
vec_data = VectorAssembler(inputCols=train_cols, outputCol="features").transform(dataset)
# Training
model = KMeans(k=2, seed=100).fit(vec_data.select('features'))
# Testing
predicted = model.transform(vec_data)
evaluator = ClusteringEvaluator(predictionCol="prediction")
print("Clustering Accuracy is: ", evaluator.evaluate(predicted))
