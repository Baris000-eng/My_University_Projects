#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <math.h>

/*
 * bitParity8bit - returns 1 if the number of 1's in a byte is odd,
 *   else it returns 0	
 *   Examples: bitParity8bit(257) = 0, bitParity8bit(7) = 1
 *   Legal ops: & ( ) >> + 
 *   Do not use a loop!
 */
int bitParity8bit(int input)
{
	// These are all lines of code that you need.
	int mask = 0x09;
	int quarterParity = (input & mask) ^ ((input >> 1) & mask); 
	int mask2 = 0x05;//This bit is the first bit for the case in which the integer is 4-bit.
	int halfParity = (input & mask2) ^ ((input >> 2) & mask2); 
	int mask3 = 0x01;//This bit is the second bit for the case in which the integer is 4-bit.
	return (halfParity & mask3)^ ((halfParity >> 3) & mask3);;
}

/*
 * bitSum8bit - returns count of number of 1's in a byte
 *   Examples: bitSum8bit(257) = 2, bitSum8bit(7) = 3
 *   Legal ops: & ( ) >> + 
 *   Do not use a loop!
 */
int bitSum8bit(int input)
{
	// These are all lines of code that you need.
	int mask = 0x55;
	int quarterSum = (input & mask) + ((input >> 1) & mask); 
	int mask2 = 0x33;
	int halfSum = (input&mask2)+((input>>2)& mask2));
	int mask3 = 0x11;
	return (halfSum & mask3) + ((halfSum >> 2) & mask2);
}

float u2f(unsigned input) {
	unsigned frac_mask = 0x07;
  	unsigned exp_mask = 0x78;
  
  	unsigned frac = input & frac_mask;
  	unsigned exp = (input & exp_mask) >> 3;
  	unsigned sign = input >> 7;
	float num = 0;
	num += (frac >> 2) * 0.5;
	num += ((frac >> 1) & 0x1) * 0.25;
	num += (frac & 0x1) * 0.125;
	if(exp) {
		num += 1.0;
	}
	float two_exp;
	if(exp) 
		two_exp = powf( (float) 2.0, exp - 7.0);
	else
		two_exp = powf( (float) 2.0, -6.0);
	if(sign) {
		num = (-1) * num * two_exp;
	} else {
		num = num * two_exp;
	}
	return num;
}


int main(int argc, char *argv[]) 
{

	if(argc != 3) {
		printf("Usage: %s num1 num2\n", argv[0]);
		return -1;
	}
	int parity_input = atoi(argv[1]);
	int sum_input = atoi(argv[2]);

	// please enter the bit representation of 1.125 
	// as a 1-byte number, 
	// it can be a hexadecimal like 0x3f
	unsigned u1 = ;

	// please enter the bit representation of -6.5
        // as a 1-byte number,
        // it can be a hexadecimal like 0x3f
        unsigned u2 = ;
	printf("%d, %d, %0.3f, %0.1f\n", bitParity8bit(parity_input), bitSum8bit(sum_input), u2f(u1), u2f(u2));
	return 0;
}
