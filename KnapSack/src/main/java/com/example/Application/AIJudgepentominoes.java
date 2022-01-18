package com.example.Application;

public class AIJudgepentominoes {
    public static int[][][] grid = new int[5][8][33];
    public static int score=0;
    public static void main(String[] args){

    }



    ///// ///// ///// ///// ///// ///// ///// ///// ///// ///// ///// ///// ///// ///// ///// ///// /////
    // for comments on how these methods work please look at AIjudgeParcels
    // difference in this file is that it is meant for pentominoes not parcels
    //
    //
    //
    //
    //
    //
    //
    //
    public static void scoring(Boxes i[]){	
        
        for (int j = 0; j < i.length; j++) {
            i[j].setScore(judgeVolumes(i[j].getAllBoxes(), i[j].getRotation(), i[j].getOrientation()));
            //System.out.println(AIJudgeParcels.judgeValues(i[j].getAllBoxes(), i[j].getRotation(), i[j].getOrientation()));
        }
        //System.out.println(i[0].getScore());
    }
    public static int[][][] getMatrix(Boxes box){
        emptyGrid();
        int[] PieceIDs=box.getAllBoxes();
        int[] rotations=box.getRotation();

        int[] orientations=box.getOrientation();
        for(int i=0;i<PieceIDs.length;i++){
            tryPlacePiece(PieceIDs[i], rotations[i], orientations[i]);
        }
        int[][][]temp=clone3Dint(grid);
        emptyGrid();
        return temp;
    }
    public static int[][][] clone3Dint(int[][][] list) {
        int[][][] clone = new int[list.length][list[0].length][list[0][0].length];
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                for(int k=0; k< list[0][0].length;k++){
                    clone[i][j][k] = list[i][j][k];
                }
            }
        }
        return clone;
    }
    

    public static int judgeVolumes(int[] PieceIDs,int[] rotations, int[] orientations){
        
        emptyGrid();
        score=0;
        
        
        for(int i=0;i<PieceIDs.length;i++){
            tryPlacePiece(PieceIDs[i], rotations[i], orientations[i]);
        }
        score=gradeGrid(grid);
        emptyGrid();
        return score;
    }
    public static void emptyGrid(){
        fillNegative(grid);
    }

    //
    public static void tryPlacePiece(int pieceID,int rotation,int orientation){
        int[] cords=findNextEmpty3D(grid);
        int[][]slice=get2DSlice(grid, orientation, cords[0], cords[1], cords[2]);
        int[] tempcords=getcoords(orientation, cords[0], cords[1], cords[2]);

        if(PieceFit(slice, pieceID, rotation, adjustX(tempcords[1],pieceID,rotation), tempcords[0])){
            addPiece(slice, PentominoDatabase.data[pieceID][rotation], pieceID, tempcords[0],adjustX(tempcords[1],pieceID,rotation));
            add2Dslice(grid, slice, orientation, tempcords[2]);

        }
        
        
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
    public static int[] getcoords(int orientation,int x,int y,int z){
        if(orientation==0){
            int[] answer={y,z,x};
            return answer;
        }
        if(orientation==1){
            int[] answer={x,z,y};
            return answer;
        }
        if(orientation==2){
            int[] answer={x,y,z};
            return answer;
        }
        else{
            throw new OutOfMemoryError();
        }
    }
    public static int gradeGrid(int[][][] grid){
        int score=grid.length*grid[0].length*grid[0][0].length;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                for(int k=0;k<grid[0][0].length;k++){
                    if(grid[i][j][k]==-1){
                        score--;

                    }
                }
            }
        }
        return score;

    }

    

    public static int[] findNextEmpty3D(int[][][] Grid) {
        int[] emptyCords = new int[3];

        for (int i = 0; i < Grid.length; i++) {
            for (int j = 0; j < Grid[0].length; j++) {
                for(int k=0; k< Grid[0][0].length;k++){
                    if (Grid[i][j][k] == -1) {
                        emptyCords[0] = i;
                        emptyCords[1] = j;
                        emptyCords[2] = k;
                        return emptyCords;

                    }
                }
            }
        }
        return emptyCords;
    }
    public static void fillNegative(int[][][] grid){
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                for(int k=0;k<grid[0][0].length;k++){
                    grid[i][j][k]=-1;
                }
            }
        }
        return;

    }
    public static int adjustX(int x, int pieceID, int mutation) {
        int updated_x = 0;
        updated_x = x;
        int zerocount = 0;
        int[][] piece = PentominoDatabase.data[pieceID][mutation];
        for (int i = 0; i < piece[0].length; i++) {
            if (piece[0][i] == 0) {
                zerocount++;
            } else {
                break;
            }
        }
        updated_x = updated_x - zerocount;
        return updated_x;
    }
    public static int[] ChartoPieceID(char[] list) {
        int[] CharToPiece = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            CharToPiece[i] = characterToID(list[i]);
        }
        return CharToPiece;
    }
    public static int characterToID(char character) {
		int pentID = -1;
		if (character == 'X') {
			pentID = 0;
		} else if (character == 'I') {
			pentID = 1;
		} else if (character == 'Z') {
			pentID = 2;
		} else if (character == 'T') {
			pentID = 3;
		} else if (character == 'U') {
			pentID = 4;
		} else if (character == 'V') {
			pentID = 5;
		} else if (character == 'W') {
			pentID = 6;
		} else if (character == 'Y') {
			pentID = 7;
		} else if (character == 'L') {
			pentID = 8;
		} else if (character == 'P') {
			pentID = 9;
		} else if (character == 'N') {
			pentID = 10;
		} else if (character == 'F') {
			pentID = 11;
		} else if (character == 'A') {
			pentID = 12;
		} else if (character == 'B') {
			pentID = 13;
		} else if (character == 'C') {
			pentID = 14;
		}
		return pentID;
	}
    public static int[][] get2DSlice(int[][][] grid , int orientation, int x,int y, int z){
        //System.out.println("x:"+x+" y:"+y+" z:"+z);
        if(orientation==0){
            return grid[x];
        }
        if(orientation==1){
            int[][] new2DGrid=new int[grid.length][grid[0][0].length];
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[0][0].length;j++){
                    new2DGrid[i][j]=grid[i][y][j];
                }
            }
            return new2DGrid;
        }
        else{
            // for(int i=0;i<grid.length;i++){
            //     for(int j=0;j<grid[i].length;j++){
            //         System.out.print(grid[i][j]);
            //     }
            //     System.out.println("");
            // }
            //System.out.println("laatste");
            int[][] new2DGrid=new int[grid.length][grid[0].length];
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[0].length;j++){
                    //System.out.println("stats: i:"+i+" j:"+j+" z:"+z);
                    new2DGrid[i][j]=-1;
                    new2DGrid[i][j]=grid[i][j][z];
                    
                }
            }
            return new2DGrid;
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

    public static void add2Dslice(int[][][] grid, int[][]slice,int orientation,int depth){
        if(orientation==0){
            grid[depth]=slice;
            return;

        }
        if(orientation==1){
            //int[][] new2DGrid=new int[grid.length][grid[0][0].length];
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[0][0].length;j++){
                    grid[i][depth][j] = slice[i][j];
                }
            }
            return;

        }
        if(orientation==2){
            //int[][] new2DGrid=new int[grid.length][grid[0].length];
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[0].length;j++){
                    grid[i][j][depth]=slice[i][j];
                }
            }
            return;

        }
    }

    public static boolean PieceFit(int[][] grid, int PieceID, int Piecemutation, int x, int y) {
        // if the x is negative then the starting point is of the grid and therefor
        // invalid
        if (x < 0) {

            return false;
        }

        // shorthand for readability
        int[][][][] database = PentominoDatabase.data;

        // if the piece doesnt extend past the borders
        if (grid.length > y + database[PieceID][Piecemutation].length - 1) {
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
                //int[][] gridclone = clone2Dint(grid);
                //Search.addPiece(gridclone, database[PieceID][Piecemutation], Piecemutation, y, x);

                
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
}
