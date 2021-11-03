import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Future;

import java.util.Random; // for nextPiece method 
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pentris {
        public static ArrayList<Integer> pentPieces = new ArrayList<Integer>();
    
    public int[][] grid;
    //contains current pieceID
    public static int pieceID;
    //contains the held pieceID
    public int heldPieceID=-1;
    //contains rotation
    public static int rotation=0;

    public static int FutureRotation;
    //contains the pieceIDs of the next pieces
    public ArrayList<Integer> pieceIDs = new ArrayList<Integer>();
    //contains all pentominoPieces
    public int[][][][] pentominoDatabase=PentominoDatabase.data;

    //the current location of a piece
    public volatile static int PieceX=0;
    public volatile static int PieceY=2;

    //the startposition for every position
    public int StartY=0;
    public int StartX=2;
    
    //variable to end the game
    public boolean Lost=false;


    //this method should make the piece fall by 1 if it can fall
    public void fallingPiece(){

    }


    //this method should rotate a piece if posible, has to rotate left and right
    //this should be done in the rotation variable
    public static void rotatePiece(Boolean right){
       
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
      
    }


    final public static double minimumWait = 0.25; 
    // This is the minimum amount of seconds the piece waits until it drops 1 down again.
    final public static double accelerationTimeFrame = 10; 
    // Everytime that the timeframe fits in the time, the pieces drop a bit faster
    final public static double acceleration = 0.05; 
    // Every time frame the pieces will fall 0.05 seconds faster.
    final public static long millisecondsToSeconds = 1000; 
    // Milliseconds times 1000 creates seconds. //// This will be used for the Thread.sleep(long milliseconds) to convert the milliseconds to seconds
    
    
    //Acceleration method, should return an increasingly small int for the amount of second between piece drops
    public double fallingAcceleration(double time){
        double timeIndicate = 1;
        countingloop:
        for (double i = accelerationTimeFrame; i < time; i+=accelerationTimeFrame){
            timeIndicate -= acceleration;
            if(timeIndicate <= minimumWait){
                timeIndicate = minimumWait;
                break countingloop;
            }    
        }return timeIndicate;}

    //this method removes a line from the grid
    public void removeLine(int line){

    }


    //this method should check if a line is full
    public void lineCheck(){

    }


    //this method should update the nextpiece and pieceid variables
    //every piece should get its turn in 12 pieces
    public static void nextPiece() { //Lianne
        // choose randomly from 12 pieces, 
    pieceIDs.clear();
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
    pentPieces.add(11);

    if (pentPieces.size() < 1) {
        pentPieces.clear();
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
        pentPieces.add(11);
    }else { 
        Collections.shuffle(pentPieces);
        for (int i=0 ; i<pentPieces.size() ; i++) { // go through shuffles arraylist
            pieceIDs.add(pentPieces.get(i));
            pentPieces.remove(i);
        }

        int StartY=0;
        int StartX=2;
        // rotations needs to become zero
        }
    }
    

    private int left = KeyEvent.VK_LEFT;
    private int down = KeyEvent.VK_DOWN;
    private int right = KeyEvent.VK_RIGHT;
    private int space = KeyEvent.VK_SPACE;

    
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
            System.out.println("pieceY = "+PieceY);
        }else if (keyCode == space) {
            rotatePiece(true); // If the spacebar is pressed the piece should be rotated once.
        }
    }


    //this method should hold the current piece 
    public void holdPiece(){
        if(heldPieceID==-1){
            heldPieceID=pieceID;
            PieceX=StartX;
            PieceY=StartY;
            nextPiece();
        }else{
            int temp=pieceID;
            pieceID=heldPieceID;
            heldPieceID=temp;
            PieceX=StartX;
            PieceY=StartY;
        }
    }

    //this function evaluated if a piece can be placed on a give grid at a certain x and y location
    public static Boolean PieceFit(int[][]grid,int PieceID,int Piecemutation,int x,int y){
        //if the x is negative then the starting point is of the grid and therefor invalid
        if(x<0){         
                return false;
            }
            //shorthand for readability
            int[][][][]database=PentominoDatabase.data;
            //if the piece doesnt extend past the borders
            if(grid.length>y+database[PieceID][Piecemutation].length-1){
                if(grid[0].length>x+database[PieceID][Piecemutation][0].length-1){
                    //then it wil check for every square whether the matrix has a 1 there(meaning its a square to be placed)
                    //and if the grid already has a value there
                    for(int i=0;i<database[PieceID][Piecemutation].length;i++){
                        for(int j=0;j<database[PieceID][Piecemutation][0].length;j++){
                            if(grid[i+y][j+x]>=0&database[PieceID][Piecemutation][i][j]==1){
                                ///if so they overlap so you cant place the piece
                                return false;
                            }
                        }
                    }
                    //int[][]gridclone=clone2Dint(grid);
                    //Search.addPiece(gridclone, database[PieceID][Piecemutation], Piecemutation, y, x);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }


// here should write the methods which are used when you start a game
    public Pentris(){
        long startingtime=System.currentTimeMillis();
        long endingtime;
        try{ 
            while(!Lost){
                endingtime=System.currentTimeMillis();
                Thread.sleep((long)fallingAcceleration(endingtime-startingtime));
                lineCheck();
                fallingPiece();
            }
        }
        catch(InterruptedException e){}
    }


//this method only makes an instance of the game
    public static void main() {
        Pentris game = new Pentris();

    } 
}
