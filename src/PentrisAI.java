import java.util.ArrayList;

public class PentrisAI {

    int bestrotation = 0;
    int bestX = 0;
    int[] best;
    public static int[][][][] pentominoDatabase = PentominoDatabase.data;

    public PentrisAI() {
        Thread ai = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    best = bestRotationPosition(Pentris.getGrid(), Pentris.getPieceID());
                    bestrotation = best[1];
                    bestX = best[0];
                    // System.out.println("x:"+bestX+" rotation:"+bestrotation);
                    if (Pentris.getRotation() != bestrotation) {
                        Pentris.rotatePiece(true);
                    }
                    // TODO make move horizontalmove
                    if (Pentris.getX() < bestX) {
                        Pentris.moveHorizontal(true);

                    }
                    if (Pentris.getX() > bestX) {
                        Pentris.moveHorizontal(false);

                    }
                    if (Pentris.getX() == bestX && Pentris.getRotation() == bestrotation) {
                        Pentris.dropPiece();
                    }
                }
            }
        };
        ai.start();
    }

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

    public static int[] simulateDrop(int[][] grid, int pieceID, int rotation, int PieceX, int PieceY) {
        int[][] cloneGrid = clone2Dint(grid);
        if (!PieceFit(grid, pieceID, rotation, PieceY, PieceX)) {
            int[] bla = { 9999, 9999 };
            return bla;
        }
        // drop the piece on the cloned grid
        for (int i = 1; i < 50; i++) {
            // System.out.println("pieceY = " + PieceY);
            if (!PieceFit(cloneGrid, pieceID, rotation, PieceX, PieceY + i)) {
                PieceY += i - 1; // Piece has to be added on this Y position
                // System.out.println("dropped "+PieceY);
                Search.addPiece(cloneGrid, pentominoDatabase[pieceID][rotation], rotation, PieceX, PieceY);
                break;
            }
        }
        // clearing lines
        // TODO: hier moeten de lines worden weggehaald van die grid

        int height = calcHeight(cloneGrid);
        // code to check amount of lines cleared
        int holes = calcHoles(grid);
        int[] bla = { height, holes };
        return bla;
    }

    // this method removes a line from the grid
    public static void removeLine(int line, int[][] grid) {
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

    public static int[] bestRotationPosition(int[][] grid1, int pieceID) {

        int[][] grid = Pentris.clone2Dint(grid1);
        int bestRotation = 0;
        int bestX = 0;
        int bestholes = 9999;
        int bestheight = 9999;
        int[][] heightAndHoles = new int[grid[0].length][2];
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < 4; j++) {
                heightAndHoles[i] = simulateDrop(grid, pieceID, j, i, 0);
            }
        }

        for (int i = 0; i < heightAndHoles.length; i++) {
            // System.out.println("height:"+heightAndHoles[i][0]+"
            // holes:"+heightAndHoles[i][1]);
        }

        for (int i = 0; i < heightAndHoles.length; i++) {
            if (bestholes > heightAndHoles[i][1]
                    || (bestholes == heightAndHoles[i][1] && bestheight > heightAndHoles[i][0])) {
                bestholes = heightAndHoles[i][1];
                bestheight = heightAndHoles[i][0];
                bestX = i / 4;
                bestRotation = i % 4;
            }
        }
        int[] best = { bestX, bestRotation };
        return best;
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
            System.out.println("line:" + i + " is" + holes);
            foundTop = false;
        }
        return holes;
    }

    public static int calcHeight(int[][] grid) {
        int[][] cloneGrid = clone2Dint(grid);
        boolean topFound = false;
        int top = 0;
        for (int i = 0; i < cloneGrid[0].length; i++) {
            for (int j = 0; j < cloneGrid.length; j++) {
                if (cloneGrid[j][i] > -1) {
                    top = cloneGrid[0].length - i;
                    topFound = true;
                    break;
                }
            }
            if (topFound) {
                break;
            }
        }
        return top;

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

}
