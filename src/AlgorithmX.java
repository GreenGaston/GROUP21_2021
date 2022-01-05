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
    private static ArrayList perfectSolution = new ArrayList();
    private static ArrayList partialSolution = new ArrayList();

    /*******************************************************************************************************************************************
     * DOCUMENTATION: KNUTH'S ALGORITHM X, EXACT COVER PROBLEM
     * -------------------------------------------------------------------------------------------------
     * PSEUDO CODE: 
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
     * Knuth's algorithm uses the dancing link technique: 
     * We tansform the exact cover problem in form of matrix of 0 and 1
     * Each 1 is represented by a node of linked list and the whole matrix is transformed into a mesh of 4 way connected nodes
     * Each node contains: 
     ** Pointer to node left to it
     ** Pointer to node right of it
     ** Pointer to node above it
     ** Pointer to node below it
     ** Pointer to list header node to which it belongs

     -------------------------------------------------------------------------------------------------

     PSEUDO CODE:
     f( h.right == h ) { 
     printSolutions(); 
     return; 
    } 
    else { 
     ColumnNode column = getMinColumn(); 
     cover(column); 

     for( Node row = column.down ; rowNode != column ;
        rowNode = rowNode.down ) { 
            solutions.add( rowNode ); 

            for( Node rightNode = row.right ; rightNode != row ;
                 rightNode = rightNode.right ) 
                    cover( rightNode ); 

     Search( k+1); 
     solutions.remove( rowNode ); 
     column = rowNode.column; 

     for( Node leftNode = rowNode.left ; leftNode != row ;
              leftNode = leftNode.left )                                                                             
            uncover( leftNode ); 
     } 
     uncover( column ); 
} 
     * source: 1. https://www.geeksforgeeks.org/exact-cover-problem-algorithm-x-set-2-implementation-dlx/?ref=lbp
     *         2. https://www.geeksforgeeks.org/exact-cover-problem-algorithm-x-set-1/
     * 
     * Genetisch algorithm:
     * random character strings maken en aan een hele hoop data containers toevoegen 
     * strings staan voor pentominoe peices die we willen gebruiken
     * voor iedere char string evaluate hoe goed die de ruimte vult en wat score is
     * met selectiemodel genen mixen en nieuwe generaties maken
     * 5% kans voor character om te veranderen in ander character
     * Als heel vaak doen --> gemiddelde score voor elke generatie wordt beter (survival of fittest)
     * Heel dichtbij de oplossing
     * 
     * -----------------------------------------------------------------------------------------------
     * 
     * REFERENCES: 
     * https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/sudoku.paper.html
     * https://arxiv.org/pdf/cs/0011047.pdf
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

    public static boolean hasRows(ArrayList matrixA) {
        // Check if the matrix has columns, if not, terminate successfully
        
        for (i = 0; i < matrixA.size(); i++) {
            countCols++;
            if (countCols > 0) {
                hasR = true;
             }
        } return hasR;
    }

    public void search(ArrayList matrixA, int k) {
        if(hasRows(matrixA)==true) {
            c = chooseColumn();
            r = chooseRow();

            while(r!=c) {
                if(k < partialSolution.size()) {
                    partialSolution.remove(k);
                }
                partialSolution.add(k,r); // Include row r in the partial solution
            }
    
            // TODO: For each column j such that Ar,j = 1
            // pak lijn Ar,c als eerste lijn geselecteerd en komt een rij tegen met een 1 op zelfde locatie als in andere rij, dan haal je die eruit.
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