
// Project contributors: Lütfü && Baris 

//EXPLANATIONS OF THE PART-1 && PART-2

Part 1:
The Macros are as follows: we have PAGE_FRAMES assigned as 1024, PAGE_MASK assigned as 1023, and OFFSET_MASK  assigned as 1023. PAGE_MASK and OFFSET_MASK are 1024-1 because of the indexing.
The search_tlb first has int difference, this is then compared with 0 and the maximum of them gets assigned to an int type variable called current. This is for the iteration, where the iteration is done until the tlbindex. Then we have a struct for holding the adress of the tlb entry, and we use % to get the correct index. And we create a struct for holding a tlb entry for the search. If the searched address has the correct logical page, we simply return the searched physical adress using the strurct we created.

In the add_to_tlb function, similarly to search_tlb, we first create the int variables to store the indexes, we have currentIndex used to find the index, which will be added to the tlb, we use modulo (because it is like circular, we go to the start again) and notice that the tlb is like circular, it goes to the start again, and fifo is used. Since we have the tlbindex variable, and it is always updated at the end, we just get the address in tlb array, and replace the logical and physical pages. And lastly we increment the tlbindex by 1, so that tlbindex has the correct value for the next called functions.

We have updateMem fuction, which has two arguments; logical_page and physical_page, we first copy the memory on the backing to the main memory, using memcpy function. then we set pagetable[logical_page] to physical_page, since we need to insert the physical_page into the pagetable.

Then we have the main function, in the while loop, the first TODO is setting the varaibles offset, and logical_page. offset is logical_adress & OFFSET_MASK, and logical_page is set to  PAGE_MASK &  (logical_address >> OFFSET_BITS). Then we have another TODO for the page fault part, since there is a page fault, we first increment the page_faults, then we set physical_page to free_page. And since a  new free_page is set we increment free_page. And then we call the updateMem to update the memory.

Part 2:
The Macros are as follows: we have PAGE_FRAMES assigned as 256, as told in the pdf, PAGE_MASK is assigned as 1023, and OFFSET_MASK  assigned as 1023. PAGE_MASK and OFFSET_MASK are (1024-1=1023) because of the indexing. The functions named search_tlb, add_to_tlb, and updateMem are all similar to the Part1. Only  updateMem has a small difference, in a for loop, pagetable is all set to -1. This funtion is different because on part 1 we had the same size for both physical and virtual addresses, but now the sizes are different. So we need to set them to -1.

There is fifo function which takes one argument, int* free_page. This function is to use the FIFO page replacement algorithm. we store the *free_page in an int called selectedPage, then we update the *free_page. Basically update is: increment the free_page, and take the modulo to go back to the beginning (thinking it like it is circular). And return the selectedPage.

There is a lru function, it has two arguments, first one is int pgFault, which is the number of page faults. And the second argument is int* refTable, which is the reference table for the LRU, for deciding the least recently used page. In the function, if the pageFault number is less than page frames there is no need for page replacement, so there is still space for new pages, and there is no need for page replacement, we simply return -1. But in the other case, we need a replacement. We first initalize two int to store the page index and the value. Then we iterate in the pagetable, on every iteration, if page is not empty, and the value of the page is 0 or page value is bigger than the page that is being iterated in the reference table. If that is the case, we update leastRecentlyUsedPage to the current iteration number (which is iterator variable). And the also update the pageval to the value that is in the refTable[iterator]. Then we return the pagetable[leastRecentlyUsedPage].

And in the main function, we first get the fourth command line argument ( as specified in the pdf) and store it in an array called selectInput, which stores the choice of the page replacement algorithm. Then the first TODO is offset=OFFSET_MASK & logical_address, and int logical_page = PAGE_MASK &  (logical_address >> OFFSET_BITS). These are similar to the part 1. Then the next TODO is to handle when there is a page fault. In that case, we first increment the page_faults by 1, this is the same as part 1. And then we get into a switch case, to decide which page replacement algorithm to choose. If select input is 1, then we use the fifo algorithm, and if selectInput is 1, then we use lru algorithm. And lastly we call the updateMem function to update the memory.

Which Parts of The Project-3 Are Working? 

Both of the parts (Part-1 and Part-2) correctly work. 

How do we complie our code? 
For the compilation of part 1, we first compile it with gcc part1.c -o a.out. Then, we run the part 1 as follows; ./a.out BACKING_STORE.bin addresses.txt


For the compilation of part 2, we first compile it with gcc part2.c -o a.out. Then we run the part 2 as follows; 
if we use the FIFO page replacement, we run it as ./a.out BACKING_STORE.bin addresses.txt p 0
if we use the LRU page replacement, we run it as ./a.out BACKING_STORE.bin addresses.txt p 1
