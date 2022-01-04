package src;

import java.util.*;
import java.io.*;

public class AlgorithmX {
    static int start;
    static int i, j, k;
    int rows, columns;
    static int r, c;
    static int countCols = 0;
    private static int[][] matrixA;
    private static ArrayList perfectSolution = new ArrayList();
    private static ArrayList partialSolution = new ArrayList();

    /*******************************************************************************************************************************************
     * DOCUMENTATION: KNUTH'S ALGORITHM X, EXACT COVER PROBLEM
     * Knuth's algorithm X pseudocode:
     * 1. If the matrix A has no columns, the current partial solution is a valid
     *      solution; terminate successfully
     *      1. Otherwise choose a column c (deterministically)
     *      2. Choose a row r such that Ar,c = 1 (nondeterministically)
     *      3. Include row r in the partial solution
     *      4. For each column j such that Ar,j = 1
     *          for each row k such that Ak,j = 1
     *              delete row k from matrix A
     *          delete column j from matrix A
     *      5. repeat this algorithm recursively on the reduced matrix A
     * source: https://en.wikipedia.org/wiki/Knuth%27s_Algorithm_X
     * 
     * @param args
     */

    public static void main(String[] args) {

    }

    public static void hasRows(int[][] matrixA) { 
        // Check if the matrix has columns, if not, terminate successfully
        for (i = 0; i < matrixA.length; i++) {
            countCols++;
            if (countCols == 0) {
                return; // TODO: terminate successfully
            } 
        } 
       
        //TODO: Otherwise choose a column c (deterministically)
            for (c = 0; c < matrixA[0].length; c++) {
                    
                }
                //TODO: Choose a row r such that Ar,c = 1 (nondeterministically)
            for (r = 0; r < matrixA[0].length; r++) {
                
                } 
                //TODO: Include row r in the partial solution
                partialSolution.add(r);
        
                
    
        //TODO: For each column j such that Ar,j = 1
        for(j=0 ;  j < matrixA[0].length ; j++) {
            //  * for each row k such that Ak,j = 1
            for (k = 0; k < matrixA[0].length; k++) {
                //  * delete row k from matrix A
                int[][] clone = new int[matrixA.length][matrixA[0].length-k];
            }
        }
        //  * delete column j from matrix A
    
        //TODO: repeat this algorithm recursively on the reduced matrix A
       
    }
}