
#include <stdint.h> // uint32
#include <stdlib.h> // exit, perror
#include <unistd.h> // usleep
#include <stdio.h> // printf
#include <pthread.h> // pthread_*
#ifdef __APPLE__
	#include <dispatch/dispatch.h>
	typedef dispatch_semaphore_t psem_t;
#else
	#include <semaphore.h> // sem_*
	typedef sem_t psem_t;
#endif
void psem_init(psem_t *sem, uint32_t value);
void psem_wait(psem_t sem);
void psem_post(psem_t sem);

void lock();
void unlock();


// Make the program a bit slower
#define SLOWDOWN usleep(1000)

#define total_stock 1000
int stock = total_stock;
int sold = 0;


pthread_t tid[1024] = {0}; // Max number of threads
int thread_count = 0; // Total number of utilized threads


// Sell 1 stock
void *sell(void *vargp)//to obtain the race condition, remove the lock() and unlock() calls from the *sell() function
{    
	  lock(); //to fix the race condition, put the lock() call to the beginning of the *sell function
	// TODO : Add lock()/unlock() calls in this function in appropriate places.
	SLOWDOWN;
	if (stock > 0)
	{
		SLOWDOWN;
		stock -= 1;
		SLOWDOWN;
		sold += 1;
		printf("Sold. ");
	}
	else
	{
   	printf("No more stocks available.\n");
	}
         unlock();//to fix the race condition, put the unlock() call here.
         return NULL;
}

// Calls func() in a new thread
void create_new_thread(void *(func)(void* vargp))
{
	pthread_create(&tid[thread_count++], NULL, func, NULL);
}

// TODO : define any global variables you need here:
sem_t smp;
//Here, the type of the global variable is sem_t.
//Here, the name of the global variable is smp.
//We can use psem_t smp instead of sem_t smp.


// This code should result in more sales than stocks available
// by abusing the race condition
void trigger_race_condition()
{
	int countVar=0; 
    //initializing the integer variable called countVar.

        for(countVar=0;countVar<999;countVar++){
             create_new_thread(&sell);
        }

}

// Initialization code
void init()
{
	// TODO : code here
        sem_init(&smp,0,1);
        // Here, we can use psem_init(&smp,1) instead of sem_init(&smp,0,1)
        
        
}
// Acquire a lock
void lock()
{
	// TODO : code here
        sem_wait(&smp);  
        //Here, we can use psem_wait(smp) instead of sem_wait(&smp).
}

// Release an acquired lock
void unlock()
{
	// TODO : code here
        sem_post(&smp);
       //Here, We can use psem_post(smp) instead of sem_post(&smp).
        
        
}

int main()
{
	init();

	trigger_race_condition();

	// Wait for all threads to finish execution
	for (int i=0; i<thread_count; ++i)
		pthread_join(tid[i], NULL);

	printf("\n");
	if (stock + sold == total_stock)
		printf("All done. %d stock left and %d sold (total %d).\n", stock, sold, total_stock);
	else
		printf("What just happened?! We have %d stock left but sold %d (total %d)!\n",
			stock, sold, stock+sold);

	return 0;
}

// psem_* functions are sem_* alternatives compatible on both macOs and Linux.
#ifdef __APPLE__
void psem_init(psem_t *sem, uint32_t value) {
	*sem = dispatch_semaphore_create(value);
}
void psem_wait(psem_t sem) {
	dispatch_semaphore_wait(sem, DISPATCH_TIME_FOREVER);
}
void psem_post(psem_t sem) {
	dispatch_semaphore_signal(sem);
}
#else
void psem_init(psem_t *sem, u_int32_t value) {
	sem_init(sem, 0, value);
}
void psem_wait(psem_t sem) {
	sem_wait(&sem);
}
void psem_post(psem_t sem) {
	sem_post(&sem);
}
#endif
