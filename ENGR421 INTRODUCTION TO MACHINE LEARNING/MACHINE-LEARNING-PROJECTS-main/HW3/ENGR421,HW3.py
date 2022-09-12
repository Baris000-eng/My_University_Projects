import matplotlib.pyplot as plt #importing necessary libraries
import numpy as np
import pandas as pd
import math

def safe_logarithm_calculation(x): #To be safe for the log(0) case.
    p= math.pow(10,-100)
    return np.log(x + p)


np.random.seed(421)
print()
print("Mean 1: \n") 
m_F= [+0.0, +2.5] # The first mean parameter 
print(m_F)
print()
print("Mean 2: \n")
m_S= [-2.5, -2.0] #The second mean parameter
print(m_S)
print()
print("Mean 3: \n")
m_T= [+2.5, -2.0] #The third mean parameter
print(m_T)
print()
print("Mean parameters: \n")
cl_m = np.array([m_F, 
                 m_S, 
                 m_T]) 
print(cl_m)

print()
print("--------------------------------------------------")
print()
print("Covariance Matrix 1: \n")
cov_F= [[+3.2, +0.0], 
        [+0.0, +1.2]] #The first covariance matrix parameter
print(cov_F)
print()
print("Covariance Matrix 2: \n")
cov_S= [[+1.2, +0.8], 
        [+0.8, +1.2]] #The second covariance matrix parameter
print(cov_S)
print()
print("Covariance Matrix 3: \n")
cov_T= [[+1.2, -0.8], 
        [-0.8, +1.2]] #The third covariance matrix parameter
print(cov_T)
print()
print("Covariance Matrix parameters: \n")
cl_cov = np.array([cov_F,
                   cov_S,
                   cov_T]) 
print(cl_cov)
print()
print("-------------------------------------------------")
print()
s_1= 120 #The first size parameter
s_2= 80  #The second size parameter
s_3= 100 #The third size parameter
print("Size 1: \n")
print(s_1)
print()
print("Size 2: \n")
print(s_2)
print()
print("Size 3: \n")
print(s_3)
print()
print("Size parameters: \n")
cl_s = np.array([s_1, s_2, s_3])
print(cl_s)
print()
print("-------------------------------------------------")
print()
print("Generation of the Random Data Points: \n")
p_1 = np.random.multivariate_normal(cl_m[0,:], cl_cov[0,:,:], cl_s[0]) #creating the random data points
p_2 = np.random.multivariate_normal(cl_m[1,:], cl_cov[1,:,:], cl_s[1])
p_3 = np.random.multivariate_normal(cl_m[2,:], cl_cov[2,:,:], cl_s[2])
stack = np.vstack((p_1, p_2, p_3)) #vertically stacking the random data points(p_1, p_2, p_3)

labels = np.concatenate((np.repeat(1, cl_s[0]), np.repeat(2, cl_s[1]), np.repeat(3, cl_s[2]))) #creating the class labels
redStr= "r."
greenStr="g."
blueStr="b."
str_1= "x1"
str_2= "x2"
l_b= -6
u_b= +6
plt.figure(figsize = (15, 15))
plt.plot(p_1[:,0], p_1[:,1], redStr, markersize = 14)
plt.plot(p_2[:,0], p_2[:,1], greenStr, markersize = 14)
plt.plot(p_3[:,0], p_3[:,1], blueStr, markersize = 14)
plt.xlim((l_b, u_b)) #creating the limits for x values
plt.ylim((l_b, u_b)) #creating the limits for y values
plt.xlabel(str_1)
plt.ylabel(str_2)
plt.title("Graph which shows the generated random data points") # for giving title to graph
plt.show()
print()
print("-----------------------------------------------------------------")
def findMaxNum(myArr):  #finds the maximum number in an array.
    maxNumber=myArr[0]
    l= len(myArr)
    for i in range(l):
        if myArr[i]>maxNumber:
            maxNumber=myArr[i]
    return maxNumber

def my_sum(arr):  #finds the sum of the numbers in an array.
    t=0
    l= len(arr)
    for w in range(0,l):
        t=t+arr[w]
    return t

maxLabel = findMaxNum(labels)


#----------------------------------------------------------------------------------------------------------------
Y_t = np.zeros((300, maxLabel)).astype(int)
Y_t[range(300), labels - 1] = 1

def sigmoid_calculation_function(A, w, w0): #Sigmoid func
    l= -(np.matmul(A, w) + w0)
    denum= (1 + np.exp(l))
    res= 1/denum
    return res


def gradient_calculation_forW(B, Y_tru, Y_pr): #gradient function for W
    return (np.asarray([-np.matmul(Y_tru[:,r] - Y_pr[:,r], B) for r in range(maxLabel)]).T)

def gradient_calculation_forw0(y_t, y_p): #gradient function for w0
    diff= y_t - y_p
    return(-np.sum(diff, axis = 0))

#learning parameters
etaVal = 0.01 #step size
epsVal = 0.001 

np.random.seed(421)
sh= stack.shape[1]
WVal = np.random.uniform(low = -0.01, high = 0.01, size = (sh, maxLabel)) #initializing the WVal
w0Val = np.random.uniform(low = -0.01, high = 0.01, size = (1, maxLabel)) #initializing the w0val


itr = 1
obj_vals = []

while True: #applying the gradient descent algorithm
    Y_p = sigmoid_calculation_function(stack, WVal, w0Val) #generating the predicted values by using sigmoid
    obj_vals = np.append(obj_vals, np.sum((1/2)*((Y_t- Y_p)**2))) #using the sum squared errors.
    W_old_Val = WVal
    w0_old_Val = w0Val
    change_of_WVal= etaVal * gradient_calculation_forW(stack, Y_t, Y_p)
    change_of_w0Val= etaVal * gradient_calculation_forw0(Y_t, Y_p)
    WVal = WVal - change_of_WVal
    w0Val = w0Val - change_of_w0Val
    if np.sqrt(np.sum((w0Val - w0_old_Val))**2 + np.sum((WVal - W_old_Val)**2)) < epsVal:
        break
    itr= itr+1

print()
print("W: \n")
print(WVal)
print()
print("w0: \n")
print(w0Val)


plt.figure(figsize = (14, 10))                        #Creating the objective function. 
plt.title("Objective function during iterations")
plt.plot(range(1, itr + 1), obj_vals, "k-")
plt.xlabel("Iteration")
plt.ylabel("Error")
plt.show()

print("-------------------------------------------------------------")
print()
print("The Confusion Matrix: \n")

y_prd = 1+ np.argmax(Y_p, axis = 1)
cnf_m= pd.crosstab(y_prd, labels, rownames = ['y_pred'], colnames = ['y_truth']) #creating the confusion matrix by using pd.crosstab function
print(cnf_m)

print()
iV_X1 = np.linspace(-6, +6, 1201)
iV_X2 = np.linspace(-6, +6, 1201)
gridOfX1, gridOfX2 = np.meshgrid(iV_X1, iV_X2)
discrim_values = np.zeros((len(iV_X1), len(iV_X2), maxLabel))

for p in range(maxLabel):
    discrim_values[:,:,p] = WVal[0, p] * gridOfX1 + WVal[1, p] * gridOfX2 + w0Val[0, p]

val_1 = discrim_values[:,:,0]
val_2 = discrim_values[:,:,1]
val_3 = discrim_values[:,:,2]
val_1[(val_1 < val_2) & (val_1 < val_3)] = np.nan
val_2[(val_2 < val_1) & (val_2 < val_3)] = np.nan
val_3[(val_3 < val_1) & (val_3 < val_2)] = np.nan
discrim_values[:,:,0] = val_1
discrim_values[:,:,1] = val_2
discrim_values[:,:,2] = val_3 

n= "none"
plt.figure(figsize = (13, 13))

bl= "k"
plt.plot(stack[labels == 1, 0], stack[labels == 1, 1], redStr, markersize = 10)
plt.plot(stack[labels == 2, 0], stack[labels == 2, 1], greenStr, markersize = 10)
plt.plot(stack[labels == 3, 0], stack[labels == 3, 1], blueStr, markersize = 10)
plt.plot(stack[y_prd != labels, 0], stack[y_prd != labels, 1], "ko", markersize = 12, fillstyle = n)

d_1= discrim_values[:,:,0] - discrim_values[:,:,1]
d_2= discrim_values[:,:,0] - discrim_values[:,:,2]
d_3= discrim_values[:,:,1] - discrim_values[:,:,2]

#Creating the decision boundaries between classes by utilizing plt.contour() function. 
plt.contour(gridOfX1, gridOfX2 ,d_1, levels = 0, colors = bl)
plt.contour(gridOfX1, gridOfX2 ,d_2, levels = 0, colors = bl)
plt.contour(gridOfX1, gridOfX2 ,d_3, levels = 0, colors = bl)
plt.xlabel(str_1)
plt.ylabel(str_2)
plt.title("Graph showing the decision boundaries and the misclassified data points") # for giving title to graph
plt.show()