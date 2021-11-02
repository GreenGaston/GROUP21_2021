import java.awt.event.KeyEvent;

public class KeypadMethod{
    private static int left = KeyEvent.VK_LEFT;
    private static int down = KeyEvent.VK_DOWN;
    private static int right = KeyEvent.VK_RIGHT;
    private static int space = KeyEvent.VK_SPACE;
    public static int pieceX = 2;
    public static int pieceY = 0;
    public static int width = 5;
    public static int height = 12;

    public static int[][] piece;
    public static int[][] grid;

    public static void main(String[] args) {
        UI ui = new UI(width,height,50);
    }

    public static void keypadMethod(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == left) {
            
            if (pieceX != 0 && pieceFit(pieceX-1, pieceY, piece, grid)){
                pieceX -= 1; // If the keypad left is pressed the piece should go 1 position to the left. That's why the x coordinate of the piece is subtracted by 1.
            }
            System.out.println("pieceX = "+pieceX);
        }else if (keyCode == right && pieceFit(pieceX+1, pieceY, piece, grid)) {
            if (pieceX != width-1){
                pieceX += 1; // If the keypad right is pressed the piece should go 1 position to the right. That's why the x coordinate of the piece is added by 1.
            }
            System.out.println("pieceX = "+pieceX);
        }else if (keyCode == down) {
            if (pieceFit(pieceX, pieceY+1, piece, grid)){
                pieceY += 1; // If the keypad down is pressed the piece should go down to the place where it is going to be placed. (To show it smoothly in the UI, drop it down using a much smaller wait then when playing the normal way.)
                dropPiece();
            }
            System.out.println("pieceY = "+pieceY);
        }else if (keyCode == space) {
            rotationMethod(); // If the spacebar is pressed the piece should be rotated once.
        }
    }

    public static void rotationMethod(){
        System.out.println("Piece rotates once!");
    }

    public static boolean pieceFit(int x, int y, int[][] piece, int[][] grid){
        return true;
    }

    public static void dropPiece(){
        
    }
}