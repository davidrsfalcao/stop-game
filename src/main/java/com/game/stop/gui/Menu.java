package com.game.stop.gui;


import com.game.stop.cli.LoginRegister;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Window of menu initial
 *
 *
 * @author davidfalcao
 *
 */
public class Menu extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 700;


    private JButton btnLogin;
    private JButton btnLoginFacebook;
    private JButton btnExit;
    private JTextField usernameText;
    private JPasswordField passwordText;

    /**
     * Constructor
     */
    public Menu(){
        setResizable(false);
        setTitle("STOP game");
        pack();
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(this.getClass().getClassLoader().getResource("Menu.jpg"));
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

        getRootPane().setDefaultButton(btnLogin);
        btnLogin.requestFocus();

    }

    /**
     * Initialization of buttons
     *
     */
    private void setUpButtons() {
        // Login
        btnLogin = new JButton("Login/Register");
        btnLogin.setForeground(Color.RED);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {


                if((usernameText.getText() != "") & (usernameText.getForeground() != Color.GRAY)){

                    String res = "";
                    for(char a : passwordText.getPassword()){
                        res+= a;
                    }

                    if((res != "") & (passwordText.getForeground() != Color.GRAY)){
                        LoginRegister logres = new LoginRegister(usernameText.getText(), res);
                    }



                }


            }
        });

        // Login Facebook
        btnLoginFacebook = new JButton("Login Facebook");
        btnLoginFacebook.setForeground(Color.BLUE);

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

        // Username Field
        usernameText = new JTextField();
        usernameText.setHorizontalAlignment(JTextField.CENTER);
        usernameText.setForeground(Color.GRAY);
        usernameText.setText("Username");
        usernameText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameText.getText().equals("Username")) {
                    usernameText.setText("");
                    usernameText.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameText.getText().isEmpty()) {
                    usernameText.setForeground(Color.GRAY);
                    usernameText.setText("Username");
                }
            }
        });

        // Password Field
        passwordText = new JPasswordField();
        passwordText.setEchoChar('*');
        passwordText.setHorizontalAlignment(JTextField.CENTER);
        passwordText.setForeground(Color.GRAY);
        passwordText.setText("Password");
        passwordText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordText.getText().equals("Password")) {
                    passwordText.setText("");
                    passwordText.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordText.getText().isEmpty()) {
                    passwordText.setForeground(Color.GRAY);
                    passwordText.setText("Password");
                }
            }
        });

    }

    /**
     * Display the buttons
     *
     */
    private void addButtons() {
        usernameText.setBounds(400, 240, 224, 50);
        getContentPane().add(usernameText);

        passwordText.setBounds(400, 300, 224, 50);
        getContentPane().add(passwordText);

        btnLogin.setBounds(400, 360, 224, 50);
        getContentPane().add(btnLogin);

        btnLoginFacebook.setBounds(400, 420, 224, 50);
        getContentPane().add(btnLoginFacebook);


        btnExit.setBounds(400, 540, 224, 50);
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
        this.dispose();
    }



}
