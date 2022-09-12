#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

// you may want to use these functions in your program
// converts a string to lowercase
char* toLower(char* s) {
  for(char *p=s; *p; p++) *p=tolower(*p);
  return s;
}

// converts a string to uppercase
char* toUpper(char* s) {
  for(char *p=s; *p; p++) *p=toupper(*p);
  return s;
}

/*
 * checks if the contents of string str1 and str2 are equal
 * first convert both strings to lowercase or uppercase
 * then use strcmp() function to compare
 * return 1 if equal. Else 0.
 * YOU MAY USE ANY STRING FUNCTION 
 */
int equalStr(char str1[], char str2[]){
	// your code here
       int comparison=0;
        str1=toUpper(str1);
        str2=toUpper(str2);
        comparison= strcmp(str1,str2);
        if(comparison==0)
            return 1;
        
            
            return 0;
}


/*
 * exchanges the contents of string str1 and str2
 * You may assume that length of str1 and str2 is equal
 * YOU MAY USE strcpy function
 */
void exchangeStr(char *str1, char *str2)
{
	// write your code below
        int capacity= 100;
        char chArr[capacity];
        strcpy(chArr, str1);
        strcpy(str1, str2);
        strcpy(str2, chArr);
}


/*
 * Counts the total words in the string str
 * Hint: count the number of instances when there is ' ' or '.' characters
 * DO NOT USE any string functions 
 * returns the count
 */
int countWords(char str[])
{
	// write your code below
            int numberOfTheWords=0;
            int i=0;
        // write your code below
           for (i = 0;str[i] != '\0';i++){
            if ((str[i] == ' ' && str[i+1] != ' ')||(str[i] == '.' && str[i+1] != '.'))
                numberOfTheWords++;
           }
        return numberOfTheWords;

}



int main() 
{

	
	if ((countWords("Hello.") == 1) && (countWords("FaLcOn is a Bird.\0")) == 4){
		printf("countWords test passed!\n");
	}
	else{
		printf("countWords test failed!\n");
	}
	
	char str1[] = "geeks\0"; 
	char str2[] = "forgeeks\0";
	char str3[] = "geeks\0"; 
	char str4[] = "forgeeks\0"; 	
	exchangeStr(str1, str2);

	if (strcmp(str1, str4) == 0 && strcmp(str2, str3)==0){
		printf("exchangeStr test passed!\n");
	}
	else{
		printf("exchangeStr test failed!\n");
	}

	char str5[] = "radar\0";
	char str6[] = "RadaR\0";
	char str7[] = "joke\0";
	char str8[] = "joker\0";
  	
	if (equalStr(str5, str6)==1 && equalStr(str7, str8)==0){
		printf("EqualStr test passed!\n");
	}
	else{
		printf("EqualStr test failed!\n");
	}
	
	return 0;
}

