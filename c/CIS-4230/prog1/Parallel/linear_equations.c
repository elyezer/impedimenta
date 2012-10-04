/*!
    \file   linear_equations.c
    \brief  A gaussian elimination solver.
    \author (C) Copyright 2012 by Peter C. Chapin <PChapin@vtc.vsc.edu>
*/

#include <math.h>
#include <string.h>
#include "linear_equations.h"
#include "thread_pool/thread_pool.h"
#include "thread_pool/worker.h"

//
// The following function does the major work of reducing the system.
//
static void elimination(
    int size,
    floating_type a[size][size],
    floating_type *b,
    int *error
) {
    // Create and initialize thread pool and tasks.
    struct thread_pool pool;
    thread_pool_init(&pool);
    struct worker_work * tasks = malloc(size * sizeof(struct worker_work));
    for(int i = 0; i < size; i++) {
        worker_work_init(&tasks[i]);
    }

    floating_type max;  // the largest value in column i is ``max``
    int max_row;        // the largest value in column i is at row ``max_row``
    floating_type temp;
    floating_type * temp_row = malloc(size * sizeof(floating_type));

    // When examining row i, you're also going to simplify row i + 1. Do not
    // examine the last row, or you will attempt to simplify a row that does not
    // exist.
    for(int i = 0; i < size - 1; i++) {
        // Consider the column containing a[i][i]. Look at all the values from
        // a[i][i] to the bottom of the matrix, and find the row with the
        // largest value.
        max = fabsf(a[i][i]); // fabsf(x): single-precision absolute value of x
        max_row = i;
        for(int row = max_row + 1; row < size; row++) {
            if(max < fabsf(a[i][row])) {
                max = fabsf(a[i][row]);
                max_row = row;
            }
        }

        // If row max_row has a larger value than row i (as per the restrictions
        // above), they should be swapped. This provides better numerical
        // stability when working on large matrices.
        if(i != max_row) {
            memcpy(temp_row,   a[i],       size * sizeof(floating_type));
            memcpy(a[i],       a[max_row], size * sizeof(floating_type));
            memcpy(a[max_row], temp_row,   size * sizeof(floating_type));
            temp = b[i];
            b[i] = b[max_row];
            b[max_row] = temp;
        }

        // Finally -- the part to be parallelized!
        // TODO: parallelize
        // For each row below row i, the value in column i should be made zero.
        // Do this by subtracting (row i) * coefficient from each subsequent
        // row. The coefficient ``k`` is different for each row.
        for(int row = i + 1; row < size; row++) {
            floating_type k = a[row][i] / a[i][i];
            for(int col = 0; col < size; col++)
                a[row][col] -= k * a[i][col];
            b[row] -= k * b[i];
        }
    }

    thread_pool_die(&pool);
    free(tasks);
    free(temp_row);
}


//
// The following function does the back substitution step.
//
static void back_substitution(
    int size,
    floating_type a[size][size],
    floating_type *b,
    int *error
) {
    floating_type sum;
    int           i, j;
    *error = 0;

    for(i = size - 1; i >=0; --i) {
        if(fabsf(a[i][i]) <= 1.0E-6) {
            *error = -2;
            return;
        }

        sum = b[i];
        for(j = i + 1; j < size; ++j) {
            sum -= a[i][j] * b[j];
        }
        b[i] = sum / a[i][i];
    }
}


//
// The following (public) function solves Ax=b using Gaussian elimination. It
// returns the solution in b. The value of *error is -1 if there is a proble
// with the parameters and -2 if the system can't be solved.
//
void gaussian_solve(
    int size,
    floating_type a[size][size],
    floating_type *b,
    int *error
) {
    elimination(size, a, b, error);
    if(*error == 0) back_substitution(size, a, b, error);
}
