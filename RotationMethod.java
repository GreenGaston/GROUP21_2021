public class RotationMethod {
        //Put all pentominoes with random rotation/flipping on a random position on the board
    		for (int i = 0; i < input.length; i++) {
    			
    			//Choose a pentomino and randomly rotate/flip it
    			int pentID = characterToID(input[i]);
    			int mutation = random.nextInt(PentominoDatabase.data[pentID].length);
    			int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];}

	public static boolean PieceFit(int[][]grid,int PieceID,int Piecemutation,int x,int y){
	//if the x is negative then the starting point is of the grid and therefor invalid
		if(x<0){
						
	return false;
					}
		
	

    
}
