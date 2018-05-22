package gui;

import gui.utils.ImagePanel;
import gui.utils.Resizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ServerPage extends Page {
    private JButton btnExit;

    /**
     * Constructor
     */
    public ServerPage(){
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


    }

    /**
     * Initialization of buttons
     *
     */
    private void setUpButtons() {

        // Exit
        btnExit = new JButton("Back");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                String msg = "If you go back you close the server. Are you sure?";
                int res = JOptionPane.showConfirmDialog(rootPane, msg);

                if (res == JOptionPane.YES_OPTION)
                    PageManager.getInstance().pop_page();
            }
        });

    }


    /**
     * Display the buttons
     *
     */
    private void addButtons() {
        Font font = new Font("Arial", Font.PLAIN, 12*ratio);

        btnExit.setBounds(400*ratio, 540*ratio, 224*ratio, 50*ratio);
        btnExit.setFont(font);
        getContentPane().add(btnExit);

    }
}
