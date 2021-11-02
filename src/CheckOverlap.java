public class CheckOverlap { // Check if there aren't any peices that overlap in the grid
/* 
//if the x is negative then the starting point is of the grid and therefor invalid
if(x<0){
            
            return false;
        }
// Overlap if place where you want to place tetris isn't empty

*/








    //this function evaluated if a piece can be placed on a give grid at a certain x and y location
    public static boolean PieceFit(int[][]grid,int PieceID,int Piecemutation,int x,int y){
        
        

        //shorthand for readability
        int[][][][]database=PentominoDatabase.data;


        //if the piece doesnt extend past the borders
        if(grid.length>y+database[PieceID][Piecemutation].length-1){
            if(grid[0].length>x+database[PieceID][Piecemutation][0].length-1){
                //then it wil check for every square whether the matrix has a 1 there(meaning its a square to be placed)
                //and if the grid already has a value there
                for(int i=0;i<database[PieceID][Piecemutation].length;i++){
                    for(int j=0;j<database[PieceID][Piecemutation][0].length;j++){
                        if(grid[i+y][j+x]>=0&database[PieceID][Piecemutation][i][j]==1){
                            ///if so they overlap so you cant place the piece
                            return false;
                        }
                    }
                }
                int[][]gridclone=clone2Dint(grid);
                Search.addPiece(gridclone, database[PieceID][Piecemutation], Piecemutation, y, x);

                if (checkMinus(gridclone)){
                    return false;
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
