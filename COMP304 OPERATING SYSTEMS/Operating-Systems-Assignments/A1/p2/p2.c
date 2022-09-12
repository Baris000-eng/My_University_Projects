#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <signal.h>
#include <time.h>
#include <sys/time.h>

int fd1[2];
int fd2[2];
int r=0;
int w=1;
int forkNumber=0;
int forkNumber2=0;//initialization of necesssary variables

int main(void){
  char string[40];
  struct timeval tv;
  time_t curtime;
  time_t curtime2;
  char string2[40];
  char string3[40];
  time_t curtime3;
  time_t curtime4;
  char string4[40];//definition of necessary variables 
  pipe(fd1);//first pipe creation
  forkNumber=fork();//first fork() call
  if(forkNumber<0){//fail in forking
    return 1;
  } else if(forkNumber>0){//parent process
    gettimeofday(&tv,NULL);
    curtime=tv.tv_sec;
    strftime(string,40,"%T",localtime(&curtime));
    write(fd1[w],string,sizeof(string));
    close(fd1[w]);
    wait(NULL);
    read(fd1[r],string4,sizeof(string4));
    printf("The current time is %s\n",string4);
    close(fd1[r]);
    kill(forkNumber,SIGKILL);
    exit(0);
  } else if(forkNumber==0){//child process
    read(fd1[r],string,sizeof(string));
    printf("The current time is %s\n",string);
    close(fd1[r]);
    sleep(3);
    pipe(fd2);//second pipe creation
    forkNumber2= fork();//second fork call
    if(forkNumber2<0){//fail in fork
      return 1;
    } else if(forkNumber2>0){
      gettimeofday(&tv,NULL);//usage of gettimeofday() function to get the current time
      curtime2=tv.tv_sec;
      strftime(string2,40,"%T",localtime(&curtime2));//converting time_t variable to string
      write(fd2[w],string2,sizeof(string2));
      wait(NULL);
      read(fd2[r],string3,sizeof(string3));
      printf("The current time is %s\n",string3);
      sleep(3);
      gettimeofday(&tv,NULL);
      curtime4=tv.tv_sec;
      strftime(string4,40,"%T",localtime(&curtime4));
      write(fd1[w],string4,sizeof(string4));
      close(fd1[w]);
      kill(forkNumber2,SIGKILL);
    } else if(forkNumber2==0){
      read(fd2[r],string2,sizeof(string2));
      printf("The current time is %s\n",string2);
      close(fd2[r]);
      sleep(3);
      gettimeofday(&tv,NULL);
      curtime3=tv.tv_sec;
      strftime(string3,40,"%T",localtime(&curtime3));
      write(fd2[w],string3,sizeof(string3));
      close(fd2[w]);
    }
  }
}


