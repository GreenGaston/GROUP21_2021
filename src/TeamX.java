// package src;
// import java.util.*;
// import java.io.*;

// public class TeamX { // class that implements knuth's algorithm X: with dancing links

// class Sudoku
// {
//  /* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
//   * a standard Sudoku puzzle, SIZE is 3 and N is 9. */
//  int SIZE, N;

//  /* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
//   * not yet been revealed are stored as 0. */
//  int Grid[][];


//  /* The solve() method should remove all the unknown characters ('x') in the Grid
//   * and replace them with the numbers from 1-9 that satisfy the Sudoku puzzle. */

//     /*********************************************************************
//      * * DOCUMENTATION: KNUTH'S ALGORITHM X, EXACT COVER PROBLEM
//      * -------------------------------------------------------------------------------------------------
//      * PSEUDO CODE:
//      * 1. If the matrix A has no columns, the current partial solution is a valid
//      * solution; terminate successfully
//      * 1. Otherwise choose a column c (deterministically)
//      * 2. Choose a row r such that Ar,c = 1 (nondeterministically)
//      * 3. Include row r in the partial solution
//      * 4. For each column j such that Ar,j = 1
//      * for each row k such that Ak,j = 1
//      * delete row k from matrix A
//      * delete column j from matrix A
//      * 5. repeat this algorithm recursively on the reduced matrix A
//      * source: https://en.wikipedia.org/wiki/Knuth%27s_Algorithm_X
//      * 
//      * PSEUDO CODE FROM THE PAPER:
//      * // If R[h] = h, print the current solution (see below) and return.
//      * // Otherwise choose a column object c (see below).
//      * // Cover column c (see below).
//      * // For each r ← D[c], D[D[c]], ... , while r != c,
//      * // set Ok ← r;
//      * // for each j ← R[r], R[R[r]], ... , while j != r,
//      * // cover column j (see below);
//      * // search(k + 1);
//      * // set r ← Ok and c ← C[r];
//      * // for each j ← L[r], L[L[r]], ... , while j != r,
//      * // uncover column j (see below).
//      * // Uncover column c (see below) and return.
//      */

//     public void solve()
//  {
//   AlgorithmXSolver solver= new AlgorithmXSolver();
//   solver.run(Grid);
//  }

//  public class AlgorithmXSolver 
//  {        
//   private ColumnNode root = null; // this is the starting node of the linked list
//   private ArrayList solution = new ArrayList(); // a raw Array List for dynamically storing the solutions. It slows things
//   // down a bit, but this how I started and ran out of time before I could come up with a more efficient way to do it.

//   // the run method. We pass the Grid[][] as input
//   private void run(int[][] initialMatrix) 
//   {
//    byte[][] matrix = createMatrix(initialMatrix); // create the sparse matrix. We use the type byte to speed things up. I tried using
//    // using all the primitive types, expecting the same results in terms
//    // of speed; the only performance boost should have been in terms of space.
//    // Yet, there was a marked difference in the running times. Hence, I used byte[][] whenever possible.
//    ColumnNode doubleLinkedList = createDoubleLinkedLists(matrix);   // create the circular doubly-linked toroidal list   
//    search(0); // start the Dancing Links process of searching and covering and uncovering recursively
//   }

//   // data structures
//   class Node  // we define a node that knows about four other nodes, as well as its column head
//   {
//    Node left;
//    Node right;
//    Node up;
//    Node down;
//    ColumnNode head;
//   }

//   class ColumnNode extends Node // a special kind of node that contains information about that column
//   {
//    int size = 0;
//    ColumnID info;
//   }

//   class ColumnID  // helps us store information about that column
//   {
//    int constraint = -1;
//    int number = -1;
//    int position = -1;
//   }

//   // create a sparse matrix for Grid
//   private byte[][] createMatrix(int[][] initialMatrix)
//   {      
//    int[][] clues = null; // stores the numbers that are already given on the board i.e. the 'clues'
//    ArrayList cluesList = new ArrayList(); // the list used to get the clues. Because we use a raw ArrayList, we later have to cast to int[] before storing in clues
//    int counter = 0; 
//    for(int r = 0; r < N; r++) // iterates over the rows of Grid
//    {
//     for(int c = 0; c < N; c++) // iterates over the columns of Grid
//     {
//      if(initialMatrix[r][c] > 0) // if the number on the Grid is != 0 (the number is a clue and not a blank space to solved for), then store it
//      {
//       cluesList.add(new int[]{initialMatrix[r][c],r,c}); // store the number, the row number and the column number
//       counter++;
//      }
//     }
//    }
//    clues = new int[counter][]; // store the clues once we've gotten them
//    for(int i = 0; i < counter; i++) 
//    {
//     clues[i] = (int[])cluesList.get(i); 
//    }

//    // Now, we build our sparse matrix
//    byte[][] matrix = new byte[N*N*N][4*N*N];
//    // The rows of our matrix represent all the possibilities, whereas the columns represent the constraints.
//    // Hence, there are N^3 rows (N rows * N columns * N numbers), and N^2 * 4 columns (N rows * N columns * 4 constraints)

//    // iterate over all the possible digits d
//    for(int d = 0; d < N; d++) 
//    {
//     // iterate over all the possible rows r
//     for(int r = 0; r < N; r++) 
//     {
//      // iterator over all the possible columns c
//      for(int c = 0; c < N; c++) 
//      {
//       if(!filled(d,r,c,clues)) // if the cell is not already filled
//       {
//        // this idea for this way of mapping the sparse matrix is taken from the Python implementation: https://code.google.com/p/narorumo/wiki/SudokuDLX
//        int rowIndex = c + (N * r) + (N * N * d);
//        // there are four 1s in each row, one for each constraint
//        int blockIndex = ((c / SIZE) + ((r / SIZE) * SIZE));
//        int colIndexRow = 3*N*d+r;
//        int colIndexCol = 3*N*d+N+c;
//        int colIndexBlock = 3*N*d+2*N+blockIndex;
//        int colIndexSimple = 3*N*N+(c+N*r);
//        // fill in the 1's
//        matrix[rowIndex][colIndexRow] = 1;
//        matrix[rowIndex][colIndexCol] = 1;
//        matrix[rowIndex][colIndexBlock] = 1;
//        matrix[rowIndex][colIndexSimple] = 1;
//       }
//      }
//     }
//    }
//    return matrix;
//   }

//   // check if the cell to be filled is already filled with a digit. The idea for this is credited to Alex Rudnick as cited above
//   private boolean filled(int digit, int row, int col, int[][] prefill) {
//    boolean filled = false;
//    if(prefill != null) 
//    {
//     for(int i = 0; i < prefill.length; i++) 
//     {
//      int d = prefill[i][0]-1;
//      int r = prefill[i][1];
//      int c = prefill[i][2];
//      // calculate the block indices
//      int blockStartIndexCol = (c/SIZE)*SIZE;
//      int blockEndIndexCol = blockStartIndexCol + SIZE;
//      int blockStartIndexRow = (r/SIZE)*SIZE;
//      int blockEndIndexRow = blockStartIndexRow + SIZE;
//      if(d != digit && row == r && col == c) {
//       filled = true;
//      } else if((d == digit) && (row == r || col == c) && !(row == r && col == c)) 
//      {
//       filled = true;
//      } else if((d == digit) && (row > blockStartIndexRow) && (row < blockEndIndexRow) && (col > blockStartIndexCol) && (col < blockEndIndexCol) && !(row == r && col == c)) 
//      {
//       filled = true;
//      }
//     }
//    }
//    return filled;
//   }


//   // the method to convert the sparse matrix Exact Cover problem to a doubly-linked list, which will allow us to later
//   // perform our Dancing Links magic.
//   // Given that we have 4 constraints for Sudoku, I created a new class ColumnID that is a property of all columns.
//   // This ColumnID property contains the information about the constraint and allows us to identify which constraint position
//   // we're on, as well as the row and the column and the digit
//   // the first constraint is row constraint, the second is col, the third is block, and the fourth is cell.
//   // Every constraint contains N^2 columns for every cell 
//   // The idea for this is taken from Jonathan Chu's explanation (cited above)
//   private ColumnNode createDoubleLinkedLists(byte[][] matrix) 
//   {
//    root = new ColumnNode(); // the root is used as an entry-way to the linked list i.e. we access the list through the root
//    // create the column heads
//    ColumnNode curColumn = root;
//    for(int col = 0; col < matrix[0].length; col++) // getting the column heads from the sparse matrix and filling in the information about the 
//     // constraints. We iterate for all the column heads, thus going through all the items in the first row of the sparse matrix
//    {
//     // We create the ColumnID that will store the information. We will later map this ID to the current curColumn
//     ColumnID id = new ColumnID();
//     if(col < 3*N*N) 
//     {
//      // identifying the digit
//      int digit = (col / (3*N)) + 1;
//      id.number = digit;
//      // is it for a row, column or block?
//      int index = col-(digit-1)*3*N;
//      if(index < N) 
//      {
//       id.constraint = 0; // we're in the row constraint
//       id.position = index;
//      } else if(index < 2*N) 
//      {
//       id.constraint = 1; // we're in the column constraint
//       id.position = index-N;
//      } else 
//      {
//       id.constraint = 2; // we're in the block constraint
//       id.position = index-2*N;
//      }            
//     } else
//     {
//      id.constraint = 3; // we're in the cell constraint
//      id.position = col - 3*N*N;
//     }
//     curColumn.right = new ColumnNode();
//     curColumn.right.left = curColumn;
//     curColumn = (ColumnNode)curColumn.right;
//     curColumn.info = id; // the information about the column is set to the new column
//     curColumn.head = curColumn; 
//    }
//    curColumn.right = root; // making the list circular i.e. the right-most ColumnHead is linked to the root
//    root.left = curColumn;

//    // Once all the ColumnHeads are set, we iterate over the entire matrix
//    // Iterate over all the rows
//    for(int row = 0; row < matrix.length; row++) 
//    {
//     // iterator over all the columns
//     curColumn = (ColumnNode)root.right;
//     Node lastCreatedElement = null;
//     Node firstElement = null;
//     for(int col = 0; col < matrix[row].length; col++) {
//      if(matrix[row][col] == 1)  // i.e. if the sparse matrix element has a 1 i.e. there is a clue here i.e. we were given this value in the Grid
//      {
//       // create a new data element and link it
//       Node colElement = curColumn;
//       while(colElement.down != null) 
//       {
//        colElement = colElement.down;
//       }
//       colElement.down = new Node();
//       if(firstElement == null) {
//        firstElement = colElement.down;
//       }
//       colElement.down.up = colElement;
//       colElement.down.left = lastCreatedElement;
//       colElement.down.head = curColumn;
//       if(lastCreatedElement != null) 
//       {
//        colElement.down.left.right = colElement.down;
//       }
//       lastCreatedElement = colElement.down;
//       curColumn.size++;
//      }
//      curColumn = (ColumnNode)curColumn.right;
//     }
//     // link the first and the last element, again making it circular
//     if(lastCreatedElement != null) 
//     {
//      lastCreatedElement.right = firstElement;
//      firstElement.left = lastCreatedElement;
//     }
//    }
//    curColumn = (ColumnNode)root.right;
//    // link the last column elements with the corresponding columnHeads
//    for(int i = 0; i < matrix[0].length; i++) 
//    {
//     Node colElement = curColumn;
//     while(colElement.down != null) 
//     {
//      colElement = colElement.down;
//     }
//     colElement.down = curColumn;
//     curColumn.up = colElement;
//     curColumn = (ColumnNode)curColumn.right;
//    }
//    return root; // We've made the doubly-linked list; we return the root of the list
//   }

//   // the searching algorithm. Pseudo-code from Jonathan Chu's paper (cited above).
//   private void search(int k) 
//   {
//    if(root.right == root) // if we've run out of columns, we've solved the exact cover problem!
//    {
//     mapSolvedToGrid(); // map the solved linked list to the grid
//     return;
//    }
//    ColumnNode c = choose(); // we choose a column to cover
//    cover(c);
//    Node r = c.down;
//    while(r != c) 
//    {
//     if(k < solution.size()) 
//     {
//      solution.remove(k); // if we had to enter this loop again
//     }         
//     solution.add(k,r); // the solution is added

//     Node j = r.right;
//     while(j != r) {
//      cover(j.head);
//      j = j.right;
//     }
//     search(k+1); //recursively search

//     Node r2 = (Node)solution.get(k);
//     Node j2 = r2.left;
//     while(j2 != r2) {
//      uncover(j2.head);
//      j2 = j2.left;
//     }
//     r = r.down;
//    }
//    uncover(c);
//   }

//   // this allows us to map the solved linked list to the Grid
//   private void mapSolvedToGrid() 
//   {
//    int[] result = new int[N*N];
//    for(Iterator it = solution.iterator(); it.hasNext();)  // we use Iterators to iterate over every single element of the ArrayList
//     // we stop iterating once we run out of elements in the list
//    {
//     // for the first step, we pull all the values of the solved Sudoku board from the linked list to an array result[] in order
//     int number = -1; // initialize number and cell number to be a value that can't occur
//     int cellNo = -1;
//     Node element = (Node)it.next();
//     Node next = element;
//     do {
//      if (next.head.info.constraint == 0) 
//      { // if we're in the row constraint
//       number = next.head.info.number; 
//      } 
//      else if (next.head.info.constraint == 3) 
//      { // if we're in the cell constraint
//       cellNo = next.head.info.position;
//      }
//      next = next.right;
//     } while(element != next);
//     result[cellNo] = number; // feed values into result[]
//    }
//    // for the second step, we feed all the values of the array result[] (in order) to the Grid   
//    int resultCounter=0;
//    for (int r=0; r<N; r++) // iterates for the rows
//    {
//     for (int c=0; c<N; c++) // iterates for the columns
//     {
//      Grid[r][c]=result[resultCounter];
//      resultCounter++;
//     }
//    }  
//   }



//   private ColumnNode choose() {
//    // According to Donald Knuth's paper, it is most efficient to choose the column with the smallest possible size.
//    // That is what we do.
//    ColumnNode rightOfRoot = (ColumnNode)root.right; // we cast the node to the right of the root to be a ColumnNode
//    ColumnNode smallest = rightOfRoot; 
//    while(rightOfRoot.right != root) 
//    {
//     rightOfRoot = (ColumnNode)rightOfRoot.right;
//     if(rightOfRoot.size < smallest.size) // choosing which column has the lowest size
//     {
//      smallest = rightOfRoot;
//     }         
//    }      
//    return smallest;
//   }

//   // covers the column; used as a helper method for the search method. Pseudo code by Jonathan Chu (credited above)
//   private void cover(Node column) 
//   {
//    // we remove the column head by remapping the node to its left to the node to its right; thus, the linked list no longer contains
//    // a way to access the column head. Later when we uncover it, we can easily do so by just reversing this process.
//    column.right.left = column.left;
//    column.left.right = column.right;

//    // We also have to do this covering for all the rows in the column
//    Node curRow = column.down;
//    while(curRow != column) // because it's circular!
//    {
//     Node curNode = curRow.right;
//     while(curNode != curRow) 
//     {
//      curNode.down.up = curNode.up;
//      curNode.up.down = curNode.down;
//      curNode.head.size--;
//      curNode = curNode.right;
//     }
//     curRow = curRow.down;
//    }
//   }

//   // uncovers the column i.e. adds back all the nodes of the column to the linked list
//   private void uncover(Node column) 
//   {
//    Node curRow = column.up;
//    while(curRow != column) // do this for all the nodes of the column to be uncovered first, and then reinsert the columnHead
//    {
//     Node curNode = curRow.left;
//     while(curNode != curRow) 
//     {
//      curNode.head.size++;
//      curNode.down.up = curNode; // reinserts node into linked list
//      curNode.up.down = curNode;
//      curNode = curNode.left;
//     }
//     curRow = curRow.up;
//    }
//    column.right.left = column; // reinserts column head
//    column.left.right = column;
//   }

//  }


//  /*****************************************************************************/
//  /* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE FUNCTIONS BELOW THIS LINE. */
//  /*****************************************************************************/

//  /* Default constructor.  This will initialize all positions to the default 0
//   * value.  Use the read() function to load the Sudoku puzzle from a file or
//   * the standard input. */
//  public Sudoku( int size )
//  {
//   SIZE = size;
//   N = size*size;

//   Grid = new int[N][N];
//   for( int i = 0; i < N; i++ ) 
//    for( int j = 0; j < N; j++ ) 
//     Grid[i][j] = 0;
//  }


//  /* readInteger is a helper function for the reading of the input file.  It reads
//   * words until it finds one that represents an integer. For convenience, it will also
//   * recognize the string "x" as equivalent to "0". */
//  static int readInteger( InputStream in ) throws Exception
//  {
//   int result = 0;
//   boolean success = false;

//   while( !success ) {
//    String word = readWord( in );

//    try {
//     result = Integer.parseInt( word );
//     success = true;
//    } catch( Exception e ) {
//     // Convert 'x' words into 0's
//     if( word.compareTo("x") == 0 ) {
//      result = 0;
//      success = true;
//     }
//     // Ignore all other words that are not integers
//    }
//   }

//   return result;
//  }


//  /* readWord is a helper function that reads a word separated by white space. */
//  static String readWord( InputStream in ) throws Exception
//  {
//   StringBuffer result = new StringBuffer();
//   int currentChar = in.read();
//   String whiteSpace = " \t\r\n";
//   // Ignore any leading white space
//   while( whiteSpace.indexOf(currentChar) > -1 ) {
//    currentChar = in.read();
//   }

//   // Read all characters until you reach white space
//   while( whiteSpace.indexOf(currentChar) == -1 ) {
//    result.append( (char) currentChar );
//    currentChar = in.read();
//   }
//   return result.toString();
//  }


//  /* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
//   * grid is filled in one row at at time, from left to right.  All non-valid
//   * characters are ignored by this function and may be used in the Sudoku file
//   * to increase its legibility. */
//  public void read( InputStream in ) throws Exception
//  {
//   for( int i = 0; i < N; i++ ) {
//    for( int j = 0; j < N; j++ ) {
//     Grid[i][j] = readInteger( in );
//    }
//   }
//  }


//  /* Helper function for the printing of Sudoku puzzle.  This function will print
//   * out text, preceded by enough ' ' characters to make sure that the printint out
//   * takes at least width characters.  */
//  void printFixedWidth( String text, int width )
//  {
//   for( int i = 0; i < width - text.length(); i++ )
//    System.out.print( " " );
//   System.out.print( text );
//  }


//  /* The print() function outputs the Sudoku grid to the standard output, using
//   * a bit of extra formatting to make the result clearly readable. */
//  public void print()
//  {
//   // Compute the number of digits necessary to print out each number in the Sudoku puzzle
//   int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

//   // Create a dashed line to separate the boxes 
//   int lineLength = (digits + 1) * N + 2 * SIZE - 3;
//   StringBuffer line = new StringBuffer();
//   for( int lineInit = 0; lineInit < lineLength; lineInit++ )
//    line.append('-');

//   // Go through the Grid, printing out its values separated by spaces
//   for( int i = 0; i < N; i++ ) {
//    for( int j = 0; j < N; j++ ) {
//     printFixedWidth( String.valueOf( Grid[i][j] ), digits );
//     // Print the vertical lines between boxes 
//     if( (j < N-1) && ((j+1) % SIZE == 0) )
//      System.out.print( " |" );
//     System.out.print( " " );
//    }
//    System.out.println();

//    // Print the horizontal line between boxes
//    if( (i < N-1) && ((i+1) % SIZE == 0) )
//     System.out.println( line.toString() );
//   }
//  }


//  /* The main function reads in a Sudoku puzzle from the standard input, 
//   * unless a file name is provided as a run-time argument, in which case the
//   * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
//   * outputs the completed puzzle to the standard output. */
//  public static void main( String args[] ) throws Exception
//  {
//   InputStream in;
//   if( args.length > 0 ) 
//    in = new FileInputStream( args[0] );
//   else
//    in = System.in;

//   // The first number in all Sudoku files must represent the size of the puzzle.  See
//   // the example files for the file format.
//   int puzzleSize = readInteger( in );
//   if( puzzleSize > 100 || puzzleSize < 1 ) {
//    System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
//    System.exit(-1);
//   }

//   Sudoku s = new Sudoku( puzzleSize );

//   // read the rest of the Sudoku puzzle
//   s.read( in );

//   // Solve the puzzle.  We don't currently check to verify that the puzzle can be
//   // successfully completed.  You may add that check if you want to, but it is not
//   // necessary.
//   long startTime = System.currentTimeMillis(); // test
//   s.solve();
//   long endTime = System.currentTimeMillis(); // test
//   System.out.println(endTime-startTime); // test
//   //  s.solve();

//   // Print out the (hopefully completed!) puzzle
//   s.print();
//  }
// }