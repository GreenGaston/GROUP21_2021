// package src;

import java.util.ArrayList;

public class Knuth_X_Table_Parcels {
    int columnLength;
    int rowLength;
    int layersAmount;
    int layerSize;
    int volume;
    public static void main(String[] args) {
        Knuth_X_Table_Parcels table = new Knuth_X_Table_Parcels(5, 8, 33);
        int[][][][] parcelDatabase = ParcelDatabase.parcels;
        ArrayList<ArrayList<Integer>> tempList = new ArrayList<>();
        tempList = table.fillTable(parcelDatabase);
        // table.print2DInt(table.arrayListToArray(tempList));
        System.out.println("Total pieces placed: "+tempList.size());
    }
    
    public Knuth_X_Table_Parcels(int rows, int columns, int layers){
        this.columnLength = rows;
        this.rowLength = columns;
        this.layersAmount = layers;
        this.layerSize = columnLength*rowLength;
        this.volume = layerSize*layersAmount;

    }
    
    public ArrayList<ArrayList<Integer>> fillTable(int[][][][] database){
        // The box to fill is 16.5 * 2.5 * 4.0 (measured per 1 meter)
        // The box to fill is 33.0 * 5.0 * 8.0 (measured per 0.5 meters)
        // 33 * 5 * 8 =  1320 boxes of size 0.5 * 0.5 * 0.5 to be filled
        // The tables will be filled as follows:
            // Every position of the 1320 possible position will either get 1 or 0
            // It is a 2D table, with on one axes 1320 positions to be filled
            // And on the other axes all the pieces in all mutations on all possible places
            // Every spot that has been filled by a piece or box, will recieve a 1, others a 0
        // Look at the table as follows:
            // The first layer has 8 columns and 5 rows
            // There are 33 layers
            // So for example, position 687 on the table represents
                // the 7th position of the first row on the 18th layer
                // 17 * 40 = 680, 687-680=7, so it is on the 18th layer
                // 7 < 8 so it's just in row 1 on position 7



        ArrayList<ArrayList<Integer>> optionsTable = new ArrayList<>();
        

        // Fill the table with parcels in all orientations
        // For every piece and it's orientations
        for (int i = 0; i < database.length; i++){
                // For every placement position
            for (int k = 1; k <= volume; k++){

                // // Used for test purposes
                // // Using the next 3 lines to be able to stop on a certain step in the programm
                // if (k == 137 && i == 1 && j == 1){
                //     System.out.println("Start here");
                // }

                // System.out.println("Start K: "+k+" current I: "+i+" current J: "+j);
                // System.out.println("Horizontal: "+pieceFitHorizontal(k, database[i][j]));
                if (parcelFit(k, database[i])){
                    optionsTable.add(placeParcel(k, database[i]));
                }
            }
        }

        return optionsTable;
    }
    
        
    
    private ArrayList<Integer> placeParcel(int k, int[][][] parcel) {
        ArrayList<Integer> placeOption = new ArrayList<>();
        // First fill all the positions with zero's
        // These are the positions where no parcel is located.
        fillWithZero(placeOption);

        //TODO place the code for placing the parcel here

        return placeOption;
    }

    private boolean parcelFit(int k, int[][][] parcel) {
        // Check on what layer the piece will be placed
        int layerCount = -1;
        for (int i = 0; i < k; i+=layerSize){
            layerCount++;
        }

        // Check on what position in the layer the piece will be placed
        int layerPosition = k-layerCount*layerSize;
        
        // Check in what row the piece will be placed
        int rowCount = -1;
        for (int i = 0; i < layerPosition; i+=rowLength){
            rowCount++;
        }
        
        // Check in what column the piece will be placed
        int columnCount = layerPosition-1-rowCount*rowLength;
        
        if (layerCount+parcel.length > layersAmount || rowCount+parcel[0].length > columnLength || columnCount+parcel[0][0].length > rowLength){
            return false;
        }else{
            return true;
        }
    }

    private ArrayList<Integer> fillWithZero(ArrayList<Integer> list) {
        for (int i = 0; i < volume; i++){
            list.add(0);
        }
        return list;
    }

    // Used for test purposes
    private void print1DList(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); i++){
            System.out.print(list.get(i)+" ");
        }
        System.out.println();
    }
    
    // Used for test purposes
    private void print2DInt(int[][] array){
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j++){
                System.out.print(array[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Used for test purposes
    private int[][] arrayListToArray(ArrayList<ArrayList<Integer>> list){
        int[][] tempArray = new int[list.size()][list.get(0).size()];
        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < list.get(0).size(); j++){
                tempArray[i][j] = list.get(i).get(j);
            }
        }
        return tempArray;
    }
}