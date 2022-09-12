#include <stdio.h>

// Name: BARIŞ KAPLAN
// Date:23.11.2020(23 November 2020)

// Answers the Questions 1 and Question 4 here:

// Question 1 a) 
//LINE-4: The first integer argument is passed in the rdi/edi register. Hence, the fourth line copies the argument to a local (offset -4 bytes from the frame pointer value stored in rbp).
//LINE-5: The first integer argument is passed in the rsi/esi register. Thus, the fifth line copies the argument to a local (offset -4 bytes from the frame pointer value stored in rbp).
//LINE-6: The first integer argument is passed in the rdx/edx register. So, the sixth line copies the argument to a local (offset -4 bytes from the frame pointer value stored in rbp).

// Question 1 b) 
//LINE-13: Since the program assigns the value of sum/sum which is 1 to the integer variable sumdivsum, the compiler move 1 to DWORD PTR [rbp-8]. 

// Question 4) // distsq function returns an integer value which gives the square of the longest edge in the right triangle. When we take the square root of the return value of the function distsq, we will find the length of the hypotenuse. Moreover, the square root of the return value of the function distsq gives the distance of an arbitrary point to the origin in the coordinate plane. For example, lets take the point (3,4), then the distance to the origin of this point will be 5. Hence, distsq function is used for calculating the distance of a point to origin in the coordinate plane and for calculating the square of the hypotenuse.


/*
 * someFunction (Task 2)
 * Check for arguments of this function in the manual (all args are int)
 * Read and understand the manual for instructions.
 * returns an integer
 */
int someFunction(int a) {
    int num1= a+2;
    int num2= a-2;
    int mult= num1*num2;
    mult= mult+27;
    return mult;
}


/*
 * distsq (Task 3)
 * Check for arguments of this function in the manual(all args are int)
 * Read and understand the manual for instructions.
 * returns an integer
 */
int distsq(int x,int y) {
   x= x*x;
   y= y*y;
   return x+y;
}



int main() 
{
	if ((someFunction(6) == 59) && (someFunction(10) == 123)){
		printf("someFunction test passed!\n");
	}
	else{
		printf("someFunction test failed!\n");
	}

  if ((distsq(8, 6) == 100) && (distsq(3,4) == 25)){
		printf("distsq test passed!\n");
	}
	else{
		printf("distsq test failed!\n");
	}
	return 0;
}
