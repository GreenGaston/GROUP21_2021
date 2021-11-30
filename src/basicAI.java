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
        a = 0.51006;        //height
        b = 0.55;           //amount of holes
        c = 0.28;           //the bumpiness
        d = 0.86;           //amount of lines cleared
        e = 0.15;           //Highest vs lowest point
        piececount++;
        robotMovements();
        return botmovements;
    }

    public static ArrayList<Integer> cloneArrayList(ArrayList<Integer> list){
        ArrayList<Integer> clone = new ArrayList<>();
        for(int i =0; i < list.size(); i++){
            clone.add(list.get(i));
        }
        return clone;
    }


    ///make 2 list and add them to one and another
    public static void robotMovements(){
        high = 10000;
        current = 0;
        //testgrid = test123;
        botmovements = new ArrayList<>();
        int pieceID = Pentris.getPieceID();
        //int pieceID = 9;
        botmovements = new ArrayList<Integer>();
        int[][] testgridlocal = clone2Dint(Pentris.grid);
       
        int bumpiness = 0;
        int highmin =0;
        int x = 0; 
        int y = 0;
        int k = 4;
        int xmovement = 0;
        for(int j =0; j < testgrid.length - Pentris.pentominoDatabase[pieceID][k].length + 1; j++){
            ArrayList<Integer> movementx = new ArrayList<>();
            //System.out.println("X: " + x);
           
            
            for(int i = 3; i >= 0; i--){
                k--;
                ArrayList<Integer> botmovementstest = cloneArrayList(movementx);

               
                for(int bmv = 0; bmv < i; bmv++){
                    botmovementstest.add(KeyEvent.VK_UP);
                }

                //simulating spacebar
                testgridlocal = clone2Dint(testgrid);
                pieceToPlace = Pentris.pentominoDatabase[pieceID][i];
                dropPiece(testgridlocal, pieceToPlace, y, x);
                heightofgridelements = cloneArrayList(checkHeight(testgridlocal));
                highmin = Math.abs(Collections.max(heightofgridelements) - Collections.min(heightofgridelements));
                

                for(int bump = 0; bump < heightofgridelements.size() - 1; bump++){
                    bumpiness += Math.abs(heightofgridelements.get(i) - heightofgridelements.get(i+1));
                    //System.out.println("bump: " + bumpiness);
                }
                current = sumOfArrayList(heightofgridelements)* a + calcHoles(testgridlocal) * b + bumpiness * c - lineCheck(testgridlocal) * d + highmin * e + Collections.max(heightofgridelements) * 5;
               
                
                 //System.out.println();
                if(current <= high){
                    botmovements = cloneArrayList(botmovementstest);
                    xmovement = x;
                    high = current;
                //debugging part:
                //  System.out.println("------------------------------------------------------------------");
                //  System.out.println("Piece number: " + piececount);
                //  System.out.println();
                //  System.out.println("sum of height: " + sumOfArrayList(heightofgridelements));
                //  System.out.println("Amount of hole: " + calcHoles(testgridlocal));
                //  System.out.println("Lines cleared: " + lineCheck(testgridlocal));
                //  System.out.println("The bumpiness: " + bumpiness);
                //  System.out.println("The difference between maxmin: " + highmin);
                //  System.out.println();
                //  System.out.println("Current score: " + current + " highscore: " + high);
                //  System.out.println("movement: " + botmovementstest);
                //  System.out.println();
                //  printGrid(testgridlocal);
                }
                bumpiness = 0;      
                highmin = 0;
                y = 0;       
               
            }
            x++;
            k = 4;
        }
        for(int l =0; l < xmovement; l++){
            botmovements.add(KeyEvent.VK_RIGHT);
        }
        botmovements.add(KeyEvent.VK_SPACE);
        Pentris.botmovements = cloneArrayList(botmovements);
        
    }
    public static int sumOfArrayList(ArrayList<Integer> list){
        int sum = 0;
        for(int i = 0; i < list.size(); i++){
            sum += list.get(i);
        }
        return sum;

    }
    public static int[][] clone2Dint(int[][] list) {
        int[][] clone = new int[list.length][list[0].length];
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                clone[i][j] = list[i][j];
            }
        }
        return clone;
    }
    
    public static void dropPiece(int[][] grid, int[][] piece, int PieceY, int PieceX) {

        for (int i = 1; i < 50; i++) {
          
            if (!PieceFit(grid, piece, PieceY + i, PieceX)) {
                PieceY += i - 1; // Piece has to be added on this Y position
                addPiece(grid, piece, PieceX, PieceY);
                break;
            }
        }

    }

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
               if(grid[i][j] > -1){
                    heightofgridelements1.add(grid[i].length - top);
                    check = true;
                    break inner;
               }
               top++;
            }
            if(check == false){
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