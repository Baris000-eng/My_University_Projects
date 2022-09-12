#Importing the necessary libraries
import cvxopt as cvx
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import scipy.spatial.distance as dt
import math

img_file= 'hw06_images.csv' #Image file
lbl_file= 'hw06_labels.csv' #Label file


imgD= np.genfromtxt(img_file, delimiter = ",") #Reading the files
lblD= np.genfromtxt(lbl_file, delimiter = ",")


#Dividing the data set called imgD to the training set and test set
train_data_of_clothing_images = imgD[:1000] #Assignment of the first 1000 image data to the training set of imgD.

#print(train_data_of_clothing_image_labels)
test_data_of_clothing_images = imgD[1000:]#Assignment of the remaining 4000 image data to the test set of imgD.

#Dividing the data set called lblD to the training set and test set
train_data_of_clothing_image_labels = lblD[:1000] #Assignment of the first 1000 labels to the training set of lblD.
test_data_of_clothing_image_labels = lblD[1000:] #Assignment of the remaining 4000 labels to the test set of lblD.




##Gaussian kernel function
def gau_ker(p_1, p_2, s):
    dst = dt.cdist(p_1, p_2)
    K = np.exp(- dst**2 / (2 * s**2))
    return(K)

##Support Vector Machine Algorithm
def sup_vec_machine_alg(y_tr,C_val):
    s_val = 10
    K_tr = gau_ker(train_data_of_clothing_images, train_data_of_clothing_images, s_val)
    yyK = np.matmul(y_tr[:,None], y_tr[None,:]) * K_tr

    C_val = 10
    eps = math.pow(10,-3)

    A1 = cvx.matrix(yyK)
    A2 = cvx.matrix(-np.ones((len(y_tr), 1)))
    A3 = cvx.matrix(np.vstack((-np.eye(len(y_tr)), np.eye(len(y_tr)))))
    A4 = cvx.matrix(np.vstack((np.zeros((len(y_tr), 1)), C_val * np.ones((len(y_tr), 1)))))
    A5 = cvx.matrix(1.0 * y_tr[None,:])
    A6 = cvx.matrix(0.0)
                        
    result = cvx.solvers.qp(A1, A2, A3, A4, A5, A6)
    alp = np.reshape(result["x"], len(y_tr))
    alp[alp < C_val * eps] = 0
    alp[alp > C_val * (1 - eps)] = C_val

    s_ind, = np.where(alp != 0)
    act_ind, = np.where(np.logical_and(alp != 0, alp < C_val))
    w0 = np.mean(y_tr[act_ind] * (1 - np.matmul(yyK[np.ix_(act_ind, s_ind)], alp[s_ind])))
    return w0,alp

lblInd= 1 

l1 = np.copy(train_data_of_clothing_image_labels)     #for Label = 1
l1[l1 != lblInd] = -1
l1[l1 == lblInd] = 1

lblInd= 2

l2 = np.copy(train_data_of_clothing_image_labels)  #for Label = 2
l2[l2 != lblInd] = -1 
l2[l2 == lblInd] = 1


lblInd=3

l3 = np.copy(train_data_of_clothing_image_labels)  #for Label = 3
l3[l3 != lblInd] = -1
l3[l3 == lblInd] = 1

lblInd=4

l4 = np.copy(train_data_of_clothing_image_labels)  #for Label = 4
l4[l4 != lblInd] = -1
l4[l4 == lblInd] = 1

lblInd=5

l5 = np.copy(train_data_of_clothing_image_labels)  #for Label = 5
l5[l5 != lblInd] = -1
l5[l5 == lblInd] = 1


w0,alp= sup_vec_machine_alg(l1,10)
w0_2,alp_2=sup_vec_machine_alg(l2,10)
w0_3,alp_3=sup_vec_machine_alg(l3,10)
w0_4,alp_4=sup_vec_machine_alg(l4,10)
w0_5,alp_5=sup_vec_machine_alg(l5,10)

s_val= 10
K_tr = gau_ker(train_data_of_clothing_images, train_data_of_clothing_images, s_val)
f_pr_1 = np.matmul(K_tr, l1[:,None] * alp[:,None]) + w0
f_pr_2 = np.matmul(K_tr, l2[:,None] * alp_2[:,None]) + w0_2
f_pr_3 = np.matmul(K_tr, l3[:,None] * alp_3[:,None]) + w0_3
f_pr_4 = np.matmul(K_tr, l4[:,None] * alp_4[:,None]) + w0_4
f_pr_5 = np.matmul(K_tr, l5[:,None] * alp_5[:,None]) + w0_5

K_tst = gau_ker(test_data_of_clothing_images, train_data_of_clothing_images, s_val)
    
    
f_pr_1t = np.matmul(K_tst, l1[:,None] * alp[:,None]) + w0
f_pr_2t = np.matmul(K_tst, l2[:,None] * alp_2[:,None]) + w0_2
f_pr_3t = np.matmul(K_tst, l3[:,None] * alp_3[:,None]) + w0_3
f_pr_4t = np.matmul(K_tst, l4[:,None] * alp_4[:,None]) + w0_4
f_pr_5t = np.matmul(K_tst, l5[:,None] * alp_5[:,None]) + w0_5


f_arr= [f_pr_1,f_pr_2,f_pr_3,f_pr_4,f_pr_5]
f_arr_t= [f_pr_1t,f_pr_2t,f_pr_3t,f_pr_4t,f_pr_5t]

y_pr_train= np.argmax(f_arr, axis=0)+1 #getting the maximum value among all the y_predicted_train values
y_pr_test= np.argmax(f_arr_t, axis=0)+1 #getting the maximum value among all the y_predicted_test values
l_tr= len(train_data_of_clothing_image_labels)
l_tst= len(test_data_of_clothing_image_labels)

#Creating the confusion matrix for the training set by using pd.crosstab function
print("------------------------------------------------------------------")
print("\n THE CONFUSION MATRIX FOR THE TRAINING DATA SET : \n")
cnf_mat_for_trn = pd.crosstab(np.reshape(y_pr_train, l_tr), train_data_of_clothing_image_labels, rownames = ['y_predicted'], colnames = ['y_train'])
print(cnf_mat_for_trn)
print("------------------------------------------------------------------")

#Creating the confusion matrix for the test set by using pd.crosstab function
print("\n THE CONFUSION MATRIX FOR THE TEST DATA SET : \n")
cnf_mat_for_tst=pd.crosstab(np.reshape(y_pr_test, l_tst), test_data_of_clothing_image_labels, rownames = ['y_predicted'], colnames = ['y_test'])
print(cnf_mat_for_tst)
print("------------------------------------------------------------------")

C_Vals = [0.1,1,10,100,1000] #Array containing regularization parameter values
y_preds = np.zeros((len(C_Vals), len(train_data_of_clothing_image_labels)))
y_preds2= np.zeros((len(C_Vals),len(test_data_of_clothing_image_labels)))

#for my_num in range(len(C_Vals)): 
    #w0,alp = sup_vec_machine_alg(train_data_of_clothing_image_labels, C_Vals[my_num]) 
   # y_prd = [ guessing_algorithm(test_data_of_clothing_images[my_num]) for my_num in range(len(test_data_of_clothing_image_labels))]
   # y_prd2= [ guessing_algorithm(train_data_of_clothing_images[my_num]) for my_num in range(len(train_data_of_clothing_image_labels))]
   # y_preds[my_num] = y_prd
   # y_preds2[my_num]= y_prd2

#first_cl_acc = [np.sqrt(np.mean((train_data_of_clothing_image_labels - y_preds[my_num])**2)) for my_num in range(len(C_Vals))] 
#second_cl_acc= [np.sqrt(np.mean((test_data_of_clothing_image_labels - y_preds2[my_num])**2)) for my_num in range(len(C_Vals))] 




#print()
#plt.figure(figsize=(12,8))
#b= "black"
#plt.scatter(C_Vals, first_cl_acc, color="red")
#plt.scatter(C_Vals, second_cl_acc , color="blue")
#plt.plot(C_Vals, first_cl_acc, color="red")
#plt.plot(C_Vals,second_cl_acc,color="blue")
#plt.title("REGULARIZATION PARAMETER VS ACCURACY")
#plt.xlabel("REGULARIZATION PARAMETER (C)")
#plt.ylabel("Accuracy")
#plt.show()
#print()
#print("-------------------------------------------------------------------------------------")





