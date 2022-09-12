#importing the necessary libraries
import pandas as pd
import numpy as np
import math
import matplotlib.pyplot as plt


img_file= 'hw02_images.csv'
lbl_file= 'hw02_labels.csv'

#I have assigned header to None to remove the headers from the data sets.
imgD= pd.read_csv(img_file, header=None, delimiter=",")#reading the file which contains the clothing images
lblD= pd.read_csv(lbl_file, header=None, delimiter=",")#reading the file which contains the labels of the clothing images
clothingImgNum=35000

def safeLogarithm(num):
    eps= math.pow(10,-100)
    res= num+eps
    return(np.log(res))


#Dividing the data set called imgD to the training set and test set
train_data_of_clothing_images = imgD.iloc[0:30000] #Assignment of the first 30000 image data to the training set of imgD.
test_data_of_clothing_images = imgD.iloc[30000:clothingImgNum]#Assignment of the remaining 30000 image data to the test set of imgD.

#Dividing the data set called lblD to the training set and test set
train_data_of_clothing_image_labels = lblD.iloc[0:30000] #Assignment of the first 30000 labels to the training set of lblD.
test_data_of_clothing_image_labels = lblD.iloc[30000:clothingImgNum] #Assignment of the remaining 5000 labels to the test set of lblD.

lenOfLbl= len(train_data_of_clothing_image_labels)
lbls= train_data_of_clothing_image_labels[0]
tstLbls= test_data_of_clothing_image_labels[0]

ind1= train_data_of_clothing_image_labels[(lbls==1)].index #Obtaining the lowest index where label=1 (obtained from the training set of lblD)
ind2= train_data_of_clothing_image_labels[(lbls==2)].index #Obtaining the lowest index where label=2 (obtained from the training set of lblD)
ind3= train_data_of_clothing_image_labels[(lbls==3)].index #Obtaining the lowest index where label=3 (obtained from the training set of lblD)
ind4= train_data_of_clothing_image_labels[(lbls==4)].index #Obtaining the lowest index where label=4 (obtained from the training set of lblD)
ind5= train_data_of_clothing_image_labels[(lbls==5)].index #Obtaining the lowest index where label=5 (obtained from the training set of lblD)


means1= train_data_of_clothing_images.iloc[ind1,:]
means2= train_data_of_clothing_images.iloc[ind2,:]
means3= train_data_of_clothing_images.iloc[ind3,:]
means4= train_data_of_clothing_images.iloc[ind4,:]
means5= train_data_of_clothing_images.iloc[ind5,:]

def my_custom_avr_func(my_arr): #Finds the average of a list
    s= 0
    l= len(my_arr)
    for i in range(0,l):
        s=s+my_arr[i] #summation of the elements in a list
    avrg= s/l #division of the list sum by the number of the list elements
    return avrg

#Estimations of the mean parameters
meanArr1= means1.apply(lambda meanPrm1 : my_custom_avr_func(np.array(meanPrm1)),axis=0) 
meanArr2= means2.apply(lambda meanPrm2 : my_custom_avr_func(np.array(meanPrm2)),axis=0)
meanArr3= means3.apply(lambda meanPrm3 : my_custom_avr_func(np.array(meanPrm3)),axis=0)
meanArr4= means4.apply(lambda meanPrm4 : my_custom_avr_func(np.array(meanPrm4)),axis=0)
meanArr5= means5.apply(lambda meanPrm5 : my_custom_avr_func(np.array(meanPrm5)),axis=0)
print()
print()
print("The Estimation of the Mean Parameters : \n")
m = np.array([meanArr1,meanArr2,meanArr3,meanArr4,meanArr5]) #concatenation of the estimations of the mean parameters
print(m)
stdDeviations_1= train_data_of_clothing_images.iloc[ind1,:]
stdDeviations_2= train_data_of_clothing_images.iloc[ind2,:]
stdDeviations_3= train_data_of_clothing_images.iloc[ind3,:]
stdDeviations_4= train_data_of_clothing_images.iloc[ind4,:]
stdDeviations_5= train_data_of_clothing_images.iloc[ind5,:]

#Estimations of the standard deviation parameters
stdDevArray1= stdDeviations_1.apply(lambda stdDevPrm1 : np.std((np.array(stdDevPrm1))),axis=0)
stdDevArray2= stdDeviations_2.apply(lambda stdDevPrm2 : np.std((np.array(stdDevPrm2))),axis=0)
stdDevArray3= stdDeviations_3.apply(lambda stdDevPrm3 : np.std((np.array(stdDevPrm3))),axis=0)
stdDevArray4= stdDeviations_4.apply(lambda stdDevPrm4 : np.std((np.array(stdDevPrm4))),axis=0)
stdDevArray5= stdDeviations_5.apply(lambda stdDevPrm5 : np.std((np.array(stdDevPrm5))),axis=0)
print()
print()
print("------------------------------------------------------------------------") 
print()
print()
print("The Estimation of the Standard Deviation Parameters : \n")
stdDevs= np.array([stdDevArray1,stdDevArray2,stdDevArray3,stdDevArray4,stdDevArray5]) #concatenation of the estimations of the standard deviation parameters
print(stdDevs)

#Obtaining the lengths of some subsets of the training data set of image labels
l1= len(train_data_of_clothing_image_labels[lbls==1]) #obtaining the length of the sublist where label=1
l2= len(train_data_of_clothing_image_labels[lbls==2]) #obtaining the length of the sublist where label=2
l3= len(train_data_of_clothing_image_labels[lbls==3]) #obtaining the length of the sublist where label=3
l4= len(train_data_of_clothing_image_labels[lbls==4]) #obtaining the length of the sublist where label=4
l5= len(train_data_of_clothing_image_labels[lbls==5]) #obtaining the length of the sublist where label=5
print()
print()
print("------------------------------------------------------------------------")
print()
print()
print("The Estimation of the Prior Probabilities : \n")
print()

#Prior probability estimations
probOf1= l1 / lenOfLbl
probOf2= l2 / lenOfLbl
probOf3= l3 / lenOfLbl
probOf4= l4 / lenOfLbl
probOf5= l5 / lenOfLbl

prProbs= np.array([probOf1,probOf2,probOf3,probOf4,probOf5]) #Concatenation of the estimations of the prior probabilities
print(prProbs)
print()
print()

print("------------------------------------------------------------------------")

def findTheLargestNumber(n1,n2,n3,n4,n5): #finds the maximum of 5 numbers
    if n1>=n2 and n1>=n3 and n1>=n4 and n1>=n5:
        return n1
    elif n2>=n1 and n2>=n3 and n2>=n4 and n2>=n5:
        return n2
    elif n3>=n1 and n3>=n2 and n3>=n4 and n3>=n5:
        return n3
    elif n4>=n1 and n4>=n2 and n4>=n3 and n4>=n5:
        return n4
    return n5

def my_sq(my_num): #finds the square of a number
    r= my_num*my_num
    return r
    
#evaluation of the score values
def evalScr(value,meanValue,sDev,probability): 
    logPrb= math.log(probability)
    logsDev=np.log(sDev)
    diff=value-meanValue
    scr_val=  np.sum((-0.5*np.log(2*math.pi)) - (my_sq(diff)/(2*my_sq(sDev)))-(logsDev)) + logPrb
    return scr_val

#choosing a class depending on the score values
def choose_the_class(my_num):
    val1= evalScr(my_num,m[0],stdDevs[0],0.2)
    val2= evalScr(my_num,m[1],stdDevs[1],0.2)
    val3= evalScr(my_num,m[2],stdDevs[2],0.2)
    val4= evalScr(my_num,m[3],stdDevs[3],0.2)
    val5= evalScr(my_num,m[4],stdDevs[4],0.2)
    #calling the findTheLargestNumber function with the parameters val1,val2,val3,val4, and val5(The purpose is to find the largest score value)
    if(findTheLargestNumber(val1,val2,val3,val4,val5)==val1):
        return 1 
    elif(findTheLargestNumber(val1,val2,val3,val4,val5)==val2):
        return 2
    elif(findTheLargestNumber(val1,val2,val3,val4,val5)==val3):
        return 3
    elif(findTheLargestNumber(val1,val2,val3,val4,val5)==val4):
        return 4
    return 5

print()
print()
print("The Confusion Matrix for the training set: \n")
print()
trnPrd = train_data_of_clothing_images.apply(lambda trnPrm: choose_the_class(trnPrm),axis=1)#choosing a class for each of the 784 pixels in a clothing image
conf_mat1 = pd.crosstab(trnPrd,lbls,rownames = ['y_pred'],colnames = ['y_truth']) #usage of the pd.crosstab function of pandas library to display confusion matrix
print(conf_mat1)

print()
print()
print("-----------------------------------------------")
print("The Confusion Matrix for the test set: \n")
print()
tstPrd = test_data_of_clothing_images.apply(lambda tstPrm: choose_the_class(tstPrm),axis=1)
conf_mat2 = pd.crosstab(tstPrd,tstLbls,rownames = ['y_pred'],colnames = ['y_truth']) #usage of the pd.crosstab function of pandas library to display confusion matrix
print(conf_mat2)

