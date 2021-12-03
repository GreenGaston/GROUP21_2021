// package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class startMenu implements ActionListener, MouseListener {
    JFrame UI;
    //////////////////////////////////////////
    JPanel menuPanel;
    //////////////////////////////////////////
    JLabel colon1;
    JLabel colon2;
    JLabel colon3;
    JLabel colon4;
    JLabel colon5;

    JLabel level;
    JLabel levelNumber;

    JLabel colorblindmode;

    JLabel gridSizes;
    JLabel sizeX;
    JLabel sizeY;
    JLabel times;
    JLabel x;
    JLabel y;

    JLabel name;

    JLabel menu;
    //////////////////////////////////////////
    JButton buttonPlus;
    JButton buttonMinus;
    JButton buttonColormode;
    JButton closeMenu;
    JButton sizeXPlus;
    JButton sizeXMinus;
    JButton sizeYPlus;
    JButton sizeYMinus;
    JButton play;
    JButton startBot;
    //////////////////////////////////////////
    JTextField inputName;
    //////////////////////////////////////////
    int levelInt = 1;
    int minSizeX = 5;
    int minSizeY = 15;
    int maxSizeX = 15;
    int maxSizeY = 20;
    int intSizeX = minSizeX;
    int intSizeY = minSizeY;
    String colorblindNormal = "Normal";
    String menuLetters = "Nidus Sans";
    String playerName;
    boolean isColorBlind;
    boolean showMenu = true;
    boolean playBot = false;

    public static void main(String[] args) {
        new startMenu();
    }

    public startMenu() {
        // Textfield:
        inputName = new JTextField();
        inputName.setLocation(200, 160);
        inputName.setSize(195, 50);
        inputName.addActionListener(this);
        inputName.addMouseListener(this);

        // Buttons:
        // ButtonPlus:
        buttonPlus = new JButton("+");
        buttonPlus.addActionListener(this);
        buttonPlus.setBackground(Color.BLACK);
        buttonPlus.setForeground(Color.WHITE);
        buttonPlus.setFont(new Font(menuLetters, Font.BOLD, 26));
        buttonPlus.setLocation(345, 50);
        buttonPlus.setSize(50, 50);

        // ButtonMinus:
        buttonMinus = new JButton("-");
        buttonMinus.addActionListener(this);
        buttonMinus.setBackground(Color.BLACK);
        buttonMinus.setForeground(Color.WHITE);
        buttonMinus.setFont(new Font(menuLetters, Font.BOLD, 26));
        buttonMinus.setLocation(200, 50);
        buttonMinus.setSize(50, 50);

        // ButtonColorMode:
        buttonColormode = new JButton(colorblindNormal);
        buttonColormode.addActionListener(this);
        buttonColormode.setBackground(Color.BLACK);
        buttonColormode.setForeground(Color.WHITE);
        buttonColormode.setFont(new Font(menuLetters, Font.BOLD, 18));
        buttonColormode.setLocation(200, 105);
        buttonColormode.setSize(195, 50);

        // CloseMenu:
        closeMenu = new JButton("Close");
        closeMenu.addActionListener(this);
        closeMenu.setBackground(Color.RED);
        closeMenu.setForeground(Color.WHITE);
        closeMenu.setFont(new Font(menuLetters, Font.PLAIN, 13));
        closeMenu.setLocation(328, 5);
        closeMenu.setSize(67, 15);

        // Buttons for gridsizes:
        sizeXMinus = new JButton("-");
        sizeXMinus.addActionListener(this);
        sizeXMinus.setBackground(Color.BLACK);
        sizeXMinus.setForeground(Color.WHITE);
        sizeXMinus.setFont(new Font(menuLetters, Font.BOLD, 26));
        sizeXMinus.setLocation(200, 215);
        sizeXMinus.setSize(50, 50);

        sizeXPlus = new JButton("+");
        sizeXPlus.addActionListener(this);
        sizeXPlus.setBackground(Color.BLACK);
        sizeXPlus.setForeground(Color.WHITE);
        sizeXPlus.setFont(new Font(menuLetters, Font.BOLD, 26));
        sizeXPlus.setLocation(345, 215);
        sizeXPlus.setSize(50, 50);

        sizeYMinus = new JButton("-");
        sizeYMinus.addActionListener(this);
        sizeYMinus.setBackground(Color.BLACK);
        sizeYMinus.setForeground(Color.WHITE);
        sizeYMinus.setFont(new Font(menuLetters, Font.BOLD, 26));
        sizeYMinus.setLocation(200, 270);
        sizeYMinus.setSize(50, 50);

        sizeYPlus = new JButton("+");
        sizeYPlus.addActionListener(this);
        sizeYPlus.setBackground(Color.BLACK);
        sizeYPlus.setForeground(Color.WHITE);
        sizeYPlus.setFont(new Font(menuLetters, Font.BOLD, 26));
        sizeYPlus.setLocation(345, 270);
        sizeYPlus.setSize(50, 50);

        // Button to start the game:
        play = new JButton("Start Game");
        play.addActionListener(this);
        play.setBackground(Color.BLUE);
        play.setForeground(Color.WHITE);
        play.setFont(new Font(menuLetters, Font.BOLD, 30));
        play.setLocation(25, 345);
        play.setSize(200, 45);

        // Button to start the game with the bot:
        startBot = new JButton("BOT");
        startBot.addActionListener(this);
        startBot.setBackground(Color.BLUE);
        startBot.setForeground(Color.WHITE);
        startBot.setFont(new Font(menuLetters, Font.BOLD, 30));
        startBot.setLocation(250, 345);
        startBot.setSize(125, 45);

        // Labels:
        // Labels for level:
        level = new JLabel("Level");
        level.setFont(new Font(menuLetters, Font.BOLD, 18));
        level.setForeground(Color.CYAN);
        level.setLocation(20, 50);
        level.setSize(100, 50);

        colon1 = new JLabel(":");
        colon1.setFont(new Font(menuLetters, Font.BOLD, 18));
        colon1.setForeground(Color.CYAN);
        colon1.setLocation(150, 50);
        colon1.setSize(20, 50);

        levelNumber = new JLabel(" " + levelInt);
        levelNumber.setFont(new Font(menuLetters, Font.BOLD, 18));
        levelNumber.setForeground(Color.WHITE);
        levelNumber.setLocation(290, 50);
        levelNumber.setSize(50, 50);

        // Labels for colorblind:
        colorblindmode = new JLabel("Colormode");
        colorblindmode.setFont(new Font(menuLetters, Font.BOLD, 18));
        colorblindmode.setForeground(Color.CYAN);
        colorblindmode.setLocation(20, 105);
        colorblindmode.setSize(100, 50);

        colon2 = new JLabel(":");
        colon2.setFont(new Font(menuLetters, Font.BOLD, 18));
        colon2.setForeground(Color.CYAN);
        colon2.setLocation(150, 105);
        colon2.setSize(20, 50);

        // Labels for name:
        name = new JLabel("Name");
        name.setFont(new Font(menuLetters, Font.BOLD, 18));
        name.setForeground(Color.CYAN);
        name.setLocation(20, 160);
        name.setSize(100, 50);

        colon3 = new JLabel(":");
        colon3.setFont(new Font(menuLetters, Font.BOLD, 18));
        colon3.setForeground(Color.CYAN);
        colon3.setLocation(150, 160);
        colon3.setSize(20, 50);

        // Labels for menu titel:
        menu = new JLabel("MENU");
        menu.setFont(new Font(menuLetters, Font.BOLD, 30));
        menu.setForeground(Color.CYAN);
        menu.setLocation(160, 5);
        menu.setSize(100, 50);

        // Labels for gridsizes
        gridSizes = new JLabel("Gridsize");
        gridSizes.setFont(new Font(menuLetters, Font.BOLD, 18));
        gridSizes.setForeground(Color.CYAN);
        gridSizes.setLocation(20, 215);
        gridSizes.setSize(100, 50);

        x = new JLabel("X");
        x.setFont(new Font(menuLetters, Font.BOLD, 18));
        x.setForeground(Color.CYAN);
        x.setLocation(120, 215);
        x.setSize(100, 50);

        y = new JLabel("Y");
        y.setFont(new Font(menuLetters, Font.BOLD, 18));
        y.setForeground(Color.CYAN);
        y.setLocation(120, 270);
        y.setSize(100, 50);

        sizeX = new JLabel(" " + minSizeX);
        sizeX.setFont(new Font(menuLetters, Font.BOLD, 18));
        sizeX.setForeground(Color.WHITE);
        sizeX.setLocation(290, 215);
        sizeX.setSize(50, 50);

        sizeY = new JLabel("" + minSizeY);
        sizeY.setFont(new Font(menuLetters, Font.BOLD, 18));
        sizeY.setForeground(Color.WHITE);
        sizeY.setLocation(290, 270);
        sizeY.setSize(50, 50);

        colon4 = new JLabel(":");
        colon4.setFont(new Font(menuLetters, Font.BOLD, 18));
        colon4.setForeground(Color.CYAN);
        colon4.setLocation(150, 215);
        colon4.setSize(20, 50);

        colon5 = new JLabel(":");
        colon5.setFont(new Font(menuLetters, Font.BOLD, 18));
        colon5.setForeground(Color.CYAN);
        colon5.setLocation(150, 270);
        colon5.setSize(20, 50);

        // Panel:
        // Menupanel:
        menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createEmptyBorder());
        menuPanel.setLayout(null);
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setPreferredSize(new Dimension(400, 400));

        menuPanel.add(level);
        menuPanel.add(colon1);
        menuPanel.add(buttonMinus);
        menuPanel.add(levelNumber);
        menuPanel.add(buttonPlus);

        menuPanel.add(colorblindmode);
        menuPanel.add(colon2);
        menuPanel.add(buttonColormode);

        menuPanel.add(name);
        menuPanel.add(colon3);
        menuPanel.add(inputName);

        menuPanel.add(sizeX);
        menuPanel.add(sizeY);
        menuPanel.add(sizeXMinus);
        menuPanel.add(sizeXPlus);
        menuPanel.add(sizeYMinus);
        menuPanel.add(sizeYPlus);
        menuPanel.add(gridSizes);
        menuPanel.add(colon4);
        menuPanel.add(colon5);
        menuPanel.add(x);
        menuPanel.add(y);

        menuPanel.add(menu);
        menuPanel.add(closeMenu);
        menuPanel.add(play);
        menuPanel.add(startBot);

        // Frame:
        UI = new JFrame("PENTRIS");
        UI.add(menuPanel);
        UI.setUndecorated(true);
        UI.pack();
        UI.setVisible(true);
        UI.setResizable(false);
        UI.setLocationRelativeTo(null);
        UI.setPreferredSize(new Dimension(400, 400));
        UI.setMinimumSize(new Dimension(400, 400));
        UI.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.CYAN));
    }

    public void actionPerformed(ActionEvent e) {
        // Button press for Level:
        if (e.getSource() == buttonPlus && levelInt == 1) {
            levelInt = 5;
        } else if (e.getSource() == buttonMinus && levelInt == 5) {
            levelInt = 1;
        } else if (e.getSource() == buttonPlus && levelInt < 25) {
            levelInt += 5;
        } else if (e.getSource() == buttonMinus && levelInt > 1) {
            levelInt -= 5;
        }

        if (levelInt >= 10) {
            levelNumber.setText("" + levelInt);
        } else {
            levelNumber.setText(" " + levelInt);
        }

        // Button press for Colorblind:
        if (e.getSource() == buttonColormode && colorblindNormal.equals("Normal")) {
            colorblindNormal = "Colorblind";
            buttonColormode.setText(colorblindNormal);
            isColorBlind = true;
        } else if (e.getSource() == buttonColormode && colorblindNormal.equals("Colorblind")) {
            colorblindNormal = "Normal";
            buttonColormode.setText(colorblindNormal);
            isColorBlind = false;
        }

        // Button press for gridsize:
        if (e.getSource() == sizeXMinus && intSizeX > minSizeX) {
            intSizeX--;
        } else if (e.getSource() == sizeXPlus && intSizeX < maxSizeX) {
            intSizeX++;
        } else if (e.getSource() == sizeYMinus && intSizeY > minSizeY) {
            intSizeY--;
        } else if (e.getSource() == sizeYPlus && intSizeY < maxSizeY) {
            intSizeY++;
        }

        if (intSizeX >= 10 && intSizeY >= 10) {
            sizeX.setText("" + intSizeX);
            sizeY.setText("" + intSizeY);
        } else if (intSizeX >= 10) {
            sizeX.setText("" + intSizeX);
            sizeY.setText(" " + intSizeY);
        } else if (intSizeY >= 10) {
            sizeX.setText(" " + intSizeX);
            sizeY.setText("" + intSizeY);
        } else {
            sizeX.setText(" " + intSizeX);
            sizeY.setText(" " + intSizeY);
        }

        // Button to start the game and close the menu:
        if (e.getSource() == play && !inputName.getText().equals("")
                && !inputName.getText().equals("Enter your name!")) {
            playerName = inputName.getText();
            inputName.setText("");
            showMenu = false;
            // System.out.println("Start Game");
            // System.out.println(playerName);
            UI.dispose();
        } else if (e.getSource() == play && inputName.getText().equals("")) {
            inputName.setText("Enter your name!");
        }

        if (e.getSource() == startBot){
            playerName = "BOT";
            showMenu = false;
            playBot = true;
            UI.dispose();
        }

        // Button for closing the menu:
        if (e.getSource() == closeMenu) {
            showMenu = false;
            System.exit(0);
        }
    }

    public boolean getIsColorblind() {
        return isColorBlind;
    }

    public boolean getShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean x) {
        showMenu = x;
    }

    public String getName() {
        return playerName;
    }

    public int getLevel() {
        return levelInt;
    }

    public int getGridsizeX() {
        return intSizeX;
    }

    public int getGridsizeY() {
        return intSizeY;
    }
    
    public boolean getPlayBot() {
        return playBot;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == inputName) {
            inputName.setText("");
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}