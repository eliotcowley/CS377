/*
 * File: ~mlsmith/cs377-examples/upc/philosophers.upc
 * Written by: Tarek El-Ghazawi, William Carlson, Thomas Sterling,
 *             and Katherine Yelick
 * Modified by: Marc Smith
 * Date: October 30, 2008
 * 
 * Compile: upcc -T 5 philosophers.upc
 * Run:     upcrun philosophers
 *
 * Description: UPC text implementation of Dining Philosophers
 *              problem. Busy waiting solution
 *
 */

#include <stdio.h>
#include <upc_relaxed.h>

typedef enum{THINKING, STARVING, EATING} philosopher_status_t;
upc_lock_t * shared a_fork[THREADS];

/*
 * Allocate shares resources: forks (really chopsticks)
 */
void initialize(void)
{
  a_fork[MYTHREAD] = upc_global_lock_alloc();
}

/*
 * Simulates the lifecycle of a single dining philosopher
 */
void life_cycle(void)
{
  philosopher_status_t state;
  int num_meals=0,
      delay_thinking=1,
      delay_eating=2;
  int left, right, 
      got_left, got_right;

  left = MYTHREAD;
  right = (MYTHREAD+1) % THREADS;
  state = THINKING;

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

    // tries to lock both forks
    got_right = upc_lock_attempt(a_fork[right]);
    got_left = upc_lock_attempt(a_fork[left]);

    if (got_left && got_right)
    {  // got both...
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
 
      state = THINKING;
    }
    else 
    { // either one or none of the forks were available
      //printf("Philosopher %2d: at least one fork was unavailable...\n",
      //       MYTHREAD);
      if (got_left)
        upc_unlock(a_fork[left]);
      if (got_right)
        upc_unlock(a_fork[right]);
    }
  }
 
  printf("Philosopher %2d:***I ate too much, I am leaving!***\n",
         MYTHREAD);
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
