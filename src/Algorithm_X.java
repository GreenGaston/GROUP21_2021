package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Algorithm_X {
    static int[][] solution;
    static int[][] bestPartialSolution;
    static long startTime;
    static int highestCounter = 0;
    static int deepestDepth = 0;

    public static void main(String[] args) {
        
        Knuth_X_Table_LPT lptTable = new Knuth_X_Table_LPT(5, 8, 1);
        int[][][][] pieceDatabase = Knuth_PentominoDatabase.data;
        ArrayList<ArrayList<Integer>> PLTlist = new ArrayList<>();
        PLTlist = lptTable.fillTable(pieceDatabase);

        Knuth_X_Table_Parcels parcelTable = new Knuth_X_Table_Parcels(5, 8, 33);
        int[][][][] parcelDatabase = ParcelDatabase.parcels;
        ArrayList<ArrayList<Integer>> parcelList = new ArrayList<>();
        parcelList = parcelTable.fillTable(parcelDatabase);
                
        
        
        // Used for test purposes
        ArrayList<Integer> temp = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> testList  =new ArrayList<ArrayList<Integer>>();
        int[][] matrix = {
            {1,0,0,0,0,0,0,0},
            {0,1,0,0,1,0,0,1},
            {0,1,0,0,1,0,0,0},
            {0,0,0,0,2,2,0,2},
            {0,0,0,2,0,2,2,0},
            {0,0,3,3,0,0,3,3},
            {0,0,3,0,0,0,0,3}
        };
        
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                
                temp.add(matrix[i][j]);
            }
            testList.add(temp);
            temp = new ArrayList<Integer>();
        }

        temp = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> testFalseList  =new ArrayList<ArrayList<Integer>>();
        int[][] falseMatrix = {
            {1,0,0,0,0,0,0,0},
            {0,2,0,0,2,0,0,2},
            {0,2,2,0,2,0,0,0},
            {0,0,0,0,2,2,0,2},
            {0,0,0,2,0,2,2,0},
            {0,0,2,2,0,0,2,2},
            {0,0,2,0,0,0,0,2}
        };
        
        for (int i = 0; i < falseMatrix.length; i++){
            for (int j = 0; j < falseMatrix[0].length; j++){
                
                temp.add(falseMatrix[i][j]);
            }
            testFalseList.add(temp);
            temp = new ArrayList<Integer>();
        }
        startTime = System.currentTimeMillis();
        // print2DList(PLTlist, "Startlist");
        // Depth is used for testpurposes
        int depth = 0;
        algorithmX(parcelList, depth);
        System.out.println("NO SOLUTION!");
        System.out.println(highestCounter+" out of "+(parcelList.get(0).size()-1)+" spaces filled");
        long endTime = System.currentTimeMillis();
        System.out.println("Time used: "+(endTime-startTime)+" milliseconds");
        
    }

    private static void algorithmX(ArrayList<ArrayList<Integer>> listToSolve, int depth) {
        /*
        * For algorithm X we don't actually delete the lines and columns as supposed
        * Instead we mark each column and row with a 0, 1 or 2.
        * So the original list which needs solving is now 1 row and 1 column bigger
        * the marker row is row 1 and the marker column is column 1
        * Index (0,0) is marked as 1 so that it both the column and row look like it's deleted
        * Example:
        *   {1,0,0,0,0,0,0,0}
            {0,1,0,0,1,0,0,1}
            {0,1,0,0,1,0,0,0}
            {0,0,0,0,1,1,0,1}
            {0,0,0,1,0,1,1,0}
            {0,0,1,1,0,0,1,1}
            {0,0,1,0,0,0,0,1}
        */

        // Creating all the needed lists,
        // for every layer of recursion new lists are needed
        ArrayList<Integer> allValidColumnsStored = new ArrayList<>();
        ArrayList<Integer> allValidRowsStored = new ArrayList<>();
        ArrayList<Integer> usedIndices = new ArrayList<Integer>();
        ArrayList<Integer> removedRows = new ArrayList<>();
        ArrayList<Integer> removedColumns = new ArrayList<>();

        // if (depth == 7){
        //     print2DList(listToSolve, "list");
        // }

        // This loop is used for checking if all the columns are marked deleted (1)
        boolean empty = true;
        for (int i = 1; i < listToSolve.get(0).size(); i++) {
            if (listToSolve.get(0).get(i) == 0){
                empty = false;
                break;
            }
        }

        // // If the previous loop gives back that the list is empty,
        // // go inside this if-statement
        // if (empty){
        //     // Check if the empty list is a valid solution
        //     if (validSolution(listToSolve)){
        //         // Get only the lines out of the list which combine to a solution
        //         solution = getSolution(listToSolve);
        //         // Print the solution in an extern .txt file
        //         SaveSolutions(solution);
        //         // Stop the time, and print the time used for finding the solve
        //         long endTime = System.currentTimeMillis();
        //         System.out.println("Time used: "+(endTime-startTime)+" milliseconds");
        //         System.exit(0);
        //     }
        //     return;
        // }

        // Start with a lowest amount of ones of 1
        // Go through every possible amount of ones
        for (int tryCount = 0; tryCount <= listToSolve.size(); tryCount++){
            // print2DList(listToSolve, "list");
            /* 
            * Step 1:
            * Finding the column(s) with the lowest amount of ones in it.
            */
            allValidColumnsStored = new ArrayList<>();
            findColumnOnes(allValidColumnsStored, tryCount, listToSolve);
            
            if (allValidColumnsStored.size() != 0 && tryCount != 0){
                // Go through all the found columns
                for (int a = 0; a < allValidColumnsStored.size(); a++){
                    /* 
                    * Step 2:
                    * Finding the corresponding row(s) to the columns of step 1.
                    * To find it, make sure the 1 in the row is the same 1 as in the column.
                    */
                    allValidRowsStored = new ArrayList<>();
                    findRowsOnes(allValidRowsStored, allValidColumnsStored.get(a), listToSolve);
                    /*
                    * Step 3:
                    * Select one of the found rows,
                    * Check where the ones are,
                    * Check in other rows for ones in the same columns,
                    * Delete all just-selected rows and columns.
                    */
                    for (int i = 0; i < allValidRowsStored.size(); i++){
                        if (!usedIndices.contains(allValidRowsStored.get(i))){
                            usedIndices.add(allValidRowsStored.get(i));

                            selectAndDelete(allValidRowsStored.get(i), listToSolve, removedRows, removedColumns);
                    
                            if (!hasEmptyColumn(listToSolve)){
                                algorithmX(listToSolve, depth+1);
                            }

                            int counter = 0;
                            for (int z = 1; z < listToSolve.get(0).size(); z++) {
                                if (listToSolve.get(0).get(z) == 1){
                                    counter++;
                                }
                            }

                            if (counter > highestCounter){
                                highestCounter = counter;
                                System.out.println(highestCounter+" out of "+(listToSolve.get(0).size()-1)+" spaces filled");
                                bestPartialSolution = getSolution(listToSolve);
                                SaveSolutions(bestPartialSolution);   
                                if (highestCounter == listToSolve.get(0).size()-1){
                                    long endTime = System.currentTimeMillis();
                                    System.out.println("Time used: "+(endTime-startTime)+" milliseconds");
                                    System.exit(0);
                                }                     
                            }

                            undoRemoval(listToSolve, removedRows, removedColumns);
                            removedRows = new ArrayList<>();
                            removedColumns = new ArrayList<>();
                        }
                    }
                }
            }else if (allValidColumnsStored.size() != 0 && tryCount == 0){
                System.out.println(depth);
                return;
            }
        }
    }
     
    private static void undoRemoval(ArrayList<ArrayList<Integer>> listToSolve, ArrayList<Integer> removedRows, ArrayList<Integer> removedColumns) {
        for (int i = 0; i < removedRows.size(); i++) {
            listToSolve.get(removedRows.get(i)).set(0, 0);
        }

        for (int i = 0; i < removedColumns.size(); i++) {
            listToSolve.get(0).set(removedColumns.get(i), 0);
        }
    }

    private static int[][] getSolution(ArrayList<ArrayList<Integer>> listToSolve) {
        int counter = 0;
        for (int i = 0; i < listToSolve.size(); i++) {
            if (listToSolve.get(i).get(0) == 2){
                counter++;
            }
        }
        int[][] solution = new int[counter][listToSolve.get(0).size()-1];
        for (int i = 0, k = 0; i < listToSolve.size(); i++) {
            if (listToSolve.get(i).get(0) == 2){
                for (int j = 1; j < listToSolve.get(0).size(); j++) {
                    solution[k][j-1] = listToSolve.get(i).get(j);
                }
                k++;
            }
        }
        return solution;
    }

// Step 1
    private static void findColumnOnes(ArrayList<Integer> oneCountList, int tryCount, ArrayList<ArrayList<Integer>> listToSolve){
        /*
        * oneCountList
            * Is empty and needs to be filled with all the columns with the lowest amount of ones in it
        * tryCount
            * Is the amount of ones to look for in a column
        * listToSolve
            * Is the list to look through for ones to select the correct columns
        */

        // Used to keep track of how many ones there are in the current column
        int oneCount = 0; 

        // Go through all columns
        for (int i = 1; i < listToSolve.get(0).size(); i++){
            if (listToSolve.get(0).get(i) == 0){
                // Go through all rows
                for (int j = 0; j < listToSolve.size(); j++){
                    if (listToSolve.get(j).get(0) == 0){
                        // If the current position (j, i) contains value 1,
                        // add 1 to the oneCount
                        if (listToSolve.get(j).get(i) > 0){
                            oneCount++;
                        }
                    }
                }
                // If the last position of the current column is reached
                // and the oneCount has the same value as tryCount
                // add the index of the column to the list
                if (oneCount == tryCount){
                    oneCountList.add(i);
                }
            }
            
            
            // Reset the count for the next column
            oneCount = 0;
        }
    }

    public static Boolean hasEmptyColumn(ArrayList<ArrayList<Integer>> grid){
        boolean empty=true;
        // Go through all columns
        for (int i = 1; i < grid.get(0).size(); i++) {
            if (grid.get(0).get(i) == 0){
                for (int j = 1; j < grid.size(); j++) {
                    if (grid.get(j).get(0) == 0){
                        if(grid.get(j).get(i) > 0){
                            empty=false;
                            break;
                        }
                    }
                }
                if(empty){
                    return true;
                }
                empty=true;
            }
        }
        return false;

    }
// Step 2
    private static void findRowsOnes(ArrayList<Integer> allRowsList, int currentColumn, ArrayList<ArrayList<Integer>> listToSolve) {
        /* 
        * allRowsList 
            * Is empty and needs to be filled with rows
            * The rows will be chosen by looking at the chosen columns
            * Then look in what rows the ones in those columns are
        * oneCountList
            * Is filled with the selected collums of the previous step
        * listToSolve
            * Is the list to look through for ones to select the correct rows
        */

        // Go through all the rows
        for (int j = 0; j < listToSolve.size(); j++){
            // If the current position (j,i) contains a 1,
            // and the row wasn't already selected,
                // then add the index of the row to the list
            if (listToSolve.get(j).get(0) == 0){
                if (listToSolve.get(j).get(currentColumn) > 0){
                    allRowsList.add(j);
                }
            }
        }
    }

// Step 3
    private static void selectAndDelete(int selectedRowIndex, ArrayList<ArrayList<Integer>> listToSolve, ArrayList<Integer> removedRows, ArrayList<Integer> removeCols) {
        // Check in what columns in the selected row the ones are
        for (int i = 1; i < listToSolve.get(0).size(); i++){
            if (listToSolve.get(selectedRowIndex).get(i) > 0){
                removeCols.add(i);
            }
        }

        // Go through the selected columns backwards
        for (int j = removeCols.size()-1; j > -1; j--){
            // Go through the rows backwards
            deleteLoop:
            for (int i = listToSolve.size()-1; i > 0; i--){
                if (i == selectedRowIndex && i != 1){
                    i--;
                }else if (i == selectedRowIndex){
                    break deleteLoop;
                }
                if (listToSolve.get(i).get(0) == 0){
                    // If the current position contains a 1 in the same column as the selected row,
                    // delete the current row
                    if (listToSolve.get(i).get(removeCols.get(j)) > 0 && listToSolve.get(selectedRowIndex).get(removeCols.get(j)) > 0){
                        listToSolve.get(i).set(0, 1);
                        removedRows.add(i);
                    }
                }
            }
        }
        
        listToSolve.get(selectedRowIndex).set(0, 2);
        removedRows.add(selectedRowIndex);

        // Go through the removeCols backwards
        for (int i = removeCols.size()-1; i > -1; i--){
            listToSolve.get(0).set(removeCols.get(i), 1);
        }
    }

    public static int[] removeID(int[] pieceIDs, int index) {

        int[] answerList = new int[pieceIDs.length - 1];

        for (int i = 0, k = 0; i < pieceIDs.length; i++) {

            if (i != index) {

                answerList[k] = (pieceIDs[i]);
                k++;
            }
        }
        return answerList;
    }
    
    private static boolean validSolution(ArrayList<ArrayList<Integer>> listToSolve) {
        int counter = 0;
        for (int j = 1; j < listToSolve.get(0).size(); j++) {
            for (int i = 0; i < listToSolve.size(); i++) {
                if (listToSolve.get(i).get(0) == 2){
                    if (listToSolve.get(i).get(j) > 0){
                        counter++;
                    }
                }
            }
            if (counter != 1){
                return false;
            }
            counter = 0;
        }
        return true;
    }

    private static ArrayList<Integer> copy1DArrayList(ArrayList<Integer> list) {
        ArrayList<Integer> templist=new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            templist.add(list.get(i));
        }
        return templist;
    }

    private static ArrayList<ArrayList<Integer>> copy2DArrayList(ArrayList<ArrayList<Integer>> list){
        ArrayList<ArrayList<Integer>> newlist=new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> templist=new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                templist.add(list.get(i).get(j));

            }
            newlist.add(templist);
            templist=new ArrayList<Integer>();
            
        }
        return newlist;
    }

    private static int[] clone1Dint(int[] trackKeeper) {
        int[] clone = new int[trackKeeper.length];
        for (int i = 0; i < trackKeeper.length; i++) {
            clone[i] = trackKeeper[i];
        }
        return clone;
    }

    // Used for test purposes
    private static void print1DList(ArrayList<Integer> list, String titel){
        System.out.println(titel);
        for (int i = 0; i < list.size(); i++){
            System.out.print(list.get(i)+" ");
        }
        System.out.println();
    }

    // Used for test purposes
    private static void print2DList(ArrayList<ArrayList<Integer>> list, String titel){
        System.out.println(titel);
        for (int i = 0; i < list.size(); i++){
            System.out.print(i+" ");
            for (int j = 0; j < list.get(i).size(); j++){
                System.out.print(list.get(i).get(j)+" ");
            }
            System.out.println();
        }
    }

    private static void SaveSolutions(int[][] solution){
        try{
            FileWriter myWriter = new FileWriter("Algorithm_X_Parcel_5833.txt");
            //this writes all scores back into the file while putting the current score in the right place
            for (int i = 0; i < solution.length; i++) {
                for (int j = 0; j < solution[i].length; j++) {
                    myWriter.write(solution[i][j]+" ");
                                        
                }
                myWriter.write("\n");
                
            }
            
            myWriter.close();
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
