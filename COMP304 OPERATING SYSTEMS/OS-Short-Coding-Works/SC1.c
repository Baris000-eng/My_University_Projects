#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include<sys/wait.h> 

int main(){
  int level=0;
  int i=0;
  int id=0;//initialization
  printf("Base Process ID:  %d,level: %d\n",getpid(),level);//for base process 
  for(i=0;i<4;i++){//iterating
    id=fork();
    if(id==0){//child process
      level++;//incrementing level
      printf("Process ID: %d,Parent ID: %d,level: %d\n",getpid(),getppid(),level);
    } else if(id>0){//parent process
      wait(NULL);
    }
  }
}
