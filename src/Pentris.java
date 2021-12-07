package src;

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
import java.awt.*;

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
    public static int height = 0;
    public static int width = 0;

    public static int uiWidth = 10;
    public static int uiHeight = 18;

    // the startposition for both the X and the Y
    public static int StartY;
    public static int StartX;

    // Time (in milliseconds) for after which the currentlevel increases with 1.
    final public static long levelIncreasTimeFrame = 30000;

    public static int[][] grid;
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
    public static volatile int PieceX;
    public static volatile int PieceY;

    // variable to end the game
    public static boolean Lost = false;
    public static boolean Started = false;

    public static UI ui;
    public static int[][] gridclone;
    public static boolean BEEP = false;

    public static Menu menu;
    public static endMenu endMenu;

    public static boolean showMenu = false;
    public static boolean paused = false;
    public static boolean addShadow = false;

    public static boolean startPauseTimer;
    public static long pauseStart;
    public static long pauseEnd;

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

    public static boolean holdCharge = true;
    public static int score = 0;
    public static int[] scaling = { 0, 40, 100, 300, 1200, 4800 };
    public static int beginning = (int) System.currentTimeMillis() / 1000;
    public static long pausingTime;
    public static boolean stopmusic = false;

    public static String name;
    public static int level = 1;
    public static int gameLevel;
    public static boolean isColorblind;
    public static int startingLevel;
    public static double startingAcceleration;

    // this method should hold the current piece
    public static void holdPiece() {
        if (heldPieceID == -1) {
            heldPieceID = pieceID;
            PieceX = StartX;
            PieceY = StartY;
            nextPiece();
            ui.setHoldPiece(pentominoDatabase[heldPieceID][0], heldPieceID);
        } else {
            if (holdCharge) {
                int temp = pieceID;
                pieceID = heldPieceID;
                heldPieceID = temp;
                PieceX = StartX;
                PieceY = StartY;
                holdCharge = false;
                ui.setHoldPiece(pentominoDatabase[heldPieceID][0], heldPieceID);
            }
        }
    }

    public static int[] idlist = new int[12];
    private static ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    public static void nextPiece() {

        // In this method there are multiple out-commented orders of the pentominos.
        // Every order is shown as follows:
        //------------------------------------------
        // Scenario <Nr.>
        // <Order Here>
        //------------------------------------------
        // Comment only one option in to use.


        PieceX = StartX;
        PieceY = StartY;
        rotation = 0;
        if (nextPieces.isEmpty()) {
    //-----------------------------------------------------
    // Scenario Normal:
            Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
            Collections.shuffle(nextPieces);
    //-----------------------------------------------------
    // Scenario 1:
            // Collections.addAll(nextPieces, 9, 4, 0, 11, 8, 3, 7, 10, 1, 6, 2, 5);
    //-----------------------------------------------------
    // Scenario 2:
            // Collections.addAll(nextPieces, 9, 4, 0, 11, 8, 3, 10, 7, 6, 2, 5, 1);
    //-----------------------------------------------------
    // Scenario 3:
            // Collections.addAll(nextPieces, 1, 5, 2, 6, 7, 10, 3, 4, 0, 9, 11, 8);
    //-----------------------------------------------------
    // Scenario 4:
            // Collections.addAll(nextPieces, 5, 2, 6, 7, 1, 10, 3, 8, 11, 9, 0, 4);
    //-----------------------------------------------------
    // Scenario 5:
            // Collections.addAll(nextPieces, 6, 5, 7, 3, 11, 9, 2, 0, 4, 10, 8, 1);
    //-----------------------------------------------------
    // Scenario 6:
            // Collections.addAll(nextPieces, 1, 8, 10, 4, 0, 2, 9, 11, 3, 7, 6, 5);
    //-----------------------------------------------------
    // Scenario 7:
            // Collections.addAll(nextPieces, 8, 4, 0, 9, 10, 2, 11, 3, 7, 6, 5, 1);
    //-----------------------------------------------------
    // Scenario 8:
            // Collections.addAll(nextPieces, 1, 8, 10, 4, 0, 2, 11, 7, 9, 3, 6, 5);
    //-----------------------------------------------------
    // Scenario 9:
            // Collections.addAll(nextPieces, 4, 0, 8, 1, 5, 9, 6, 7, 3, 10, 11, 2);
    //-----------------------------------------------------
    // Scenario 10:
            // Collections.addAll(nextPieces, 9, 11, 4, 0, 5, 8, 6, 2, 10, 7, 3, 1);
    //-----------------------------------------------------
        }

        // String teststring="";
        // for(int i=0;i<nextPieces.size();i++){
        // teststring+=nextPieces.get(i)+",";
        // }
        // System.out.println(teststring);

        // this is a surprise code for later!
        idlist[nextPieces.get(0)] += 1;
        // System.out.println(nextPieces.get(0));
        pieceID = nextPieces.get(0);
        nextPieces.remove(0);
        // recheck for the nextpiece
        // if (nextPieces.isEmpty()) {
        //     Collections.addAll(nextPieces, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1);
        //     Collections.shuffle(nextPieces);
        // }

        // System.out.println(nextPieces.get(0)+"testerino");
        ui.setNextPiece(pentominoDatabase[nextPieces.get(0)][0], nextPieces.get(0));

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
    public static long fallingAcceleration(long time) {
        int currentLevel = calculateLevel(time);
        long timeIndicate = 0;

        if (currentLevel == 1) {
            timeIndicate = 1000;
        } else if (currentLevel == 2) {
            timeIndicate = 896;
        } else if (currentLevel == 3) {
            timeIndicate = 792;
        } else if (currentLevel == 4) {
            timeIndicate = 688;
        } else if (currentLevel == 5) {
            timeIndicate = 583;
        } else if (currentLevel == 6) {
            timeIndicate = 479;
        } else if (currentLevel == 7) {
            timeIndicate = 375;
        } else if (currentLevel == 8) {
            timeIndicate = 271;
        } else if (currentLevel == 9) {
            timeIndicate = 167;
        } else if (currentLevel == 10) {
            timeIndicate = 125;
        } else if (currentLevel >= 11 && currentLevel <= 13) {
            timeIndicate = 104;
        } else if (currentLevel >= 14 && currentLevel <= 16) {
            timeIndicate = 83;
        } else if (currentLevel >= 17 && currentLevel <= 19) {
            timeIndicate = 63;
        } else if (currentLevel >= 20 && currentLevel <= 29) {
            timeIndicate = 42;
        } else if (currentLevel >= 30) {
            timeIndicate = 21;
        }

        return timeIndicate;
    }

    public static int calculateLevel(long time) {
        int currentLevel = 0;
        currentLevel += (time / levelIncreasTimeFrame) + startingLevel;
        return currentLevel;
    }

    public static void dropPiece() {

        for (int i = 1; i < 50; i++) {
            // System.out.println("pieceY = " + PieceY);
            if (!PieceFit(grid, pieceID, rotation, PieceY + i, PieceX)) {
                // System.out.println("her");

                PieceY += i - 1; // Piece has to be added on this Y position
                placePiece();
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

    // Method that checks if a line is full
    public static void lineCheck() { //TODO: bugfixing: when last pentomino fills entire line, remove line and show menu
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
                removeLine(line);
                count = 0;
                line++;// Everything moved down 1 line, so the check has to move down 1 as well
                lines++;
            } else {
                count = 0;
            }
        }
        if (lines > 0) {
            playSound("line.wav");
        }
        score = score + (scaling[lines] * level);
        // System.out.println(score);
    }

    public static int getScore() { // returns the score so that we can show it in the GUI
        return score;
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
        if (!Lost && Started) {
            int keyCode = event.getKeyCode();

            if (keyCode == left && PieceFit(grid, pieceID, rotation, PieceY, PieceX - 1) && !paused) {
                PieceX -= 1; // If the keypad left is pressed the piece should go 1 position to the left.
                             // That's why the x coordinate of the piece is subtracted by 1.
                // System.out.println("pieceX = " + PieceX);

            } else if (keyCode == right && PieceFit(grid, pieceID, rotation, PieceY, PieceX + 1) && !paused) {
                PieceX += 1; // If the keypad right is pressed the piece should go 1 position to the right.
                             // That's why the x coordinate of the piece is added by 1.
                // System.out.println("pieceX = " + PieceX);

            } else if (keyCode == down && PieceFit(grid, pieceID, rotation, PieceY + 1, PieceX) && !paused) {
                PieceY += 1; // If the keypad down is pressed the piece should go down to the place where it
                             // is going to be placed. (To show it smoothly in the UI, drop it down using a
                             // much smaller wait then when playing the normal way.)
                fallingPiece();
                // System.out.println("pieceY = " + PieceY);

            } else if (keyCode == up && !paused) {
                rotatePiece(true); // If the keypad up is pressed the piece should be rotated right once.

            } else if (keyCode == space && !paused) {
                // System.out.println("check");
                dropPiece(); // Drop the piece if spacebar is pressed.

            } else if (keyCode == z && !paused) {
                rotatePiece(true); // If the keypad z is pressed the piece should be rotated right once.

            } else if (keyCode == x && !paused) {
                rotatePiece(false); // If the keypad x is pressed the piece should be rotated left once.

            } else if (keyCode == c && !paused) {
                holdPiece(); // If the keypad c is pessed the piece should be stored and used at a later
                             // point in the game.
            } else if (keyCode == esc) {
                if (!menu.getShowMenu()) {
                    menu.UI.setVisible(true);
                    ;
                    menu.setPaused(true);
                    // System.out.println("Game is paused and menu is shown");
                }
            }

            gridclone = clone2Dint(grid);
            if (addShadow) {
                addShadow(gridclone);
            }
            addPiece(gridclone, pentominoDatabase[pieceID][rotation], pieceID, PieceX, PieceY);
            ui.setState(gridclone);
            // playSound("beep.wav");
        }

    }

    public static ArrayList<String> getHighscores() {
        ArrayList<String> highscores = new ArrayList<String>();
        try {

            File myObj = new File("Scores.txt");
            Scanner myReader = new Scanner(myObj);
            String data;
            for (int i = 0; i < 5 && myReader.hasNextLine(); i++) {
                data = myReader.nextLine();
                highscores.add(data);

            }
            myReader.close();
        } catch (IOException e) {
            // System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return highscores;
    }

    public static void placePiece() {
        addPiece(grid, pentominoDatabase[pieceID][rotation], pieceID, PieceX, PieceY);
        nextPiece();
        if (!PieceFit(grid, pieceID, rotation, StartY, StartX)) {
            Lost = true;
        }
        lineCheck();
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

    public static String getTime() {
        int playtime = ((int) System.currentTimeMillis() / 1000) - beginning - ((int) pausingTime / 1000);
        int minutes = playtime / 60;
        int seconds = playtime % 60;
        if (minutes == 0 && seconds == 0) {
            return "00:00";
        }
        if (minutes == 0 && seconds < 10) {
            return "00:0" + seconds;
        }
        if (minutes < 10 && seconds == 0) {
            return "0" + minutes + ":00";
        }
        if (minutes == 0) {
            return "00:" + seconds;
        }
        if (seconds == 0) {
            return minutes + ":00";
        }
        if (seconds < 10) {
            return "" + minutes + ":0" + seconds;
        } else {
            return "" + minutes + ":" + seconds;
        }
    }

    public static void addShadow(int[][] grid) {
        int[][] piece = pentominoDatabase[pieceID][rotation];
        for (int i = 1; i < 50; i++) {
            // System.out.println("pieceY = " + PieceY);
            if (!PieceFit(grid, pieceID, rotation, PieceY + i, PieceX)) {
                addPiece(grid, piece, 12, PieceX, PieceY + i - 1);
                break;
            }
        }
    }

    public static int[][] getGrid() {
        return grid;
    }

    public static int getPieceID() {
        return pieceID;
    }

    public static int getRotation() {
        return rotation;
    }

    public static int getX() {
        return PieceX;
    }

    public static ArrayList<Integer> botmovements;
    public static void main(String[] args) throws InterruptedException, AWTException {
        while (!Lost) {
            startMenu startMenu = new startMenu();

            startMenu.setShowMenu(true);
            while (startMenu.getShowMenu()) {
                Thread.sleep(100);
            }

            name = startMenu.getName();
            gameLevel = startMenu.getLevel();
            height = startMenu.getGridsizeY();
            width = startMenu.getGridsizeX();
            isColorblind = startMenu.getIsColorblind();
            boolean playBot = startMenu.getPlayBot();
            menu = new Menu(isColorblind);
            stopmusic = false;

            StartY = 0;
            if (width <= 6) {
                StartX = 0;
            } else {
                StartX = 0;
                //StartX = width / 2 - 1;
            }

            PieceX = StartX;
            PieceY = StartY;
            grid = new int[width][height];
            gridclone = clone2Dint(grid);
            startingLevel = gameLevel;

            ui = new UI(width, height, 30, isColorblind);

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j] = -1;
                }
            }

            Thread music = new Thread() {
                @Override
                public void run() {
                    Clip clip;

                    try {
                        AudioInputStream input = AudioSystem.getAudioInputStream(new File("Pentris.wav"));
                        clip = AudioSystem.getClip();
                        clip.open(input);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        while (!stopmusic) {
                            Thread.sleep(40);
                        }
                        // System.out.println("etstestttsetset");
                        clip.stop();
                    } catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                // this starts the thread
            };

            music.start();
            Started = true;
            nextPiece();
            long startingTime = System.currentTimeMillis();
            long currentTime;
            pauseStart = 0;
            pausingTime = 0;
            startPauseTimer = false;
            gridclone = clone2Dint(grid);
            beginning = (int) System.currentTimeMillis() / 1000;
            // PentrisAI ai=new PentrisAI();

//TetrisAI-----------------------------------------------------------------------------------------------------------------------
            if (playBot){
                Robot excecuter = new Robot();
                basicAI ai = new basicAI();       
                Thread tetrisbot = new Thread(){

                    public void run(){
                        try{
                            while(!Lost){
                                ai.testgrid = grid;
                                ai.getRobotMovements();
                                ArrayList<Integer> movements = ai.cloneArrayList(ai.botmovements);
                                //System.out.println("Inputted movements: " + ai.botmovements);

                                for(int i = 0; i < movements.size(); i++){
                                    int current = movements.get(i);
                                    excecuter.keyPress(current);
                                    excecuter.delay(50);
                                }
                                Thread.sleep(1000);

                                if (menu.getPaused()) {
                                    startPauseTimer = true;
                                    pauseStart = System.currentTimeMillis();
                                }
                                while (menu.getPaused()) {
                                    Thread.sleep(100);
                                }
                                if (startPauseTimer) {
                                    pauseEnd = System.currentTimeMillis();
                                    startPauseTimer = false;
                                    pausingTime += (pauseEnd - pauseStart);
                                    // System.out.println(pauseEnd);
                                    // System.out.println(pauseStart);
                                    // System.out.println(pausingTime);
                                }
                                ui.setColorblind(menu.getIsColorblind());
            
                            }
                        }catch(InterruptedException e){

                        }
                        
                    }
                };tetrisbot.start();
            }
            startMenu.setPlayBot(false);
//-------------------------------------------------------------------------------------------------------------------------------

            try {
                while (!Lost) {
                    gridclone = clone2Dint(grid);
                    if (addShadow) {
                        addShadow(gridclone);
                    }
                    addPiece(gridclone, pentominoDatabase[pieceID][rotation], pieceID, PieceX, PieceY);
                    // System.out.println("frame");
                    ui.setState(gridclone);
                    currentTime = System.currentTimeMillis();
                    long playingTime = currentTime - startingTime;
                    Thread.sleep(fallingAcceleration(playingTime - pausingTime));

                    fallingPiece();
                    lineCheck();

                    if (menu.getPaused()) {
                        startPauseTimer = true;
                        pauseStart = System.currentTimeMillis();
                    }
                    while (menu.getPaused()) {
                        Thread.sleep(100);
                    }
                    if (startPauseTimer) {
                        pauseEnd = System.currentTimeMillis();
                        startPauseTimer = false;
                        pausingTime += (pauseEnd - pauseStart);
                        // System.out.println(pauseEnd);
                        // System.out.println(pauseStart);
                        // System.out.println(pausingTime);
                    }
                    ui.setColorblind(menu.getIsColorblind());
                }
                // System.out.println(score);
            } catch (InterruptedException e) {
            }
            // ui.setLost();
            stopmusic = true;
            playSound("Lost.wav");

            ui.setState(gridclone);
            String scoreLine = name + ":" + score + "\n";
            // this part of the code writes to scores.txt
            ArrayList<String> file = new ArrayList<String>();
            try {
                File myObj = new File("Scores.txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    file.add(data);

                    // System.out.println(data);
                }

                myReader.close();
                FileWriter myWriter = new FileWriter("Scores.txt");
                Boolean found = true;
                for (int i = 0; i < file.size(); i++) {
                    // System.out.println(file.get(i));

                    if (Integer.valueOf(file.get(i).split(":")[1]) < score && found) {
                        myWriter.write(scoreLine);
                        myWriter.write(file.get(i) + "\n");
                        found = false;
                    } else {
                        myWriter.write(file.get(i) + "\n");
                    }
                }
                if (found) {
                    myWriter.write(scoreLine);
                }
                myWriter.close();
                // System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                // System.out.println("An error occurred.");
                e.printStackTrace();
            }

            // System.out.println("check");
            endMenu = new endMenu();
            endMenu.setLost(Lost);
            while (endMenu.getLost()) {
                endMenu.UI.setVisible(true);
                Thread.sleep(100);
            }
            Lost = endMenu.getLost();
            ui.window.dispose();
        }
    }

    public static void moveHorizontal(boolean b) {
    }
}