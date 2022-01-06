package src;
import java.util.*;

import java.io.*;

public class TeamX { // class that implements knuth's algorithm X: with dancing links
// Compare this to the hungergames: TeamX is team Katniss.

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

 static MemberNode start; // start DANCING LINKS, and may the odds be ever in your favour.
// TODO: root
// TODO: dataNode
// TODO: h
// TODO: rowNode

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
            if (rightOfRoot.size < smallest.size) // choosing which column has the lowest size
            {
                smallest = rightOfRoot;
            }
        }
        return smallest;
    }

    public void exactCover() {
        // TODO: remove the columns head by remapping the node to its left to the node
        // to its right so that the linked list no longer contains a way to access the
        // column head.
        MemberNode column = dataNode.column;

        column.right.left = column.left;
        column.left.right = column.right;

        for (MemberNode row = column.below; row != column; row = row.below)
            for (MemberNode rightNode = row.right; rightNode != row; rightNode = rightNode.right) {
                rightNode.above.below = rightNode.below;
                rightNode.below.above = rightNode.above;
            }
    }

    public void uncover() { // add back all values of the column of the list
        // TODO: uncover column
        MemberNode column = dataNode.column;

        for (MemberNode row = column.above; row != column; row = row.above)
            for (MemberNode leftNode = row.left; leftNode != row; leftNode = leftNode.right) {
                leftNode.above.below = leftNode;
                leftNode.below.above = leftNode;
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

            for (MemberNode row = column.below; rowNode != column; rowNode = rowNode.down) {
                partialSolution.add(rowNode);

                for (MemberNode rightNode = row.right; rightNode != row; rightNode = rightNode.right)
                    exactCover(rightNode);

                search(k + 1);
                partialSolution.remove(rowNode);
                column = rowNode.column;

                for (MemberNode leftNode = rowNode.left; leftNode != row; leftNode = leftNode.left)
                    uncover(leftNode);
            }
            uncover(column);
        }
    }
    public static void main(String[] args) {
        // TODO: test with 4x4 and 5x5
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