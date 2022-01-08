import java.util.ArrayList;

public class X {

    public static ArrayList<ArrayList<Integer>> answer;
    private static ArrayList<ArrayList<Integer>> tabel;
    private static int k;

    public static void main(String[] args) {
        search(tabel,k);
    }

    public static void search(ArrayList<ArrayList<Integer>> tabel, int k) {
        if (tabel.isEmpty()) { //If the matrix A has no columns, the current partial solution is a valid
            // solution; terminate successfully
            return;
        } else {
            int columns = chooseCol(tabel); // Otherwise choose a column c (deterministically)
            int[] rows = chooseRow(tabel, columns); 
            for(int r=0 ; r < rows.length ; r++) {
                
            }
            // Choose a row r such that Ar,c = 1 (nondeterministically)

            while(row != columns) {
                if (k < answer.size()) {
                    answer.remove(k);
                } answer.add(k, row); // Include row r in the partial solution

                while(j != row) {

                }
            } // For each column j such that Ar,j = 1
        }
    //     for each row k such that Ak,j = 1
    //  * delete row k from matrix A
    //  * delete column j from matrix A

        search(tabel, k+1); // repeat this algorithm recursively on the reduced matrix A
    }

    public static int[] chooseRow(ArrayList<ArrayList<Integer>> tabel, int column) {
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
        int[] rows = { a, b };
        return rows;
    }

    public static int chooseCol(ArrayList<ArrayList<Integer>> tabel) {
        int score = 0;
        int colom = 0;
        int tempscore = 0;

        for (int i = 0; i < tabel.get(0).size(); i++) {

            for (int j = 0; j < tabel.size(); j++) {
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
            for (int j = 0; j < list.get(i).size(); j++) {
                if (j != column) {
                    templist.add(list.get(i).get(j));
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
            for (int j = 0; j < list.get(i).size(); j++) {
                if (i != row) {
                    templist.add(list.get(i).get(j));
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
            for (int j = 0; j < list.get(i).size(); j++) {
                templist.add(list.get(i).get(j));

            }
            newlist.add(templist);
            templist = new ArrayList<Integer>();

        }
        return newlist;
    }

}