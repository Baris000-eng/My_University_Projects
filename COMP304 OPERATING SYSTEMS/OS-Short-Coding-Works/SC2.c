#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>


int main(){
  int idNum=0;//initialization
  idNum=fork();
  if(idNum<0){//fail in fork
    return 1;
  } else if(idNum==0){//child process
    execlp("ps","ps","f",(char *)NULL);//using exec
  } else if(idNum>0){//parent process
    wait(NULL);
    printf("Child finished execution\n");
  }
  return 0;
}
