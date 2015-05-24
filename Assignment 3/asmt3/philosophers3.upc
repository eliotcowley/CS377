/*
 * File: philosophers3.upc
 * Written by: Tarek El-Ghazawi, William Carlson, Thomas Sterling,
 *             and Katherine Yelick
 * Modified by: Marc Smith
 * Modified further by: Eliot Cowley
 * Date: April 4, 2015
 * 
 * Compile: upcc -T 5 philosophers3.upc
 * Run:     upcrun philosophers3
 *
 * Description: UPC implementation of the Dining Philosophers problem, using 
 * upc_lock() instead of upc_lock_attempt(). To avoid deadlock, a "waiter" seats 
 * at most four philosophers at a time at the table.
 *
 */

#include <stdio.h>
#include <upc_relaxed.h>

typedef enum{THINKING, STARVING, EATING} philosopher_status_t;
typedef enum{STANDING, SITTING} philosopher_table_t;
upc_lock_t * shared a_fork[THREADS];
upc_lock_t * shared table;

// with five philosophers, only four will be seated at the table
/* treat as bools--0 for not occupied, 1 for occupied--initially no seats are 
occupied */
int shared a_table[THREADS-1]; 
int shared num_seated = 0;

/*
 * Allocate shares resources: forks (really chopsticks)
 */
void initialize(void)
{
  a_fork[MYTHREAD] = upc_global_lock_alloc();
  table = upc_global_lock_alloc();
}

/*
 * Simulates the lifecycle of a single dining philosopher
 */
void life_cycle(void)
{
  philosopher_status_t state;
  philosopher_table_t table_state;
  int num_meals=0,
      delay_thinking=1,
      delay_eating=2;
  int left, right, 
      got_left, got_right;

  state = THINKING;
  table_state = STANDING;

  while (num_meals < 20) // life of each philo. is 20 meals
  { 
    if (state == THINKING) 
    {
      printf("Philosopher%2d:---I am thinking\n", MYTHREAD);
      sleep(delay_thinking);
      printf("Philosopher%2d:---I finished thinking, now I am starving\n", 
             MYTHREAD);
      state = STARVING;
    }

    if (table_state == STANDING) {
	upc_lock(table);
	// if there is at least one empty seat
	if (num_seated < (THREADS-1)) {
		// loop through table array looking for empty seat
		int i = 0;
		while (a_table[i]) { // while seat is taken
			i++;
		}

		// found a seat
		table_state = SITTING;
		num_seated++;
		a_table[i] = 1;
		printf("Philosopher %2d: I have been seated\n", MYTHREAD);
		upc_unlock(table);

		left = i;
		right = (i + 1) % THREADS;
		// picks up forks
		upc_lock(a_fork[left]);
		upc_lock(a_fork[right]);

		// philosopher has acquired both forks
		printf("Philosopher %2d: I have both forks---I start eating\n",
			     MYTHREAD);
		state = EATING;
		sleep(delay_eating);
		num_meals++;

    		printf("Philosopher %2d: I have both forks---I finished eating my %d meal", 
             		MYTHREAD, num_meals);
		if (num_meals > 1) printf("s");
		printf("\n");

    		fflush(stdout);

		// release both forks
		upc_unlock(a_fork[left]);
		upc_unlock(a_fork[right]);

		// leave table
		upc_lock(table);
    		state = THINKING;
		table_state = STANDING;
		num_seated--;
		a_table[i] = 0;
		printf("Philosopher %2d: I have left the table\n", MYTHREAD);
		upc_unlock(table);
  	}
	// if the table is full
	else {
		upc_unlock(table);
	}
    }
  }
  printf("Philosopher %2d:***I ate too much, I am leaving!***\n", MYTHREAD);
}

/*
 * Begin the dining philosophers simulation.
 */
int main(void)
{
  // should be run with >=2 THREADS
  initialize();

  upc_barrier;

  printf("main(): before life_cycle() \n");

  life_cycle();
  printf("main(): after life_cycle() \n");

  upc_barrier;

  if (MYTHREAD == 0)
    printf("***---> all philosophers left the table <--***\n");

  return 0;
}
