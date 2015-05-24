/* Celsius to Fahrenheit Conversion
    Print Celsius values from 0 to 1000 in increments of 0.01 and their
    corresponding Fahrenheit values.
*/

#include <stdio.h>
#include <time.h>

#define TBL_SZ 1000

int main() {
    /* create clocks to measure time */
    clock_t startTime, endTime;
    double executionTime;
    startTime = clock();

	float step = 0.01;
	float cels, fahr;
	int lower = 0;
	int upper = TBL_SZ;

	cels = lower;

	/* header */
	printf("Celsius\tFahrenheit\n");

	while (cels <= upper) {
		fahr = ((9.0/5.0) * cels) + 32.0;
		printf("%3.2f\t\t%6.2f\n", cels, fahr);
		cels = cels + step;
	}

	endTime = clock();
	executionTime = (double) (endTime-startTime);
	printf("Execution Time: %f\n", executionTime);
	/* execution time on gcc: 720,000 */
	return 0;
}


