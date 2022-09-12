#Necessary libraries
import numpy as np
import matplotlib.pyplot as plt
import math 
import pandas as pd

d_file= "hw05_data_set.csv"
d_s = np.genfromtxt(d_file, skip_header=1, delimiter = ",")#Reading the csv file
x_v = d_s[:,0] #First column to x values
y_v = d_s[:,1].astype(int) #Second column to y values


#Dividing the data set into train set and test set
x_tr = x_v[:150] #The first 150 data points of the x values to the x_training_set.
x_ts = x_v[150:] #The rest 122 data points of the x values to the x_test_set.
y_tr = y_v[:150] #The first 150 data points of the y values to the y_training_set.
y_ts = y_v[150:] #The rest 122 data points of the y values to the y_test_set.


def findMinIndex(arr): #A function which finds the index of the minimum value in an array.
    minInd=0
    for i in range(0,len(arr)):
        if arr[i]<arr[minInd]:
            minInd=i
    return minInd

def decision_tree_regression_algorithm_for_learning(P): 
    #data structures needed
    n_ind = {}
    check_terminal = {}
    is_split_needed = {}
    n_cuts = {}
    n_avr = {}
    
    n_ind[1] = np.array(range(len(y_tr)))
    check_terminal[1] = False
    is_split_needed[1] = True
    while True:
       
        s_n = [key for key, value in is_split_needed.items() if value == True]
       
        if len(s_n) == 0:
            break
       
        for s_node in s_n:
            d_ind = n_ind[s_node]
            is_split_needed[s_node] = False
            n_mean = np.mean((y_tr[d_ind]))
            if len((x_tr[d_ind])) <= P:
                check_terminal[s_node] = True
                n_avr[s_node] = n_mean
            else:
                check_terminal[s_node] = False
                
                different_vals = np.sort(np.unique(x_tr[d_ind]))
                l = len(different_vals)

                pos = (different_vals[1:len(different_vals)] + different_vals[0:(len(different_vals) - 1)]) / 2
                scr = np.repeat(0, len(pos))

                for my_num in range(len(pos)):
                    l_i = d_ind[x_tr[d_ind] <= pos[my_num]]
                    r_i = d_ind[x_tr[d_ind] > pos[my_num]]
                    errors= 0
                    if len(r_i) > 0:
                        errors= errors+np.sum((y_tr[r_i] - np.mean(y_tr[r_i]))**2) #sum of squares total
                    if len(l_i) > 0:
                        errors = errors+np.sum((y_tr[l_i] - np.mean(y_tr[l_i]))**2)#sum of squares total
            
                    scr[my_num] = errors / (len(l_i) + len(r_i)+1)

                if len(different_vals) == 1:
                    check_terminal[s_node] = True
                    n_avr[s_node] = n_mean
                    continue
                best_cut = pos[findMinIndex(scr)] #Finding best split
                n_cuts[s_node] = best_cut

                l_i = d_ind[x_tr[d_ind] < best_cut]
                n_ind[2 * s_node] = l_i
                
                is_split_needed[2 * s_node] = True
                check_terminal[2 * s_node] = False
                

                r_i = d_ind[x_tr[d_ind] >= best_cut]
                n_ind[2 * s_node + 1] = r_i
                is_split_needed[2 * s_node + 1] = True
                check_terminal[2 * s_node + 1] = False
                
    return check_terminal, n_cuts, n_avr


def guess(p, check_terminal, n_cuts, n_avr):
    z= 1
    while(True):
        if check_terminal[z] == True:
             return n_avr[z]
        else:
            if p>n_cuts[z]:
                z= 2*z + 1
            else:
                z= 2*z
           
                
def findMin(arr):   #A function which finds the minimum value in an array and returns that value
    minNum= arr[0]
    for j in range(len(arr)):
        if(arr[j]<minNum):
            minNum= arr[j]
    return minNum

def findMax(arr):#A function which finds the maximum value in an array and returns that value
    maxNum= arr[0]
    for j in range(len(arr)):
        if(arr[j]>maxNum):
            maxNum= arr[j]
    return maxNum


d_pts = np.linspace(findMin(x_v), findMax(x_v), 1000)

P = 25 #Setting the preprunning parameter to 25.

check_terminal, n_cuts, n_avr = decision_tree_regression_algorithm_for_learning(P) #Learning the decision tree for P=25.

y_prd = [ guess(d_pts[my_num], check_terminal, n_cuts, n_avr) for my_num in range(len(d_pts))]
print()
print("------------------------------------------------------------------------------------")
print()
plt.figure(figsize=(11,6))
plt.scatter(x_tr, y_tr, color="blue", label="training_data")
plt.scatter(x_ts, y_ts, color="red",  label="test_data")
plt.plot(d_pts, y_prd, "k")
plt.title("Training Data Points & Test Data Points & Fit for P=25")
plt.xlabel("Eruption Time (min)")
plt.ylabel("Waiting time to next eruption (min)")
plt.legend()
plt.show()
print()
print("-------------------------------------------------------------------------------------")
print()
y_prd = [ guess(x_ts[my_num], check_terminal, n_cuts, n_avr) for my_num in range(len(y_ts))]
rmse = np.sqrt(np.mean((y_ts - y_prd)**2)) #Calculating rmse for the y test data (for P=25)

print("\n")
print("RMSE is "+str(rmse)+" for the test set when P is "+str(P)+"\n")
y_prd_2 = [ guess(x_tr[my_num], check_terminal, n_cuts, n_avr) for my_num in range(len(y_tr))]
rmse_2 = np.sqrt(np.mean((y_tr - y_prd_2)**2)) #Calculating rmse for the training data (for P=25)
print("\nRMSE is "+str(rmse_2)+" for the training set when P is "+str(P)+"\n")
print("\n")
print("-------------------------------------------------------------------------------------")

P_Vals = [5,10,15,20,25,30,35,40,45,50] #Creating an array including preprunning parameter values starting at 5, ending at 50, and with a step size 5.
y_preds = np.zeros((len(P_Vals), len(y_ts)))
y_preds2= np.zeros((len(P_Vals),len(y_tr)))

for my_num in range(len(P_Vals)): #For all the preprunning parameter values in the P_Vals array
    check_terminal, n_cuts, n_avr = decision_tree_regression_algorithm_for_learning(P_Vals[my_num]) #Learn decision tree for each of the P values in P_Vals array. 
    y_prd = [ guess(x_ts[my_num], check_terminal, n_cuts, n_avr) for my_num in range(len(y_ts))]
    y_prd2= [ guess(x_tr[my_num], check_terminal, n_cuts, n_avr) for my_num in range(len(y_tr))]
    y_preds[my_num] = y_prd
    y_preds2[my_num]= y_prd2

rmse = [np.sqrt(np.mean((y_ts - y_preds[my_num])**2)) for my_num in range(len(P_Vals))] #For each of the P values in P_Vals array, calculating rmse values for the y test set 
second_rmse= [np.sqrt(np.mean((y_tr - y_preds2[my_num])**2)) for my_num in range(len(P_Vals))]#For each of the P values in P_Vals array, calculating rmse values for the y training set 




print()
plt.figure(figsize=(12,8))
b= "black"
print("\n RED DATA POINTS (TEST DATA SET) & BLUE DATA POINTS (TRAINING DATA SET) \n")


plt.scatter(P_Vals, rmse, color="red")
plt.scatter(P_Vals, second_rmse , color="blue")


plt.plot(P_Vals, rmse, color="red")
plt.plot(P_Vals,second_rmse,color="blue")
plt.title("PrePrunning Size Values(P) vs RMSE Values")
plt.xlabel("PrePruning SIZE VALUES (P)")
plt.ylabel("RMSE VALUES")
plt.show()
print()
print("-------------------------------------------------------------------------------------")