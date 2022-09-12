/**
 * virtmem.c 
 */

#include <stdio.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>

#define TLB_SIZE 16
#define PAGES 1024
#define PAGE_FRAMES 256 
#define PAGE_MASK 1023 /* TODO */

#define PAGE_SIZE 1024
#define OFFSET_BITS 10
#define OFFSET_MASK 1023/* TODO */


#define MEMORY_SIZE PAGES * PAGE_SIZE

// Max number of characters per line of input file to read.
#define BUFFER_SIZE 10

struct tlbentry {
   int logical;
   int physical;
};

// TLB is kept track of as a circular array, with the oldest element being overwritten once the TLB is full.
struct tlbentry tlb[TLB_SIZE];
// number of inserts into TLB that have been completed. Use as tlbindex % TLB_SIZE for the index of the next TLB line to use.
int tlbindex = 0;

// pagetable[logical_page] is the physical page number for logical page. Value is -1 if that logical page isn't yet in the table.
int pagetable[PAGES];

signed char main_memory[MEMORY_SIZE];

// Pointer to memory mapped backing file
signed char *backing;

int max(int a, int b)
{
  if (a > b)
    return a;
  return b;
}


// FIFO
int fifo(int *freePg){   //get the address of the free page
	int selectedPage = *freePg;   //store this in a temp varaible
	
	*freePg = (*freePg + 1) % PAGE_FRAMES;
	return selectedPage; //return the old 
}

// LRU  algorithm for page replacement algorithm that uses counting table
int lru(  int pgFault, int* refTable) {
  // If the pageFault number is less than page frames there is no need for page replacement
  // which means there are still space for new pages without replacement
  if(pgFault <= PAGE_FRAMES) {  //no page replacement 
    return pgFault - 1;  //return -1 if there are still space.
  }
  
  int leastRecentlyUsedPage = 0;    //store the index of the leastRecentlyUsedPage
  int pageVal = 0;                  //store the value of the leastRecentlyUsedPage

  int iterator;
  for(iterator = 0; iterator < PAGES; iterator++) {
    if(pagetable[iterator] != -1) {    //check if page is empty or not
      if( (pageVal > refTable[iterator]) || (pageVal == 0) ) {      //check if pageVal is zero or if pageVal is bigger than refTable
        leastRecentlyUsedPage = iterator;  //update leastRecentlyUsedPage index
        pageVal = refTable[iterator];  //update pageVal with the value of the reference table
      }
    }
  }
  
return pagetable[leastRecentlyUsedPage];  //return the corresponding value in the pagetable
}


//update the page table and update the memory
void updateMem(int logical_page, int physical_page){
         memcpy(main_memory + physical_page * PAGE_SIZE, backing + logical_page * PAGE_SIZE, PAGE_SIZE);  //copying from backing to the memory

	 int pageNum;
	 for( pageNum = 0; pageNum < PAGES; pageNum++){    //find the pagetable entry to replace
	 	if(pagetable[pageNum] == physical_page){   
	 		pagetable[pageNum] = -1;
	 	}
	 }
	 pagetable[logical_page] = physical_page;  //inserting the physical_page into the pagetable
}

/* Returns the physical address from TLB or -1 if not present. */
int search_tlb(int logical_page) {      // if tlb hit, return the physical adress from the TLB, else return -1.
    /* TODO */    
    int difference; 
    difference = tlbindex - TLB_SIZE;
    int current;
    current = max(difference,0);
    
    int x = 0;
    for(  x = current ; x < tlbindex ; x++){  
	     int currentIndex;
   	     currentIndex = x % TLB_SIZE;    //get the current index to use in the tlb, using modulo operation since tlb is thought as circular in this project.
	    if ( (&tlb[currentIndex])->logical == logical_page) {  //find the searched entry
	    	return  (&tlb[currentIndex])->physical;    //return the physical adress from the tlb 
	    }
   }
    return -1; 
    /* TODO */
}

/* Adds the specified mapping to the TLB, replacing the oldest mapping (FIFO replacement). */
void add_to_tlb(int logical, int physical) {  //update the TLB, use FIFO
    /* TODO */
    int currentIndex;
    currentIndex = tlbindex % TLB_SIZE;           //find the current index for the TLB by using modulo operation (thinking like a circular)
    (&tlb[currentIndex])->logical = logical;      //update logical page
    (&tlb[currentIndex])->physical = physical;    //update the physical page
    tlbindex++;                                   //since we add to the TLB, we incremented the TLb index.
    /* TODO */
}

int main(int argc, const char *argv[])
{
  int pageReferenceTable[PAGES];  //this is for LRU
  
  int a;
  
  for( a = 0 ; a < PAGES ; a ++ ){   //initalizing the pageReferenceTable elements to 0
  	pageReferenceTable[a] = 0;
  }
  
  int selectInput =0;  //store the choice of page replacement algorithm
  selectInput = atoi(argv[4]);   //get the fourth command line argument ( as specified in the pdf)
  
  printf("%s", argv[2]);   
  
  const char *backing_filename = argv[1]; 
  int backing_fd = open(backing_filename, O_RDONLY);
  backing = mmap(0, MEMORY_SIZE, PROT_READ, MAP_PRIVATE, backing_fd, 0); 
  const char *input_filename = argv[2];
  FILE *input_fp = fopen(input_filename, "r");
  // Fill page table entries with -1 for initially empty table.
  int i;
  for (i = 0; i < PAGES; i++) {
    pagetable[i] = -1;
  }
  
  // Character buffer for reading lines of input file.
  char buffer[BUFFER_SIZE];
  
  // Data we need to keep track of to compute stats at end.
  int total_addresses = 0;
  int tlb_hits = 0;
  int page_faults = 0;
  
  // Number of the next unallocated physical page in main memory
  int free_page = 0;
  
  while (fgets(buffer, BUFFER_SIZE, input_fp) != NULL) {
    total_addresses++;
    int logical_address = atoi(buffer);

    /* TODO */
    // Calculate the page offset and logical page number from logical_address */
    int offset =  OFFSET_MASK & logical_address; 
    
    int logical_page = PAGE_MASK &  (logical_address >> OFFSET_BITS);
    
    
    
    /* TODO */
    
      if(selectInput == 1) { 
      pageReferenceTable[logical_page] = total_addresses;
    }
    
    int physical_page = search_tlb(logical_page);
    // TLB hit
    if (physical_page != -1) {
      tlb_hits++;
      // TLB miss
    } else {
      physical_page = pagetable[logical_page];
      
      // Page fault
      if (physical_page == -1) {
          /* TODO */
          
          //There is a page fault 
          page_faults++;    //since there is a page fault, increment the page_faults
         switch (selectInput){ //choose which page replacement algorithm to use depending on the value of the selectInput
         	case 0: //if selectInput is 0, choose fifo
         		physical_page = fifo(&free_page);   //fifo
      			break;
    		case 1: //if selectInput is 1, choose lru
              		physical_page = lru(page_faults,pageReferenceTable);
      			break;
    		default:
    		printf("invalid\n");     
}
           //Copy page from backing file into pyhsical memory 
           updateMem(logical_page, physical_page);
           }         
     	  /* TODO */

      add_to_tlb(logical_page, physical_page);
    }
    
    int physical_address = (physical_page << OFFSET_BITS) | offset;
    signed char value = main_memory[physical_page * PAGE_SIZE + offset];
    
    printf("Virtual address: %d Physical address: %d Value: %d\n", logical_address, physical_address, value);
  }
  
  printf("Number of Translated Addresses = %d\n", total_addresses);
  printf("Page Faults = %d\n", page_faults);
  printf("Page Fault Rate = %.3f\n", page_faults / (1. * total_addresses));
  printf("TLB Hits = %d\n", tlb_hits);
  printf("TLB Hit Rate = %.3f\n", tlb_hits / (1. * total_addresses));
  
  return 0;
}

