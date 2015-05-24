/* Program: FindMax v2 (using pointers to access arrays)
* Author: Eliot Cowley
* Date: 2/6/2015
* CS377 Parallel Programming
*
* Description:
* Write a program that finds the max of N numbers.
* The program will prompt the user for a list of
* numbers, which will be read from stdin using
* the C library function, scanf().
*
* This is a first program in C. While the problem
* is simple, many core features of the language
* will be mastered along the way.
*
* This program will use arrays, which in C are
* just pointers. You will write three versions of
* the program, one using array notation ( []'s ),
* a second using pointers ( * ), and a third using
* dynamic memory allocation.
*
* Fill an array of int values, prompting the user from stdin;
* echo the list of numbers entered, then find the max and print it
*/

/* include standard library */
#include <stdio.h>

/* constants */
#define N 10 /* number of numbers to input */

/* declare variables */
int nums[N]; /* array of input numbers */
int *pointer; /* pointer to array */

/* declare functions */
void init_numbers(int n, int* numbers);
int read_numbers(int* numbers);
void print_numbers(int n, int* numbers);
int find_max(int n, int* numbers);

int main()
{
	init_numbers(N, nums);
	read_numbers(nums);
	print_numbers(N, nums);
	find_max(N, nums);
	return 0;
}

/* initialize n elements of numbers array to -1 */
void init_numbers(int n, int * numbers)
{
	int i;
	
	pointer = numbers;
	
	for (i = 0; i < n; i++) {
		*(pointer + i) = -1;
	}
}

/* read numbers from stdin */
int read_numbers(int * numbers)
{
	printf("Please enter %d numbers: ", N);
	int i;
	int n;
	pointer = numbers;
	for (i = 0; i < N; i++) {
		scanf("%d", &n);
		*(pointer + i) = n;
	}
}

/* print n elements of given array */
void print_numbers(int n, int* numbers)
{
	printf("Numbers you input: ");
	int i;
	pointer = numbers;
	for (i = 0; i < n; i++) {
		printf("%d ", *(pointer + i));
	}
	printf("\n");
}
/* find max from first n numbers in given array */
int find_max(int n, int* numbers)
{
	int i;
	int max = 0;
	pointer = numbers;
	for (i = 0; i < n; i++) {
		if (*(pointer + i) > max) {
			max = *(pointer + i);
		}
	}
	printf("Max: %d\n", max);
}