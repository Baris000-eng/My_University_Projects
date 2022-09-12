#include "mylibrary.h"
int sum_of_1_to_n(int n)
{
    int sum = 0;
    int i=0;
    for(i=0;i<=n;i++){
        sum=sum+i;
    }


    //TODO: sum all numbers from 1 to n

    return sum;
}

int sum_of_even_numbers(int *array, int count)
{
	int sum = 0;
	int remainder=0;
	for (int i=0;i<count;++i)
	{     
		remainder=(array[i]%2);
		if((remainder)==0){//taking the even numbers from the array
		//TODO: only add even numbers, e.g., 4. Skip odd numbers, e.g., 3
		sum += array[i];
        }
	}
	return sum;
}

int sum_of_odd_numbers(int *array, int count)
{
	int sum = 0;
	int remainder=0;
	for (int i=0;i<count;++i)
	{     
		remainder=(array[i]%2);
		if((remainder)==1){ 
		
		sum += array[i];
        }
	}
	return sum;
}
int max_of_numbers(int *array, int count)
{       
	//TODO: return the maximum number from the array
	int maximumNumber= array[0];
	int length= sizeof(array);
	for(int i=0;i<length;i++){
		if(array[i]>maximumNumber){// if an array item is bigger than maximumNumber
			maximumNumber= array[i];//assigning the maximumNumber to that item.
		}
	}
	return maximumNumber;
}
int min_of_numbers(int *array, int count)
{       
	//TODO: return the maximum number from the array
	int minimumNumber= array[0];
	int length= sizeof(array);
	for(int i=0;i<length;i++){
		if(array[i]<minimumNumber){// if an array item is 
    //smaller than the minimumNumber
			minimumNumber= array[i];//assigning the minimumNumber to that item
		}
	}
	return minimumNumber;
}


int reversed_number(int number)
{       
	int reversed = 0;
	int remainder=0;
        while(number!=0){
		remainder=(number%10);
		reversed=(10*reversed)+(remainder);
		number=(number/10);
        }
	//TODO: if input is 12345, return 54321

	return reversed;
}

int is_prime(int number)
{
    //TODO: return 1 if the input number is prime, otherwise 0
    int b=0;
    int halfNum=0;
    int remainder=0;
    int isPrimeNumber=1;
    halfNum=number/2;
    if(number==0||number==1){
        return !isPrimeNumber;//if number is 0 or 1, return 0.
    } else {
        for(b=2;b<=halfNum;b++){//checking whether the number is prime or not
            remainder=number%b;
            if((remainder)==0){
                isPrimeNumber=0;
            }
        }
    }
    return(isPrimeNumber);
}
int count_primes(int start, int end)
{       int primeCount=0;
        int temporaryVar=0;
        int i=0;
	//TODO: return the count of prime numbers in range [start, end] inclusive.
        if(start>end){//changing the places of the start value and end value
		temporaryVar=start;
		start=end;
		end=temporaryVar;
    }
          for(i=start;i<=end;i++){
		    if(is_prime(i)){//if the number i is prime
                primeCount++;//increment the primeCount by one.
           }
	  }
	return primeCount;
}


char alphabet_index(int index)
{      
	//TODO: for index 0, return A. index 1, B, etc. until 25 for Z.
	//if index is out of range, return space ' '.
	if(index==0){
		return 'A';
	} else if(index==1){
		return 'B';
	} else if(index==2){
		return 'C';
	} else if(index==3){
		return 'D';
    } else if(index==4){
		return 'E';
	} else if(index==5){ 
		return 'F';
	} else if(index==6){
		return 'G';
	} else if(index==7){
		return 'H';
	} else if(index==8){
		return 'I';
	} else if(index==9){ 
		return 'J';
	} else if(index==10){ 
		return 'K';
	} else if(index==11){
		return 'L';
	} else if(index==12){
		return 'M';
	} else if(index==13){ 
		return 'N';
	} else if(index==14){
		return 'O';
	} else if(index==15){ 
		return 'P';
	} else if(index==16){ 
		return 'Q';
	} else if(index==17){ 
		return 'R';
	} else if(index==18){
		return 'S';
	} else if(index==19){ 
		return 'T';
	} else if(index==20){ 
		return 'U';
	} else if(index==21){ 
		return 'V';
	} else if(index==22){
		return 'W';
	} else if(index==23){ 
		return 'X';
	} else if(index==24){ 
		return 'Y';
	} else if(index==25){
		return 'Z';
	} 
        return ' ';
}
