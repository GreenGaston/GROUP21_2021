package src;

import java.util.*;
import java.io.*;

public class KnuthAlgorithmX {
    int i, j;
    int rows, columns;
    static boolean hasCol = false;
    static int r, c;
    static int countCols = 0;
    static ArrayList<Integer> cols = new ArrayList<Integer>();

/*******************************************************************************************************************************************
 * DOCUMENTATION: KNUTH'S ALGORITHM X, EXACT COVER PROBLEM
 * Knuth's algorithm X pseudocode:
 * 1. If the matrix A has no columns, the current partial solution is a valid solution; terminate successfully
 *      1. Otherwise choose a column c (deterministically)
 *      2. Choose a row r such that Ar,c = 1 (nondeterministically)
 *      3. Include row r in the partial solution
 *      4. For each column j such that Ar,j = 1
 *          for each row i such that Ai,j = 1
 *              delete row i from matrix A
 *          delete column j from matrix A
 *      5. repeat this algorithm recursively on the reduced matrix A
 * source: https://en.wikipedia.org/wiki/Knuth%27s_Algorithm_X
 *  
 * @param args
 */

    public static void main(String[] args) {

    }

    public static boolean hasRows(int[][] matrixA) { // checks if the matrix has columns
        for (c = 0; c < matrixA.length; c++) {
            countCols++;
            if (countCols > 0) {
                hasCol = true;
            }
        } return hasCol; // returns true is the matrix has columns.
        // for (r = 0; r < matrixA[0].length; r++) {
          
        // }
    }
}