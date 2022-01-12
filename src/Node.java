//package src;

public class Node { // class that implements DANCING LINKS
    // a mesh of 4 way connected nodes

    // each node contains:
    Node left; // Pointer to node left to it
    Node right; // Pointer to node right of it
    Node above; // Pointer to node above it
    Node below; // Pointer to node below it
    ColNode header; // Pointer to list header node to which it belongs

    int data;
    Node next;
    Node prev; // variables for a Node in a Circular Doubly Linked list

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
 * 3.
 * https://www.geeksforgeeks.org/doubly-circular-linked-list-set-1-introduction-and-insertion/
 * 
 * REFERENCES:
 * https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/sudoku.paper.html
 * https://arxiv.org/pdf/cs/0011047.pdf
 * 
 * 
 * @param args
 */
