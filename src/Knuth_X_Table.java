package src;

import java.util.ArrayList;

public class Knuth_X_Table {
    int columnLength;
    int rowLength;
    int layersAmount;
    int layerSize;
    int volume;
    public static void main(String[] args) {
        Knuth_X_Table table = new Knuth_X_Table(5, 8, 33);
        //TODO make sure that the user can acces both the boxes and the pieces by clicking a button
        //TODO make sure that button changes the value of the useBoxes boolean
        // boolean useBoxes = false;
        // if (useBoxes){
        //    // TODO create a dataBase with the 3 boxes. Make it a int[][][][].
        //        // Consisting of smaller boxes with
        // }else{
            int[][][][] pieceDatabase = Knuth_PentominoDatabase.data;
            ArrayList<ArrayList<Integer>> tempList = new ArrayList<>();
            tempList = table.fillTable(pieceDatabase);
            table.print2DInt(table.arrayListToArray(tempList));
            System.out.println("Total pieces placed: "+tempList.size());
        // }

        
        // // Used for test purposes
        // int[][] testPiece = {{0,0,1},{0,1,1},{1,1,0}};
        // int[][] storedPiece = table.rotatePiece(testPiece);
        // storedPiece = table.rotatePiece(storedPiece);
        // storedPiece = table.rotatePiece(storedPiece);
        // storedPiece = table.rotatePiece(storedPiece);
        // int[][] storedPiece2 = table.flipPiece(testPiece);
        // storedPiece2 = table.flipPiece(storedPiece2);
        // storedPiece2 = table.flipPiece(storedPiece2);
        // storedPiece2 = table.flipPiece(storedPiece2);
    }
    
    public Knuth_X_Table(int rows, int columns, int layers){
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
        

        // Fill the table with pieces in horizontal orientation
        // For every piece
        for (int i = 0; i < database.length; i++){
            // For every mutation
            for (int j = 0; j < database[i].length; j++){
                // For every placement position
                for (int k = 1; k <= volume; k++){

                    // // Used for test purposes
                    // // Using the next 3 lines to be able to stop on a certain step in the programm
                    // if (k == 137 && i == 1 && j == 1){
                    //     System.out.println("Start here");
                    // }

                    // System.out.println("Start K: "+k+" current I: "+i+" current J: "+j);
                    // System.out.println("Horizontal: "+pieceFitHorizontal(k, database[i][j]));
                    if (pieceFitHorizontal(k, database[i][j])){
                        optionsTable.add(placePieceHorizontal(k, database[i][j]));
                    }    
                    // System.out.println("Vertical_LRO: "+pieceFitVertical_LRO(k, database[i][j]));
                    // The name of the method says LRO, this stands for left-right orientation
                    if (pieceFitVertical_LRO(k, database[i][j])){
                        optionsTable.add(placePieceVertical_LRO(k, database[i][j]));
                    }                        
                    // System.out.println("Vertical_TBO: "+pieceFitVertical_TBO(k, database[i][j]));
                    // The name of the method says TBO, this stands for top-bottom orientation
                    if (pieceFitVertical_TBO(k, database[i][j])){
                        optionsTable.add(placePieceVertical_TBO(k, database[i][j]));
                    }
                }
            }
        }

        return optionsTable;
    }
    
    private ArrayList<Integer> placePieceVertical_TBO(int k, int[][] piece) {
        ArrayList<Integer> placeOption = new ArrayList<>();
        // First fill all the positions with zero's
        // These are the positions where no piece or box is located.
        placeOption = fillWithZero(placeOption);

        for (int i = 0; i < piece.length; i++){
            for (int j = 0; j < piece[0].length; j++){
                placeOption.set(k-1, piece[i][j]);
                // System.out.println("k: "+k+" j: "+j);
                // System.out.println("k-1: "+(k-1));
                // System.out.println("Position "+k+" becomes: "+piece[i][j]);
                k+=rowLength;
            }
            k+=rowLength*(columnLength-piece[0].length);
        }
        // print1DList(placeOption);
        return placeOption;
    }
    
    private ArrayList<Integer> placePieceVertical_LRO(int k, int[][] piece) {
        ArrayList<Integer> placeOption = new ArrayList<>();
        // First fill all the positions with zero's
        // These are the positions where no piece or box is located.
        placeOption = fillWithZero(placeOption);
        
        for (int i = 0; i < piece.length; i++){
            for (int j = 0; j < piece[0].length; j++){
                placeOption.set(k-1, piece[i][j]);
                // System.out.println("Position "+k+" becomes: "+piece[i][j]);
                k++;
            }
            k+=layerSize-piece[0].length;
        }
        // print1DList(placeOption);
        return placeOption;
    }    
    
    private ArrayList<Integer> placePieceHorizontal(int k, int[][] piece) {
        ArrayList<Integer> placeOption = new ArrayList<>(); 
        // First fill all the positions with zero's
        // These are the positions where no piece or box is located.
        placeOption = fillWithZero(placeOption);
        
        for (int i = 0; i < piece.length; i++){
            for (int j = 0; j < piece[0].length; j++){
                placeOption.set(k-1, piece[i][j]);
                // System.out.println("Position "+k+" becomes: "+piece[i][j]);
                k++;
            }
            k+=rowLength-piece[0].length;
        }
        // print1DList(placeOption);
        return placeOption;
    }
    
    private boolean pieceFitVertical_TBO(int k, int[][] piece) {
        // The check covers two vertical orientations of the piece
        // The piece can be orientated from front to back, or left to right
        
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

        // Check if the piece fits with the calculated values
        if(layerCount+piece.length <= layersAmount && rowCount+piece[0].length <= columnLength){
            return true;
        }else{
            return false;
        }
    }

    private boolean pieceFitVertical_LRO(int k, int[][] piece) {
        // The check covers two vertical orientations of the piece
        // The piece can be orientated from front to back, or left to right
        
        // Check on what layer (of size 5*8) the piece will be placed
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
        
        // Check if the piece fits with the calculated values
        if(layerCount+piece.length <= layersAmount && columnCount+piece[0].length <= rowLength){
            return true;
        }else{
            return false;
        }
    }

    private boolean pieceFitHorizontal(int k, int[][] piece) {
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
        
        if (rowCount+piece.length > columnLength || columnCount+piece[0].length > rowLength){
            return false;
        }else{
            return true;
        }
    }
    
    // // Rotating the piece 90 degrees to the right
    // public int[][] rotatePiece(int[][] piece){
        //     int[][] rotatedPiece = new int[piece[0].length][piece.length];
        
        //     int l = rotatedPiece[0].length-1;
        //     int k = 0;
    //     boolean firstVisit = true;
    //     // Go through piece array from top left to top right all to way to bottom right
    //     // Simultaniously go through the new piece array from top right to bottom left
    //     for (int i = 0; i < piece.length; i++){
    //         for (int j = 0; j < piece[0].length; j++){
    //             if(firstVisit){
        //                 k = 0;
    //                 firstVisit = false;
    //             }
    //             rotatedPiece[k][l] = piece[i][j];
    //             k++;
    //             if (k > rotatedPiece.length){
        //                 k = 0;
    //             }
    //         }
    //         l--;
    //         firstVisit = true;
    //     }
    //     print2DInt(piece);
    //     print2DInt(rotatedPiece);
    //     return rotatedPiece;
    // }
    
    // // Flipping the piece horizontally
    // public int[][] flipPiece(int[][] piece){
    //     int[][] flippedPiece = new int[piece.length][piece[0].length];
        
    //     int l = flippedPiece[0].length-1;
    //     int k = 0;
    //     boolean firstVisit = true;
    //     // Go through piece array from top left to top right all to way to bottom right
    //     // Simultaniously go through the new piece array from top right to bottom left
    //     for (int i = 0; i < piece.length; i++){
        //         for (int j = 0; j < piece[0].length; j++){
    //             if(firstVisit){
        //                 l = flippedPiece[0].length-1;
        //                 firstVisit = false;
        //             }
        //             flippedPiece[k][l] = piece[i][j];
    //             l--;
    //             if (l < 0){
    //                 l = flippedPiece[0].length-1;
    //             }
    //         }
    //         k++;
    //         firstVisit = true;
    //     }
    //     print2DInt(piece);
    //     print2DInt(flippedPiece);
    //     return flippedPiece;
    // }
    
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
    public int[][] arrayListToArray(ArrayList<ArrayList<Integer>> list){
        int[][] tempArray = new int[list.size()][list.get(0).size()];
        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < list.get(0).size(); j++){
                tempArray[i][j] = list.get(i).get(j);
            }
        }
        return tempArray;
    }
}
