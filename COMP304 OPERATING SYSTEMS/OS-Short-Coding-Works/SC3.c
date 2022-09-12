#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>


int main(){
  int defaultNumber=0;
  int idNumber=0;
  int sleepNumber=0;
  int exitNumber=0;//initializing necessary variables 
  idNumber=fork();
  if(idNumber>0){//parent process
    sleepNumber=5;
    sleep(sleepNumber);
  } else if(idNumber<0){//fail in forking 
    defaultNumber=-1;
    return defaultNumber;
  } else if(idNumber==0){//child process
    exit(exitNumber);
  }
    return 0;
}
