









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


    //contains location of a piece
    public Point pieceLocation;
    //contains current position
    public int[][] currentPiece;
    //contains current pieceID
    public int pieceID;
    //contains the held piece
    public int[][] heldPiece;
    //contains the held pieceID
    public int heldPieceID;
    //contains rotation
    public int rotation;
    //contains the pieceIDs of the next pieces
    public ArrayList<Integer> pieceIDs = new ArrayList<Integer>();
    //contains all pentominoPieces
    public int[][][][] pentominoDatabase=PentominoDatabase.data;
    




    //this method should make the piece fall by 1 if it can fall
    public void fallingPiece(){
    }



    //this method should rotate a piece if posible has to rotate left and right
    //this should be done in the rotation variable
    public void rotatePiece(){

    }

    //Acceleration method
    public void acceleratePiece(){

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
    public void holdPiece(){}




    
}
