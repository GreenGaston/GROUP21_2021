// package src;

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

public class Menu implements ActionListener{
    JFrame UI;
//////////////////////////////////////////
    JPanel menuPanel;
//////////////////////////////////////////
    JLabel colon1;
    JLabel colorblindmode;

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
    JButton buttonColormode;
    JButton closeMenu;
    JButton continueGame;
//////////////////////////////////////////
    String colorblindNormal = "Normal";
    String menuLetters = "Calibri";
    boolean isColorBlind = false;
    boolean showMenu = false;
    boolean paused = false;
    public static void main(String[] args) {
        // new Menu();
    }

    public Menu(boolean colors){
        isColorBlind = colors;
    // Buttons:
        // ButtonColorMode:
        if (isColorBlind){
            buttonColormode = new JButton("Colorlbind");
        }else{
            buttonColormode = new JButton(colorblindNormal);
        }
        buttonColormode.addActionListener(this);
        buttonColormode.setBackground(Color.BLACK);
        buttonColormode.setForeground(Color.WHITE);
        buttonColormode.setFont(new Font(menuLetters, Font.BOLD, 20));
        buttonColormode.setLocation(200, 60);
        buttonColormode.setSize(195,50);

        // CloseMenu:
        closeMenu = new JButton("Close");
        closeMenu.addActionListener(this);
        closeMenu.setBackground(Color.RED);
        closeMenu.setForeground(Color.WHITE);
        closeMenu.setFont(new Font(menuLetters, Font.PLAIN, 15));
        closeMenu.setLocation(328, 5);
        closeMenu.setSize(67,15);

        // Button to continue the game:
        continueGame = new JButton("Continue");
        continueGame.addActionListener(this);
        continueGame.setBackground(Color.BLUE);
        continueGame.setForeground(Color.WHITE);
        continueGame.setFont(new Font(menuLetters, Font.BOLD, 30));
        continueGame.setLocation(100, 345);
        continueGame.setSize(200,50);


    // Labels:
        // Labels for colorblind:
        colorblindmode = new JLabel("Colormode");
        colorblindmode.setFont(new Font(menuLetters, Font.BOLD, 20));
        colorblindmode.setForeground(Color.WHITE);
        colorblindmode.setLocation(20, 60);
        colorblindmode.setSize(100,50);

        colon1 = new JLabel(":");
        colon1.setFont(new Font(menuLetters, Font.BOLD, 20));
        colon1.setForeground(Color.WHITE);
        colon1.setLocation(150, 60);
        colon1.setSize(20,50);

        // Labels for menu titel:
        menu = new JLabel("MENU");
        menu.setFont(new Font(menuLetters, Font.BOLD, 30));
        menu.setForeground(Color.WHITE);
        menu.setLocation(160,5);
        menu.setSize(100,50);

        // Labels for controls:
        controls = new JLabel("Controls");
        controls.setFont(new Font(menuLetters, Font.BOLD, 25));
        controls.setForeground(Color.WHITE);
        controls.setLocation(157,105);
        controls.setSize(100,50);

        esc = new JLabel("Esc");
        esc.setFont(new Font(menuLetters, Font.BOLD, 20));
        esc.setForeground(Color.WHITE);
        esc.setLocation(127, 130);
        esc.setSize(100,50);

        left = new JLabel("Left");
        left.setFont(new Font(menuLetters, Font.BOLD, 20));
        left.setForeground(Color.WHITE);
        left.setLocation(120, 155);
        left.setSize(100,50);

        right = new JLabel("Right");
        right.setFont(new Font(menuLetters, Font.BOLD, 20));
        right.setForeground(Color.WHITE);
        right.setLocation(110, 180);
        right.setSize(100,50);

        down = new JLabel("Down");
        down.setFont(new Font(menuLetters, Font.BOLD, 20));
        down.setForeground(Color.WHITE);
        down.setLocation(105, 205);
        down.setSize(100,50);

        spacebar = new JLabel("Spacebar");
        spacebar.setFont(new Font(menuLetters, Font.BOLD, 20));
        spacebar.setForeground(Color.WHITE);
        spacebar.setLocation(77, 230);
        spacebar.setSize(100,50);

        up_x = new JLabel("Up / X");
        up_x.setFont(new Font(menuLetters, Font.BOLD, 20));
        up_x.setForeground(Color.WHITE);
        up_x.setLocation(98, 255);
        up_x.setSize(100,50);

        z = new JLabel("Z");
        z.setFont(new Font(menuLetters, Font.BOLD, 20));
        z.setForeground(Color.WHITE);
        z.setLocation(141, 280);
        z.setSize(100,50);

        c = new JLabel("C");
        c.setFont(new Font(menuLetters, Font.BOLD, 20));
        c.setForeground(Color.WHITE);
        c.setLocation(141, 305);
        c.setSize(100,50);
        

        openMenu = new JLabel("Open menu");
        openMenu.setForeground(Color.WHITE);
        openMenu.setFont(new Font(menuLetters, Font.BOLD, 20));
        openMenu.setLocation(250, 130);
        openMenu.setSize(100,50);

        goLeft = new JLabel("Go left");
        goLeft.setForeground(Color.WHITE);
        goLeft.setFont(new Font(menuLetters, Font.BOLD, 20));
        goLeft.setLocation(250, 155);
        goLeft.setSize(100,50);

        goRight = new JLabel("Go right");
        goRight.setForeground(Color.WHITE);
        goRight.setFont(new Font(menuLetters, Font.BOLD, 20));
        goRight.setLocation(250, 180);
        goRight.setSize(100,50);

        softDrop = new JLabel("Soft drop");
        softDrop.setForeground(Color.WHITE);
        softDrop.setFont(new Font(menuLetters, Font.BOLD, 20));
        softDrop.setLocation(250, 205);
        softDrop.setSize(100,50);

        hardDrop = new JLabel("Hard drop");
        hardDrop.setForeground(Color.WHITE);
        hardDrop.setFont(new Font(menuLetters, Font.BOLD, 20));
        hardDrop.setLocation(250, 230);
        hardDrop.setSize(100,50);

        rotateRight = new JLabel("Rotate right");
        rotateRight.setForeground(Color.WHITE);
        rotateRight.setFont(new Font(menuLetters, Font.BOLD, 20));
        rotateRight.setLocation(250, 255);
        rotateRight.setSize(100,50);
        
        rotateLeft = new JLabel("Rotate left");
        rotateLeft.setForeground(Color.WHITE);
        rotateLeft.setFont(new Font(menuLetters, Font.BOLD, 20));
        rotateLeft.setLocation(250, 280);
        rotateLeft.setSize(100,50);

        storePiece = new JLabel("Store/Use piece");
        storePiece.setForeground(Color.WHITE);
        storePiece.setFont(new Font(menuLetters, Font.BOLD, 20));
        storePiece.setLocation(250, 305);
        storePiece.setSize(150,50);


        colon2 = new JLabel(":");
        colon2.setFont(new Font(menuLetters, Font.BOLD, 20));
        colon2.setForeground(Color.WHITE);
        colon2.setLocation(200, 130);
        colon2.setSize(20,50);

        colon3 = new JLabel(":");
        colon3.setFont(new Font(menuLetters, Font.BOLD, 20));
        colon3.setForeground(Color.WHITE);
        colon3.setLocation(200, 155);
        colon3.setSize(20,50);

        colon4 = new JLabel(":");
        colon4.setFont(new Font(menuLetters, Font.BOLD, 20));
        colon4.setForeground(Color.WHITE);
        colon4.setLocation(200, 180);
        colon4.setSize(20,50);

        colon5 = new JLabel(":");
        colon5.setFont(new Font(menuLetters, Font.BOLD, 20));
        colon5.setForeground(Color.WHITE);
        colon5.setLocation(200, 205);
        colon5.setSize(20,50);

        colon6 = new JLabel(":");
        colon6.setFont(new Font(menuLetters, Font.BOLD, 20));
        colon6.setForeground(Color.WHITE);
        colon6.setLocation(200, 230);
        colon6.setSize(20,50);

        colon7 = new JLabel(":");
        colon7.setFont(new Font(menuLetters, Font.BOLD, 20));
        colon7.setForeground(Color.WHITE);
        colon7.setLocation(200, 255);
        colon7.setSize(20,50);

        colon8 = new JLabel(":");
        colon8.setFont(new Font(menuLetters, Font.BOLD, 20));
        colon8.setForeground(Color.WHITE);
        colon8.setLocation(200, 280);
        colon8.setSize(20,50);

        colon9 = new JLabel(":");
        colon9.setFont(new Font(menuLetters, Font.BOLD, 20));
        colon9.setForeground(Color.WHITE);
        colon9.setLocation(200, 305);
        colon9.setSize(20,50);


    // Panel:
        // Menupanel:
        menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createEmptyBorder());
        menuPanel.setLayout(null);
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setPreferredSize(new Dimension(400,400));

        menuPanel.add(colorblindmode);
        menuPanel.add(colon2);
        menuPanel.add(buttonColormode);

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
        menuPanel.add(continueGame);
        

    // Frame:
        UI = new JFrame();
        UI.add(menuPanel);
        UI.setUndecorated(true);
        UI.pack();
        UI.setResizable(false);
        UI.setPreferredSize(new Dimension(400, 400));
        UI.setMinimumSize(new Dimension (400,400));
        UI.setLocationRelativeTo(null);
        UI.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
    }

    public void actionPerformed(ActionEvent e) {
        // Button press for Colorblind:
        if (e.getSource() == buttonColormode && isColorBlind == false){
            colorblindNormal = "Colorblind";
            buttonColormode.setText(colorblindNormal);
            isColorBlind = true;
        }else if(e.getSource() == buttonColormode && isColorBlind == true){
            colorblindNormal = "Normal";
            buttonColormode.setText(colorblindNormal);
            isColorBlind = false;
        }


        // Button to start the game and close the menu:
        if (e.getSource() == continueGame){
            System.out.println("Continue Game");
            showMenu = false;
            paused = false;
            UI.dispose();
        }


        // Button for closing the menu:
        if (e.getSource() == closeMenu){
            System.out.println("Continue Game");
            showMenu = false;
            paused = false;
            UI.dispose();
        }
    }
    
    public boolean getIsColorblind(){
        return isColorBlind;
    }

    public boolean getShowMenu(){
        return showMenu;
    }

    public void setShowMenu(boolean x){
        showMenu = x;
    }

    public void setPaused(boolean x){
        paused = x;
    }

    public boolean getPaused(){
        return paused;
    }

    public void setColorblind(boolean x){
        isColorBlind = x;
    }
}