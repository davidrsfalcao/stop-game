package gui;


import gui.utils.ImagePanel;
import gui.utils.Resizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Window of menu initial
 *
 *
 * @author davidfalcao
 *
 */
public class Menu extends Page {

    private static final long serialVersionUID = 1L;

    private JButton btnLogin;
    private JButton btnLoginFacebook;
    private JButton btnCreateServer;
    private JButton btnExit;
    private JTextField usernameText;
    private JPasswordField passwordText;

    /**
     * Constructor
     */
    public Menu(){
        super();
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(this.getClass().getResource("res/Menu.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Resizer a = new Resizer();
        myImage = a.resize(myImage, WIDTH*ratio,HEIGHT*ratio);
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
                        //LoginRegister logres = new LoginRegister(usernameText.getText(), res);
                    }

                }


            }
        });

        // Login Facebook
        btnLoginFacebook = new JButton("Login Facebook");
        btnLoginFacebook.setForeground(Color.BLUE);
        btnLoginFacebook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

            }
        });

        // Create Server
        btnCreateServer = new JButton("Create Server");
        btnCreateServer.setForeground(Color.BLACK);
        btnCreateServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                PageManager.getInstance().push_page(new ServerPage());
            }
        });


        // Exit
        btnExit = new JButton("Quit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                PageManager.getInstance().pop_page();
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
        Font font = new Font("Arial", Font.PLAIN, 12*ratio);

        usernameText.setBounds(400*ratio, 240*ratio, 224*ratio, 50*ratio);
        usernameText.setFont(font);
        getContentPane().add(usernameText);

        passwordText.setBounds(400*ratio, 300*ratio, 224*ratio, 50*ratio);
        passwordText.setFont(font);
        getContentPane().add(passwordText);

        btnLogin.setBounds(400*ratio, 360*ratio, 224*ratio, 50*ratio);
        btnLogin.setFont(font);
        getContentPane().add(btnLogin);

        btnLoginFacebook.setBounds(400*ratio, 420*ratio, 224*ratio, 50*ratio);
        btnLoginFacebook.setFont(font);
        getContentPane().add(btnLoginFacebook);

        btnCreateServer.setBounds(400*ratio, 480*ratio, 224*ratio, 50*ratio);
        btnCreateServer.setFont(font);
        getContentPane().add(btnCreateServer);


        btnExit.setBounds(400*ratio, 540*ratio, 224*ratio, 50*ratio);
        btnExit.setFont(font);
        getContentPane().add(btnExit);

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
