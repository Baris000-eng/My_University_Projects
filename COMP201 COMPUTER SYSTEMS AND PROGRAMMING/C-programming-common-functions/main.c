#include <stdio.h> // printf, scanf
#include <stdlib.h> // malloc
#include "mylibrary.h"
#include "myprogram.h"



int main()
{
	int n, res;
	fprintf(stderr, "Which task to run? (1-7) ");
	scanf("%d", &n);

	if (n < 1 || n > 7)
	{
		fprintf(stderr, "Invalid task number.\n");
		return -1;
	}
	fprintf(stderr, "Task %d selected.\n", n);
	if (n == 1)
	{
		fprintf(stderr, "Enter the number: ");
		scanf("%d", &n);
		res = sum_of_1_to_n(n);
		printf("%d\n", res);
	}
	else if (n == 2)
	{
		fprintf(stderr, "How many numbers? ");
		scanf("%d", &n);
		int *a = (int *)malloc(sizeof(int)*n);
		for (int i=0;i<n;++i)
			scanf("%d", &a[i]);
		res = sum_of_even_numbers(a, n);
		printf("%d\n", res);
	}
	else if (n == 3)
	{
		fprintf(stderr, "How many numbers? ");
		scanf("%d", &n);
		int *a = (int *)malloc(sizeof(int)*n);
		for (int i=0;i<n;++i)
			scanf("%d", &a[i]);
		res = max_of_numbers(a, n);
		printf("%d\n", res);
	}
	else if (n == 4)
	{
		fprintf(stderr, "Enter the number: ");
		scanf("%d", &n);
		res = reversed_number(n);
		printf("%d\n", res);
	}
	else if (n == 5)
	{
		fprintf(stderr, "Enter the number: ");
		scanf("%d", &n);
		res = is_prime(n);
		printf("%d\n", res);
	}
	else if (n == 6)
	{
		int start, end;
		fprintf(stderr, "Enter start and end numbers: ");
		scanf("%d%d", &start, &end);
		res = count_primes(start, end);
		printf("%d\n", res);
	}
	else if (n == 7)
	{
		fprintf(stderr, "Enter the index: ");
		scanf("%d", &n);
		printf("%c\n", alphabet_index(n));
	}

	wrapper();
	return 0;
}
