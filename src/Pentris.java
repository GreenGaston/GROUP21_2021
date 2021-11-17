// package src;

import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Pentris {
    final public static int height = 18;
    final public static int width = 8;

    // the startposition for both the X and the Y
    final public static int StartY = 0;
    final public static int StartX = 0;

    // This is the minimum amount of seconds the piece waits until it drops 1 down
    // again.
    final public static double minimumWait = 1000;
    // Everytime that the timeframe fits in the time, the pieces drop a bit faster
    final public static double accelerationTimeFrame = 10;
    // Every time frame the pieces will fall 0.05 seconds faster.
    final public static double acceleration = 0.05;
    // Milliseconds times 1000 creates seconds. //// This will be used for the
    // Thread.sleep(long milliseconds) to convert the milliseconds to seconds
    final public static long millisecondsToSeconds = 1000;

    public static int[][] grid = new int[width][height];
    public static ArrayList<Integer> pentPieces = new ArrayList<Integer>();
    // contains current pieceID
    public static int pieceID;
    // contains the held pieceID
    public static int heldPieceID = -1;
    // contains rotation
    public static int rotation = 0;

    public static int FutureRotation;
    // contains the pieceIDs of the next pieces
    public static ArrayList<Integer> pieceIDs = new ArrayList<Integer>();
    // contains all pentominoPieces
    public static int[][][][] pentominoDatabase = PentominoDatabase.data;

    // the current location of a piece
    public static volatile int PieceX = StartX;
    public static volatile int PieceY = StartY;

    // variable to end the game
    public static boolean Lost = false;

    public static UI ui = new UI(width, height, 30,false);
    public static int[][] gridclone = clone2Dint(grid);
    public static boolean BEEP = false;

    public static boolean showMenu = false;
    public static boolean paused = false;


    // Keys used for playing pentris
    private static int left = KeyEvent.VK_LEFT;
    private static int right = KeyEvent.VK_RIGHT;
    private static int up = KeyEvent.VK_UP;
    private static int down = KeyEvent.VK_DOWN;
    private static int space = KeyEvent.VK_SPACE;
    private static int c = KeyEvent.VK_C;
    private static int z = KeyEvent.VK_Z;
    private static int x = KeyEvent.VK_X;
    private static int esc = KeyEvent.VK_ESCAPE;

    public static boolean holdCharge=true;
    public static int score=0;
    public static int level=1;
    public static int[] scaling={0,40,100,300,1200,4800};

    // this method should hold the current piece
    public static void holdPiece() {
        if (heldPieceID == -1) {
            heldPieceID = pieceID;
            PieceX = StartX;
            PieceY = StartY;
            holdCharge = false;
            nextPiece();
            ui.setHoldPiece(pentominoDatabase[heldPieceID][0],heldPieceID);
        } else {
            if (holdCharge) {
                int temp = pieceID;
                pieceID = heldPieceID;
                heldPieceID = temp;
                PieceX = StartX;
                PieceY = StartY;
                holdCharge = false;
                ui.setHoldPiece(pentominoDatabase[heldPieceID][0],heldPieceID);
            }
        }
    }

    public static int[] idlist = new int[12];
    private static ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    public static void nextPiece() {
        PieceX = StartX;
        PieceY = StartY;
        rotation = 0;
        if (nextPieces.isEmpty()) {
            Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
            Collections.shuffle(nextPieces);
        }

        // this is a suprise code for later!
        idlist[nextPieces.get(0)] += 1;
        // System.out.println(nextPieces.get(0));
        pieceID = nextPieces.get(0);
        nextPieces.remove(0);

    }

    // this method should rotate a piece if posible has to rotate left and right
    // this should be done in the rotation variable
    public static void rotatePiece(Boolean right) {
        if (right == true) {
            FutureRotation = rotation + 1;
        } else {
            FutureRotation = rotation - 1;
        }

        if (rotation == 3 && right == true) {
            FutureRotation = 0;
        } else if (rotation == 0 && right == false) {
            FutureRotation = 3;
        }

        if (PieceFit(grid, pieceID, FutureRotation, PieceY, PieceX)) {
            rotation = FutureRotation;
        }
    }

    public static void playMidi() {
        try {
            // Obtains the default Sequencer connected to a default device.
            Sequencer sequencer = MidiSystem.getSequencer();

            // Opens the device, indicating that it should now acquire any
            // system resources it requires and become operational.
            sequencer.open();

            // create a stream from a file
            InputStream is = new BufferedInputStream(new FileInputStream(new File("pentris.mid")));

            // Sets the current sequence on which the sequencer operates.
            // The stream must point to MIDI file data.
            sequencer.setSequence(is);

            // Starts playback of the MIDI data in the currently loaded sequence.
            sequencer.start();
        } catch (MidiUnavailableException | InvalidMidiDataException | IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void playSound(String path) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    // this method should make the piece fall by 1 if it can fall
    public static void fallingPiece() {
        if (PieceFit(grid, pieceID, rotation, PieceY + 1, PieceX)) {
            PieceY += 1;
            // System.out.println("fell");
        } else {
            placePiece();
            playSound("beep.wav");
            holdCharge = true;
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
                placePiece();
                nextPiece();
                playSound("beep.wav");
                holdCharge = true;
                break;
            }
        }

    }

    // this method removes a line from the grid
    public static void removeLine(int line) {
        int[][] updatedGrid = new int[grid.length][grid[0].length];
        int placeInGrid;

        for (int i = 0; i < updatedGrid.length; i++) {
            placeInGrid = grid[0].length - 1;
            for (int gridLine = updatedGrid[0].length - 1; gridLine >= 0; gridLine--) {
                if (placeInGrid < 0) {
                    updatedGrid[i][gridLine] = -1;
                } else if (gridLine == line) {
                    placeInGrid--;
                    updatedGrid[i][gridLine] = grid[i][placeInGrid];
                    placeInGrid--;
                } else {
                    updatedGrid[i][gridLine] = grid[i][placeInGrid];
                    placeInGrid--;
                }

            }
        }
        grid = updatedGrid;
    }

    //this method should check if a line is full
    public static void lineCheck(){
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

                removeLine(line);
                lines++;// Everything moved down 1 line, so the check has to move down 1 as well

            } else {
                count = 0;
            }
        }   
            if(lines>0){
                playSound("line.wav");
            }
            score=score+(scaling[lines]*level);
            //System.out.println(score);
                          
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
        } else if (y < 0) {
            return false;
        }

        else if (grid.length > y + database[PieceID][Piecemutation].length - 1) {
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
            PieceY += 1; // If the keypad down is pressed the piece should go down to the place where it
                         // is going to be placed. (To show it smoothly in the UI, drop it down using a
                         // much smaller wait then when playing the normal way.)
            fallingPiece();
            // System.out.println("pieceY = " + PieceY);

        } else if (keyCode == up) {
            rotatePiece(true); // If the keypad up is pressed the piece should be rotated right once.

        } else if (keyCode == space) {
            // System.out.println("check");
            dropPiece(); // Drop the piece if spacebar is pressed.

        } else if (keyCode == z) {
            rotatePiece(true); // If the keypad z is pressed the piece should be rotated right once.

        } else if (keyCode == x) {
            rotatePiece(false); // If the keypad x is pressed the piece should be rotated left once.

        } else if (keyCode == c) {
            holdPiece(); // If the keypad c is pessed the piece should be stored and used at a later
                         // point in the game.
        } else if (keyCode == esc) {
            if (showMenu){
                showMenu = false;
                paused = false;
                System.out.println("Game is resumed and menu is closed");
            }else{
                showMenu = true;
                paused = true;
                System.out.println("Game is paused and menu is shown");
            }
    
            ui.openCloseMenu(showMenu);
        }
        gridclone = clone2Dint(grid);
        addPiece(gridclone, pentominoDatabase[pieceID][rotation], pieceID, PieceX, PieceY);
        ui.setState(gridclone);
        // playSound("beep.wav");

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

    public static void main(String[] args) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = -1;
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("are you colorblind?(Y/N)");
        String color=scanner.nextLine();
        String isColorBlind="Y";
        String name="";
        System.out.println("what is your name?");
        //reader.nextLine();
        name=scanner.nextLine();
        scanner.close();


        
        //System.out.println(color);
        if(color.equals(isColorBlind)){
            //System.out.println("test");
            ui.setColorblind(true);
        }
        scanner.close();


        //this thread plays the music
        new Thread() {
            @Override
            public void run() {
                Clip clip;
                try {
                    AudioInputStream input = AudioSystem.getAudioInputStream(new File("Pentris.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(input);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
            // this starts the thread
        }.start();

        nextPiece();
        long startingTime = System.currentTimeMillis();
        long currentTime;
        gridclone = clone2Dint(grid);
        try {
            while (!Lost) {
                gridclone = clone2Dint(grid);
                addPiece(gridclone, pentominoDatabase[pieceID][rotation], pieceID, PieceX, PieceY);
                //System.out.println("frame");
                ui.setState(gridclone);
                currentTime = System.currentTimeMillis();
                long playingTime = currentTime - startingTime;
                Thread.sleep((long) fallingAcceleration(playingTime));

                fallingPiece();
                lineCheck();
            }
            System.out.println(score);
            
        } catch (InterruptedException e) {
        }
        

        String scoreLine= name+":"+score+"\n";
        //this part of the code writes to scores.txt
        ArrayList<String> file=new ArrayList<String>();
        try {
            
            File myObj = new File("Scores.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                file.add(data);

                //System.out.println(data);
                }
            myReader.close();


            FileWriter myWriter = new FileWriter("Scores.txt");
            Boolean found=true;
            for(int i=0;i<file.size();i++){
                //System.out.println(file.get(i));


                if(Integer.valueOf(file.get(i).split(":")[1])<score&&found){
                    myWriter.write(scoreLine);
                    myWriter.write(file.get(i)+"\n");
                    found=false;
                }
                else{
                    myWriter.write(file.get(i)+"\n");
                }
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("check");
    }
}