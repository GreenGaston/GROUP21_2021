// package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Algorithm_X {
    static int[][] solution;
    public static void main(String[] args) {
        
        Knuth_X_Table_LPT lptTable = new Knuth_X_Table_LPT(5, 8, 33);
        int[][][][] pieceDatabase = Knuth_PentominoDatabase.data;
        ArrayList<ArrayList<Integer>> PLTlist = new ArrayList<>();
        PLTlist = lptTable.fillTable(pieceDatabase);
        
        // ArrayList<Integer> PLTsolution = new ArrayList<>();
        // PLTsolution = solution;
                
        // Knuth_X_Table_Parcels parcelTable = new Knuth_X_Table_Parcels(5, 8, 33);
        // int[][][][] parcelDatabase = ParcelDatabase.parcels;
        // ArrayList<ArrayList<Integer>> parcelList = new ArrayList<>();
        // parcelList = parcelTable.fillTable(parcelDatabase);
        
        // ArrayList<ArrayList<Integer>> copyList = copy2DArrayList(parcelList);
        
        // ArrayList<Integer> parcelSolution = new ArrayList<>();
        // algorithmX(parcelList, new ArrayList<ArrayList<Integer>>(), copyList);
        // parcelSolution = solution;
        
        
        
        // Used for test purposes
        ArrayList<Integer> temp = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> testList  =new ArrayList<ArrayList<Integer>>();
        int[][] matrix = {
            {1,0,0,0,0,0,0,0},
            {0,2,0,0,2,0,0,2},
            {0,2,0,0,2,0,0,0},
            {0,0,0,0,2,2,0,2},
            {0,0,0,2,0,2,2,0},
            {0,0,2,2,0,0,2,2},
            {0,0,2,0,0,0,0,2}
        };
        
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                
                temp.add(matrix[i][j]);
            }
            testList.add(temp);
            temp = new ArrayList<Integer>();
        }
        
        algorithmX(PLTlist);
    }

   
    private static void algorithmX(ArrayList<ArrayList<Integer>> listToSolve) {
        ArrayList<Integer> allValidColumnsStored = new ArrayList<>();
        ArrayList<Integer> allValidRowsStored = new ArrayList<>();
        ArrayList<Integer> usedIndices = new ArrayList<Integer>();
        ArrayList<Integer> removedRows = new ArrayList<>();
        ArrayList<Integer> removedColumns = new ArrayList<>();


        boolean empty = true;
        for (int i = 1; i < listToSolve.get(0).size(); i++) {
            if (listToSolve.get(0).get(i) == 0){
                empty = false;
                break;
            }
        }

        if (empty){
            if (validSolution(listToSolve)){
                solution = getSolution(listToSolve);
                SaveSolutions(solution);
                System.exit(0);
            }
            return;
        }

        // if (listToSolve.size() == 0){
        //     if (validSolution(solution)){
        //         allSolves.add(solution);
        //         print2DList(solution, "solution");
        //     }
        //     bestSolution.add(solution);
        //     return;
        // }else if(hasEmptyColumn(listToSolve)){
        //     bestSolution.add(solution);
        // }

        // Start with a lowest amount of ones of 1
        // Go through every possible amount of ones
        //print2DList(listToSolve, "StartList");
        for (int tryCount = 0; tryCount <= listToSolve.size(); tryCount++){
            /* 
            * Step 1:
            * Finding the column(s) with the lowest amount of ones in it.
            */
            allValidColumnsStored.clear();
            findColumnOnes(allValidColumnsStored, tryCount, listToSolve);
            //print1DList(allValidColumnsStored, "valid cols");
            
            if (allValidColumnsStored.size() != 0 && tryCount != 0){
                // Go through all the found columns
                for (int a = 0; a < allValidColumnsStored.size(); a++){
                    /* 
                    * Step 2:
                    * Finding the corresponding row(s) to the columns of step 1.
                    * To find it, make sure the 1 in the row is the same 1 as in the column.
                    */
                    allValidRowsStored.clear();
                    findRowsOnes(allValidRowsStored, allValidColumnsStored.get(a), listToSolve);
                    //print1DList(allValidRowsStored, "valid rows");
                    /*
                    * Step 3:
                    * Select one of the found rows,
                    * Check where the ones are,
                    * Check in other rows for ones in the same columns,
                    * Delete all just-selected rows and columns.
                    */
                    for (int i = 0; i < allValidRowsStored.size(); i++){
                        // fillTrackKeeper(listToSolve);
                        if (!usedIndices.contains(allValidRowsStored.get(i))){
                            usedIndices.add(allValidRowsStored.get(i));
                            // cloneSolution.add(cloneParts.get(allValidRowsStored.get(i)));
                            // selectAndDelete(allValidRowsStored.get(i), cloneList, cloneParts);
                            // System.out.println("Before");
                            // for (int z = 0; z < trackKeeperCopy.length; z++){
                            //     System.out.print(trackKeeperCopy[z]+", ");
                            // }
                            // // System.out.println();
                            // int tempCounter = globalCounter;
                            // usedIndexesCopy[tempCounter] = trackKeeper[allValidRowsStored.get(i)]+1;
                            // globalCounter++;
                    
                            // trackKeeperCopy = 
                            

                            selectAndDelete(allValidRowsStored.get(i), listToSolve, removedRows, removedColumns);
                            // System.out.println("After");
                            // for (int z = 0; z < trackKeeperCopy.length; z++){
                            //     System.out.print(trackKeeperCopy[z]+", ");
                            // }
                            // System.out.println();

                            //print2DList(cloneSolution, "cloneSolution");
                            if (hasEmptyColumn(listToSolve)){
                                algorithmX(listToSolve);
                                // algorithmX(cloneList, cloneSolution, cloneParts);
                            }

                            undoRemoval(listToSolve, removedRows, removedColumns);
                            // print2DList(listToSolve, "undo");
                            // cloneSolution = copy2DArrayList(solution);
                            // cloneParts = copy2DArrayList(solutionParts);
                        }
                    }
                }
            }
        }
    }
  
    //returns a list with how many 1s are in each row

   

  
   
    private static void undoRemoval(ArrayList<ArrayList<Integer>> listToSolve, ArrayList<Integer> removedRows, ArrayList<Integer> removedColumns) {
        for (int i = 0; i < removedRows.size(); i++) {
            listToSolve.get(removedRows.get(i)).set(0, 0);
        }
        removedRows.clear();

        for (int i = 0; i < removedColumns.size(); i++) {
            listToSolve.get(0).set(removedColumns.get(i), 0);
        }
        removedColumns.clear();
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

        // System.out.println("Trycount: "+tryCount);
        // Go through all columns
        for (int i = 0; i < listToSolve.get(0).size(); i++){

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
                // listToSolve.get(0).set(i, 3);
                // System.out.println(i);
            }
            
            // Reset the count for the next column
            oneCount = 0;
        }
    }


    public static Boolean hasEmptyColumn(ArrayList<ArrayList<Integer>> grid){
        // print2DList(grid, "grid");
        boolean empty=true;
        for (int i = 0; i < grid.get(0).size(); i++) {
            if (grid.get(0).get(i) == 0){
                for (int j = 0; j < grid.size(); j++) {
                    if (grid.get(j).get(0) == 0){
                        if(grid.get(j).get(i) > 0){
                            empty=false;
                            break;
                        }
                    }
                }
                if(empty){
                // System.out.println("returned false");

                    return false;
                }
                empty=true;
            }
        }
        //System.out.println("returned true");
        return true;

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
    private static void selectAndDelete(int selectedRowIndex, ArrayList<ArrayList<Integer>> listToSolve, ArrayList<Integer> removedRows, ArrayList<Integer> removeCols/*ArrayList<ArrayList<Integer>> partList*/) {
        // Check in what columns in the selected row the ones are
        // System.out.println("rowindex "+selectedRowIndex);
        for (int i = 0; i < listToSolve.get(0).size(); i++){
            if (listToSolve.get(selectedRowIndex).get(i) > 0){
                removeCols.add(i);
            }
        }
        // print1DList(removeCols, "removeCols");
        // int[] deletedRows=new int[listToSolve.size()];
        // int counter=0;

        // System.out.println("Before");
        // for (int i = 0; i < trackKeeper.length; i++){
        //     System.out.print(trackKeeper[i]+", ");
        // }
        // System.out.println();


        // Go through the selected columns backwards
        for (int j = removeCols.size()-1; j > -1; j--){
            // Go through the rows backwards
            deleteLoop:
            for (int i = listToSolve.size()-1; i > -1; i--){
                if (i == selectedRowIndex && i != 0){
                    i--;
                }else if (i == selectedRowIndex){
                    break deleteLoop;
                }
                // If the current position contains a 1 in the same column as the selected row,
                // delete the current row
                if (listToSolve.get(i).get(removeCols.get(j)) > 0 && listToSolve.get(selectedRowIndex).get(removeCols.get(j)) > 0){
                    // listToSolve.remove(i);
                    listToSolve.get(i).set(0, 1);
                    removedRows.add(i);
                    // partList.remove(i);
                    // deletedRows[counter]=i;
                    // counter++;
                    //print2DList(listToSolve, "removed row");
                }
            }
        }
        
        listToSolve.get(selectedRowIndex).set(0, 2);
        removedRows.add(selectedRowIndex);
        // partList.remove(selectedRowIndex);
        // trackKeeper = removeID(trackKeeper, selectedRowIndex);

        // System.out.println("After");
        // for (int i = 0; i < trackKeeper.length; i++){
        //     System.out.print(trackKeeper[i]+", ");
        // }
        // System.out.println();


        // for (int i = 0; i < deletedRows.length; i++) {
        //     partList.remove(deletedRows[i]);
        // }
        // deleteRows(partList, selectedRowIndex, removeCols);
        // print2DList(listToSolve, "removed row");


        // Go through the removeCols backwards
        for (int i = removeCols.size()-1; i > -1; i--){
            listToSolve.get(0).set(removeCols.get(i), 1);
           // print2DList(listToSolve, "removed column");
        }


        /*
        * Check the new list on columns without any ones.
        * If there is at least 1 such column, no solution is possible.
        * Then this mehtod comes to an end and the next row is checked
        *
        * If every column has at least 1 one, go back to the first step with the new list.
        */
        // if (nextStepPossibleCheck(listToSolve)){
        //     algorithmX(listToSolve);
        // }
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

    private static boolean nextStepPossibleCheck(ArrayList<ArrayList<Integer>> listToSolve) {
        if (listToSolve.size() != 0){
            // Go through all the columns
            // System.out.println(listToSolve.size());
            for (int i = 0; i < listToSolve.get(0).size(); i++){
                loop:
                // Go through all the rows
                for (int j = 0; j < listToSolve.size(); j++){
                    // If a column contains a 1, break and check next column
                    if (listToSolve.get(j).get(i) > 0){
                        break loop;
                    }
                    // If a column contains no 1, return false
                    if (j == listToSolve.size()-1){
                        return false;
                    }
                }
            }            
            return true;
        }else{
            return true;
        }
    }

    private static boolean noDuplicate(ArrayList<Integer> list, int j) {
        // Go through the list with selected rows
        // Check if index j is already in there
        for (int i = 0; i < list.size(); i++){
            if (list.get(i) == j){
                return false;
            }
        }
        return true;
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

    public static ArrayList<ArrayList<Integer>> copy2DArrayList(ArrayList<ArrayList<Integer>> list){
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

    public static int[] clone1Dint(int[] trackKeeper) {
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
            System.out.print(list.get(i)+", ");
        }
        System.out.println();
    }

    // Used for test purposes
    private static void print2DList(ArrayList<ArrayList<Integer>> list, String titel){
        System.out.println(titel);
        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < list.get(i).size(); j++){
                System.out.print(list.get(i).get(j)+" ");
            }
            System.out.println();
        }
    }

    public static void SaveSolutions(int[][] solution){
        try{
            FileWriter myWriter = new FileWriter("Algorithm_X.txt");
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
