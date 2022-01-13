# GROUP21_2021

# BruteForce Code
    Start this code by running the Search.java file.
    This process takes a long time, as it is brute forcing.

# Branching Tree Code
    Start this code by running the PentominoSolver.java file.
    - If you run this code, you will be asked to put in some Letters which represent pieces.
        - Choose from: X, I, L, N, P, V, U, T, W, P, F, Z
        - Type this with capital letters and without anything in between.
    - You will also be asked to put in a grid size, the x and y respectively.

# phase 2
to play the tetris/pentris game please open pentris.java

# Phase 3
    for phase 3 we made a lot of methods to solve the packing problem of filling a grid

    Parcel3Dsolver and Pentomino3Dsolver
        these files try to solve the problem by:
        1.searching for the first empty space
        2 trying to put every piece there
        3 if it fits repeat steps 1 2 and 3 until you find a solution or there are no solutions to be found
        these files are very slow but are guarenteed to find the best solution


    ParcelGA ParcelGAV2 PentominoGA PentominoGAV2 and Given_Case_GA
        these files try to solve the problem by making a population of individuals containing the parcels
        and then judges these individuals on how well they fill the grid
        then they pick a next generation based on how the last generation performed
        and what selection method was specified

        the differnces between the methods

        Parcel vs Pentomino
        the parcels and pentominoes are placed differently so the files 
        do the same using diffent placement methods

        V2
        some of these files have V2 in the name this means they use diffent
        boxes and placement methods
        the boxes in these files contain coordinates to place the boxes at
        while the ones in the files without V2 do not and try to place the boxes at the next empty spot

        Given_Case_GA
        is a GA which you can give specified parcels
        it uses the boxes from the nonV2 files and doesnt mutate the give parcels as that would defeat the point

    Pentomino solver
        we added a method to this file from phase one which tries to solve this problem
        by filling one of its sides with pentominoes, if it is able to do that it will tile
        that solution to get a solution

        this method only works for grid have a side which surface area is divisible by 5 
        as those are the conditions for a side to be filable with pentominoes
    
    algorithm_X
        this file uses Knuth's Algorithm which is an algoritm used for exact cover problems
        and as any packing problem has an equivalent exact cover problem we convert the truck into a exact cover, then we find all possible ways we can place the parcels and pentominoes and put thos conditions in a giant matrix which we can use knuths algorithm on to find a solution

    
