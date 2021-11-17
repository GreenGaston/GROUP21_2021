/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * This class takes care of all the graphics to display a certain state.
 * Initially, you do not need to modify (or event understand) this class in
 * Phase 1. You will learn more about GUIs in Period 2, in the Introduction to
 * Computer Science 2 course.
 */
public class UI extends JPanel implements KeyListener {
    private JFrame window;
    private int[][] state;
    private int size;
    private final int SquareX = 10;
    private final int SquareY = 1;
    private final int Width = 3;
    private int[][] holdPiece=new int[5][5];
    private Color holdColor=Color.black;
    private int[][] nextPiece=new int[5][5];
    private Color nextColor=Color.black;
    private Boolean colorBlind=false;
    private boolean showNotShow;
    public int rightOfGrid;
    private int miniSize=1;

    public void setHoldPiece(int[][]piece,int pieceid){
        holdPiece=piece;
        holdColor=GetColorOfID(pieceid);
    }
    public void setNextPiece(int[][] piece,int pieceid){
        nextPiece=piece;
        nextColor=GetColorOfID(pieceid);
    }

    // frame.setJMenuBar(battlegui.createMenu());

    // JPanel gui = new JPanel(new GridLayout(1,2,5,5));
    // gui.setBorder(new EmptyBorder(5,5,5,5));
    // gui.add(battlegui.createContentPane());
    // gui.add(battlegui.createContentPane());
    // frame.setContentPane(gui);

    /**
     * Constructor for the GUI. Sets everything up
     * 
     * @param x     x position of the GUI
     * @param y     y position of the GUI
     * @param _size size of the GUI
     */
    public UI(int x, int y, int _size,Boolean Colorblind) {
        size = _size;
        colorBlind=Colorblind;

        setPreferredSize(new Dimension(100+x * size + 350, y * size + 100));
        window = new JFrame("Pentris");
        window.setTitle("Pentris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setMinimumSize(new Dimension(500, 500));
        window.add(this);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.addKeyListener(this);


        rightOfGrid=100+size*x;
        state = new int[x][y];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }

    }

    public void openCloseMenu(boolean showNotShow){
        this.showNotShow = showNotShow; // Comment out for testing and in if tests are completed
        // return true; // Comment in for testing and out if tests are completed
    }

    public boolean getOpenMenu(){
        return showNotShow;
    }


    public void drawGameLayout(Graphics g){
        Graphics2D localGraphics2D = (Graphics2D) g;
        localGraphics2D.setColor(Color.BLACK);
        localGraphics2D.fill(getVisibleRect());
        localGraphics2D.translate(10,50);
        Font myFont2 = new Font("Comic Sans MS", Font.BOLD, 60);
        localGraphics2D.setFont(myFont2);
        localGraphics2D.setColor(Color.CYAN.darker());
        localGraphics2D.drawString("PENTRIS", 70, 0);
        localGraphics2D.translate(0,50);

        if(size-10>0){
            miniSize=size-10;
        }

        // draw lines
        localGraphics2D.setColor(Color.WHITE);
        for (int i = 0; i <= state.length; i++) {
            localGraphics2D.drawLine(i * size+100, 0, i * size+100, state[0].length * size);
        }
        for (int i = 0; i <= state[0].length; i++) {
            localGraphics2D.drawLine(100, i * size, state.length * size+100, i * size);
        }

        // draw blocks
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                localGraphics2D.setColor(GetColorOfID(state[i][j]));
                localGraphics2D.fill(new Rectangle2D.Double(i * size + 101, j * size + 1, size - 1, size - 1));
            }
        }
        //draw holdpiece
         for (int i = 0; i < holdPiece.length; i++) {
             for (int j = 0; j < holdPiece[0].length; j++) {
                 if(holdPiece[i][j]>0){
                    localGraphics2D.setColor(holdColor);
                    localGraphics2D.fill(new Rectangle2D.Double(i * miniSize + 10, j * miniSize + 70, miniSize - 1,miniSize - 1));
                    
                 }
             }
         }
        //draw nextpiece
        for (int i = 0; i < nextPiece.length; i++) {
            for (int j = 0; j < nextPiece[0].length; j++) {
                if(nextPiece[i][j]>0){
                   localGraphics2D.setColor(nextColor);
                   localGraphics2D.fill(new Rectangle2D.Double(i * size + 300, j * size  + 100, size  - 1, size  - 1));
                }
            }
        }



        // draw boxes around the Strings
        localGraphics2D.setColor(Color.CYAN.darker());
        localGraphics2D.setStroke(new BasicStroke(Width));
        Rectangle box = new Rectangle(SquareX+rightOfGrid, SquareY, 325, 210);
        localGraphics2D.setColor(Color.CYAN.darker());
        localGraphics2D.draw(box);
        localGraphics2D.setColor(Color.CYAN.darker());
        localGraphics2D.drawLine(10+rightOfGrid, 40, 470+rightOfGrid, 40);
        localGraphics2D.drawLine(248+rightOfGrid, 0, 248+rightOfGrid, 1000);
        localGraphics2D.drawLine(10+rightOfGrid, 538, 370+rightOfGrid, 538);
        localGraphics2D.drawLine(10+rightOfGrid, 0, 10+rightOfGrid, 1000);
        localGraphics2D.drawLine(10+rightOfGrid, 250, 248+rightOfGrid, 250);
        localGraphics2D.drawLine(10+rightOfGrid, 325, 248+rightOfGrid, 325);
        localGraphics2D.drawLine(10+rightOfGrid, 360, 248+rightOfGrid, 360);

        // draw string for the menu's
        Font myFont = new Font("Comic Sans MS", Font.BOLD, 20);
        localGraphics2D.setFont(myFont);
        localGraphics2D.setColor(Color.WHITE);
        localGraphics2D.drawString("NEXT PIECE", 20+rightOfGrid, 25);
        localGraphics2D.drawString("TIMER", 20+rightOfGrid, 235);
        localGraphics2D.drawString("HIGH SCORES", 20+rightOfGrid, 350);
    }

    public void drawMenu(Graphics g){
        Graphics2D menu = (Graphics2D) g;
        menu.setColor(Color.BLACK);
        menu.fillRect(100,100,300,300);
        menu.setColor(Color.WHITE);
        menu.drawLine(100, 100, 400, 100);
        menu.drawLine(100, 100, 100, 400);
        menu.drawLine(100, 400, 400, 400);
        menu.drawLine(400, 100, 400, 400);
        menu.setColor(Color.WHITE);
        menu.drawString("MENU", 105, 125);
    }

    /**
     * This function is called BY THE SYSTEM if required for a new frame, uses the
     * state stored by the UI class.
     */
    public void paintComponent(Graphics g) {
        if (getOpenMenu()){
            drawGameLayout(g); // Inside drawGameLayout is everything what is shown during the complete game
            drawMenu(g);
        }else{
            drawGameLayout(g);
        }
    }

    /**
     * Decodes the ID of a pentomino into a color
     * 
     * @param i ID of the pentomino to be colored
     * @return the color to represent the pentomino. It uses the class Color (more
     *         in ICS2 course in Period 2)
     */
    private Color GetColorOfID(int i) {
        if(!colorBlind){
        if (i == 0) {
            return Color.BLUE.brighter();
        } else if (i == 1) {
            return Color.BLUE.darker();
        } else if (i == 2) {
            return Color.CYAN;
        } else if (i == 3) {
            return Color.GREEN;
        } else if (i == 4) {
            return Color.MAGENTA;
        } else if (i == 5) {
            return Color.PINK;
        } else if (i == 6) {
            return Color.RED;
        } else if (i == 7) {
            return Color.YELLOW;
        } else if (i == 8) {
            return new Color(123, 40, 49);
        } else if (i == 9) {
            return new Color(0, 0, 100);
        } else if (i == 10) {
            return new Color(100, 0, 0);
        } else if (i == 11) {
            return new Color(0, 100, 0);
        } else {
            return Color.BLACK;
        }}
        else{
            if (i == 0) {
                return Color.decode("#E0C524");
            } else if (i == 1) {
                return Color.decode("#E0C524");
            } else if (i == 2) {
                return Color.decode("#E0C524");
            } else if (i == 3) {
                return Color.decode("#F54B35");
            } else if (i == 4) {
                return Color.decode("#F54B35");
            } else if (i == 5) {
                return Color.decode("#7163E0");
            } else if (i == 6) {
                return Color.decode("#7163E0");
            } else if (i == 7) {
                return Color.decode("#7163E0");
            } else if (i == 8) {
                return Color.decode("#4EEDB0");
            } else if (i == 9) {
                return Color.decode("#4EEDB0");
            } else if (i == 10) {
                return Color.decode("#F0A7E5");
            } else if (i == 11) {
                return Color.decode("#F0A7E5");
            } else {
                return Color.BLACK;
            }}
        }
    
        public void setColorblind(boolean blind){
            colorBlind=blind;
        }
        
    /**
     * This function should be called to update the displayed state (makes a copy)
     * 
     * @param _state information about the new state of the GUI
     */
    public void setState(int[][] _state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = _state[i][j];
            }
        }

        // Tells the system a frame update is required
        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        Pentris.keypadMethod(e);
    }

    public void keyReleased(KeyEvent e) {
    }
}
