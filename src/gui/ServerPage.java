package gui;

import gui.utils.ImagePanel;
import gui.utils.Resizer;
import server.Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerPage extends Page {
    private JButton btnCreateServer;
    private JButton btnExit;
    private JTextField serverMessage;
    private JTextField portText;
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

        getRootPane().setDefaultButton(btnCreateServer);
        btnCreateServer.requestFocus();


    }

    /**
     * Initialization of buttons
     *
     */
    private void setUpButtons() {

        // Create Server
        btnCreateServer = new JButton("Create Server");
        btnCreateServer.setForeground(Color.BLACK);
        btnCreateServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                int port;
                try {
                    port = Integer.parseInt(portText.getText());
                } catch (NumberFormatException e) {
                    return;
                }

                btnCreateServer.setVisible(false);
                portText.setVisible(false);

                String message = serverMessage.getText()+":"+port;
                serverMessage.setText(message);
                serverMessage.setVisible(true);
                //Server.port = port;
                //server = Server.getInstance();

                //server.start();

            }
        });

        // Exit
        btnExit = new JButton("Quit");
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

        // Port Field
        portText = new JTextField();
        portText.setHorizontalAlignment(JTextField.CENTER);
        portText.setForeground(Color.GRAY);
        portText.setText("port");
        portText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (portText.getText().equals("port")) {
                    portText.setText("");
                    portText.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (portText.getText().isEmpty()) {
                    portText.setForeground(Color.GRAY);
                    portText.setText("port");
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

        btnExit.setBounds(400*ratio, 540*ratio, 224*ratio, 50*ratio);
        btnExit.setFont(font);
        getContentPane().add(btnExit);

        Font font1 = new Font("Arial", Font.PLAIN, 30*ratio);
        serverMessage.setBounds(250*ratio, 240*ratio, 524*ratio, 100*ratio);
        serverMessage.setFont(font1);
        getContentPane().add(serverMessage);
        serverMessage.setVisible(false);

        btnCreateServer.setBounds(400*ratio, 300*ratio, 224*ratio, 50*ratio);
        btnCreateServer.setFont(font);
        getContentPane().add(btnCreateServer);

        portText.setBounds(400*ratio, 240*ratio, 224*ratio, 50*ratio);
        portText.setFont(font);
        getContentPane().add(portText);

    }


    @Override
    public void dispose(){

    }
}
