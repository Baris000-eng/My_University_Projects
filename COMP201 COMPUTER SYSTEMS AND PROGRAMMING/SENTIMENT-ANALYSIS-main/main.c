#include <stdio.h>
#include <math.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
 char* toLower(char* s);
int main(void) {
   int wordCounter=0;
   FILE *filePointer;
   FILE *filePointer2;
   char *token;
   char *token2;
   char wordArray[1000];
   char array1[5000];
   char array2[5000];
   int  positiveReviewsArray[5000];
   int  negativeReviewsArray[5000];
   int score=0;
   int comparisonNumber=0;
   int comparisonNumber2=0;
   int value=0;
   int secondValue=0;
   int positiveReview4Counter=0;
   int positiveReview3Counter=0;
   int negativeReview2Counter=0;
   int negativeReview1Counter=0;
   int negativeReview0Counter=0;
   double averageScore=0.0;//Initalizing the necessary variables.
   filePointer=fopen("movieReviews.txt","r");//opening the movie reviews file
   filePointer2=fopen("wordList.txt","r");//opening the word list file.
   printf("Enter a word: ");
   scanf("%s",wordArray);//Taking an input string from the user.
   while(fgets(array1,5000,filePointer)!=NULL){
   token= strtok(array1," ");
   while(token!=NULL){
     token= toLower(token);
     comparisonNumber= strcmp(wordArray,token);
     if(comparisonNumber==0){//comparing the token and input string
     value=(int)(array1[0]) -'0';
     score=score+value;
     wordCounter++;//increment the wordCounter variable
     }
     token=strtok(NULL," ");
   }
}
   averageScore=(double)score/(double)wordCounter;//averageScore assignment
   printf("%s appears %0.1f times.\n",wordArray,(double)wordCounter);
   printf("The average score for reviews containg the word %s is %0.4f\n",wordArray,averageScore);
     while(fgets(array1,5000,filePointer)!=NULL){
     token=strtok(array1," ");
     while(fgets(array2,5000,filePointer2)!=NULL){
     token2=strtok(array2,"\r");
     while(token2!=NULL){
     token2=toLower(token2);
     while(token!=NULL){
     comparisonNumber2=strcmp(token,token2);
     if(comparisonNumber2==0){//comparing token2 and token
       secondValue=array1[0]-'0';
       if(secondValue==4){
         positiveReview4Counter++;
       } else if(secondValue==3){
         positiveReview3Counter++;
       } else if(secondValue==2){
         negativeReview2Counter++;
       } else if(secondValue==1){
         negativeReview1Counter++;
       } else {
         negativeReview0Counter++;
       }
     }
     token=strtok(NULL," ");
     }
     token2=strtok(NULL,"\r");
     }
}
}
     positiveReviewsArray[0]=positiveReview4Counter;
     positiveReviewsArray[1]=positiveReview3Counter;
     negativeReviewsArray[0]=negativeReview2Counter;
     negativeReviewsArray[1]=negativeReview1Counter;
     negativeReviewsArray[2]=negativeReview0Counter;//storing the values about positiveness and negativeness in two seperate arrays.
     int maxOfPositive= positiveReviewsArray[0];
     if(positiveReviewsArray[1]>positiveReviewsArray[0]){//finding the maximum of the positive array
        maxOfPositive=positiveReviewsArray[0];
     }
     int maxOfNegative=negativeReviewsArray[0];
     if(negativeReviewsArray[1]>negativeReviewsArray[0]&&negativeReviewsArray[1]>negativeReviewsArray[2]){//find the max of negative array
         maxOfNegative=negativeReviewsArray[1];
     } else if(negativeReviewsArray[2]>negativeReviewsArray[0]&&negativeReviewsArray[2]>negativeReviewsArray[1]){
         maxOfNegative=negativeReviewsArray[2];
     }
     fclose(filePointer);
     fclose(filePointer2);                                            
     printf("%d\n",maxOfPositive);
     printf("%d\n",maxOfNegative);
  
 }  
 char* toLower(char* s) {
   char *p=s;
   while(*p){
   *p=tolower(*p);
   p++;
   }
   return s;
} 
