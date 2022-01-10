//package src;

public class Parcel3Dsolver {

    public static int gridscore=0;
    public static int[][][] answergrid;

    
    public static void main(String[] args){

        
        char[] Parcels={'C','C','C','C','B','B','B','B','B','B'};
        int[][][]grid=new int[6][6][7];
        fillNegative(grid);
        if(solve3DParcel(grid, charsToPieceIDs(Parcels))){
            System.out.println("huza");
            print3dint(answergrid);

            
        }
        else{
            System.out.println("fuck");
        }
    }
    public static int[][][] Solve(int[][][] grid,char[] parcels){
        fillNegative(grid);
        solve3DParcel(grid, charsToPieceIDs(parcels));
        return answergrid;
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
    public static void print1dint(int[] grid){
        for(int i=0;i<grid.length;i++){
            
                System.out.print(grid[i]);

        }
        System.out.println("");
    }




    public static boolean solve3DParcel(int[][][] grid, int[]pieces){

        int score=gradeGrid(grid);
        if(score>gridscore){
            gridscore=score;
            answergrid=clone3Dint(grid);
        }
        int[][][]gridclone=clone3Dint(grid);

        int[] emptyCords=findNextEmpty3D(grid);


        if(pieces.length==1){
            for(int j=0;j<getMaxRotation(pieces[0]);j++){
                if(piecefit3d(grid, getParcel(pieces[0], j), emptyCords[0], emptyCords[1], emptyCords[2])){
                    placePiece3D(grid, getParcel(pieces[0],j), pieces[0], emptyCords[0], emptyCords[1], emptyCords[2]);
                    answergrid=grid;
                    return true;
                }
            }
            return false;

        }


        else{
            for(int i=0;i<pieces.length;i++){
                for(int j=0;j<getMaxRotation(pieces[i]);j++){
                    if(piecefit3d(grid, getParcel(pieces[i], j), emptyCords[0], emptyCords[1], emptyCords[2])){
                        
                        placePiece3D(gridclone, getParcel(pieces[i],j), pieces[i], emptyCords[0], emptyCords[1], emptyCords[2]);

                        int[] temporaryPieces = removeID(pieces, i);

                        if(solve3DParcel(gridclone, temporaryPieces)){
                            return true;
                        }
                        gridclone=clone3Dint(grid);





                    }


                }
            }
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


    public static void placePiece3D(int[][][] grid, int[][][] piece, int pieceID, int x, int y, int z ){

        for(int i=0;i<piece.length;i++){
            for(int j=0;j<piece[0].length;j++){
                for(int k=0;k<piece[0][0].length;k++){
                    grid[i+x][j+y][k+z]=pieceID;

                }
            }
        }
    }


    public static int getMaxRotation(int parcelID){
        if(parcelID==0){
            return 4;
        }
        if(parcelID==1){
            return 6;
        }
        else{
            return 1;
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
