
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
    private final int SquareX = 240;
    private final int SquareY = 1;
    private final int Width = 3;

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
    public UI(int x, int y, int _size) {
        size = _size;
        
        setPreferredSize(new Dimension(x * size * 4 , y * size));
        window = new JFrame("Pentris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.add(this);
        window.pack();
        window. setLocationRelativeTo(null);
        window.setVisible(true);
        window.addKeyListener(this);
        
        state = new int[x][y];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }
    }

    /**
     * This function is called BY THE SYSTEM if required for a new frame, uses the
     * state stored by the UI class.
     */
    public void paintComponent(Graphics g) {
        Graphics2D localGraphics2D = (Graphics2D) g;

        localGraphics2D.setColor(Color.BLACK);
        localGraphics2D.fill(getVisibleRect());

        // draw lines
        localGraphics2D.setColor(Color.WHITE);
        for (int i = 0; i <= state.length; i++) {
            localGraphics2D.drawLine(i * size, 0, i * size, state[0].length * size);
        }
        for (int i = 0; i <= state[0].length; i++) {
            localGraphics2D.drawLine(0, i * size, state.length * size, i * size);
        }

        // draw blocks
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                localGraphics2D.setColor(GetColorOfID(state[i][j]));
                localGraphics2D.fill(new Rectangle2D.Double(i * size + 1, j * size + 1, size - 1, size - 1));
            }
        }

        // draw boxes around the Strings
        localGraphics2D.setColor(Color.CYAN.darker());
        localGraphics2D.setStroke(new BasicStroke(Width));
        Rectangle box = new Rectangle(SquareX, SquareY, 325, 210);
        localGraphics2D.setColor(Color.CYAN.darker());
        localGraphics2D.draw(box);
        localGraphics2D.setColor(Color.CYAN.darker());
        localGraphics2D.drawLine(240, 40, 600, 40);
        localGraphics2D.drawLine(478, 0, 478, 1000);
        localGraphics2D.drawLine(240, 538, 600, 538);
        localGraphics2D.drawLine(240, 0, 240, 1000);
        localGraphics2D.drawLine(240, 250, 478, 250);
        localGraphics2D.drawLine(240, 325, 478, 325);
        localGraphics2D.drawLine(240, 360, 478, 360);

        // draw string for the menu's
        Font myFont = new Font("Dialog", Font.BOLD, 20);
        localGraphics2D.setFont(myFont);
        localGraphics2D.setColor(Color.WHITE);
        localGraphics2D.drawString("NEXT PIECE", 250, 25);
        localGraphics2D.drawString("TIMER", 250, 235);
        localGraphics2D.drawString("HIGH SCORES", 250, 350);

    }

    /**
     * Decodes the ID of a pentomino into a color
     * 
     * @param i ID of the pentomino to be colored
     * @return the color to represent the pentomino. It uses the class Color (more
     *         in ICS2 course in Period 2)
     */
    private Color GetColorOfID(int i) {
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
            return Color.RED.brighter();
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
        }
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
