// package src;

/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * This class takes care of all the graphics to display a certain state.
 * Initially, you do not need to modify (or event understand) this class in
 * Phase 1. You will learn more about GUIs in Period 2, in the Introduction to
 * Computer Science 2 course.
 */
public class UI extends JPanel implements KeyListener {
    JFrame window;

    private int[][] state;
    private int size;
    private final int SquareX = 10;
    private final int SquareY = 1;
    private final int Width = 3;
    private int[][] holdPiece = new int[5][5];
    private Color holdColor = Color.black;
    private int globalX;
    private int globalY;

    private Boolean colorBlind;

    private long playingTime;

    private int[][] nextPiece = new int[5][5];
    private Color nextColor = Color.black;
    public int rightOfGrid;
    public int moveGridRight = 100;
    public int leftOfGrid = 200;
    private int miniSize = 1;
    private boolean gamelost = false;

    public void setHoldPiece(int[][] piece, int pieceid) {
        holdPiece = piece;
        holdColor = GetColorOfID(pieceid);
    }

    public void setNextPiece(int[][] piece, int pieceid) {
        nextPiece = piece;
        nextColor = GetColorOfID(pieceid);
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
    public UI(int x, int y, int _size, Boolean Colorblind) {
        // JPanel gamePanel = new JPanel();
        // gamePanel.add(this);

        globalX = x;
        globalY = y;
        size = _size;
        this.colorBlind = Colorblind;
        moveGridRight = leftOfGrid;
        rightOfGrid = leftOfGrid + size * x;
        setPreferredSize(new Dimension(leftOfGrid + 200 + x * size + 85, y * size + 145));
        window = new JFrame("PENTRIS");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setMinimumSize(new Dimension(500, 500));
        window.add(this, BorderLayout.CENTER);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.addKeyListener(this);
        window.setBackground(Color.BLACK);

        state = new int[x][y];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }
    }

    public boolean lost = false;

    public void setLost() {
        lost = true;
    }

    /**
     * This function is called BY THE SYSTEM if required for a new frame, uses the
     * state stored by the UI class.
     */
    public void paintComponent(Graphics g) {
        try {
            Graphics2D localGraphics2D = (Graphics2D) g;
            localGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            localGraphics2D.setColor(Color.BLACK);
            localGraphics2D.fill(getVisibleRect());
            localGraphics2D.translate(35, 65);
            Font myFont2 = new Font("Nidus Sans", Font.BOLD, 60);
            localGraphics2D.setFont(myFont2);
            localGraphics2D.setColor(Color.WHITE);

            Image logo = ImageIO.read(new File("logov2.png"));
            logo = logo.getScaledInstance(287, 120, Image.SCALE_DEFAULT);
            localGraphics2D.drawImage(logo, leftOfGrid - 60, -65, null);
            // localGraphics2D.drawString("PENTRIS", 70, 0);
            localGraphics2D.translate(0, 50);

            Image designRight = ImageIO.read(new File("rightSideGrid.png")); // the design on the right side of the grid
            designRight = designRight.getScaledInstance(215, 456, Image.SCALE_DEFAULT);
            localGraphics2D.drawImage(designRight, rightOfGrid + 5, -2, null);

            Image designLeft = ImageIO.read(new File("leftSideGrid2.png")); // the design on the left side of the grid
            designLeft = designLeft.getScaledInstance(215, 470, Image.SCALE_DEFAULT);
            localGraphics2D.drawImage(designLeft, leftOfGrid - 215, -11, null);

            if (size - 10 > 0) {
                miniSize = size - 10;
            }
   
            // draw highscores
            Font smallFont = new Font("Nidus Sans", Font.BOLD, 15);
            localGraphics2D.setFont(smallFont);
            ArrayList<String> highscores = Pentris.getHighscores();
            for (int i = 0; i < highscores.size(); i++) {
                localGraphics2D.drawString(highscores.get(i), 50 + rightOfGrid, 350 + 15 * i);
            }

            // draw current score
            Font smallFont3 = new Font("Nidus Sans", Font.BOLD, 40);
            localGraphics2D.setFont(smallFont3);
            int score = Pentris.getScore();
            String showScore = Integer.toString(score);
            localGraphics2D.drawString(showScore, leftOfGrid - 139, 100);

            // draw timer:
            Font smallFont2 = new Font("Nidus Sans", Font.BOLD, 35);
            localGraphics2D.setFont(smallFont2);
            String time = Pentris.getTime();
            localGraphics2D.drawString(time, rightOfGrid + 50, 225);

            // draw lines
            localGraphics2D.setColor(Color.WHITE);
            for (int i = 0; i <= state.length; i++) {
                localGraphics2D.drawLine(i * size + moveGridRight, 0, i * size + moveGridRight, state[0].length * size);
            }
            for (int i = 0; i <= state[0].length; i++) {
                localGraphics2D.drawLine(moveGridRight, i * size, state.length * size + moveGridRight, i * size);
            }
            // draw lines around grid
            localGraphics2D.setColor(Color.CYAN);
            localGraphics2D.setStroke(new BasicStroke(Width + 4));
            // localGraphics2D.drawLine(leftOfGrid-1, -1, state.length * size +2
            // ,state[0].length * size +2);
            localGraphics2D.drawLine(leftOfGrid - 4, 1, leftOfGrid - 4, state[0].length * size + 1);
            localGraphics2D.drawLine(rightOfGrid + 4, 1, rightOfGrid + 4, state[0].length * size + 1);
            localGraphics2D.setStroke(new BasicStroke(Width));
            localGraphics2D.drawLine(leftOfGrid - 1, -1, state.length * size + 2 + leftOfGrid - 1, -1);
            localGraphics2D.setStroke(new BasicStroke(Width + 1));
            localGraphics2D.drawLine(leftOfGrid - 1, state[0].length * size + 3,
                    state.length * size + 3 + leftOfGrid - 1, state[0].length * size + 2);
            localGraphics2D.setColor(Color.WHITE);

            // localGraphics2D.drawRect(leftOfGrid-1, -1, state.length * size +2
            // ,state[0].length * size +2);

            // draw blocks
            for (int i = 0; i < state.length; i++) {
                for (int j = 0; j < state[0].length; j++) {
                    localGraphics2D.setColor(GetColorOfID(state[i][j]));
                    localGraphics2D.fill(
                            new Rectangle2D.Double(i * size + 1 + moveGridRight, j * size + 1, size - 1, size - 1));
                    localGraphics2D.setColor(GetColorOfID(state[i][j]).darker().darker());
                    localGraphics2D.fill(new Rectangle2D.Double(i * size + 1 + moveGridRight, j * size + (size * 3 / 4),
                            size - 1, size - (size * 3 / 4)));
                    localGraphics2D.setColor(GetColorOfID(state[i][j]).brighter());
                    localGraphics2D.fill(new Rectangle2D.Double(i * size + 1 + moveGridRight, j * size + 1, size - 1,
                            size - (size * 3 / 4)));
                }
            }
            // draw holdpiece
            localGraphics2D.translate(15, 180);
            for (int i = 0; i < holdPiece.length; i++) {
                for (int j = 0; j < holdPiece[0].length; j++) {
                    if (holdPiece[i][j] > 0) {
                        localGraphics2D.setColor(holdColor);
                        localGraphics2D.fill(new Rectangle2D.Double(i * miniSize * 1.5 + 15, j * miniSize * 1.5 + 105,
                                miniSize * 1.5 - 1.5,
                                miniSize * 1.5 - 1.5));
                    }
                }
            }
            // draw nextpiece
            localGraphics2D.translate(-15, -180);
            for (int i = 0; i < nextPiece.length; i++) {
                for (int j = 0; j < nextPiece[0].length; j++) {
                    if (nextPiece[i][j] > 0) {
                        localGraphics2D.setColor(nextColor);
                        localGraphics2D.fill(
                                new Rectangle2D.Double(i * size + 30 + rightOfGrid, j * size + 50, size - 1, size - 1));
                    }
                }
            }

            // draw string for the menu's
            Font myFont = new Font("Nidus Sans", Font.BOLD, 20);
            localGraphics2D.setFont(myFont);
            localGraphics2D.setColor(Color.CYAN);
            localGraphics2D.drawString("NEXT PIECE", 20 + rightOfGrid, 25);
            localGraphics2D.drawString("TIMER", 20 + rightOfGrid, 167);
            localGraphics2D.drawString("HIGH SCORES", 20 + rightOfGrid, 313);
            localGraphics2D.drawString("CURRENT SCORE", leftOfGrid - 200, 25);
            localGraphics2D.drawString("STORED PIECE", leftOfGrid - 200, 167);

            if (lost) {
                Image image = ImageIO.read(new File("gameover.jpg"));
                image = image.getScaledInstance(leftOfGrid + globalX * size + 260, globalY * size + 100,
                        Image.SCALE_DEFAULT);
                localGraphics2D.drawImage(image, -50, -150, null);
                // System.out.println("LOSER");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        if (!colorBlind) {
            if (i == 0) {
                return Color.MAGENTA.brighter();
            } else if (i == 1) {
                return Color.BLUE.darker();
            } else if (i == 2) {
                return Color.CYAN;
            } else if (i == 3) {
                return Color.GREEN;
            } else if (i == 4) {
                return Color.RED.darker();
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
            } else if (i == 12) {
                return new Color(128, 128, 128);
            } else {
                return Color.BLACK;
            }
        } else {
            if (i == 0) {
                return Color.decode("#ff0000");
            } else if (i == 1) {
                return Color.decode("#00ff00");
            } else if (i == 2) {
                return Color.decode("#1bbf1b");
            } else if (i == 3) {
                return Color.decode("#ff00ff");
            } else if (i == 4) {
                return Color.decode("#ffff00");
            } else if (i == 5) {
                return Color.decode("#ffffff");
            } else if (i == 6) {
                return Color.decode("#ff8000");
            } else if (i == 7) {
                return Color.decode("#e3e3ff");
            } else if (i == 8) {
                return Color.decode("#adadad");
            } else if (i == 9) {
                return Color.decode("#ff009d");
            } else if (i == 10) {
                return Color.decode("#ff7373");
            } else if (i == 11) {
                return Color.decode("#fc99ff");
            } else if (i == 12) {
                return new Color(128, 128, 128);
            } else {
                return Color.BLACK;
            }
        }
    }

    public void setColorblind(boolean blind) {
        colorBlind = blind;
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