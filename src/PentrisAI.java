public class PentrisAI {

    int bestrotation=0;
    int bestX=0;
    int[] best;

    public PentrisAI(){
        
        
        Thread ai= new Thread() {
            public void run() {
            while(true){
                best=bestRotationPosition(Pentris.getGrid(), Pentris.getPieceID());
                bestrotation=best[1];
                bestX=best[0];
                while(Pentris.getRotation()!=bestrotation){
                    Pentris.rotatePiece(true);
                }
                while(Pentris.getX()!=bestX){
                    if(Pentris.getX()<bestX){
                        Pentris.PieceX+=1;

                    }
                    if(Pentris.getX()>bestX){
                        Pentris.PieceX+=-1;
                        
                    }
                }
                Pentris.dropPiece();


            }

        }};
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

    public static int[] simulateDrop(int[][]grid,int pieceID,int rotation,int PieceX,int PieceY){
        int[][]cloneGrid=Pentris.clone2Dint(grid);
        if (!PieceFit(grid, pieceID, rotation, PieceY, PieceX)){
            int[] bla={9999,9999};
            return bla;
        } 
        //drop the piece on the cloned grid
        for (int i = 1; i < 50; i++) {
            // System.out.println("pieceY = " + PieceY);
            if (!PieceFit(cloneGrid, pieceID, rotation, PieceY + i, PieceX)) {
                PieceY += i - 1; // Piece has to be added on this Y position
                
            }
        }

        int height=999;
        for(int i=0;i<cloneGrid.length;i++){
            for(int j=0;j<cloneGrid[i].length;j++){
                if (cloneGrid[i][j]>-1){
                    height=i;
                }
            }
        }
        //code to check amount of lines cleared
        int linesCleared=0;
        boolean full=true;
        for(int i=0;i<cloneGrid.length;i++){
            for(int j=0;j<cloneGrid[i].length;j++){
                if(cloneGrid[i][j]==-1){
                    full=false;
                }

            }
            if(full){
                for(int j=0;j<cloneGrid[i].length;j++){
                    cloneGrid[i][j]=-1;}
                if(i!=0){
                    for(int j=i;j>0;j--){
                        for(int k=0;k>cloneGrid[j].length;k++){
                            cloneGrid[j][k]=cloneGrid[j-1][k];

                        }
                    }
                }
                for(int j=0;j<cloneGrid[i].length;j++){
                    cloneGrid[0][j]=-1;}


                linesCleared++;
                full=false;
                
            }
        }


       
        //code to count the amount of holes
        int holes=0;
        Boolean columnTop=false;
        for(int i=0;i<cloneGrid[0].length;i++){
            for(int j=0;j<cloneGrid.length;j++){
                if(cloneGrid[j][i]>-1){
                    columnTop=true;
                }
                if(columnTop&&cloneGrid[j][i]==-1){
                    holes++;
                }

            }
            columnTop=false;
        }

        int[] bla={height-linesCleared,holes};
        return bla;


    }


    public static int[] bestRotationPosition(int[][]grid,int pieceID){

        int bestRotation=0;
        int bestX=0;
        int bestholes=9999;
        int bestheight=9999;
        int[][] heightAndHoles=new int[grid[0].length][2];
        for(int i=0;i<grid[0].length;i++){
            for(int j=0;j<4;j++){
                heightAndHoles[i]=simulateDrop(grid, pieceID, j, i, 0);

            }

            
        }

        for(int i=0;i<heightAndHoles.length;i++){
            if(bestholes>heightAndHoles[i][1]||(bestholes==heightAndHoles[i][1]&&
                bestheight>heightAndHoles[i][0])){
                    bestholes=heightAndHoles[i][1];
                    bestheight=heightAndHoles[i][0];
                    bestX=i/4;
                    bestRotation=i%4;
            }

        }
        int[] best={bestX,bestRotation};

        
        return best;

    }
}
