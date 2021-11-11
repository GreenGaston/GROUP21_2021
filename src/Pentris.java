import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
<<<<<<< HEAD
import java.util.concurrent.Future;

import java.util.Random; // for nextPiece method 
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pentris {
        public static ArrayList<Integer> pentPieces = new ArrayList<Integer>();
    
    public int[][] grid;
=======

public class Pentris {
    final public static int height=15;
    final public static int width=5;

    //the startposition for both the X and the Y
    final public static int StartY=0;
    final public static int StartX=2;

    // This is the minimum amount of seconds the piece waits until it drops 1 down again.
    final public static double minimumWait = 0.25; 
    // Everytime that the timeframe fits in the time, the pieces drop a bit faster
    final public static double accelerationTimeFrame = 10; 
    // Every time frame the pieces will fall 0.05 seconds faster.
    final public static double acceleration = 0.05; 
    // Milliseconds times 1000 creates seconds. //// This will be used for the Thread.sleep(long milliseconds) to convert the milliseconds to seconds
    final public static long millisecondsToSeconds = 1000; 

    public static int[][] grid = new int[height][width];
    public static ArrayList<Integer> pentPieces = new ArrayList<Integer>();
>>>>>>> 91b4a668fe91af6ebc1ef37b7343b566c8fcdc4b
    //contains current pieceID
    public static int pieceID;
    //contains the held pieceID
    public static int heldPieceID=-1;
    //contains rotation
    public static int rotation=0;
<<<<<<< HEAD

    public static int FutureRotation;
=======
>>>>>>> 91b4a668fe91af6ebc1ef37b7343b566c8fcdc4b
    //contains the pieceIDs of the next pieces
    public static ArrayList<Integer> pieceIDs = new ArrayList<Integer>();
    // contains all pentominoPieces
    public static int[][][][] pentominoDatabase = PentominoDatabase.data;

    //the current location of a piece
<<<<<<< HEAD
    public volatile static int PieceX=0;
    public volatile static int PieceY=2;
=======
    public static volatile int PieceX=StartX;
    public static volatile int PieceY=StartY;
>>>>>>> 91b4a668fe91af6ebc1ef37b7343b566c8fcdc4b

    // variable to end the game
    public static boolean Lost = false;

    // Keys used for playing pentris
    private static int left = KeyEvent.VK_LEFT;
    private static int right = KeyEvent.VK_RIGHT;
    private static int up = KeyEvent.VK_UP;
    private static int down = KeyEvent.VK_DOWN;
    private static int space = KeyEvent.VK_SPACE;
    private static int c = KeyEvent.VK_C;
    private static int z = KeyEvent.VK_Z;

    // this method should hold the current piece
    public static void holdPiece() {
        if (heldPieceID == -1) {
            heldPieceID = pieceID;
            PieceX = StartX;
            PieceY = StartY;
            nextPiece();
        } else {
            int temp = pieceID;
            pieceID = heldPieceID;
            heldPieceID = temp;
            PieceX = StartX;
            PieceY = StartY;
        }
    }

    // private static ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    // public static void nextPiece() {
    // PieceX = StartX;
    // PieceY = StartY;
    // rotation = 0;
    // if (nextPieces.isEmpty()) {
    // Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
    // Collections.shuffle(nextPieces);
    // }

    // // System.out.println(nextPieces.get(0));
    // pieceID = nextPieces.get(0);
    // nextPieces.remove(0);
    // }

    // this method should update the nextpiece and pieceid variables
    // every piece should get its turn in 12 pieces
    public static void nextPiece() { // Lianne
        PieceX = StartX; // reset starting points
        PieceY = StartY;
        rotation = 0; // reset rotation to 0
        if (pentPieces.isEmpty()) {
            // If there is only one element in the arraylist, clear the arraylist
            // and add all the IDs to the arraylist again
            pentPieces.add(0);
            pentPieces.add(1);
            pentPieces.add(2);
            pentPieces.add(3);
            pentPieces.add(4);
            pentPieces.add(5);
            pentPieces.add(6);
            pentPieces.add(7);
            pentPieces.add(8);
            pentPieces.add(9);
            pentPieces.add(10);
            pentPieces.add(11); // make an arraylist with the pentomino IDs
            Collections.shuffle(pentPieces); // randomize the order of the arraylist
        } // If there's more than one element in the arraylist, you can get a
          // pentomino out of the list
        pieceID = pentPieces.get(0); // take the first ID and add it to the
        // pieceIDs arraylist
        pentPieces.remove(0); // remove that piece from the pentPieces arraylist
    }
    //this method should rotate a piece if posible has to rotate left and right
    //this should be done in the rotation variable
    public static void rotatePiece(Boolean right){
<<<<<<< HEAD
       
       if( right == true){
           FutureRotation = rotation+1;
       }

       else{
           FutureRotation= rotation-1;
       }
       
       if(rotation == 3 && right == true){
        FutureRotation = 0;
        
       }

       else if (rotation == 0 && right == false){
           FutureRotation = 3;
       }

       if (PieceFit(grid, pieceID, FutureRotation, PieceX, PieceY)){
          rotation = FutureRotation;
       }
      
=======
        // TODO Samanta Dil Mohamed
        System.out.println("Piece is rotated!");
    }


    //this method should make the piece fall by 1 if it can fall
    public static void fallingPiece(){
        // TODO Yuxuan Kong
>>>>>>> 91b4a668fe91af6ebc1ef37b7343b566c8fcdc4b
    }

    // this method should make the piece fall by 1 if it can fall
    public static void fallingPiece() {
        if (PieceFit(grid, pieceID, rotation, PieceY + 1, PieceX)) {
            PieceY += 1;
            // System.out.println("fell");
        } else {
            placePiece();
        }
    }

    // Acceleration method, should return an increasingly small int for the amount
    // of second between piece drops
    public static double fallingAcceleration(double time) {
        double timeIndicate = 1;
        countingloop: for (double i = accelerationTimeFrame; i < time; i += accelerationTimeFrame) {
            timeIndicate -= acceleration;
            if (timeIndicate <= minimumWait) {
                timeIndicate = minimumWait;
                break countingloop;
            }
        }
        return timeIndicate;
    }

    public static void dropPiece() {

        for (int i = 1; i < 50; i++) {
            // System.out.println("pieceY = " + PieceY);
            if (!PieceFit(grid, pieceID, rotation, PieceY + i, PieceX)) {
                // System.out.println("her");

                PieceY += i - 1; // Piece has to be added on this Y position
                break;
            }
        }
    }

    //this method removes a line from the grid
    public static void removeLine(int line){
        int[][] updatedGrid = new int[grid.length][grid[0].length];
        int placeInGrid;


<<<<<<< HEAD
    
    //this method should update its location and rotation based on keypad inputs
    public void keypadMethod(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == left && PieceFit(grid,pieceID,rotation,PieceX-1,PieceY)) {
            
                PieceX -= 1; // If the keypad left is pressed the piece should go 1 position to the left. That's why the x coordinate of the piece is subtracted by 1.
            //System.out.println("pieceX = "+pieceX); 
        }else if (keyCode == right && PieceFit(grid, pieceID, rotation, PieceX+1,PieceY)) {
            
                PieceX += 1; // If the keypad right is pressed the piece should go 1 position to the right. That's why the x coordinate of the piece is added by 1.
            
            //System.out.println("pieceX = "+pieceX);
        }else if (keyCode == down) {
            if (PieceFit(grid,pieceID,rotation,PieceX, PieceY+1)){
                PieceY += 1; // If the keypad down is pressed the piece should go down to the place where it is going to be placed. (To show it smoothly in the UI, drop it down using a much smaller wait then when playing the normal way.)
                fallingPiece();
            }
        }
        grid = updatedGrid;
    }
=======
    //this method should check if a line is full
    public static void lineCheck(){
        int count = 0; 
>>>>>>> 91b4a668fe91af6ebc1ef37b7343b566c8fcdc4b

            for(int line = grid[0].length - 1; line >= 0; line--){//loops through every line
                for(int i = 0; i < grid.length; i++){//loops through the width
                    if(grid[i][line] > -1){//checks if every element in a line is > than -1 and thus filled
                        count++;
                    }
                }
                if(count >= grid.length){//if the count is equal to the grid[line] lenght then the line is full and needs to be removed.
                    count = 0;

                    removeLine(line); 
                    line++;//Everything moved down 1 line, so the check has to move down 1 as well
                }
                else{
                    count = 0;
                }
            }               
    }

    // this function evaluated if a piece can be placed on a give grid at a certain
    // x and y location
    public static Boolean PieceFit(int[][] grid, int PieceID, int Piecemutation, int x, int y) {
        // shorthand for readability
        int[][][][] database = PentominoDatabase.data;
        // if the x is negative then the starting point is of the grid and therefor
        // invalid
        if (x < 0) {
            return false;
            // if the piece doesnt extend past the borders
        } else if (grid.length > y + database[PieceID][Piecemutation].length - 1) {
            if (grid[0].length > x + database[PieceID][Piecemutation][0].length - 1) {
                // then it wil check for every square whether the matrix has a 1 there(meaning
                // its a square to be placed)
                // and if the grid already has a value there
                for (int i = 0; i < database[PieceID][Piecemutation].length; i++) {
                    for (int j = 0; j < database[PieceID][Piecemutation][0].length; j++) {
                        if (grid[i + y][j + x] >= 0 & database[PieceID][Piecemutation][i][j] == 1) {
                            /// if so they overlap so you cant place the piece
                            return false;
                        }
                    }
                }
                // int[][]gridclone=clone2Dint(grid);
                // Search.addPiece(gridclone, database[PieceID][Piecemutation], Piecemutation,
                // y, x);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // this method should update its location and rotation based on keypad inputs
    public static void keypadMethod(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (keyCode == left && PieceFit(grid, pieceID, rotation, PieceY, PieceX - 1)) {
            PieceX -= 1; // If the keypad left is pressed the piece should go 1 position to the left.
                         // That's why the x coordinate of the piece is subtracted by 1.
            // System.out.println("pieceX = " + PieceX);

        } else if (keyCode == right && PieceFit(grid, pieceID, rotation, PieceY, PieceX + 1)) {
            PieceX += 1; // If the keypad right is pressed the piece should go 1 position to the right.
                         // That's why the x coordinate of the piece is added by 1.
            // System.out.println("pieceX = " + PieceX);

        } else if (keyCode == down && PieceFit(grid, pieceID, rotation, PieceY + 1, PieceX)) {
            PieceY += 1; // If the keypad down is pressed the piece should go down one extra place on
                         // on top of the standard falling of the piece.
            // System.out.println("pieceY = " + PieceY);

        } else if (keyCode == up) {
            rotatePiece(true); // If the keypad up is pressed the piece should be rotated right once.

        } else if (keyCode == space) {
            // System.out.println("check");
            dropPiece(); // Drop the piece if spacebar is pressed. Drop it to the place where it is
                         // going to be placed as far down as possible

        } else if (keyCode == z) {
            rotatePiece(false); // If the keypad z is pressed the piece should be rotated right once.

        } else if (keyCode == c) {
            holdPiece(); // If the keypad c is pessed the piece should be stored and used at a later
                         // point in the game.
        }

        int[][] gridclone = clone2Dint(grid);

        // Show the updated grid in the UI everytime a key is pressed.
        if (PieceFit(gridclone, pieceID, rotation, PieceX, PieceY)) {
            grid = clone2Dint(gridclone);
            Search.addPiece(grid, pentominoDatabase[pieceID][rotation], pieceID, PieceX, PieceY);
            ui.setState(grid);
        }

    }

    public static void placePiece() {
        addPiece(grid, pentominoDatabase[pieceID][rotation], pieceID, PieceX, PieceY);
        nextPiece();
        if (!PieceFit(grid, pieceID, rotation, StartY, StartX)) {
            Lost = true;

        }
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

    public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y) {
        for (int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1) {
                    // Add the ID of the pentomino to the board if the pentomino occupies this
                    // square
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }

    public static void fillGridEmpty(int[][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = -1;
            }
        }
    }

    public static void main(String[] args) {
        fillGridEmpty(grid);
        nextPiece();
        long startingTime = System.currentTimeMillis();
        long currentTime;
        UI ui = new UI(width, height, 30);
        int[][] gridclone = clone2Dint(grid);
        try {
            while (!Lost) {
                ui.setState(gridclone);

                // Wait a little moment before dropping the piece 1 block. This is on basis of the time the player is playing.
                currentTime = System.currentTimeMillis();
                long playingTime = currentTime - startingTime;
                Thread.sleep((long) fallingAcceleration(playingTime));

                // Drop the piece 1 block if it can be dropped
                fallingPiece();
                // Check if there is a full line on the grid.
                    // If so, remove it and update the grid so every line above the removed line is dropped 1 line.
                lineCheck();

                gridclone = clone2Dint(grid);
                addPiece(gridclone, pentominoDatabase[pieceID][rotation], pieceID, PieceX, PieceY);
            }
        } catch (InterruptedException e) {
        }
    }
}
public static void keypadMethod(KeyEvent e) {
}