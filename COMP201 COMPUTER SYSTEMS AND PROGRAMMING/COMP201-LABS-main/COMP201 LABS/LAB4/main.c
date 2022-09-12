/**
 * This program computes the sum of the first n
 * prime numbers.  Optionally, it allows the user
 * to provide n as a command line argument, but
 * defaults to the first n = 10 primes
 */
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
int sum(int *arr,int n);
int *getPrimes(int n);
int isPrime(int x);

/**
 * This function takes an integer array (of size n) and
 * returns the sum of its elements.  It returns 0 if the
 * array is NULL or if its size is <= 0
 */
int sum(int *arr, int n) {
  int i=0;
  int total;
  for(i=0; i<n; i++) {
    total+=arr[i];
  }
  return total;
}

/**
 * This function returns an array containing the first
 * n prime numbers
 */
int* getPrimes(int n) {
  int result[n];
  int i = 0;
  int x = 2;
  while(i < n) {
    if(isPrime(x)) {
      result[i] = x;
      i++;
    }
    x=x+1;
  }
  return result;
}

/**
 * This function determines returns true if the give
 * integer is prime and false otherwise.
 */
int isPrime(int x) {
  if(x % 2 ==0&&x!=2) {
    return 0;
  }
  for(int i=3; i<=sqrt(x); i+=2) {
    if(x % i == 0) {
      return 0;
    }
  }
  return 1;
}

int main(int argc, char **argv) {

  int n = 10; //default to the first 10 primes
  if(argc == 2) {
    atoi(argv[2]);
  }

  int *primes = getPrimes(n);

  int s = sum(primes, n);
  printf("The sum of the first %d primes is %d\n", n, s);

  return 0;
}




