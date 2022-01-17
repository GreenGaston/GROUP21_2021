package com.example.Application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class endMenu extends JFrame implements ActionListener {
    JFrame UI;
    //////////////////////////////////////////
    JPanel menuPanel;
    //////////////////////////////////////////
    JLabel controls;
    JLabel esc;
    JLabel up_x;
    JLabel down;
    JLabel left;
    JLabel right;
    JLabel spacebar;
    JLabel c;
    JLabel z;

    JLabel openMenu;
    JLabel goRight;
    JLabel goLeft;
    JLabel softDrop;
    JLabel hardDrop;
    JLabel storePiece;
    JLabel rotateRight;
    JLabel rotateLeft;

    JLabel colon2;
    JLabel colon3;
    JLabel colon4;
    JLabel colon5;
    JLabel colon6;
    JLabel colon7;
    JLabel colon8;
    JLabel colon9;

    JLabel menu;
    //////////////////////////////////////////
    JButton closeMenu;
    JButton restartGame;
    //////////////////////////////////////////
    String menuLetters = "Nidus Sans";
    boolean showMenu = false;
    boolean lost = false;

    public static void main(String[] args) {
        new endMenu();
    }

    public endMenu() {
        // Buttons:
        // CloseMenu:
        closeMenu = new JButton("Close");
        closeMenu.addActionListener(this);
        closeMenu.setBackground(Color.RED);
        closeMenu.setForeground(Color.WHITE);
        closeMenu.setFont(new Font(menuLetters, Font.PLAIN, 13));
        closeMenu.setLocation(325, 5);
        closeMenu.setSize(67, 15);

        // Button to continue the game:
        restartGame = new JButton("Restart");
        restartGame.addActionListener(this);
        restartGame.setBackground(Color.BLUE);
        restartGame.setForeground(Color.WHITE);
        restartGame.setFont(new Font(menuLetters, Font.BOLD, 30));
        restartGame.setLocation(100, 345);
        restartGame.setSize(200, 45);

        // Labels:
        // Labels for menu titel:
        menu = new JLabel("MENU");
        menu.setFont(new Font(menuLetters, Font.BOLD, 30));
        menu.setForeground(Color.CYAN);
        menu.setLocation(160, 5);
        menu.setSize(100, 50);

        // Labels for controls:
        controls = new JLabel("Controls");
        controls.setFont(new Font(menuLetters, Font.BOLD, 24));
        controls.setForeground(Color.CYAN);
        controls.setLocation(157, 75);
        controls.setSize(100, 50);

        esc = new JLabel("Esc");
        esc.setFont(new Font(menuLetters, Font.BOLD, 17));
        esc.setForeground(Color.CYAN);
        esc.setLocation(127, 100);
        esc.setSize(100, 50);

        left = new JLabel("Left");
        left.setFont(new Font(menuLetters, Font.BOLD, 17));
        left.setForeground(Color.CYAN);
        left.setLocation(120, 125);
        left.setSize(100, 50);

        right = new JLabel("Right");
        right.setFont(new Font(menuLetters, Font.BOLD, 17));
        right.setForeground(Color.CYAN);
        right.setLocation(110, 150);
        right.setSize(100, 50);

        down = new JLabel("Down");
        down.setFont(new Font(menuLetters, Font.BOLD, 17));
        down.setForeground(Color.CYAN);
        down.setLocation(105, 175);
        down.setSize(100, 50);

        spacebar = new JLabel("Spacebar");
        spacebar.setFont(new Font(menuLetters, Font.BOLD, 17));
        spacebar.setForeground(Color.CYAN);
        spacebar.setLocation(77, 200);
        spacebar.setSize(100, 50);

        up_x = new JLabel("Up / X");
        up_x.setFont(new Font(menuLetters, Font.BOLD, 17));
        up_x.setForeground(Color.CYAN);
        up_x.setLocation(98, 225);
        up_x.setSize(100, 50);

        z = new JLabel("Z");
        z.setFont(new Font(menuLetters, Font.BOLD, 17));
        z.setForeground(Color.CYAN);
        z.setLocation(141, 250);
        z.setSize(100, 50);

        c = new JLabel("C");
        c.setFont(new Font(menuLetters, Font.BOLD, 17));
        c.setForeground(Color.CYAN);
        c.setLocation(141, 275);
        c.setSize(100, 50);

        openMenu = new JLabel("Open menu");
        openMenu.setForeground(Color.WHITE);
        openMenu.setFont(new Font(menuLetters, Font.BOLD, 17));
        openMenu.setLocation(250, 100);
        openMenu.setSize(100, 50);

        goLeft = new JLabel("Go left");
        goLeft.setForeground(Color.WHITE);
        goLeft.setFont(new Font(menuLetters, Font.BOLD, 17));
        goLeft.setLocation(250, 125);
        goLeft.setSize(100, 50);

        goRight = new JLabel("Go right");
        goRight.setForeground(Color.WHITE);
        goRight.setFont(new Font(menuLetters, Font.BOLD, 17));
        goRight.setLocation(250, 150);
        goRight.setSize(100, 50);

        softDrop = new JLabel("Soft drop");
        softDrop.setForeground(Color.WHITE);
        softDrop.setFont(new Font(menuLetters, Font.BOLD, 17));
        softDrop.setLocation(250, 175);
        softDrop.setSize(100, 50);

        hardDrop = new JLabel("Hard drop");
        hardDrop.setForeground(Color.WHITE);
        hardDrop.setFont(new Font(menuLetters, Font.BOLD, 17));
        hardDrop.setLocation(250, 200);
        hardDrop.setSize(100, 50);

        rotateRight = new JLabel("Rotate right");
        rotateRight.setForeground(Color.WHITE);
        rotateRight.setFont(new Font(menuLetters, Font.BOLD, 17));
        rotateRight.setLocation(250, 225);
        rotateRight.setSize(100, 50);

        rotateLeft = new JLabel("Rotate left");
        rotateLeft.setForeground(Color.WHITE);
        rotateLeft.setFont(new Font(menuLetters, Font.BOLD, 17));
        rotateLeft.setLocation(250, 250);
        rotateLeft.setSize(100, 50);

        storePiece = new JLabel("Store/Use piece");
        storePiece.setForeground(Color.WHITE);
        storePiece.setFont(new Font(menuLetters, Font.BOLD, 17));
        storePiece.setLocation(250, 275);
        storePiece.setSize(150, 50);

        colon2 = new JLabel(":");
        colon2.setFont(new Font(menuLetters, Font.BOLD, 17));
        colon2.setForeground(Color.CYAN);
        colon2.setLocation(200, 100);
        colon2.setSize(20, 50);

        colon3 = new JLabel(":");
        colon3.setFont(new Font(menuLetters, Font.BOLD, 17));
        colon3.setForeground(Color.CYAN);
        colon3.setLocation(200, 125);
        colon3.setSize(20, 50);

        colon4 = new JLabel(":");
        colon4.setFont(new Font(menuLetters, Font.BOLD, 17));
        colon4.setForeground(Color.CYAN);
        colon4.setLocation(200, 150);
        colon4.setSize(20, 50);

        colon5 = new JLabel(":");
        colon5.setFont(new Font(menuLetters, Font.BOLD, 17));
        colon5.setForeground(Color.CYAN);
        colon5.setLocation(200, 175);
        colon5.setSize(20, 50);

        colon6 = new JLabel(":");
        colon6.setFont(new Font(menuLetters, Font.BOLD, 17));
        colon6.setForeground(Color.CYAN);
        colon6.setLocation(200, 200);
        colon6.setSize(20, 50);

        colon7 = new JLabel(":");
        colon7.setFont(new Font(menuLetters, Font.BOLD, 17));
        colon7.setForeground(Color.CYAN);
        colon7.setLocation(200, 225);
        colon7.setSize(20, 50);

        colon8 = new JLabel(":");
        colon8.setFont(new Font(menuLetters, Font.BOLD, 17));
        colon8.setForeground(Color.CYAN);
        colon8.setLocation(200, 250);
        colon8.setSize(20, 50);

        colon9 = new JLabel(":");
        colon9.setFont(new Font(menuLetters, Font.BOLD, 17));
        colon9.setForeground(Color.CYAN);
        colon9.setLocation(200, 275);
        colon9.setSize(20, 50);

        // Panel:
        // Menupanel:
        menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createEmptyBorder());
        menuPanel.setLayout(null);
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setPreferredSize(new Dimension(400, 400));

        menuPanel.add(controls);
        menuPanel.add(esc);
        menuPanel.add(openMenu);
        menuPanel.add(left);
        menuPanel.add(right);
        menuPanel.add(goRight);
        menuPanel.add(goLeft);
        menuPanel.add(rotateRight);
        menuPanel.add(rotateLeft);
        menuPanel.add(up_x);
        menuPanel.add(down);
        menuPanel.add(spacebar);
        menuPanel.add(softDrop);
        menuPanel.add(hardDrop);
        menuPanel.add(storePiece);
        menuPanel.add(z);
        menuPanel.add(c);
        menuPanel.add(colon2);
        menuPanel.add(colon3);
        menuPanel.add(colon4);
        menuPanel.add(colon5);
        menuPanel.add(colon6);
        menuPanel.add(colon7);
        menuPanel.add(colon8);
        menuPanel.add(colon9);

        menuPanel.add(menu);
        menuPanel.add(closeMenu);
        menuPanel.add(restartGame);

        // Frame:
        UI = new JFrame();
        UI.add(menuPanel);
        UI.setUndecorated(true);
        UI.pack();
        UI.setResizable(false);
        UI.setPreferredSize(new Dimension(400, 400));
        UI.setMinimumSize(new Dimension(400, 400));
        UI.setLocationRelativeTo(null);
        UI.setVisible(true);
        UI.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.CYAN));
    }

    public void actionPerformed(ActionEvent e) {
        // Button to start the game and close the menu:
        if (e.getSource() == restartGame) {
            // System.out.println("Restart Game");
            showMenu = false;
            lost = false;
            UI.dispose();
        }

        // Button for closing the menu:
        if (e.getSource() == closeMenu) {
            // System.out.println("Exit Game");
            System.exit(0);
        }
    }

    public boolean getShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean x) {
        showMenu = x;
    }

    public void setLost(boolean x) {
        lost = x;
    }

    public boolean getLost() {
        return lost;
    }
}