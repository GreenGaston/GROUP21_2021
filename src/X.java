import java.util.ArrayList;

public class X {

    public static ArrayList<ArrayList<Integer>> answer;
    private static ArrayList<ArrayList<Integer>> tabel;
    private static ArrayList solution = new ArrayList();
    private static int k;
    private static int row;
    static ArrayList<Integer> rows = new ArrayList<Integer>();
    static int j;
    private static int column;
    private static ArrayList<ArrayList<Integer>> list;

    public static void main(String[] args) {
        //search(tabel, k);

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

    public static void search(ArrayList<ArrayList<Integer>> tabel, int k) {
        if (tabel.isEmpty()) { // If the matrix A has no columns, the current partial solution is a valid
            // solution; terminate successfully
            return;
        } else {
            int columns = chooseCol(tabel); // Otherwise choose a column c (deterministically)
            removeColumn(list, column); // remove column
            int row = chooseRow(tabel, columns); // Choose a row r such that Ar,c = 1 (nondeterministically)

            while (row != columns) {
                if (k < solution.size()) {
                    solution.remove(k);
                }
                solution.add(k, row);
                // Include row in the partial solution

                // For each column j such that Ar,j = 1
                // for each row k such that Ak,j = 1
                removeRow(list, row); // delete row k from matrix A
                removeColumn(list, column); // delete column j from matrix A

                search(tabel, k + 1); // repeat this algorithm recursively on the reduced matrix A
            }
        }
    }

    public static int chooseRow(ArrayList<ArrayList<Integer>> tabel, int column) {
        int a = 0;
        int b = 0;
        for (int i = 0; i < tabel.size(); i++) {
            if (tabel.get(i).get(column) == 1) {
                if (a == 0) {
                    a = i;
                } else {
                    b = 1;
                    break;
                }
            }
        }
        rows.add(a);
        rows.add(b);
        for (int r = 0; r < rows.size(); r++) {
            row = rows.get(r);
            rows.remove(r);
        }
        return row;
    }

    public static int chooseCol(ArrayList<ArrayList<Integer>> tabel) {
        int score = 0;
        int colom = 0;
        int tempscore = 0;

        for (int i = 0; i < tabel.get(0).size(); i++) {
            for (j = 0; j < tabel.size(); j++) {
                if (tabel.get(j).get(i) == 1) {
                    tempscore += 1;
                    if (tempscore > score) {
                        break;
                    }
                }
            }
            if (tempscore > score) {
                colom = i;
                score = tempscore;
            }
            tempscore = 0;
        }
        return colom;
    }

    public static ArrayList<ArrayList<Integer>> removeColumn(ArrayList<ArrayList<Integer>> list, int column) {
        ArrayList<ArrayList<Integer>> newlist = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> templist = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            for (int a = 0; a < list.get(i).size(); a++) {
                if (a != column) {
                    templist.add(list.get(i).get(a));
                }
            }
            newlist.add(templist);
            templist = new ArrayList<Integer>();

        }
        return newlist;
    }

    public static ArrayList<ArrayList<Integer>> removeRow(ArrayList<ArrayList<Integer>> list, int row) {
        ArrayList<ArrayList<Integer>> newlist = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> templist = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            for (int b = 0; b < list.get(i).size(); b++) {
                if (i != row) {
                    templist.add(list.get(i).get(b));
                }
            }
            newlist.add(templist);
            templist = new ArrayList<Integer>();
        }
        return newlist;
    }

    public static ArrayList<ArrayList<Integer>> copy2DArrayList(ArrayList<ArrayList<Integer>> list) {
        ArrayList<ArrayList<Integer>> newlist = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> templist = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            for (int c = 0; c < list.get(i).size(); c++) {
                templist.add(list.get(i).get(c));
            }
            newlist.add(templist);
            templist = new ArrayList<Integer>();

        }
        return newlist;
    }
}