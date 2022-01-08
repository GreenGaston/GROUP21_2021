// package src;

import java.util.*;

public class TeamX { // class that implements knuth's algorithm X: with dancing links
    // Compare this to the hungergames: TeamX is team Katniss.

    static int i, k, c, rows, columns;
    static int size;
    static ColNode root = null; // starting root
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
     * PSEUDO CODE FROM THE PAPER:
     * // If R[h] = h, print the current solution (see below) and return.
     * // Otherwise choose a column object c (see below).
     * // Cover column c (see below).
     * // For each r ← D[c], D[D[c]], ... , while r != c,
     * // set Ok ← r;
     * // for each j ← R[r], R[R[r]], ... , while j != r,
     * // cover column j (see below);
     * // search(k + 1);
     * // set r ← Ok and c ← C[r];
     * // for each j ← L[r], L[L[r]], ... , while j != r,
     * // uncover column j (see below).
     * // Uncover column c (see below) and return.
     */

    static MemberNode start; // start DANCING LINKS, and may the odds be ever in your favour.

    public static ColNode createLists(ArrayList<ArrayList<Integer>> lijst) {

        // creating column headers
        root = new ColNode(); // the root is used as an entry-way to the linked list i.e. we access the list
                              // through the root
        ColNode curColumn = root;

        for (int col = 0; col < lijst.size(); col++) // getting the column heads from the sparse matrix and filling
                                                     // in the information about the
        // constraints. We iterate for all the column heads, thus going through all the
        // items in the first row of the sparse matrix
        {
            ColNode id = new ColNode();
            if (col < 3 * size * size) {
                // identifying the digit
                int digit = (col / (3 * size)) + 1;
                id.num = digit;
                // is it for a row, column or block?
                int index = col - (digit - 1) * 3 * size;
                if (index < size) {
                    id.limitation = 0; // we're in the row constraint
                    id.position = index;
                } else if (index < 2 * size) {
                    id.limitation = 1; // we're in the column constraint
                    id.position = index - size;
                } else {
                    id.limitation = 2; // we're in the block constraint
                    id.position = index - 2 * size;
                }
            } else {
                id.limitation = 3; // we're in the cell constraint
                id.position = col - 3 * size * size;
            }
            curColumn.right = new ColNode();
            curColumn.right.left = curColumn;
            curColumn = (ColNode) curColumn.right;
            // curColumn.info = id; // the information about the column is set to the new column
            curColumn.header = curColumn;
        }
        curColumn.right = root; // make the list circular i.e. the right-most ColumnHead is linked to the root
        root.left = curColumn;

        // iterate over the entire matrix

        // Iterate over all the rows
        for (int row = 0; row < lijst.size(); row++) {
            // iterator over all the columns
            curColumn = (ColNode) root.right;
            MemberNode lastCreatedElement = null;
            MemberNode firstElement = null;

            for (int col = 0; col < lijst.get(row).size(); col++) {
                if (lijst.get(row).get(col) == 1) // i.e. if the sparse matrix element has a 1 i.e. there is a clue here
                                                  // i.e.
                // we were given this value in the Grid
                {
                    // create a new data element and link it
                    MemberNode colElement = curColumn;

                    while (colElement.below != null) {
                        colElement = colElement.below;
                    }
                    colElement.below = new MemberNode();
                    if (firstElement == null) {
                        firstElement = colElement.below;
                    }
                    colElement.below.above = colElement;
                    colElement.below.left = lastCreatedElement;
                    colElement.below.header = curColumn;
                    if (lastCreatedElement != null) {
                        colElement.below.left.right = colElement.below;
                    }
                    lastCreatedElement = colElement.below;
                    curColumn.size++;
                }
                curColumn = (ColNode) curColumn.right;
            }

            // link the first and the last element, again making it circular
            if (lastCreatedElement != null) {
                lastCreatedElement.right = firstElement;
                firstElement.left = lastCreatedElement;
            }
        }
        curColumn = (ColNode) root.right;

        // link the last column elements with the corresponding columnHeads
        for (int i = 0; i < lijst.get(0).size(); i++) {
            MemberNode colElement = curColumn;
            while (colElement.below != null) {
                colElement = colElement.below;
            }
            colElement.below = curColumn;
            curColumn.above = colElement;
            curColumn = (ColNode) curColumn.right;
        }
        return root; 
    }

    public static void search(int k) {
        if (root.right == root) { // If the matrix A has no columns, the current partial solution is a valid
            System.out.println(partialSolution);
            return; // terminate successfully

        } else {
            ColNode col = chooseCol(); // choose a column to cover (deterministically)
            exactCover(col);
            MemberNode row = col.below; //Choose a row r such that Ar,c = 1 (nondeterministically)

            while (row != col) {
                if (k < partialSolution.size()) {
                    partialSolution.remove(k);
                }
                partialSolution.add(k, row); // add the solution
                MemberNode j = row.right; // dancing links

                while (j != row) {
                    exactCover(j.header);
                    j = j.right;
                }
                search(k + 1); // recursion

                MemberNode r2 = (MemberNode) partialSolution.get(k);
                MemberNode j2 = r2.left;

                while (j2 != r2) {
                    uncover(j2.header);
                    j2 = j2.left;
                }
                row = row.below;
            }
            uncover(col);
        }
    }

    public static ColNode chooseCol() { // Otherwise choose a column c (deterministically)
        // choose the column with the smallest possible size
        // According to Donald Knuth's paper, it is most efficient to choose the column
        // with the smallest possible size.
        // That is what we do.
        ColNode rightOfRoot = (ColNode) root.right; // we cast the node to the right of the root to be a
                                                    // ColumnNode
        ColNode smallest = rightOfRoot;
        while (rightOfRoot.right != root) {
            rightOfRoot = (ColNode) rightOfRoot.right;
            if (rightOfRoot.size < smallest.size) {// choosing which column has the lowest size
                smallest = rightOfRoot;
            }
        }
        return smallest;
    }

    public static void exactCover(MemberNode columns) { // remove the columns head by remapping the node to its left to the node
        // to its right so that the linked list no longer contains a way to access the
        // column head.
        // MemberNode column = MemberNode.column;
       
        columns.right.left = columns.left;
        columns.left.right = columns.right; // unlink from row

        MemberNode rightRow = columns.below;
       
        while(rightRow != columns) // because it's circular!
        {
         MemberNode rightNode = rightRow.right;
         while(rightNode != rightRow) 
         {
          rightNode.below.above = rightNode.above;
          rightNode.above.below = rightNode.below;
          rightNode.header.size--;
          rightNode = rightNode.right;
         }
         rightRow = rightRow.below;
        }
    }

    public static void uncover(MemberNode columns) { // add back all values of the column of the list
        MemberNode curRow = columns.above;
        // MemberNode column = dataNode.column;

        for (MemberNode row = columns.above; row != columns; row = row.above)
            for (MemberNode curNode = curRow.left; curNode != row; curNode = curNode.right) {
                curNode.above.below = curNode; // reinsert node into linked list
                curNode.below.above = curNode;
            }
        columns.right.left = columns; // reinsert column head
        columns.left.right = columns;
    }

    public static void main(String[] args) {

        ArrayList<Integer> temp=new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> lijst=new ArrayList<ArrayList<Integer>>();

        int[][] matrix={
            {1,0,0,1,0,0,1},
            {1,0,0,1,0,0,0},
            {0,0,0,1,1,0,1},
            {0,0,1,0,1,1,0},
            {0,1,1,0,0,1,1},
            {0,1,0,0,0,0,1}};

        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){

                temp.add(matrix[i][j]);
                }
            lijst.add(temp);
            temp=new ArrayList<Integer>();
            }

        System.out.println(createLists(lijst));
        search(k);
        System.out.println(chooseCol());
        exactCover(start);
        uncover(start);
         }
}