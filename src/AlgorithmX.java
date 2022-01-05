package src;

import java.util.*;
import java.io.*;

public class AlgorithmX {
    static boolean hasR = false;
    static int start;
    static int i, j, k;
    static int rows, columns;
    static int r, c;
    static int countCols = 0;
    private static int[][] matrixA;
    private static ArrayList perfectSolution = new ArrayList();
    private static ArrayList partialSolution = new ArrayList();

    /*******************************************************************************************************************************************
     * DOCUMENTATION: KNUTH'S ALGORITHM X, EXACT COVER PROBLEM
     * Knuth's algorithm X pseudocode:
     * 1. If the matrix A has no columns, the current partial solution is a valid
     * solution; terminate successfully
     * 1. Otherwise choose a column c (deterministically)
     * 2. Choose a row r such that Ar,c = 1 (nondeterministically)
     * 3. Include row r in the partial solution
     * 4. For each column j such that Ar,j = 1
     * for each row k such that Ak,j = 1
     * delete row k from matrix A
     * delete column j from matrix A
     * 5. repeat this algorithm recursively on the reduced matrix A
     * source: https://en.wikipedia.org/wiki/Knuth%27s_Algorithm_X
     * 
     * @param args
     */

    public static void main(String[] args) {

    }

    public static int chooseColumn() {
        // TODO: Otherwise choose a column c (deterministically)
        // choose the column with the smallest possible size
        return columns;
    }

    public static int chooseRow() {
        // TODO: Choose a row r such that Ar,c = 1 (nondeterministically)
        return rows;
    }

    public void exactCover() {
        // TODO: remove the columns head by remapping the node to its left to the node
        // to its right so that the linked list no longer contains a way to access the
        // column head.
    }
    
    public void uncover() { // add back all values of the column of the list
        //TODO: uncover column
    }

    public static boolean hasRows(int[][] matrixA) {
        // Check if the matrix has columns, if not, terminate successfully
        
        for (i = 0; i < matrixA.length; i++) {
            countCols++;
            if (countCols > 0) {
                hasR = true;
             }
        } return hasR;
    }

    public void search(int[][] matrixA, int k) {
        if(hasRows(matrixA)==true) {
            List listA = Arrays.asList(matrixA);
            c = chooseColumn();
            r = chooseRow();

            while(r!=c) {
                if(k < partialSolution.size()) {
                    partialSolution.remove(k);
                }
                partialSolution.add(k,r); // Include row r in the partial solution
            }
    
            // TODO: For each column j such that Ar,j = 1
            // for (j = 0; j < matrixA[0].length; j++) {
            //     // * for each row k such that Ak,j = 1
            //     for (k = 0; k < matrixA[0].length; k++) {
            //         listA.remove(k); // * delete row k from matrix A
            //     }
            //     listA.remove(j); // * delete column j from matrix A
            // }
            search(matrixA,k+1); // repeat this algorithm recursively on the reduced matrix A
        }
    }
}