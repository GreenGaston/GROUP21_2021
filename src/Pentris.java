
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;




public class Pentris {

    public int[][] grid;


    
    //contains current pieceID
    public int pieceID;
    //contains the held pieceID
    public int heldPieceID=-1;
    //contains rotation
    public int rotation;
    //contains the pieceIDs of the next pieces
    public ArrayList<Integer> pieceIDs = new ArrayList<Integer>();
    //contains all pentominoPieces
    public int[][][][] pentominoDatabase=PentominoDatabase.data;

    //the current location of a piece
    public volatile int PieceX=0;
    public volatile int PieceY=2;

    //the startposition for every position
    public int StartY=0;
    public int StartX=2;
    
    //variable to end the game
    public boolean Lost=false;



    //this method should make the piece fall by 1 if it can fall
    public void fallingPiece(){
    }



    //this method should rotate a piece if posible has to rotate left and right
    //this should be done in the rotation variable
    public void rotatePiece(Boolean right){

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
        }

        return timeIndicate;}

    //this method removes a line from the grid
    public void removeLine(int line){

    }


    //this method should check if a line is full
    public void lineCheck(){}


    //this method should update the nextpiece and pieceid variables
    //every piece should get its turn in 12 pieces
    public void nextPiece(){}








    private int left = KeyEvent.VK_LEFT;
    private int down = KeyEvent.VK_DOWN;
    private int right = KeyEvent.VK_RIGHT;
    private int space = KeyEvent.VK_SPACE;
    //this method should update its location and rotation based on keypad inputs
    public void keypadMethod(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == left) {
            
            if (PieceX != 0 && pieceFit(grid,pieceID,rotation,PieceX-1,PieceY)){
                PieceX -= 1; // If the keypad left is pressed the piece should go 1 position to the left. That's why the x coordinate of the piece is subtracted by 1.
            }
            //System.out.println("pieceX = "+pieceX); 
        }else if (keyCode == right && pieceFit(grid, pieceID, rotation, PieceX+1,PieceY)) {
            if (PieceX != width-1){
                PieceX += 1; // If the keypad right is pressed the piece should go 1 position to the right. That's why the x coordinate of the piece is added by 1.
            }
            //System.out.println("pieceX = "+pieceX);
        }else if (keyCode == down) {
            if (pieceFit(grid,pieceID,rotation,PieceX, PieceY+1)){
                PieceY += 1; // If the keypad down is pressed the piece should go down to the place where it is going to be placed. (To show it smoothly in the UI, drop it down using a much smaller wait then when playing the normal way.)
                dropPiece();
            }
            System.out.println("pieceY = "+PieceY);
        }else if (keyCode == space) {
            rotationMethod(); // If the spacebar is pressed the piece should be rotated once.
        }
    }



    //this method should hold the current piece 
    public void holdPiece(){
        if(heldPieceID==-1){
            heldPieceID=pieceID;
            PieceX=StartX;
            PieceY=StartY;
            nextPiece();

        }
        else{
            int temp=pieceID;
            pieceID=heldPieceID;
            heldPieceID=temp;
            PieceX=StartX;
            PieceY=StartY;
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
    public static void main(){
        Pentris game = new Pentris();



    }
    
}
