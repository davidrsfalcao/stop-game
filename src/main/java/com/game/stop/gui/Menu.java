package com.game.stop.gui;


import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

/**
 * Window of menu initial
 *
 *
 * @author davidfalcao
 *
 */
public class Menu extends JFrame{

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 700;


    private JButton btnNewGame;
    private JButton btnExit;

    /**
     * Constructor
     */
    public Menu(){
        setResizable(false);
        setTitle("STOP game");
        pack();
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(this.getClass().getClassLoader().getResource("Menu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Resizer a = new Resizer();
        myImage = a.resize(myImage, WIDTH,HEIGHT);
        setContentPane(new ImagePanel(myImage));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);


        setUpButtons();
        addButtons();

    }

    /**
     * Initialization of buttons
     *
     */
    private void setUpButtons() {
        // Play Game
        btnNewGame = new JButton("New Game");
        btnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                //options.setVisible(true);
                //options.toFront();

            }
        });

        // Exit
        btnExit = new JButton("Quit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                String msg = "Are you sure you want to quit?";
                int res = JOptionPane.showConfirmDialog(rootPane, msg);

                if (res == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });



    }

    /**
     * Display the buttons
     *
     */
    private void addButtons() {
        btnNewGame.setBounds(400, 300, 224, 50);
        getContentPane().add(btnNewGame);


        btnExit.setBounds(400, 480, 224, 50);
        getContentPane().add(btnExit);


    }

    /**
     * Start the window
     *
     */
    public void start() {
        setSize(WIDTH, HEIGHT);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
                - getSize().height / 2);

        setVisible(true);
    }



    /**
     * Initialization of the game
     *
     */
    public void initGame(){
        setVisible(false);
        //GameFrame game = new GameFrame(new Menu(), guardType, nOgres);
        //game.start();
        this.dispose();
    }



}
