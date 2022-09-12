#include <stdio.h>

void print_age(int age){
  printf("%d\n",age);
}


int ask_birth_year(){
    int birthYear=0;
    printf("Please enter your birth year: \n");
    scanf("%d",&birthYear);
    return birthYear;
}