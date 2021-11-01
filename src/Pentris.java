









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
    public void rotatePiece(){

    }

    //Acceleration method, should return an increasingly small int for the amount of second between piece drops
    public int acceleratePiece(){
        int time;

        return time;

    }

    //this method removes a line from the grid
    public void removeLine(int line){

    }


    //this method should check if a line is full
    public void lineCheck(){}


    //this method should update the nextpiece and pieceid variables
    //every piece should get its turn in 12 pieces
    public void nextPiece(){}



    //this method should update its location and rotation based on keypad inputs
    public void Keypad(){}



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




        try{ 
            while(!Lost){
                nextPiece();
                Thread.sleep(acceleratePiece());
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
