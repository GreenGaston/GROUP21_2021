import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

public class basicAI {
    //public static int[][] testgrid = Pentris.grid;
    public static int[][] testgrid = Pentris.grid;
    public static int piececount = 0;
    public static ArrayList<Integer> heightofgridelements;
    public static ArrayList<Integer> amountofholes;
    public static ArrayList<Integer> botmovements = new ArrayList<>();
    public static double high = 1000;
    public static double current;
    public static double a, b, c, d, e;
    public static int countholes;
    public static int[][] pieceToPlace;
    public boolean runrobot;


    public static ArrayList<Integer> getRobotMovements(){
        //These values decide the weight of each check in the score
        a = 0.31006;            //height
        b = 0.45;               //amount of holes
        c = 0.3;                //the bumpiness
        d = 0.96;               //amount of lines cleared
        e = 0.5;                //Highest vs lowest point
        piececount++;
        
        //decides the best movements and return them
        robotMovements();
        return botmovements;
    }

    //This method clones an arraylist
    public static ArrayList<Integer> cloneArrayList(ArrayList<Integer> list){
        ArrayList<Integer> clone = new ArrayList<>();
        for(int i =0; i < list.size(); i++){
            clone.add(list.get(i));
        }
        return clone;
    }


   
    public static void robotMovements(){
        high = 10000; //the highest score is set 10000, so that every other score is below this
        current = 0; //this is the variable for the score every space simulation cycle
        botmovements = new ArrayList<>(); //an empty list that will contain all the bot key inputs
        int pieceID = Pentris.getPieceID(); //the pieceId of the piece to place
        botmovements = new ArrayList<Integer>();//this will contain the best bot key inputs
        int[][] testgridlocal = clone2Dint(Pentris.grid); //the grid that will be used for testing
       
        int bumpiness = 0; //the bumbiness component of the score
        int highmin =0; //the highvslow component of the score
        int x = 0; //x coordinate of the piece
        int y = 0; //y coordinate of the piece
        int k = 4; //amount of mutations of every piece
        int xmovement = 0;
        k = 4;
        for(int i = 3; i >= 0; i--){
            k--; //changing the mutation
            
            ArrayList<Integer> botmovementstest = new ArrayList<>();
            if(piececount == 1){
                botmovementstest.add(KeyEvent.VK_RIGHT);
            }

            for(int bmv = 0; bmv < i; bmv++){ 
                botmovementstest.add(KeyEvent.VK_UP);
            }
           
            for(int j =0; j < testgrid.length - Pentris.pentominoDatabase[pieceID][k].length + 1; j++){

                //simulating spacebar
                testgridlocal = clone2Dint(testgrid);
                pieceToPlace = Pentris.pentominoDatabase[pieceID][i];
                dropPiece(testgridlocal, pieceToPlace, y, x);

                //calculating the rating of a certain  move
                heightofgridelements = cloneArrayList(checkHeight(testgridlocal)); //calculating the height
                highmin = Math.abs(Collections.max(heightofgridelements) - Collections.min(heightofgridelements)); // calculating the height difference between the highest and lowest point
                for(int bump = 0; bump < heightofgridelements.size() - 1; bump++){ //calculating the bumpiness
                    bumpiness += Math.abs(heightofgridelements.get(bump) - heightofgridelements.get(bump+1));
                    
                }
                //calculating the rating of the current move
                current = sumOfArrayList(heightofgridelements)* a + calcHoles(testgridlocal) * b + bumpiness * c - lineCheck(testgridlocal) * d + highmin * e;
               
                
                //if the rating is better than the previous one, store the movements in the botmovements arraylist
                if(current <= high){
                    botmovements = cloneArrayList(botmovementstest);
                    xmovement = x;
                    high = current;

                    for(int l =0; l < j; l++){
                        botmovements.add(KeyEvent.VK_RIGHT);
                    }
                }
                
                //setting all the values to null and increasing the x
                bumpiness = 0;      
                highmin = 0;
                y = 0;   
                x++;

            }
            x = 0;
        }
        
        //at the end of the botmovements it should add the space keyevent
        botmovements.add(KeyEvent.VK_SPACE);
        Pentris.botmovements = cloneArrayList(botmovements);
        
    }

    //calculates the sum of all the values in an integer arraylist
    public static int sumOfArrayList(ArrayList<Integer> list){
        int sum = 0;
        for(int i = 0; i < list.size(); i++){
            sum += list.get(i);
        }
        return sum;

    }

    //clones a 3d integer array
    public static int[][] clone2Dint(int[][] list) {
        int[][] clone = new int[list.length][list[0].length];
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                clone[i][j] = list[i][j];
            }
        }
        return clone;
    }
    

    //simulates the drop o fa piece
    public static void dropPiece(int[][] grid, int[][] piece, int PieceY, int PieceX) {

        for (int i = 1; i < 50; i++) {
          
            if (!PieceFit(grid, piece, PieceY + i, PieceX)) {//as long as the piece fits, drop the piece 1 down
                PieceY += i - 1; // Piece has to be added on this Y position
                addPiece(grid, piece, PieceX, PieceY);
                break;
            }
        }

    }

    //checks if the piece fits
    public static Boolean PieceFit(int[][] grid, int[][] piece, int x, int y) {
        // if the x is negative then the starting point is of the grid and therefor
        // invalid
        if (x < 0) {
            return false;
            // if the piece doesnt extend past the borders
        } else if (y < 0) {
            return false;
        }

        else if (grid.length > y + piece.length - 1) {
            if (grid[0].length > x + piece[0].length - 1) {
                // then it wil check for every square whether the matrix has a 1 there(meaning
                // its a square to be placed)
                // and if the grid already has a value there
                for (int i = 0; i < piece.length; i++) {
                    for (int j = 0; j < piece[0].length; j++) {
                        if (grid[i + y][j + x] >= 0 & piece[i][j] == 1) {
                            /// if so they overlap so you cant place the piece
                            return false;
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //adds the piece to the board
    public static void addPiece(int[][] field, int[][] piece, int x, int y) {
        for (int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1) {
                    // Add the ID of the pentomino to the board if the pentomino occupies this
                    // square
                    field[x + i][y + j] = 30;
                }
            }
        }
    }

    //checks height of field
    public static ArrayList<Integer> checkHeight(int[][] grid){
        ArrayList<Integer> heightofgridelements1 = new ArrayList<Integer>();
        int top;
        boolean check;
        for(int i = 0; i < grid.length; i++){
            top = 0;
            check = false;
            inner:
            for(int j =0; j < grid[i].length; j++){
               if(grid[i][j] > -1){ //if the grid at a certain point from the top is filled with a value higher than -1, there is a part of a piece there
                    heightofgridelements1.add(grid[i].length - top); //adding the height of the colunn to the arraylist
                    check = true; //if this is true the height is not 0
                    break inner;//break the loop and look for the top in the next column
               }
               top++;
            }
            if(check == false){//if there is no piece(part) in this column, the height is 0
                heightofgridelements1.add(0);
            }          
        }
        return heightofgridelements1;
    }
    


    //debugging part to print out the grid 
public static void printGrid(int[][] field){
    for(int j = 0; j < field[0].length; j++){
        for(int i = 0; i < field.length; i++){
            if(i == 0){
                System.out.print("|");
            }
            if(field[i][j] == 0){
                System.out.print("[ ]");
            }
            else if(field[i][j] > -1){
                System.out.print("[ ]");
            }
            else{
                System.out.print("   ");
            }
            if(i == field.length - 1){
                System.out.print("|");
            }
        }
        System.out.println();
    }
    System.out.println();
}

public static int lineCheck(int[][] grid) {
    int count = 0;
    int lines = 0;


    for (int line = grid[0].length - 1; line >= 0; line--) {// loops through every line
        for (int i = 0; i < grid.length; i++) {// loops through the width
            if (grid[i][line] > -1) {// checks if every element in a line is > than -1 and thus filled
                count++;
            }
        }
        if (count >= grid.length) {// if the count is equal to the grid[line] lenght then the line is full and
                                   // needs to be removed.
            count = 0;
            lines++;

        } else {
            count = 0;
        }
    }
    return lines;
}


//calculates the amount of holes in the grid
public static int calcHoles(int[][] grid) {
    int[][] cloneGrid = clone2Dint(grid);

    int holes = 0;
    boolean foundTop = false;
    for (int i = 0; i < cloneGrid.length; i++) {
        for (int j = 0; j < cloneGrid[0].length; j++) {
            if (cloneGrid[i][j] > -1) {
                foundTop = true;
            }
            if (foundTop && cloneGrid[i][j] == -1) {
                holes++;

            }
        }
        foundTop = false;
    }
    return holes;
}
}