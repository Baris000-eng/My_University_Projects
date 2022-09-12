
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#include <time.h>
#include <sys/time.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <semaphore.h>
#include <math.h>
#include <stdlib.h>
 /****************************************************************************** 
  pthread_sleep takes an integer number of seconds to pause the current thread 
  original by Yingwu Zhu
  updated by Muhammed Nufail Farooqi
  *****************************************************************************/

/**
 * pthread_sleep takes an integer number of seconds to pause the current thread
 * original by Yingwu Zhu
 * updated by Muhammed Nufail Farooqi
 * updated by Fahrican Kosar
 */
int pthread_sleep(double seconds){
    pthread_mutex_t mutex;
    pthread_cond_t conditionvar;
    if(pthread_mutex_init(&mutex,NULL)){
        return -1;
    }
    if(pthread_cond_init(&conditionvar,NULL)){
        return -1;
    }

    struct timeval tp;
    struct timespec timetoexpire;
    // When to expire is an absolute time, so get the current time and add
    // it to our delay time
    gettimeofday(&tp, NULL);
    long new_nsec = tp.tv_usec * 1000 + (seconds - (long)seconds) * 1e9;
    timetoexpire.tv_sec = tp.tv_sec + (long)seconds + (new_nsec / (long)1e9);
    timetoexpire.tv_nsec = new_nsec % (long)1e9;

    pthread_mutex_lock(&mutex);
    int res = pthread_cond_timedwait(&conditionvar, &mutex, &timetoexpire);
    pthread_mutex_unlock(&mutex);
    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&conditionvar);

    //Upon successful completion, a value of zero shall be returned
    return res;
}


struct timeval tv;

struct timeval tv2;

struct timeval tv3;


time_t curtime;


 
char buffer[30];


pthread_mutex_t mutex; //definition and initialization of the necessary variables
pthread_barrier_t barrier_2;
pthread_barrier_t barrier_queue;
sem_t  mod;
sem_t  com;
sem_t  b_event;
sem_t  b_event_2;
int alive = 1;
int current_time=0;	
int commentatorNum;
int questionNum;
double probability;
double t_speak;
double breaking_event;
int questionCount = 1;
int numComAns = 0;
int numQueue = 0;





double drand ( double low, double high )
{
    return ( (double)rand() * ( high - low ) ) / (double)RAND_MAX + low;//for creating random doubles between low and high, where high is inclusive.
}


   void* b_event_function(void* arg){
                 int length=0;
    		while(alive){
    		sem_wait(&b_event);//for waiting the sem_t variable called b_event
    		if(alive == 0){
    		break;//terminate the while loop if alive is 0.
    		}
		pthread_sleep(5.0);// for making the breaking event 5 second.
		gettimeofday(&tv2, NULL);
		int micro = (tv2.tv_sec - tv.tv_sec) * 1000000 + ((int)tv2.tv_usec - (int)tv.tv_usec);
		int milli = micro/1000;
		tv3.tv_sec = micro/1000000;
		tv3.tv_usec = micro%1000000;
		curtime=tv3.tv_sec;
		strftime(buffer,30,"%T.",localtime(&curtime));	
                 length= strlen(buffer);
		buffer[length-1] = '\0';
		printf("[%s:%ld] ",buffer+3,tv3.tv_usec);
		printf("Breaking news ends\n");
		sem_post(&b_event_2);// giving a signal to the sem_t variable called b_event_2 	
    		}
    		
    }
    
void* moderatorFunc(void* arg){
   for(int x = questionCount; x  <= questionNum;x++){
	sem_wait(&mod);//waiting for the moderator to ask the question
	pthread_mutex_lock(&mutex);
	numComAns = 0;
	numQueue = 0;
	gettimeofday(&tv2, NULL);//calling the gettimeofday function 
	int microsec = (tv2.tv_sec - tv.tv_sec) * 1000000 + ((int)tv2.tv_usec - (int)tv.tv_usec);//getting and storing the microsecond difference
	int millisec = microsec/1000;//getting and storing the millisecond difference
	tv3.tv_sec = microsec/1000000;
	tv3.tv_usec = microsec%1000000;
	curtime=tv3.tv_sec;
	strftime(buffer,30,"%T.",localtime(&curtime));	//usage of strftime to convert the time to string
	buffer[strlen(buffer)-1] = '\0';//to get rid of the dot at the end
	printf("[%s:%ld] ",buffer+3,tv3.tv_usec);
	printf("Moderator asked Question %d\n",x);
	pthread_mutex_unlock(&mutex);
  	sem_post(&com);//giving signal to the commentator to obtain answer.
  	pthread_barrier_wait(&barrier_2);//for making the moderator hit to the barrier. This barrier is for making moderator wait commentator threads
   }
  	alive = 0;
  	sem_post(&b_event);//giving signal to the sem_t variable called b_event.
	
}


void* commentatorFunc(void* arg){
         int microseconds = 0;
	int milliseconds = 0;

        int index = *(int*)arg;
	for(int x = 0 ; x < questionNum; x++){

	sem_wait(&com);//for waiting the subsequent commentator
	pthread_mutex_lock(&mutex);
       
        double pr = drand(0,1);//assigning the pr to a random double between 0 and 1, where 1 is inclusive.
        int breaking_news_happened = 0;// here, breaking event does not occur. So, we assigned this integer variable to 0.   
       
       if(pr!=0.0 && pr*100<=probability*100 &&probability != 0.0){// check whether a commentator speaks or not.    		
       		
			double speak_time = drand(1, t_speak);// assigning the speak_time to a random double between 1 and t_speak, where t_speak is inclusive.
			speak_time = ceilf(speak_time * 1000) / 1000; // to round the speak_time to 3 decimal digits.
			int current_time = 0;
			int integer_part =0;
			double remaining =0.0;
			integer_part = (int) speak_time;//taking the integer part of the speak_time of the commentator
			remaining = speak_time - integer_part;//taking the double part of the speak_time of the commentator
			double br;
					
			gettimeofday(&tv2, NULL);
			microseconds = (tv2.tv_sec - tv.tv_sec) * 1000000 + ((int)tv2.tv_usec - (int)tv.tv_usec);
			milliseconds = microseconds/1000;
			tv3.tv_sec = microseconds/1000000;
			tv3.tv_usec = microseconds%1000000;
			curtime=tv3.tv_sec;
			strftime(buffer,30,"%T.",localtime(&curtime));//to convert the time to string	
			buffer[strlen(buffer)-1] = '\0';// to get rid of the dot at the end
			printf("[%s:%ld] ",buffer+3,tv3.tv_usec);
			printf("Commentator #%d’s turn to speak for %0.3f seconds\n",index,speak_time);
			while(current_time < integer_part && breaking_news_happened !=1){
			       br = drand(0,1);//assigning br to a random double between 0 and 1, where 1 is inclusive
			       if(br!=0.0 && br*100<=breaking_event*100){
			       	gettimeofday(&tv2, NULL);
					microseconds = (tv2.tv_sec - tv.tv_sec) * 1000000 + ((int)tv2.tv_usec - (int)tv.tv_usec);
					milliseconds = microseconds/1000;
					tv3.tv_sec = microseconds/1000000;
					tv3.tv_usec = microseconds%1000000;
					curtime=tv3.tv_sec;
					strftime(buffer,30,"%T.",localtime(&curtime));	
					buffer[strlen(buffer)-1] = '\0';
					printf("[%s:%ld] ",buffer+3,tv3.tv_usec);
			       	         printf("Breaking news!\n");
			                 int len2= 0;


			       	         gettimeofday(&tv2, NULL);//calling gettimeofday function
					microseconds = (tv2.tv_sec - tv.tv_sec) * 1000000 + ((int)tv2.tv_usec - (int)tv.tv_usec);
					milliseconds = microseconds/1000;
					tv3.tv_sec = microseconds/1000000;
					tv3.tv_usec = microseconds%1000000;
					curtime=tv3.tv_sec;
					strftime(buffer,30,"%T.",localtime(&curtime));//to convert the time to string
                                          len2= strlen(buffer);
					buffer[len2-1] = '\0';//to get rid of the dot at the end.
					printf("[%s:%ld] ",buffer+3,tv3.tv_usec);

					
			       	printf("Commentator #%d is cut short due to a breaking news\n",index);
			       	breaking_news_happened = 1;
			       	sem_post(&b_event);   //giving signal to the sem_t variable called b_event.	
			       	sem_wait(&b_event_2);//waiting the sem_t variable called b_event_2.
			       }
				pthread_sleep(1.0);// for making the commentator speak 1 second.
				current_time++;//since we sleep for 1 second,	
				}
			 br = drand(0,1);//assigning br to a random double between 0 and 1, where 1 is inclusive.   
                               int len3= 0;
			 if(br!=0.0 && br*100<=breaking_event*100 && breaking_event != 0.0 && breaking_news_happened !=1){


			       	        gettimeofday(&tv2, NULL);//calling the gettimeofday function
					microseconds = (tv2.tv_sec - tv.tv_sec) * 1000000 + ((int)tv2.tv_usec - (int)tv.tv_usec);
					milliseconds = microseconds/1000;//getting and storing the milliseconds part
					tv3.tv_sec = microseconds/1000000;
					tv3.tv_usec = microseconds%1000000;
					curtime=tv3.tv_sec;
					strftime(buffer,30,"%T.",localtime(&curtime));	//to convert the time to string
					buffer[strlen(buffer)-1] = '\0';//to get rid of the dot at the end
					printf("[%s:%ld] ",buffer+3,tv3.tv_usec);
			 		printf("Breaking news!\n");
			       	         gettimeofday(&tv2, NULL);
					int microseconds = (tv2.tv_sec - tv.tv_sec) * 1000000 + ((int)tv2.tv_usec - (int)tv.tv_usec);
					int milliseconds = microseconds/1000;
					tv3.tv_sec = microseconds/1000000;
					tv3.tv_usec = microseconds%1000000;
					curtime=tv3.tv_sec;
					strftime(buffer,30,"%T.",localtime(&curtime));	//to convert the time to string


                                          len3= strlen(buffer);
					buffer[strlen(buffer)-1] = '\0';//to get rid of the dot at the end
					printf("[%s:%ld] ",buffer+3,tv3.tv_usec);
			 		printf("Commentator #%d is cut short due to a breaking news\n",index);
			              	sem_post(&b_event);//for giving signal to the sem_t variable called b_event
			       	         sem_wait(&b_event_2);//for waiting the sem_t variable called b_event_2
			 }
			else{	
			     if(breaking_news_happened !=1){

			                pthread_sleep(remaining);
			       	        gettimeofday(&tv2, NULL);//calling the gettimeofday function
					microseconds = (tv2.tv_sec - tv.tv_sec) * 1000000 + ((int)tv2.tv_usec - (int)tv.tv_usec);
					milliseconds = microseconds/1000;//getting and storing the milliseconds part.
					tv3.tv_sec = microseconds/1000000;
					tv3.tv_usec = microseconds%1000000;
					curtime=tv3.tv_sec;
					strftime(buffer,30,"%T.",localtime(&curtime));//to convert the time to string
					buffer[strlen(buffer)-1] = '\0';//for getting rid of the dot at the end.
					printf("[%s:%ld] ",buffer+3,tv3.tv_usec);
			                printf("Commentator #%d finished speaking\n",index);
			}		
			}	
	
	}
	else{
		//pthread_barrier_wait(&barrier_queue);//---------------------------------------------------------------------------------
	}	
	numComAns++;//increase the total number of commentator answers 
	
	if(numComAns<commentatorNum){//checking if all the commentators does not answer the question
		pthread_mutex_unlock(&mutex);	
		sem_post(&com);//for giving a signal to the subsequent commentator
	}
	else{
		pthread_mutex_unlock(&mutex);	
		sem_post(&mod);// for giving a signal to the subsequent moderator
	}
	
	pthread_barrier_wait(&barrier_2);
}
free(arg);
    }
   

int main(int argc, char* argv[]) {
 	
 	gettimeofday(&tv, NULL);//calling the gettimeofday function at the beginning of the main
        time_t t;
        srand((unsigned) time(&t));//for the initialization for the randomization of the times.
        for(int x = 1 ; x <10; x= x+2){// we used argv[x] for the execution in the for loop to make sure that the command line arguments part work regardless of the order of command line arguments.
	    if(strcmp("-n",argv[x])==0){//command line argument for commentator number
	    	commentatorNum = atoi(argv[x+1]);//converting the subsequent argument to integer
	    }
	  
	   else if(strcmp("-p",argv[x])==0){//command line argument for the probability of speaking
	    	probability = atof(argv[x+1]);//converting the subsequent argument to double
	    }
	   else if(strcmp("-q",argv[x])==0){//command line argument for question number
	    	questionNum = atoi(argv[x+1]);//converting the subsequent argument to integer
	    } 
	    else if(strcmp("-t",argv[x])==0){//command line argument for the t_speak
	    	t_speak = atoi(argv[x+1]);//converting the subsequent argument to integer
	    }
	    else if(strcmp("-b",argv[x])==0){//command line argument for the breaking event
	    	breaking_event = atof(argv[x+1]);//converting the subsequent argument to double
	    }
    }
  
    int i=0;
    pthread_mutex_init(&mutex, NULL);//initializing the semaphores, mutexes, and the barriers.
    pthread_barrier_init(&barrier_2, NULL, commentatorNum+1);
    pthread_barrier_init(&barrier_queue, NULL, commentatorNum);	
    sem_init(&mod, 0, 1);
    sem_init(&com, 0 ,0);
    sem_init(&b_event, 0, 0);
    sem_init(&b_event_2, 0, 0);
    
  
	          pthread_t th[commentatorNum];
    		  pthread_t mod;
    		  pthread_t b_event_thread;
           
           
           if (pthread_create(&mod, NULL, &moderatorFunc, NULL) != 0) {//creating the moderator thread with a reference to moderatorFunc.
		    perror("Failed to create thread");
		    return 1;
	   }

	    for (i = 0; i < commentatorNum; i++) {
	  	int* a = malloc(sizeof(int));
		*a = i;
		if (pthread_create(&th[i], NULL, &commentatorFunc, a) != 0) {//creating the commentator threads with a reference to commentatorFunc.
		    perror("Failed to create thread");
		    return 1;
		}
		
	    }

	    if (pthread_create(&b_event_thread, NULL, &b_event_function, NULL) != 0) {//creating the b_event thread with a reference to the b_event_function.
		    perror("Failed to create thread");
		    return 1;
	    }
	    
	    
	    
	  
	
	     if (pthread_join(mod, NULL) != 0) {//joining the moderator thread
		    return 3;
		}
	    if (pthread_join(b_event_thread, NULL) != 0) {//joining the b_event_thread
		    return 3;
		}
	    for (i = 0; i < commentatorNum; i++) {
		if (pthread_join(th[i], NULL) != 0) {//joining the commentator thread.
		    return 2;
		}
	  
	    }

	   
	 pthread_exit(0);
         pthread_mutex_destroy(&mutex);
         pthread_barrier_destroy(&barrier_2);
         pthread_barrier_destroy(&barrier_queue);
         sem_destroy(&mod);
         sem_destroy(&com);
         sem_destroy(&b_event);
         sem_destroy(&b_event_2);

         return 0;

}
