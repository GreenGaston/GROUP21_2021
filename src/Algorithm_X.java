//package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class Algorithm_X {
    static ArrayList<ArrayList<ArrayList<Integer>>> allSolves = new ArrayList<>();
    public static void main(String[] args) {
        
        // Knuth_X_Table_LPT lptTable = new Knuth_X_Table_LPT(5, 8, 33);
        // int[][][][] pieceDatabase = Knuth_PentominoDatabase.data;
        // ArrayList<ArrayList<Integer>> PLTlist = new ArrayList<>();
        // PLTlist = lptTable.fillTable(pieceDatabase);
        
        // ArrayList<Integer> PLTsolution = new ArrayList<>();
        // algorithmX(PLTlist);
        // PLTsolution = solution;
        
        // solution.clear();
        // trackKeeper.clear();
        
        // Knuth_X_Table_Parcels parcelTable = new Knuth_X_Table_Parcels(5, 8, 33);
        // int[][][][] parcelDatabase = ParcelDatabase.parcels;
        // ArrayList<ArrayList<Integer>> parcelList = new ArrayList<>();
        // parcelList = parcelTable.fillTable(parcelDatabase);
        
        // ArrayList<ArrayList<Integer>> copyList = copy2DArrayList(parcelList);
        
        // ArrayList<Integer> parcelSolution = new ArrayList<>();
        //algorithmX(parcelList, new ArrayList<ArrayList<Integer>>(), copyList);
        // parcelSolution = solution;
        
        
        // Used for test purposes
        ArrayList<Integer> temp = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> testList  =new ArrayList<ArrayList<Integer>>();
        int[][] matrix = {
            {1,0,0,1,0,0,1},
            {1,0,0,1,0,0,0},
            {0,0,0,1,1,0,1},
            {0,0,1,0,1,1,0},
            {0,1,1,0,0,1,1},
            {0,1,0,0,0,0,1}
        };
        
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                
                temp.add(matrix[i][j]);
            }
            testList.add(temp);
            temp = new ArrayList<Integer>();
        }
        
        ArrayList<ArrayList<Integer>> copyList = copy2DArrayList(testList);
        algorithmX(testList, new ArrayList<ArrayList<Integer>>(), copyList);

        // for (int i = 0; i < allSolves.size(); i++){
        //     print2DList(allSolves.get(i), "Solution "+i);
        // }
        System.out.println("Amount of solves: "+allSolves.size());
        SaveSolutions();
    }
        
    private static void algorithmX(ArrayList<ArrayList<Integer>> listToSolve, ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> solutionParts) {
        ArrayList<Integer> allValidColumnsStored = new ArrayList<>();
        ArrayList<Integer> allValidRowsStored = new ArrayList<>();
        ArrayList<ArrayList<Integer>> cloneList = copy2DArrayList(listToSolve);
        ArrayList<ArrayList<Integer>> cloneSolution = copy2DArrayList(solution);
        ArrayList<ArrayList<Integer>> cloneParts = copy2DArrayList(solutionParts);
        //print2DList(listToSolve, "hier is de huidige grid");

        if (listToSolve.size() == 0){
            if (!allSolves.contains(solution) && validSolution(solution)){
                allSolves.add(solution);
                print2DList(solution, "solution");
            }
            return;
        }

        if (!nextStepPossibleCheck(cloneList)){
            return;
        }
        // Start with a lowest amount of ones of 1
        // Go through every possible amount of ones
        //print2DList(listToSolve, "StartList");
        for (int tryCount = 0; tryCount <= listToSolve.size(); tryCount++){
            /* 
            * Step 1:
            * Finding the column(s) with the lowest amount of ones in it.
            */
            allValidColumnsStored.clear();
            allValidColumnsStored = findColumnOnes(allValidColumnsStored, tryCount, listToSolve);
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
                    allValidRowsStored = findRowsOnes(allValidRowsStored, allValidColumnsStored.get(a), listToSolve);
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
                        cloneSolution.add(cloneParts.get(allValidRowsStored.get(i)));
                        selectAndDelete(allValidRowsStored.get(i), cloneList, cloneParts);
                        // if (validSolution(copyList)){

                        // }
                        //print2DList(cloneSolution, "cloneSolution");
                        if (hasEmptyColom(cloneList)){
                            algorithmX(cloneList, cloneSolution, cloneParts);
                        }
                        cloneList = copy2DArrayList(listToSolve);
                        cloneSolution = copy2DArrayList(solution);
                        cloneParts = copy2DArrayList(solutionParts);
                    }
                }
            }
        }
    }
  
    // Step 1
    private static ArrayList<Integer> findColumnOnes(ArrayList<Integer> oneCountList, int tryCount, ArrayList<ArrayList<Integer>> listToSolve){
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

                // If the current position (j, i) contains value 1,
                // add 1 to the oneCount
                if (listToSolve.get(j).get(i) == 1){
                    oneCount++;
                }
            }
            
            // If the last position of the current column is reached
            // and the oneCount has the same value as tryCount
            // add the index of the column to the list
            if (oneCount == tryCount){
                oneCountList.add(i);
                // System.out.println(i);
            }
            
            // Reset the count for the next column
            oneCount = 0;
        }

        return oneCountList;
    }


    public static Boolean hasEmptyColom(ArrayList<ArrayList<Integer>> grid){
        
        if(grid.size()==0){
            //System.out.println("returned true");
            return true;
        }
        boolean empty=true;
        for (int i = 0; i < grid.get(0).size(); i++) {
            for (int j = 0; j < grid.size(); j++) {
                if(grid.get(j).get(i)==1){
                    empty=false;
                    break;
                }

                
            }
            if(empty){
               // System.out.println("returned false");

                return false;
            }
            empty=true;
            
        }
        //System.out.println("returned true");
        return true;

    }
// Step 2
    private static ArrayList<Integer> findRowsOnes(ArrayList<Integer> allRowsList, int currentColumn, ArrayList<ArrayList<Integer>> listToSolve) {
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
            if (listToSolve.get(j).get(currentColumn) == 1 && noDuplicate(allRowsList, j)){
                allRowsList.add(j);
            }
        }

        return allRowsList;
    }

// Step 3
    private static void selectAndDelete(int selectedRowIndex, ArrayList<ArrayList<Integer>> listToSolve, ArrayList<ArrayList<Integer>> partList) {
        ArrayList<Integer> removeCols = new ArrayList<>();
        // Check in what columns in the selected row the ones are
        for (int i = 0; i < listToSolve.get(0).size(); i++){
            if (listToSolve.get(selectedRowIndex).get(i) == 1){
                removeCols.add(i);
            }
        }
        int[] deletedRows=new int[listToSolve.size()];
        int counter=0;

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
                if (listToSolve.get(i).get(removeCols.get(j)) == 1 && listToSolve.get(selectedRowIndex).get(removeCols.get(j)) == 1){
                    if (i < selectedRowIndex){
                        selectedRowIndex -= 1;
                        // System.out.println("check");
                    }
                    listToSolve.remove(i);
                    deletedRows[counter]=i;
                    counter++;
                     //print2DList(listToSolve, "removed row");
                }
            }
        }
        
        listToSolve.remove(selectedRowIndex);

        // for (int i = 0; i < deletedRows.length; i++) {
        //     partList.remove(deletedRows[i]);
        // }
        deleteRows(partList, selectedRowIndex, removeCols);
       // print2DList(listToSolve, "removed row");


        // Go through the removeCols backwards
        for (int i = removeCols.size()-1; i > -1; i--){
            for (int j = 0; j < listToSolve.size(); j++){
                listToSolve.get(j).remove((int)removeCols.get(i));
            }
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
    private static boolean fillable(ArrayList<ArrayList<Integer>> parts,ArrayList<ArrayList<Integer>>currentanser){
        
        boolean foundone=false;

        boolean backup=false;
        print2DList(parts, "parts");
        print2DList(currentanser, "grid");
        int tes=1;
        
        
        for (int i = 0; i < currentanser.get(0).size(); i++) {
            for(int j=0;j<currentanser.size();j++){
                if(currentanser.get(j).get(i)==1){
                    foundone=true;
                    break;
                }
            }
            if(!foundone){
                for (int j = 0; j < parts.size(); j++) {
                    if(parts.get(j).get(i)==1){
                        backup=true;
                        break;
                    }
                }
                if(backup==false){
                    return false;
                }
                
            }
            backup=false;
            foundone=false;
        }
        return true;
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
                    if (listToSolve.get(j).get(i) == 1){
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
    
    private static boolean validSolution(ArrayList<ArrayList<Integer>> copyList) {
        // Check if every column has exactly one 1, if not, the solution is wrong
        int oneCount = 0;
        for (int i = 0; i < copyList.get(0).size(); i++){
            for (int j = 0; j < copyList.size(); j++){
                if (copyList.get(j).get(i) == 1){
                    oneCount++;
                }
            }
            if (oneCount != 1){
                return false;
            }
            oneCount = 0;
        }
        return true;
    }

    public static void deleteRows(ArrayList<ArrayList<Integer>> listToSolve, int selectedRowIndex, ArrayList<Integer> removeCols){
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
                if (listToSolve.get(i).get(removeCols.get(j)) == 1 && listToSolve.get(selectedRowIndex).get(removeCols.get(j)) == 1){
                    if (i < selectedRowIndex){
                        selectedRowIndex -= 1;
                        // System.out.println("check");
                    }
                    listToSolve.remove(i);
                    // print2DList(listToSolve, "removed row");
                }
            }
        }
        listToSolve.remove(selectedRowIndex);
    }

    // /*
    // * Used to fill trackKeeper with the indices of the listToSolve
    // *
    // * Whenever a row is removed from the listToSolve it lots of other rows recieve a new index
    // * So by also deleting the same rows of the trackKeeper,
    // * the values of trackKeeper still show on what index the rows of listToSolve started
    // * 
    // * Example:
    // * listToSolve has 24 rows with index 0-23
    // * every row contains another list
    // * trackKeeper then also has 24 rows with index 0-23
    // * every index contains the value of that index
    // * i.e.: index 0 = 0, index 7 = 7 etc.
    // *
    // * if rows 7, 12, 15, and 21 are deleted, it looks as follows:
    // * Start Index:          0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20  21  22  23
    // * New Index:            0   1   2   3   4   5   6       7   8   9   10      11  12      13  14  15  16  17      18  19
    // * trackKeeper value:    0   1   2   3   4   5   6       8   9   10  11      13  14      16  17  18  19  20      22  23
    // *
    // * As shown above,
    // * the indices change, but by using the trackKeeper,
    // * it is possible to keep track of the starting position of the rows
    // */
    // private static void fillTrackKeeper(ArrayList<ArrayList<Integer>> listToSolve) {
    //     for (int i = 0; i < listToSolve.size(); i++){
    //         trackKeeper.add(i);
    //     }
    // }

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
    public static void SaveSolutions(){
        try{
            FileWriter myWriter = new FileWriter("Algorithm_X.txt");
            //this writes all scores back into the file while putting the current score in the right place
            for (int i = 0; i < allSolves.size(); i++) {
                for (int j = 0; j < allSolves.get(i).size(); j++) {
                    for (int j2 = 0; j2 < allSolves.get(i).get(j).size();j2++) {
                        myWriter.write(allSolves.get(i).get(j).get(j2)+" ");
                        
                    }
                    myWriter.write("\n");
                    
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
