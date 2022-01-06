package src;
import java.util.*;
import src.AlgorithmX.Node.ColNode;
import java.io.*;

public class AlgorithmX { // class that implements knuth's algorithm X: with dancing links
    static int i, j, k, r, c, rows, columns;
    // private static ArrayList perfectSolution = new ArrayList();
    private static ArrayList partialSolution = new ArrayList();

/*********************************************************************
 * * DOCUMENTATION: KNUTH'S ALGORITHM X, EXACT COVER PROBLEM
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
 */

    static class Node { // class that implements DANCING LINKS
        // a mesh of 4 way connected nodes
        // each node contains:
        Node left; // Pointer to node left to it
        Node right; // Pointer to node right of it
        Node above; // Pointer to node above it
        Node below; // Pointer to node below it
        Node header; // Pointer to list header node to which it belongs

        int size = 0;
        int limitation = -1; // TODO: figure out what to do with this, is a part of DANCING LINKS
        int num = -1;
        int position = -1;

        int data;
        Node nest;
        Node prev; // variables for a Node in a Circular Doubly Linked list

        static class ColNode { // inner class of Node, for every column of the linked list, there is a
                               // columnNode that contains identifying info about that specific column as well
                               // as the size of the column ( number of Nodes in it)
            // Each Node points to four other nodes and its columnNode

        }
    }

    /*******************************************************************************************
     * DOCUMENTATION #2: DANING LINKS TECHNIQUE
     * Knuth's algorithm X uses the dancing link technique:
     * We transform the exact cover problem in form of matrix of 0 and 1
     * Each 1 is represented by a node of linked list and the whole matrix is
     * transformed into a mesh of 4 way connected nodes
     * Each node contains:
     ** Pointer to node left to it
     ** Pointer to node right of it
     ** Pointer to node above it
     ** Pointer to node below it
     ** Pointer to list header node to which it belongs
     * 
     * DANCING LINKS PRINCIPLE:
     * - Dancing links is a technique that relies on the idea of a doubly circular
     * linked list.
     *  
     * DOUBLY CIRCULAR LINKED LISTS:
     * Has properties of both doubly linked list and circular linked list in which
     * two consecutive elements are linked or connected by previous and next pointer
     * an dthe last node points to first node by next pointer
     * The first node points to the last node by the previous pointer as well
     * -------------------------------------------------------------------------------------------------
     * source: 1.
     * https://www.geeksforgeeks.org/exact-cover-problem-algorithm-x-set-2-implementation-dlx/?ref=lbp
     * 2. https://www.geeksforgeeks.org/exact-cover-problem-algorithm-x-set-1/
     * 3. https://www.geeksforgeeks.org/doubly-circular-linked-list-set-1-introduction-and-insertion/
     
     * REFERENCES:
     * https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/sudoku.paper.html
     * https://arxiv.org/pdf/cs/0011047.pdf
     * 
     * @param args
     */

    public static ColNode chooseColRow() {
        // TODO: Otherwise choose a column c (deterministically)
        // choose the column with the smallest possible size
        // According to Donald Knuth's paper, it is most efficient to choose the column
        // with the smallest possible size.
        // That is what we do.
        ColNode rightOfRoot = (ColNode) root.right; // we cast the node to the right of the root to be a
                                                    // ColumnNode
        ColNode smallest = rightOfRoot;
        while (rightOfRoot.right != root) {
            rightOfRoot = (ColNode) rightOfRoot.right;
            if (rightOfRoot.size() < smallest.size()) // choosing which column has the lowest size
            {
                smallest = rightOfRoot;
            }
        }
        return smallest;
    }

    private Object dataNode;
    private Object rowNode;

    public void exactCover() {
        // TODO: remove the columns head by remapping the node to its left to the node
        // to its right so that the linked list no longer contains a way to access the
        // column head.
        Node column = dataNode.column;

        column.right.left = column.left;
        column.left.right = column.right;

        for (Node row = column.down; row != column; row = row.down)
            for (Node rightNode = row.right; rightNode != row; rightNode = rightNode.right) {
                rightNode.up.down = rightNode.down;
                rightNode.down.up = rightNode.up;
            }
    }

    public void uncover() { // add back all values of the column of the list
        // TODO: uncover column
        Node column = dataNode.column;

        for (Node row = column.up; row != column; row = row.up)
            for (Node leftNode = row.left; leftNode != row; leftNode = leftNode.right) {
                leftNode.up.down = leftNode;
                leftNode.down.up = leftNode;
            }
        column.right.left = column;
        column.left.right = column;
    }

    public void search(int k) {
        // TODO: find out how to implement the right variables
        if (h.right == h) {
            System.out.println(partialSolution);
            return;
        } else {
            ColNode column = chooseColRow();
            exactCover(column);

            for (Node row = column.down; rowNode != column; rowNode = rowNode.down) {
                partialSolution.add(rowNode);

                for (Node rightNode = row.right; rightNode != row; rightNode = rightNode.right)
                    exactCover(rightNode);

                search(k + 1);
                partialSolution.remove(rowNode);
                column = rowNode.column;

                for (Node leftNode = rowNode.left; leftNode != row; leftNode = leftNode.left)
                    uncover(leftNode);
            }
            uncover(column);
        }
    }
    public static void main(String[] args) {
        // test with 4x4 and 5x5
    }
    /******************************************************************
     * GENETIC ALGORITHM SIDE NOTE FOR MY OWN UNDERSTANDING:
     * random character strings maken en aan een hele hoop data containers toevoegen
     * strings staan voor pentominoe peices die we willen gebruiken
     * voor iedere char string evaluate hoe goed die de ruimte vult en wat score is
     * met selectiemodel genen mixen en nieuwe generaties maken
     * 5% kans voor character om te veranderen in ander character
     * Als heel vaak doen --> gemiddelde score voor elke generatie wordt beter
     * (survival of fittest)
     * Heel dichtbij de oplossing
     */
}