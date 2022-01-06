public class AIJudgeParcels {
    public static int[][][] grid = new int[2][5][5];
    public static int score=0;
    public static void main(String[] args){

    }
    
    //judges based on the volume it will fill
    public static int judgeVolumes(int[] PieceIDs,int[] rotations, int[] orientations){
        
        emptyGrid();
        score=0;
        
        
        for(int i=0;i<PieceIDs.length;i++){
            tryPlacePieceVolume(PieceIDs[i], rotations[i], orientations[i]);
        }
        score=gradeGrid(grid);
        emptyGrid();
        return score;
    }

    //judges based on the values of the pieces that fit
    public static int judgeValues(int[] PieceIDs,int[] rotations, int[] orientations){
        
        emptyGrid();
        score=0;
        
        
        for(int i=0;i<PieceIDs.length;i++){
            tryPlacePieceValues(PieceIDs[i], rotations[i], orientations[i]);
        }
        
        emptyGrid();
        return score;
    }



    public static void emptyGrid(){
        fillNegative(grid);
    }

    //
    public static void tryPlacePieceVolume(int pieceID,int rotation,int orientation){
        int[] cords=findNextEmpty3D(grid);
        if(piecefit3d(grid, getParcel(pieceID, rotation), cords[0], cords[1], cords[2])){
            placePiece3D(grid, getParcel(pieceID, rotation),pieceID, cords[0], cords[1], cords[2]);
        }
    }

    public static void tryPlacePieceValues(int pieceID,int rotation,int orientation){
        int[] cords=findNextEmpty3D(grid);
        if(piecefit3d(grid, getParcel(pieceID, rotation), cords[0], cords[1], cords[2])){
            placePiece3D(grid, getParcel(pieceID, rotation),pieceID, cords[0], cords[1], cords[2]);

        }
    }

    public static void addScore(int parcel){
        if(parcel==0){
            score+=3;
        }
        if(parcel==1){
            score+=4;
        }
        if(parcel==2){
            score+=5;
        }
    }



    public static void placePiece3D(int[][][] grid, int[][][] piece, int pieceID, int x, int y, int z ){

        for(int i=0;i<piece.length;i++){
            for(int j=0;j<piece[0].length;j++){
                for(int k=0;k<piece[0][0].length;k++){
                    grid[i+x][j+y][k+z]=pieceID;

                }
            }
        }
    }


    public static int[][][] getParcel(int ParcelID,int rotation){
        if(ParcelID==0){
            return parcels[rotation];
        }
        if(ParcelID==1){
            return parcels[3+rotation];
        }
        else{
            return parcels[9];
        }

    }
    public static boolean piecefit3d(int[][][] grid, int[][][] piece, int x ,int y,int z){

        try{
            for(int i=0;i<piece.length;i++){
                for(int j=0;j<piece[0].length;j++){
                    for(int k=0;k<piece[0][0].length;k++){
                        if(grid[i+x][j+y][k+z]>=0){
                            return false;
                        }
                    }
                }
            }
            return true;
        }catch(IndexOutOfBoundsException e){
            return false;
        }
    }


    public static int[] charsToPieceIDs(char[] parcels){
        int[] parcelIDs=new int[parcels.length];
        for(int i=0;i<parcels.length;i++){
            parcelIDs[i]=chartoID(parcels[i]);
        }
        return parcelIDs;
    }

    public static int chartoID(char parcel){
        if(parcel=='A'){
            return 0;
        }
        if(parcel=='B'){
            return 1;
        }
        if(parcel=='C'){
            return 2;
        }
        return 999;
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


