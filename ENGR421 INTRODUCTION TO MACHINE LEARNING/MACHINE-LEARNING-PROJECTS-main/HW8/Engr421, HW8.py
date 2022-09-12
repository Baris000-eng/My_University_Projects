#Barış KAPLAN
#KU ID Number: 69054

#Importing the necessary libraries
import numpy as np
import matplotlib.pyplot as plt
import scipy.spatial as spa
import math


data_file= "hw08_data_set.csv" #given data file
data_points = np.genfromtxt(data_file, delimiter=",")   #Reading data
t_val = 1.25

print()
print("--------------------------------------------------------------------------------------------")
print()

#B matrix construction
dst = np.zeros((300, 300))
B_mat = np.zeros((300, 300))
for k in range(300):
    for l in range(300):
        sumSqX= math.pow((data_points[l][0] - data_points[k][0]),2)
        sumSqY= math.pow((data_points[l][1] - data_points[k][1]),2)
        dst[k][l] = np.sqrt(sumSqX + sumSqY)  
        if dst[k][l] < t_val:
            B_mat[k][l] = 1  
        else:
            B_mat[k][l] = 0  
            

#Connectivity matrix construction
print("CONNECTIVITY MATRIX : \n")
plt.figure(figsize=(12, 12))
bl="black"
plt.plot(data_points[:, 0], data_points[:, 1], '.', markersize=11, color=bl)
for v in range(300):
    for z in range(300):
        if B_mat[v][z] == 1:
            plt.plot([data_points[v][0], data_points[z][0]], [data_points[v][1], data_points[z][1]], "k", linewidth=0.5)
plt.xlabel("x1")
plt.ylabel("x2")
plt.title(" - Connectivity Matrix - ")
plt.show()

print()
print("--------------------------------------------------------------------------------------------")
print()

#D matrix construction
D_mat = np.zeros((300, 300))
for q in range(300):
    bNum = 0  
    for w in range(300):
        if B_mat[q][w] == 1:
            bNum = bNum+1
    D_mat[q][q] = bNum
    
#Normalization of L matrix 
identity_matrix = np.eye(300, dtype=int)
mat1= np.sqrt(np.linalg.inv(D_mat))
mat2= np.dot(B_mat, mat1)
mat3= np.dot(mat1,mat2)
L_sym = identity_matrix - mat3
L_eigvals, L_eigvecs = np.linalg.eig(L_sym)  
eigvals_in_order = np.argsort(L_eigvals)
sml_eigvecs1 =  L_eigvecs[:, eigvals_in_order[1]]
sml_eigvecs2 =  L_eigvecs[:, eigvals_in_order[2]]
sml_eigvecs3 =  L_eigvecs[:, eigvals_in_order[3]]
sml_eigvecs4 =  L_eigvecs[:, eigvals_in_order[4]]
sml_eigvecs5 =  L_eigvecs[:, eigvals_in_order[5]]

#Obtaining Z matrix
sml_eigvecs= np.array([sml_eigvecs1,sml_eigvecs2,sml_eigvecs3,sml_eigvecs4,sml_eigvecs5])
Z_mat = sml_eigvecs.T  
c1= Z_mat[28]
c2= Z_mat[142]
c3= Z_mat[203]
c4= Z_mat[270]
c5= Z_mat[276]

#Obtaining initial centroids
init_cens = np.vstack([c1,c2,c3,c4,c5])

#K-means clustering algorithm, Mostly from Lab11: Clustering
def update_cens(m, X):
    if m is None:
        centr = init_cens #initializing centroids to init_cens
    else:
        centr = np.vstack([np.mean(X[m == k,:], axis = 0) for k in range(5)])
    return centr

def update_mems(c, X):
    D = spa.distance_matrix(c, X)
    memb = np.argmin(D, axis = 0)
    return memb

print("--------------------------------------------------------------------------------------------")
print()
cens = None
mems = None
i = 1
while True:
    print("Iteration#"+str(i)+"")
    old_cens = cens
    cens = update_cens(mems, Z_mat) #Running algorithm on Z_mat
    if np.alltrue(cens == old_cens):
        break
    old_mems = mems
    mems = update_mems(cens, Z_mat) #Running algorithm on Z_mat
    if np.alltrue(mems==old_mems):
        break
        
    i = i+1
    
cens = update_cens(mems, data_points)

print("--------------------------------------------------------------------------------------------")
print()

#Plotting the clustering results
print("CLUSTERING RESULTS : \n")

cl_col = np.array(["#1f78b4", "#33a02c", "#e31a1c", "#ff7f00", "#6a3d9a", "#b15928",
                               "#a6cee3", "#b2df8a", "#fb9a99", "#fdbf6f", "#cab2d6", "#ffff99"])
plt.figure(figsize=(12,12))
for x in range(5):
    plt.plot(data_points[mems == x, 0], data_points[mems == x, 1], ".", markersize = 17, color=cl_col[x])
for y in range(5):
    plt.plot(cens[y, 0], cens[y, 1], "s", markersize = 15, 
             markerfacecolor = cl_col[y], markeredgecolor = "black")  
plt.xlabel("x1")
plt.ylabel("x2")
plt.title(" - CLUSTERING RESULTS - ")
plt.show()
print()
print("--------------------------------------------------------------------------------------------")
print()