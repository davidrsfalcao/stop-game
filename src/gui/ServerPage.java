package gui;

import gui.utils.ImagePanel;
import gui.utils.Resizer;
import server.Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerPage extends Page {
    private JButton btnExit;
    private JTextField serverMessage;


    private Server server;
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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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

        serverMessage = new JTextField();
        serverMessage.setHorizontalAlignment(JTextField.CENTER);
        serverMessage.setForeground(Color.WHITE);
        serverMessage.setBackground(Color.BLACK);
        InetAddress IP= null;
        try {
            IP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        serverMessage.setEditable(false);
        serverMessage.setText("Serving at " + IP.getHostAddress());


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


        Font font1 = new Font("Arial", Font.PLAIN, 30*ratio);
        serverMessage.setBounds(300*ratio, 240*ratio, 424*ratio, 100*ratio);
        serverMessage.setFont(font1);
        getContentPane().add(serverMessage);


    }

    @Override
    public void start(){
        setSize(WIDTH*ratio, HEIGHT*ratio);

        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
                - getSize().height / 2);

        setVisible(true);
        try {
            server = new Server(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();
    }
}
