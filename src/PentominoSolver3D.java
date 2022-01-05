// package src;




public class PentominoSolver3D {
    public static int counter=0;
    
    public static int[][][] answerGrid;
    //public static UI ui;
    public static String[] combinations= new String[100];//parcels
    public static String[] pentCombinations= new String[136];
    public static int answerscore;

    public static void main(String[] args) {
        
        //TODO fatsoendelijke input method maken:
        int totalVolume=5*5*5;

        if(totalVolume%5!=0){
             System.out.println("imposible dimensions");
             return;
         }


        int[][][] grid=new int[5][5][5];
        fillNegative(grid);

       
        // print3dint(grid);
        //wakker tom: de place en addpiece werken goed de logica fout zit zich waarschijnlijk
        //in de solve3Dpentominoes
        //probeer handmatig 5*3*2 op te lossen
       


        char[] list={'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y'};
        int[] beterlist=ChartoPieceID(list);
        if(SolvePentomino3D(grid, beterlist, beterlist.length)){
            System.out.println("het werkt");
        }

        print3dint(answerGrid);
        // System.out.println(counter);
        // System.out.println(findNextEmpty3D(answerGrid)[0]+"  "+findNextEmpty3D(answerGrid)[1]+"  "+findNextEmpty3D(answerGrid)[0]+"  ");
        
       
    }
    public static void print3dint(int[][][] answerGrid){
        for(int i=0;i<answerGrid.length;i++){
            for(int j=0;j<answerGrid[0].length;j++){
                for(int k=0;k<answerGrid[0][0].length;k++){
                    System.out.print(answerGrid[i][j][k]+" ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }


    public static char[] StringTochar(String jargon){
        char[] list=new char[jargon.length()];
        for(int i=0;i<jargon.length();i++){
            list[i]=jargon.charAt(i);
        }
        return list;

    }

    public static void print2dint(int[][] grid){
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                System.out.print(grid[i][j]);
            }
        System.out.println("");

        }
    }
    public static int[] clone1dint(int[] list){
        int[] newlist= new int[list.length];
        for(int i=0;i<list.length;i++){
            newlist[i]=list[i];
        }
        return newlist;
        
    }

    //HIGHLY INPRACTICAL FUNCTION
    //this finds all posible permutations that can fill a truck based on it volume

    public static void findPentCombinations(int Volume, String combination){
        if(Volume==0){
            System.out.println("test1");
            for(int i=0;i<pentCombinations.length;i++){
                if(pentCombinations[i]==""){
                    System.out.println("test2");
                    pentCombinations[i]=combination;
                    return;
                }
            }
        }
        else{
            findPentCombinations(Volume-5, combination+"T");
            findPentCombinations(Volume-5, combination+"P");
            findPentCombinations(Volume-5, combination+"L");
        }
    }
    public static void findCombinations(int Volume,String combination){
        
        if(Volume==0){
            for(int i=0;i<combinations.length;i++){
                if(combinations[i]==""){
                    combinations[i]=combination;
                    System.out.println("found");
                    return;
                }
            }
        }
        if(Volume-16>=0){
            findCombinations(Volume-16, combination+"A");
        }
        if(Volume-24>=0){
            findCombinations(Volume-24, combination+"B");
        }
        if(Volume-29>=0){
            findCombinations(Volume-29, combination+"B");
        }
        

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

    // this is the main function of the program it takes any give pieceIDs and tries
    // to fit them on the grid recursively
    // trying to place a piece in a permutation
    // if it is able to do so it will call itself again with the same pieces but
    // removing the that was just placed
    // it also gives the grid with the placed piece
    public static boolean SolvePentomino3D(int[][][] grid, int[] pieceIDs, int depth) {
        counter++;
        //System.out.println("depth: "+depth);
        

        // ui.setState(grid);
        int score=gradeGrid(grid);
        if(score>answerscore){
            answerGrid=clone3Dint(grid);
            answerscore=score;
            //System.out.println("new score  "+score);
            // if(score>=20){
            //     print3dint(answerGrid);

            // }
            
            

        }
        //System.out.println("test");

        int[] emptyCords3D = findNextEmpty3D(grid);
        int[] emptyCords;
        int[][][] gridClone = clone3Dint(grid);
        int[][][] secondaryClone = clone3Dint(grid);
        int[][] slice;
        int[][] sliceclone;

        // this is the exit condition, once it has reached a depth of 1, meaning it only
        // has one piece left, it will try and put it on the board.
        // if it can then it will return true which will in turn make the one above
        // return true etc.

        if (depth == 1) {
            for(int k=0;k<3;k++){
                slice=get2DSlice(grid, k, emptyCords3D[0], emptyCords3D[1], emptyCords3D[2]);
                emptyCords=findNextEmpty(slice);
                for (int i = 0; i < PentominoDatabase.data[pieceIDs[0]].length; i++) {

                        if (PieceFit(slice, pieceIDs[0], i, adjustX(emptyCords[1], pieceIDs[0], i), emptyCords[0])) {
                            sliceclone=clone2Dint(slice);


                            addPiece(sliceclone, PentominoDatabase.data[pieceIDs[0]][i], pieceIDs[0], emptyCords[0],
                                    adjustX(emptyCords[1], pieceIDs[0], i));
                            add2Dslice(grid, sliceclone, k, emptyCords3D[k] );
                            answerGrid = grid;
                            System.out.println("true depth: "+depth);

                            return true;
                        }
                    }
                }

            return false;

        }

        // if it isnt the final piece then it will try to fit all pieces in all posible
        // permutations on the next available tile 5*3*4*
        else {

            //TODO: account for 3d dimensional rotations

            for (int i = 0; i < pieceIDs.length; i++) {// for every piece
                for(int k=0;k<3;k++){
                    slice=get2DSlice(grid, k, emptyCords3D[0], emptyCords3D[1], emptyCords3D[2]);
                    sliceclone=clone2Dint(slice);

                    // System.out.println("slice voor:");
                    // print2dint(slice);
                    emptyCords=findNextEmpty(slice);
                    

                    for (int j = 0; j < PentominoDatabase.data[pieceIDs[i]].length; j++) {// for every permutation that
                                                                                        // piece has
                        

                        if (PieceFit(slice, pieceIDs[i], j, adjustX(emptyCords[1], pieceIDs[i], j), emptyCords[0])) {// if
                                                                                                                        // the
                                                                                                                        // piece
                                                                                                                        // fits
                            //System.out.println("piece that fits: "+pieceIDs[i]);
                            if(k==1){
                                //System.out.println();
                            }
                            sliceclone=clone2Dint(slice);
                            addPiece(sliceclone, PentominoDatabase.data[pieceIDs[i]][j], pieceIDs[i], emptyCords[0],
                                    adjustX(emptyCords[1], pieceIDs[i], j));// add it to a clone of the grid
                            // System.out.println("slice na:");
                            // print2dint(sliceclone);
                            
                            add2Dslice(gridClone, sliceclone, k, emptyCords3D[k]);
                            
                            int[] temporaryPieces = removeID(pieceIDs, i);// make a copy of the idlist without the id of the
                                                                        // piece that was just placed

                            // call itself with the clone and the new pieceidlist

                            if (SolvePentomino3D(gridClone, temporaryPieces, depth - 1)) {
                                System.out.println("loop");
                                // this is part of the exit condition once it returns true all functions will
                                // halt searching for solutions
                                return true;
                            }

                            // clear the clone of pieces placed for the next loop
                            gridClone = clone3Dint(secondaryClone);
                            sliceclone=clone2Dint(slice);
                        }
                    }
                }
            }
        }

        return false;
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

    // this function finds the next empty square

    public static int[] findNextEmpty3D(int[][][] Grid) {
        int[] emptyCords = new int[3];

        for (int x = 0; x < Grid.length; x++) {
            for (int y = 0; y < Grid[0].length; y++) {
                for(int z=0; z< Grid[0][0].length;z++){
                    
                    if (Grid[x][y][z] == -1) {
                        //System.out.println("hier:  "+x+y+z);
                        emptyCords[0] = x;
                        emptyCords[1] = y;
                        emptyCords[2] = z;
                        return emptyCords;

                    }
                   

                }
            }
        }
        System.out.println("AHHHHHHHHHHHHH");
        return emptyCords;
    }

    public static int[] findNextEmpty(int[][] Grid) {
        int[] emptyCords = new int[3];

        for (int i = 0; i < Grid.length; i++) {
            for (int j = 0; j < Grid[0].length; j++) {
                if (Grid[i][j] == -1) {
                    emptyCords[0] = i;
                    emptyCords[1] = j;
                    
                    return emptyCords;
                }
            }
        }
        return emptyCords;

    }

    // this is a function for testing purposes
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

    

    // this function makes a copy of a list while removing a certain index of said
    // list
    public static int[] removeID(int[] pieceIDs, int index) {

        int[] answerList = new int[pieceIDs.length - 1];

        for (int i = 0, k = 0; i < pieceIDs.length; i++) {

            if (i != index) {

                answerList[k] = (pieceIDs[i]);
                k++;
            }
        }
        return answerList;
    }

    // this function adjust the x position to account for empty space within the
    // matrix which represent the pieces
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

    // function for testing purposes which prints grids
    // public static void printGrid(int[][]grid){
    // for(int i=0;i<grid.length;i++){
    // for(int j=0;j<grid.length;j++){
    // System.out.print(grid[i][j]+",");
    // }
    // System.out.println("");
    // }
    // }

    // //function for testing purposes which print pieces

    // public static void printPiece(int[][] piece){
    // for(int i=0;i<piece.length;i++){
    // for(int j=0;j<piece[i].length;j++){
    // System.out.print(piece[i][j]);}
    // System.out.println("");
    // }
    // }
    public static int[][] get2DSlice(int[][][] grid , int orientation, int x,int y, int z){
        //System.out.println("x:"+x+" y:"+y+" z:"+z);
        if(orientation==0){
            int[][] new2DGrid=new int[grid[0].length][grid[0][0].length];
            
            
            for(int i=0;i<grid[0].length;i++){
                for(int j=0;j<grid[0][0].length;j++){
                    new2DGrid[i][j]=grid[x][i][j];
                    
                }
                
            }
            
            return new2DGrid;
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
            int[][] new2DGrid=new int[grid.length][grid[0].length];
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[0].length;j++){
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


    public static char[] sortingAG(int[] Numberlist, char[] characters) {
        int p = 1;
        for (int j = 0; j < Numberlist.length; j++) {
            if (p == 0) {
                break;
            }
            p = 0;
            for (int i = 0; i < Numberlist.length - 1; i++) {

                int firstnum = Numberlist[i];
                int secondnum = Numberlist[i + 1];
                char firstchar = characters[i];
                char secondchar = characters[i + 1];

                if (firstnum < secondnum) {
                    // swaps around the numbers if the firstone is bigger than the second one
                    // Also swaps around the letters in the char[] if a number is swapped.
                    Numberlist[i] = secondnum;
                    Numberlist[i + 1] = firstnum;
                    characters[i] = secondchar;
                    characters[i + 1] = firstchar;
                    p += 1;
                }

            }
        }
        return characters;

    }

    // Uses a random list of pieces and transorms the list such that there is higher
    // chance of fitting if you place them after one another
    //
    public static char[] Optimise(char[] characters) {
        int[] posibilities = new int[characters.length];

        // Checks how often you can fit a piece in an empty grid and stores that in a
        // matrix
        for (int i = 0; i <= characters.length - 1; i++) {
            if (characters[i] == 'L') {
                posibilities[i] = 12;
            } else if (characters[i] == 'I') {
                posibilities[i] = 11;
            } else if (characters[i] == 'P') {
                posibilities[i] = 10;
            } else if (characters[i] == 'Y' || characters[i] == 'F') {
                posibilities[i] = 9;
            } else if (characters[i] == 'N') {
                posibilities[i] = 8;
            } else if (characters[i] == 'V') {
                posibilities[i] = 7;
            } else if (characters[i] == 'W' || characters[i] == 'U' || characters[i] == 'T' || characters[i] == 'Z') {
                posibilities[i] = 6;
            } else {
                posibilities[i] = 1;
            }

        }
        char[] optimisedmatrix = new char[characters.length];
        int j = 0;
        int k = 0;
        // The 2 for loops make sure that the pieces with the highest amount of
        // possibilities are put either
        // first or last in the optimised matrix. So that your least 'flexible' piece is
        // used somewhere in the middle.
        for (int i = 0; i <= characters.length - 1; i++) {
            if (i % 2 == 0) {
                optimisedmatrix[j] = characters[i];
                j += 1;
            }
        }
        j = 1;
        for (int i = 0; i <= characters.length - 1; i++) {

            if (i % 2 != 0) {
                optimisedmatrix[optimisedmatrix.length - k - 1] = characters[i];
                k += 1;
            }
        }
        return optimisedmatrix;
    }

    
    // this function evaluated if a piece can be placed on a give grid at a certain
    // x and y location
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



    //TODO; make this 3D
    // Function which checks whether a -1 in the grid is surrounded by zeros, if so,
    // return true.
    public static boolean checkMinus(int[][] arrays) {
        int[][] newarray = new int[arrays.length + 2][arrays[0].length + 2];

        // creates a border all made of zeros and put it arround the original array, so
        // that you can check the -1's without having to write exceptions zeros for the
        // borders.
        for (int i = 0; i <= arrays.length + 1; i++) {
            for (int j = 0; j <= arrays[0].length + 1; j++) {
                if (i == 0 || j == 0 || i == newarray.length - 1 || j == newarray[0].length - 1) {
                    newarray[i][j] = 0;
                } else {
                    newarray[i][j] = arrays[i - 1][j - 1];
                }
            }
        }
        arrays = newarray;
        boolean result = false;

        // Looks for a -1 in the original array if there is a 0 above, below and next to
        // the -1, if so, returns true.
        for (int i = 1; i < arrays.length - 1; i++) {
            for (int j = 1; j < arrays[1].length - 1; j++) {
                if (arrays[i][j] == -1) {
                    if (arrays[i - 1][j] >= 0 && arrays[i + 1][j] >= 0 && arrays[i][j - 1] >= 0
                            && arrays[i][j + 1] >= 0) {
                        result = true;
                        return result;
                    }
                }
            }
        }
        return result;
    }
    public static int[][][][] parcels=
    {  
        //parcelA rotation 1
        {
            {{1,1},
            {1,1}},
            {{1,1},
            {1,1}},
            {{1,1},
            {1,1}},
            {{1,1},
            {1,1}}
        },
        //parcelA rotation 2
        {
            {{1,1,1,1},
            {1,1,1,1}},
            {{1,1,1,1},
            {1,1,1,1}}
        },
        //parcelA rotation 3
        {
            {{1,1},{1,1},{1,1},{1,1}},
            {{1,1},{1,1},{1,1},{1,1}}
        },
        //parcelB rotation 1 4-3-2
        {
            {{1,1},{1,1},{1,1}},
            {{1,1},{1,1},{1,1}},
            {{1,1},{1,1},{1,1}},
            {{1,1},{1,1},{1,1}}
        },
        //parcelB rotation 2 2-3-4
        {
            {{1,1,1,1},
            {1,1,1,1},
            {1,1,1,1}},
            {{1,1,1,1},
            {1,1,1,1},
            {1,1,1,1}}
        },
        //parcelB rotation 3 3-2-4
        {
            {{1,1,1,1},
            {1,1,1,1}},
            {{1,1,1,1},
            {1,1,1,1}},
            {{1,1,1,1},
            {1,1,1,1}}
        },
        //parcelB rotation 4 4-2-3
        {{{1,1,1},{1,1,1}},
        {{1,1,1},{1,1,1}},
        {{1,1,1},{1,1,1}},
        {{1,1,1},{1,1,1}}},
        //parcelB rotation 5 2-4-3
        {{{1,1,1},{1,1,1},{1,1,1},{1,1,1}},
        {{1,1,1},{1,1,1},{1,1,1},{1,1,1}}},

        //parcelB rotation 6 3-2-4
        {{{1,1,1,1},{1,1,1,1}},
        {{1,1,1,1},{1,1,1,1}},
        {{1,1,1,1},{1,1,1,1}}}
        ,
        //parcelC only rotation
        {{{1,1,1},{1,1,1},{1,1,1}},
         {{1,1,1},{1,1,1},{1,1,1}},
         {{1,1,1},{1,1,1},{1,1,1}}}





    };
        

        
}