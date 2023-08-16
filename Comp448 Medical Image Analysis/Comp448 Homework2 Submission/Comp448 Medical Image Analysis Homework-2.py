#!/usr/bin/env python
# coding: utf-8

# In[1]:


import numpy as np
import cv2
from sklearn.cluster import KMeans
import os
import cv2
from skimage.transform import resize
from skimage.filters import gabor
import re
from sklearn.metrics import silhouette_score
from sklearn.metrics import accuracy_score
import copy
import re
import matplotlib.pyplot as plt
import collections
import numpy as np
from numpy import ma 
import math
from sklearn.cluster import KMeans
from sklearn.metrics import accuracy_score, confusion_matrix
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
import matplotlib.cm as cm
from sklearn.impute import SimpleImputer
from copy import deepcopy
import matplotlib.pyplot as plt

import copy
import math
import numpy as np

def calculateGaborResp(patch):
    gabor_responses = []
    for theta in range(5):  ##### orientation #####
        for sigma in (1, 3):  ###### scale ########
            gabor_response = np.abs(gabor(patch, frequency = 0.70, theta = theta * np.pi / 4, sigma_x = sigma, sigma_y = sigma)[0])
            gabor_responses.append(gabor_response)
    
    return np.array(gabor_responses)

def calculateAccumulatedGaborResponse(patch, binNumber, d):
    accumulated_gabor_response = np.zeros((binNumber, binNumber))
    d_offsets = [(d, 0), (d, d), (0, d), (-d, d), (-d, 0), (-d, -d), (0, -d), (d, -d)]
    for di, dj in d_offsets:
        shifted_patch = np.roll(patch, (di, dj), axis=(0, 1))
        gabor_response = calculateGaborResp(shifted_patch)
        gabor_response_resized = resize(gabor_response, accumulated_gabor_response.shape, mode='constant')
        accumulated_gabor_response += np.sum(gabor_response_resized)
    
    return accumulated_gabor_response


def calculateCooccurrenceMatrix(patch, binNumber, di, dj):
    height, width = patch.shape
    coooccurence_matrix = np.zeros((binNumber, binNumber), dtype=int)
    bins_of_pixels = np.floor_divide(patch, 256 / binNumber)
    for a in range(binNumber):
        for b in range(binNumber):
            for c in range(height):
                for x in range(width):
                    if (x - dj >= 0) and (x - dj < width) and (c - di >= 0) and (c - di < height):
                        if (bins_of_pixels[c, x] == a) and (bins_of_pixels[c - di, x - dj] == b):
                            coooccurence_matrix[a, b] += 1

    return coooccurence_matrix


def calculateAccumulatedCooccurrenceMatrix(patch, binNumber, d):
    accumulated_cooccurrence_matrix = np.zeros((binNumber,binNumber))
    d_offsets = [(d,0),(d,d),(0,d),(-d,d),(-d,0),(-d,-d),(0,-d),(d,-d)]
    for di, dj in d_offsets: 
        cooccurrence = calculateCooccurrenceMatrix(patch, binNumber, di, dj)
        cooccurrence = np.array(cooccurrence)
        accumulated_cooccurrence_matrix += cooccurrence
    return accumulated_cooccurrence_matrix


def calculateCooccurrenceFeatures(accumulatedMatrix):
    normalized_accumulated_matrix = accumulatedMatrix / np.sum(accumulatedMatrix)
    angul_sec_moment = np.sum(normalized_accumulated_matrix * normalized_accumulated_matrix)
    maximum_probability = np.max(normalized_accumulated_matrix)
    a = np.linspace(1, normalized_accumulated_matrix.shape[0], normalized_accumulated_matrix.shape[0], dtype=int)
    b = np.linspace(1, normalized_accumulated_matrix.shape[0], normalized_accumulated_matrix.shape[0], dtype=int)
    a.shape = (normalized_accumulated_matrix.shape[0], 1)
    b.shape = (1, normalized_accumulated_matrix.shape[0])
    inverse_dif_denom = 1 + ((a-b)*(a-b))
    inverse_difference_moment = np.sum(normalized_accumulated_matrix / inverse_dif_denom)
    log_matrix = ma.log(normalized_accumulated_matrix)
    log_matrix = log_matrix.filled(0)
    entropy = -np.sum(normalized_accumulated_matrix * log_matrix)
    return (angul_sec_moment, maximum_probability, inverse_difference_moment, entropy)

def calculateIntensityFeatures(patch, binNumber):
    average = np.mean(patch)
    stand_dev = np.std(patch)
    hist, _ = np.histogram(patch, bins=binNumber, range=(0, 256), density=True)
    probabilities = hist / np.sum(hist)
    epsilon = np.finfo(float).eps  # Small epsilon value for dealing the case with probabilities == 0 #
    probabilities = np.where(probabilities == 0, epsilon, probabilities)
    entropy = -np.sum(probabilities * np.log2(probabilities))
    return average, stand_dev, entropy

def extractFeatures(image_path, cells_path, window_size, binNumber, d):
    image = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)
    
    with open(cells_path, 'r') as file:
        cells_data = file.readlines()
    
    features = []
    cell_labels = []
    
    for cell_data in cells_data:
        x, y, label = re.split(r'\s+', cell_data.strip())
        x = int(x)
        y = int(y)
        label = str(label)
        patch = image[y - window_size: y + window_size, x - window_size: x + window_size]
        intensity_features = calculateIntensityFeatures(patch, binNumber)
        accumulated = calculateAccumulatedCooccurrenceMatrix(patch, binNumber, d)
        cooccurrence_features = calculateCooccurrenceFeatures(accumulated)
        cell_features = np.concatenate((intensity_features, cooccurrence_features))
        features.append(cell_features)
    
    
    features = np.array(features)
    return features


def extractFeaturesGaborFilter(image_path, cells_path, window_size, binNumber, d):
    image = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)
    
    with open(cells_path, 'r') as file:
        cells_data = file.readlines()
    
    features = []
    cell_labels = []
    
    for cell_data in cells_data:
        x, y, label = re.split(r'\s+', cell_data.strip())
        x = int(x)
        y = int(y)
        label = str(label)
        patch = image[y - window_size: y + window_size, x - window_size: x + window_size]
        intensity_features = calculateIntensityFeatures(patch, binNumber)
        accumulated_gabor = calculateAccumulatedGaborResponse(patch, binNumber, d)
        textural_features = calculateCooccurrenceFeatures(accumulated_gabor)
        cell_features = np.concatenate((intensity_features, textural_features))
        features.append(cell_features)
    
    
    features = np.array(features)
    return features
    

def clusterCells(images, cells, binNumber, k, N, d):
    all_features = []
    for i in range(len(images)):
        image_path = images[i]
        cells_path = cells[i]
        features = extractFeatures(image_path, cells_path, N, binNumber, d)
        all_features.extend(features)
    
    all_features = np.array(all_features)
    
    normalized_features = (all_features - np.mean(all_features, axis=1, keepdims=True)) / np.std(all_features, axis=1, keepdims=True)
    
    kmeans = KMeans(n_clusters=k)
    labels = kmeans.fit_predict(normalized_features)
    inertia = kmeans.inertia_
    print("Inertia value of "+str(k)+"-means clustering is =  " + str(inertia) + "")
    sil_score = silhouette_score(normalized_features, labels)
    
    labels = np.array(labels)
    
    return labels,sil_score


def clusterCellsGaborFilter(images, cells, binNumber, k, N, d):
    all_features = []
    for i in range(len(images)):
        image_path = images[i]
        cells_path = cells[i]
        features = extractFeaturesGaborFilter(image_path, cells_path, N, binNumber, d)
        all_features.extend(features)
    
    all_features = np.array(all_features)
    
    normalized_features = (all_features - np.mean(all_features, axis=1, keepdims=True)) / np.std(all_features, axis=1, keepdims=True)
    
    kmeans = KMeans(n_clusters=k)
    labels = kmeans.fit_predict(normalized_features)
    inertia = kmeans.inertia_
    print("Inertia value of "+str(k)+"-means clustering is =  " + str(inertia) + "")
    sil_score = silhouette_score(normalized_features, labels)
    
    labels = np.array(labels)
    
    return labels,sil_score


def visualizeClusters(images, cluster_labels, cells, dataset_type, image_indices):
    unique_labels = np.unique(cluster_labels)
    colors = cm.rainbow(np.linspace(0, 1, len(unique_labels)))
    cluster_colors = {label: color for label, color in zip(unique_labels, colors)}

    for i, image_path in enumerate(images):
        all_cells = []

        with open(cells[i], 'r') as file:
            cells_data = file.readlines()

        for cell_data, label in zip(cells_data, cluster_labels[i * len(cells_data):(i+1) * len(cells_data)]):
            x, y, _ = re.split(r'\s+', cell_data.strip())
            x = int(x)
            y = int(y)
            color = cluster_colors[label]
            all_cells.append((x, y, color))

        plt.figure(figsize=(3,3)) ########## creating a figure of size 3 by 3 ##############
        
        # Plotting all cells for the current image
        for x, y, color in all_cells:
            plt.scatter(x, y, color=color)
        plt.gca().invert_yaxis()
        plt.xlabel("X Axis")
        plt.ylabel("Y Axis")
        plt.title(f"Clustering Results ({dataset_type.capitalize()} Image {image_indices[i]})")

    plt.show()



def calculate_cell_ratios(cluster_labels, cell_type_keeper, cell_types):
    cluster_ratios = {cluster_id: {cell_type: 0 for cell_type in cell_types} for cluster_id in set(cluster_labels)}

    for cluster_id, cell_type in zip(cluster_labels, cell_type_keeper):
        cluster_ratios[cluster_id][cell_type] += 1

    # Calculate and return the ratios
    ratios = {}
    for cluster_id, cell_counts in cluster_ratios.items():
        total_cells = sum(cell_counts.values())
        ratios[cluster_id] = {cell_type: count / total_cells for cell_type, count in cell_counts.items()}

    return ratios

# Set the parameters
N = 10
binNumber = 6
d = 5
k = 5

####### Definition of the paths to images and cells ########################
train_pngs = ["nucleus-dataset/train_8.png", "nucleus-dataset/train_11.png", "nucleus-dataset/train_14.png", "nucleus-dataset/train_21.png"]
train_txts = ["nucleus-dataset/train_8_cells", "nucleus-dataset/train_11_cells", "nucleus-dataset/train_14_cells", "nucleus-dataset/train_21_cells"]
test_pngs = ["nucleus-dataset/test_1.png", "nucleus-dataset/test_10.png"]
test_txts = ["nucleus-dataset/test_1_cells", "nucleus-dataset/test_10_cells"]

print("-------------------------------------------------------------------------------------------------------------------------")
# Define the cell types
cell_types = ["inflammation", "epithelial", "spindle"]


####correlate the obtained cluster ids with the actual classes of the cells ############

# Calculate the cluster labels
predicted_cluster_train_labels,train_score = clusterCells(train_pngs, train_txts, binNumber, k, N, d)
print()
print("The silhuette score for training is: "+str(train_score)+"")
print()


cell_types_in_train = list()

# Loop through all training images
for i, train_txt in enumerate(train_txts):
    # Initialize counters for cell types in current image
    cell_type_counts = {cell_type: 0 for cell_type in cell_types}

    # Count cell types in current image
    with open(train_txt, 'r') as file:
        for line in file:
            splitted = line.strip().split()
            cell_type = splitted[-1]
            cell_types_in_train.append(cell_type)
            if cell_type in cell_types:
                cell_type_counts[cell_type] += 1

    print(f"Train Image {i+1}:")
    print()
    for cell_type, count in cell_type_counts.items():
        print(f"Total {cell_type} cells: {count}")
    print()

# Calculate the ratios of specific cell types in each cluster
cell_ratios = calculate_cell_ratios(predicted_cluster_train_labels, cell_types_in_train, cell_types)

# Print the ratios
for cluster_id, ratios in cell_ratios.items():
    print()
    print(f"Cluster {cluster_id + 1} ratios:")
    print()
    for cell_type, ratio in ratios.items():
        print(f"{cell_type}: {ratio}")

print("-----------------------------------------------------------------------------------------------------------------------")
print()
predicted_cluster_test_labels,test_score = clusterCells(test_pngs, test_txts, binNumber, k, N, d)
print()
print("The silhuette score for testing is: "+str(test_score)+"")
print()

cell_types_in_test = list()

for i, test_txt in enumerate(test_txts):
    # Initialize counters for cell types in current image
    cell_type_counts = {cell_type: 0 for cell_type in cell_types}

    # Count cell types in current image
    with open(test_txt, 'r') as file:
        for line in file:
            splitted = line.strip().split()
            cell_type = splitted[-1]
            cell_types_in_test.append(cell_type)
            if cell_type in cell_types:
                cell_type_counts[cell_type] += 1
                

    print(f"Test Image {i+1}:")
    print()
    for cell_type, count in cell_type_counts.items():
        print(f"Total {cell_type} cells: {count}")
    print()
    

# Calculate the ratios of specific cell types in each cluster
cell_ratios_test = calculate_cell_ratios(predicted_cluster_test_labels, cell_types_in_test, cell_types)

# Print the ratios
for cluster_id, ratios in cell_ratios_test.items():
    print()
    print(f"Cluster {cluster_id + 1} ratios (Test):")
    print()
    for cell_type, ratio in ratios.items():
        print(f"{cell_type}: {ratio}")

print("------------------------------------------------------------------------------------------------------------------------")

# Visualize training data clusters
train_index_list = list(range(1, len(train_pngs)+1))
visualizeClusters(train_pngs, predicted_cluster_train_labels, train_txts, dataset_type='Train', image_indices=train_index_list)



# Visualize test data clusters
test_index_list = list(range(1, len(test_pngs)+1))
visualizeClusters(test_pngs, predicted_cluster_test_labels, test_txts, dataset_type='Test', image_indices=test_index_list)

print("-----------------------------------------------------------------------------------------------------------------------")
print("---------BONUS PART STARTS HERE---------------------")
print()
kGabor = 5
NGabor = 10
binNumberGabor = 6
dGabor = 5


predicted_train_labels_gabor, train_silhouette = clusterCellsGaborFilter(train_pngs, train_txts, binNumberGabor, kGabor, NGabor, dGabor)
print()
print("The silhuette score for training is: "+str(train_silhouette)+"")
print()


cell_types_in_train_gabor_filt = list()

# Loop through all training images
for i, train_txt in enumerate(train_txts):
    # Initialize counters for cell types in current image
    cell_type_counts = {cell_type: 0 for cell_type in cell_types}

    # Count cell types in current image
    with open(train_txt, 'r') as file:
        for line in file:
            splitted = line.strip().split()
            cell_type = splitted[-1]
            cell_types_in_train_gabor_filt.append(cell_type)
            if cell_type in cell_types:
                cell_type_counts[cell_type] += 1

    print(f"Train Image {i+1}:")
    print()
    for cell_type, count in cell_type_counts.items():
        print(f"Total {cell_type} cells: {count}")
    print()

# Calculate the ratios of specific cell types in each cluster
cell_ratios_gabor = calculate_cell_ratios(predicted_train_labels_gabor, cell_types_in_train_gabor_filt, cell_types)

# Print the ratios
for cluster_id, ratios in cell_ratios_gabor.items():
    print()
    print(f"Cluster {cluster_id + 1} ratios:")
    print()
    for cell_type, ratio in ratios.items():
        print(f"{cell_type}: {ratio}")
        
print()
print("----------------------------------------------------------------------------")
predicted_test_labels_gabor, test_silhouette = clusterCellsGaborFilter(test_pngs, test_txts, binNumberGabor, kGabor, NGabor, dGabor)        
print()
print("The silhuette score for testing is: "+str(test_silhouette)+"")
print()
cell_types_in_test_gabor_filt = list()

for i, test_txt in enumerate(test_txts):
    # Initialize counters for cell types in current image
    cell_type_counts = {cell_type: 0 for cell_type in cell_types}

    # Count cell types in current image
    with open(test_txt, 'r') as file:
        for line in file:
            splitted = line.strip().split()
            cell_type = splitted[-1]
            cell_types_in_test_gabor_filt.append(cell_type)
            if cell_type in cell_types:
                cell_type_counts[cell_type] += 1
                

    print(f"Test Image {i+1}:")
    print()
    for cell_type, count in cell_type_counts.items():
        print(f"Total {cell_type} cells: {count}")
    print()
    

# Calculate the ratios of specific cell types in each cluster
cell_ratios_gabor_test = calculate_cell_ratios(predicted_test_labels_gabor, cell_types_in_test_gabor_filt, cell_types)

# Print the ratios
for cluster_id, ratios in cell_ratios_gabor_test.items():
    print()
    print(f"Cluster {cluster_id + 1} ratios (Test):")
    print()
    for cell_type, ratio in ratios.items():
        print(f"{cell_type}: {ratio}")

print("------------------------------------------------------------------------------------------------------------------------")

# Visualize training data clusters
train_index_lst = list(range(1, len(train_pngs)+1))
visualizeClusters(train_pngs, predicted_train_labels_gabor, train_txts, dataset_type='Train', image_indices=train_index_lst)



# Visualize test data clusters
test_index_lst = list(range(1, len(test_pngs)+1))
visualizeClusters(test_pngs, predicted_test_labels_gabor, test_txts, dataset_type='Test', image_indices=test_index_lst)

print("----------BONUS PART ENDS HERE-----------------------")
print()





# 
