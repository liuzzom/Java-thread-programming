#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <pthread.h>
#include <unistd.h>
#include <limits.h>
#define SIZE 10

struct params_t{
	int* array;
	size_t start_pos, end_pos;
};
typedef struct params_t params_t;

struct rvalues_t{
	int partial_max, partial_min;
};
typedef struct rvalues_t rvalues_t;

// function prototype
void* max_min(void* args);

// main function
int main(int argc, char const *argv[]){
	int array[SIZE], i, numThread = 2, step;
	int max = INT_MIN, min = INT_MAX;

	if(argc == 2){
		numThread = atoi(argv[1]);
	}else{
		printf("Verranno utilizzati %d thread (opzione di default)\n", numThread);
	}

	srand(time(NULL));

	// array initialization
	for(i = 0; i < SIZE; i++){
		array[i] = rand()%10 +1;
		printf("%d ", array[i]);
	}
	puts("");

	step = SIZE/numThread;

	if(SIZE%numThread == 0){
		pthread_t* threads = malloc(numThread*sizeof(pthread_t));
		params_t* threads_args = malloc(numThread*sizeof(params_t));

		//threads initialization and creation
		for(i = 0; i < numThread; i++){
			threads_args[i].array = array;
			threads_args[i].start_pos = step*i;
			threads_args[i].end_pos = step*(i+1);
			pthread_create(&threads[i], NULL, &max_min, &threads_args[i]);
		}

		//threads join and results
		rvalues_t* tmp;
		for(i = 0; i < numThread; i++){
			pthread_join(threads[i], (void**)&tmp);
			if(max < tmp->partial_max){
				max = tmp->partial_max;
			}
			if(min > tmp->partial_min){
				min = tmp->partial_min;
			}
		}
		printf("Max:%d Min:%d\n", max, min);
	}else{
		pthread_t* threads = malloc((numThread+1)*sizeof(pthread_t));
		params_t* threads_args = malloc((numThread+1)*sizeof(params_t));

		//threads initialization and creation
		for(i = 0; i < numThread; i++){
			threads_args[i].array = array;
			threads_args[i].start_pos = step*i;
			threads_args[i].end_pos = step*(i+1);
			pthread_create(&threads[i], NULL, &max_min, &threads_args[i]);
		}
		threads_args[numThread].array = array;
		threads_args[numThread].start_pos = SIZE -(SIZE%numThread);
		threads_args[numThread].end_pos = SIZE;
		pthread_create(&threads[numThread], NULL, &max_min, &threads_args[numThread]);

		//threads join and results
		rvalues_t* tmp;
		for(i = 0; i < numThread; i++){
			pthread_join(threads[i], (void**)&tmp);
			if(max < tmp->partial_max){
				max = tmp->partial_max;
			}
			if(min > tmp->partial_min){
				min = tmp->partial_min;
			}
		}
		pthread_join(threads[numThread], (void**)&tmp);
		if(max < tmp->partial_max){
			max = tmp->partial_max;
		}
		if(min > tmp->partial_min){
			min = tmp->partial_min;
		}

		printf("Max:%d Min:%d\n", max, min);
	}

	return 0;
}

// function
void* max_min(void* args){
	int i;
	params_t* pp = (void*)args;
	rvalues_t* rval = malloc(sizeof(rvalues_t));

	rval->partial_max = INT_MIN;
	rval->partial_min = INT_MAX;

	for(i = pp->start_pos; i < pp->end_pos; i++){
		if(rval->partial_max < pp->array[i]){
			rval->partial_max = pp->array[i];
		}
		if(rval->partial_min > pp->array[i]){
			rval->partial_min = pp->array[i];
		}
	}

	return (void*) rval;
}