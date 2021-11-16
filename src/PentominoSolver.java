package GROUP21_2021.src;

import java.util.Scanner;

public class PentominoSolver {
    public static int[][] answerGrid;
    public static UI ui;



    public static void main(String[] args){
        int UIsize = 25;

        Scanner scanner = new Scanner(System.in);

        System.out.println("What characters do you want to fill the grid with?");
        System.out.println("You can choose from the following letters:\nF, I, L, N, P, T, U, V, W, X, Y, Z.");
        System.out.println("Type in all your characters in CAPITAL LETTERS and as a word.");
        System.out.print("Your choice of characters is: ");
        String stringOfChars = scanner.nextLine();
        char[] input = stringOfChars.toCharArray();

        // Input optimization
        input = Optimise(input);

        int[] tempListIDs = ChartoPieceID(input);
        System.out.println("You have chosen for "+tempListIDs.length+" pieces.");
        // // Print out pieceIDs of the player's choice. For testing purposes.
        // for (int i = 0; i < tempListIDs.length; i++){
        //     if (i == tempListIDs.length-1){
        //         System.out.println(tempListIDs[i]+".");
        //     }else{
        //         System.out.print(tempListIDs[i]+",");
        //     }
        // }

        
        
        System.out.println();
        System.out.println("What sizes do you want the grid to be?");
        System.out.print("Type in your choice for the length of the x-axes: ");
        int Width = scanner.nextInt();
        System.out.print("Type in your choice for the length of the y-axes: ");
        int Height = scanner.nextInt();
        System.out.println("You have chosen for a grid of "+Width+" by "+Height+".");
        System.out.println();
        int tempHeightWidth;
        if (Height > Width){
            tempHeightWidth = Height;
            Height = Width;
            Width = tempHeightWidth;
        }

        ui = new UI(Width, Height, UIsize);



        //make an empty grid
        int[][] grid= new int[Width][Height];
        for(int i=0; i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                grid[i][j]=-1;
            }
        }
        
        int[] pieceIDs=tempListIDs;

        // Check if enough pieces are given to fill the grid
        if (pieceIDs.length*5 >= Width*Height){
            long Beginning = System.currentTimeMillis();
            if(SolvePentomino(grid, pieceIDs, pieceIDs.length)){
                ui.setState(answerGrid); 
                System.out.println("Solution found");
                
                long Ending = System.currentTimeMillis();
                System.out.println("It took " + (Ending-Beginning) + " milliseconds to find a solution for this puzzle." );    
            }else{
                System.out.println("No solution possible with given pieces. Try other input!");
            }
        }else{
            System.out.println("There were too few pieces given to fill the grid. Try again!");
        }


        scanner.close();

    

    }


//this is the main function of the program it takes any give pieceIDs and tries to fit them on the grid recursively
//trying to place a piece in a permutation
//if it is able to do so it will call itself again with the same pieces but removing the that was just placed
//it also gives the grid with the placed piece
    public static boolean SolvePentomino(int[][] grid,int[] pieceIDs,int depth){
        
        // ui.setState(grid);

        int[] emptyCords =findNextEmpty(grid);
        int[][] gridClone= clone2Dint(grid);
        int[][] secondaryClone=clone2Dint(grid);
        





        //this is the exit condition, once it has reached a depth of 1, meaning it only has one piece left, it will try and put it on the board.
        //if it can then it will return true which will in turn make the one above return true etc.
        if(depth==1){
            for(int i=0; i<PentominoDatabase.data[pieceIDs[0]].length;i++){
                if(PieceFit(gridClone, pieceIDs[0], i,adjustX(emptyCords[1], pieceIDs[0], i), emptyCords[0])){
                    Search.addPiece(gridClone, PentominoDatabase.data[pieceIDs[0]][i], pieceIDs[0], emptyCords[0], adjustX(emptyCords[1], pieceIDs[0], i));
                    answerGrid=gridClone;
                    


                    return true;
                }

            }
            
            return false;
        }


        //if it isnt the final piece then it will try to fit all pieces in all posible permutations on the next available tile
        else{
            
            for(int i = 0;i<pieceIDs.length;i++){//for every piece
                
                for(int j=0; j<PentominoDatabase.data[pieceIDs[i]].length;j++){//for every permutation that piece has
                    

                    if(PieceFit(gridClone, pieceIDs[i], j, adjustX(emptyCords[1], pieceIDs[i], j), emptyCords[0])){//if the piece fits
                        
                        
                        Search.addPiece(gridClone, PentominoDatabase.data[pieceIDs[i]][j], pieceIDs[i], emptyCords[0],adjustX(emptyCords[1], pieceIDs[i], j));//add it to a clone of the grid
                        

                        int[] temporaryPieces=removeID(pieceIDs,i);//make a copy of the idlist without the id of the piece that was just placed


                        //call itself with the clone and the new pieceidlist
                        if(SolvePentomino(gridClone, temporaryPieces, depth-1)){
                           //this is part of the exit condition once it returns true all functions will halt searching for solutions
                            return true;}
                        


                        //clear the clone of pieces placed for the next loop
                        gridClone=clone2Dint(secondaryClone);
                    }
                }
            }
        }

        return false;
    }








    //this function finds the next empty square

    public static int[] findNextEmpty(int[][] Grid){
        int[] emptyCords = new int[2];



        for(int i=0;i<Grid.length;i++){
            for(int j=0;j<Grid[0].length;j++){
                if(Grid[i][j]==-1){
                    emptyCords[0]=i;
                    emptyCords[1]=j;
                    return emptyCords;

                }
            }
        }
        return emptyCords;
    }


    //this is a function for testing purposes
    public static int[][] clone2Dint(int[][] list){
        int[][] clone = new int[list.length][list[0].length];
        for(int i=0; i<list.length; i++){
            for(int j=0; j<list[i].length; j++){
                clone[i][j]=list[i][j];
            }
        }
        return clone;
    }







    //this function makes a copy of a list while removing a certain index of said list
    public static int[] removeID(int[] pieceIDs,int index){
        
        int[] answerList= new int[pieceIDs.length-1];

        for(int i=0, k=0;i<pieceIDs.length;i++){
    
            if(i!=index){
                
                answerList[k]=(pieceIDs[i]);
                k++;
            }
        }
        return answerList;
    }


    //this function adjust the x position to account for empty space within the matrix which represent the pieces
    public static int adjustX(int x,int pieceID,int mutation){
        int updated_x=0;
        updated_x=x;
        int zerocount =0;
        int[][] piece=PentominoDatabase.data[pieceID][mutation];
        for(int i=0;i<piece[0].length;i++){
            if(piece[0][i]==0){
                zerocount++;
                }
            else{
                break;
            }
        }
        updated_x=updated_x-zerocount;
        return updated_x;
    }


    //function for testing purposes which prints grids
// public static void printGrid(int[][]grid){
//     for(int i=0;i<grid.length;i++){
//         for(int j=0;j<grid.length;j++){
//             System.out.print(grid[i][j]+",");
//         }
//         System.out.println("");
//         }
//     }

// //function for testing purposes which print pieces

//     public static void printPiece(int[][] piece){
//         for(int i=0;i<piece.length;i++){
//             for(int j=0;j<piece[i].length;j++){
//                 System.out.print(piece[i][j]);}
//                 System.out.println("");
//             }
//         }


    public static int[] ChartoPieceID (char[] list){
        int[] CharToPiece = new int[list.length];
        for(int i = 0; i<list.length; i++){
            CharToPiece[i] = Search.characterToID(list[i]);
        }
        return CharToPiece;
    }

    


    public static char[] sortingAG(int[] Numberlist, char[] characters){
        int p = 1;
        for(int j = 0 ; j < Numberlist.length; j++){
            if(p == 0){
                break;
            }
            p = 0;
            for(int i = 0; i < Numberlist.length - 1; i++){
                
                int firstnum = Numberlist[i];
                int secondnum = Numberlist[i + 1];
                char firstchar = characters[i];
                char secondchar = characters[i + 1];
                    
                    if(firstnum < secondnum){
                        //swaps around the numbers if the firstone is bigger than the second one
                        //Also swaps around the letters in the char[] if a number is swapped.
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

    //Uses a random list of pieces and transorms the list such that there is  higher chance of fitting if you place them after one another
    //
    public static char[] Optimise(char[] characters){
        int[] posibilities = new int[characters.length];

        //Checks how often you can fit a piece in an empty grid and stores that in a matrix
        for(int i = 0; i <= characters.length - 1; i++){
            if(characters[i] == 'L'){
                posibilities[i] = 12;
            }
            else if(characters[i] == 'I'){
                posibilities[i] = 11;
            }
            else if(characters[i] == 'P'){
                posibilities[i] = 10;
            }
            else if(characters[i] == 'Y' || characters[i] == 'F'){
                posibilities[i] = 9;
            }
            else if(characters[i] == 'N'){
                posibilities[i] = 8;
            }
            else if(characters[i] == 'V'){
                posibilities[i] = 7;
            }
            else if(characters[i] == 'W' || characters[i] == 'U' || characters[i] == 'T' || characters[i] == 'Z'){
                posibilities[i] = 6;
            }
            else{
                posibilities[i] = 1;
            }

        }
        char[] optimisedmatrix = new char[characters.length];
        int j = 0;
        int k = 0;
        //The 2 for loops make sure that the pieces with the highest amount of possibilities are put either 
        //first or last in the optimised matrix. So that your least 'flexible' piece is used somewhere in the middle.
        for(int i = 0; i<= characters.length - 1; i++){
            if(i%2 == 0){
                optimisedmatrix[j] = characters[i];
                j += 1;
            }
        }
        j = 1;
        for(int i = 0; i<= characters.length - 1; i++){
           
            if(i%2 != 0){
                optimisedmatrix[optimisedmatrix.length - k - 1] = characters[i];
                k += 1;
            }
        }
        return optimisedmatrix;
    }



    //this function evaluated if a piece can be placed on a give grid at a certain x and y location
    public static boolean PieceFit(int[][]grid,int PieceID,int Piecemutation,int x,int y){
        //if the x is negative then the starting point is of the grid and therefor invalid
        if(x<0){
            
            return false;
        }

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

    //Function which checks whether a -1 in the grid is surrounded by zeros, if so, return true. 
    public static boolean checkMinus(int[][] arrays){
        int[][] newarray = new int[arrays.length + 2][arrays[0].length + 2];

        //creates a border all made of zeros and put it arround the original array, so that you can check the -1's without having to write exceptions zeros for the borders.
        for(int i = 0; i <= arrays.length + 1; i++){
            for(int j = 0; j <= arrays[0].length + 1; j++){
                if(i == 0 || j == 0 || i == newarray.length -1 || j == newarray[0].length - 1){
                    newarray[i][j] = 0;
                }
                else{
                    newarray[i][j] = arrays[i -1][j -1];
                }
            }
        }
        arrays = newarray;
        boolean result = false;

        //Looks for a -1 in the original array if there is a 0 above, below and next to the -1, if so, returns true.
        for(int i=1; i<arrays.length-1; i++){
            for(int j=1; j<arrays[1].length-1; j++){
                if(arrays[i][j] == -1){
                    if(arrays[i-1][j]>= 0 && arrays[i+1][j]>= 0 && arrays[i][j-1]>= 0 && arrays[i][j+1]>= 0){
                        result = true;
                        return result;
                    }
                }
            }
        }
        return result;
    }
}