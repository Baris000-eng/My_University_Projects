#importing necessary libraries
import numpy as np
import math
import matplotlib.pyplot as plt
import pandas as pd

d_f= "hw04_data_set.csv" #The given csv file
d_s= np.genfromtxt(d_f, skip_header=1, delimiter = ",") #reading the csv file

 #dividing the data set into the training set and test set (the first 150 data points to the training set
#and the remaining 122 data points to the test set.)
tr_d = d_s[:150,0]                #The first 150 data points of the first column to the trainining data of x   
tr_y = d_s[:150,1].astype(int)    #The first 150 data points of the second column to the training data of y. 
tst_d = d_s[150:,0]               #The rest 122 data points of the first column to the test data of x.
tst_y = d_s[150:,1].astype(int)   #The rest 122 data points of the second column to the test data of y.

colors= np.array(["red","green","blue"]) 

o_p = 1.5    # Origin parameter
b_w = 0.37   # Bin width 

def find_maximum(arr):        #The function which finds the maximum number in an array and returns that number
    max_elm= arr[0]
    l= len(arr)
    for i in range(l):
        if arr[i]>max_elm:
            max_elm=arr[i]
    return max_elm

maxTrainingX = find_maximum(tr_d)
l_b = np.arange(o_p, maxTrainingX, b_w)
r_b = np.arange(o_p + b_w, maxTrainingX + b_w, b_w)
step_size=math.pow(10,-3)
d_i = np.arange(o_p, maxTrainingX, step_size)

print()
print("---------------------------------------------------------------------------------")
print("\nREGRESSOGRAM:\n")
def regress_func(n):       #Regressogram
    first_iterator = 0
    first_summation = 0
    for i in range(150):
        if(l_b[n] < tr_d[i] and tr_d[i] <= r_b[n]):
            first_summation = first_summation + tr_y[i]
            first_iterator= first_iterator + 1
    first_average=first_summation/first_iterator
    return first_average

l= len(l_b)
p_hat = [regress_func(g) for g in range(l)]
plt.figure(figsize=(10,7))
plt.scatter(tr_d, tr_y)
plt.scatter(tst_d, tst_y, color=colors[0])
for v in range(l):
    plt.plot([l_b[v], r_b[v]], [p_hat[v], p_hat[v]], "k-")
for w in range(l-1):
    plt.plot([r_b[w], r_b[w]], [p_hat[w], p_hat[w + 1]], "k-")    


plt.title("Regressogram Plot with Training Data Points and Test Data Points")                           
plt.xlabel("X VALUES= Eruption Time (min)")                   
plt.ylabel("Y VALUES= Waiting time to next eruption (min)")
plt.show()

d= len(tst_d)
rmse_Val_1 = 0
for a in range(0,d):
    for t in range(0,l):
        if(l_b[t] < tst_d[a] and tst_d[a] <= r_b[t]):
            e = (tst_y[a] - p_hat[int((tst_d[a]-o_p)/b_w)])**2
            rmse_Val_1= rmse_Val_1 + e

res = math.sqrt(rmse_Val_1 / d)
print()
print("Regressogram => RMSE is "+str(res)+" when h is " +str(b_w))
print()
print("---------------------------------------------------------------------------------")
print("\nRUNNING MEAN SMOOTHER:\n")
def mean_sm(k):                   #Mean Smoother
    second_summation = 0
    second_iterator = 0
    b_w = 0.37
    bound= 0.8
    for i in range(150):
        if(abs((d_i[k]-tr_d[i])/b_w) < 0.50):
            second_summation=second_summation+tr_y[i] 
            second_iterator=second_iterator + 1
    second_average=second_summation/second_iterator
    return second_average


pHat_ForMeanSmoother = np.array([mean_sm(i) for i in range(len(d_i))])

plt.figure(figsize=(11,7))
plt.scatter(tr_d, tr_y)
plt.scatter(tst_d, tst_y, color=colors[0])
plt.plot(d_i, pHat_ForMeanSmoother, "k")
plt.title("Running Mean Smoother Plot with Training Data Points and Test Data Points")
plt.xlabel("X VALUES = Eruption Time (min)")
plt.ylabel("Y VALUES = Waiting time to next eruption (min)")
plt.show()

def my_sum(arr):   #The function which sums the numbers in an array and returns this sum.
    summation=0
    l= len(arr)
    for i in range(0,l):
        summation=summation+arr[i]
    return summation

e_1 = [(    math.pow((tst_y[i] - pHat_ForMeanSmoother[int((tst_d[i]-o_p)*1000)]),2)    ) for i in range(d)]
rmse_Val_2 = math.sqrt(my_sum(e_1) / d)
print()
print("Mean Smoother => RMSE is "+str(rmse_Val_2)+" when h is " +str(b_w))
print()
print("---------------------------------------------------------------------------------")
print("\nKERNEL SMOOTHER:\n")

def kernel_func(u):
    a= (1/math.sqrt(2* math.pi)) 
    b= np.exp(-(math.pow(u,2)) / 2)
    kernel= a*b
    return kernel
def kernel_sm_func(t):        #Kernel Smoother
    third_summation = 0
    third_iterator = 0 
    b_w=0.37
    for i in range(0,150):
        u = (d_i[t] - tr_d[i]) / b_w     # u= (x-xi) /h     
        third_iterator= third_iterator+kernel_func(u)  
        third_summation= third_summation+(kernel_func(u) * tr_y[i])
    third_average= third_summation/third_iterator
    return third_average

pHat_ForKernelSmoother = np.array([kernel_sm_func(r) for r in range(len(d_i))])

plt.figure(figsize=(12,7))
plt.scatter(tr_d, tr_y)
plt.scatter(tst_d, tst_y, color=colors[0])
plt.plot(d_i, pHat_ForKernelSmoother, "k")
plt.title("Kernel Smoother Plot with Training Data Points and Test Data Points")
plt.xlabel("X VALUES= Eruption Time (min)")
plt.ylabel("Y VALUES= Waiting time to next eruption (min)")
plt.show()

e_2 = [ (   math.pow((tst_y[x] - pHat_ForKernelSmoother[int((tst_d[x]-o_p)*1000)]),2)   )    for x in range(d)]


rmse_Val_3 = math.sqrt(my_sum(e_2) / d)
print()
print("Kernel Smoother => RMSE is "+str(rmse_Val_3)+" when h is " +str(b_w))
print()
print("---------------------------------------------------------------------------------")
print()
