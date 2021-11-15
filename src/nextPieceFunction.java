public class nextPieceFunction {
    //this method should update the nextpiece and pieceid variables
    //every piece should get its turn in 12 pieces
        public static void nextPiece() { // Lianne
            PieceX = StartX; // reset starting points
            PieceY = StartY;
            rotation = 0; // reset rotation to 0
            if (pentPieces.size() = 0) {
                // If there is only one element in the arraylist, clear the arraylist
                // and add all the IDs to the arraylist again
                pentPieces.add(0);
                pentPieces.add(1);
                pentPieces.add(2);
                pentPieces.add(3);
                pentPieces.add(4);
                pentPieces.add(5);
                pentPieces.add(6);
                pentPieces.add(7);
                pentPieces.add(8);
                pentPieces.add(9);
                pentPieces.add(10);
                pentPieces.add(11); // make an arraylist with the pentomino IDs
                Collections.shuffle(pentPieces); // randomize the order of the arraylist
            } // If there's more than one element in the arraylist, you can get a pentomino
             // out of the list
                pieceID = pentPieces.get(0); // take the first ID and add it to the pieceIDs arraylist
                pentPieces.remove(0); // remove that piece from the pentPieces arraylist
        }
}
