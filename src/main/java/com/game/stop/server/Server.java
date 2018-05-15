package com.game.stop.server;

import javax.net.ssl.*;
import java.io.*;

public class Server {
    private static SSLSocket client;  //cuncurrent hash map!
    private static SSLServerSocket serverSocket = null;
    private static SSLServerSocketFactory factory = null;
    private PrintWriter out;
    private BufferedReader in;
    private static int port;

    public static String[] ENC_PROTOCOLS = new String[] {"TLSv1.2"};
    public static String[] ENC_CYPHER_SUITES = new String[] {"TLS_DHE_RSA_WITH_AES_128_CBC_SHA"};

    public Server(int port) throws IOException {


        String file = this.getClass().getClassLoader().getResource("server.keys").getPath();

        System.setProperty("javax.net.ssl.keyStore", file);
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
//		System.setProperty("javax.net.ssl.trustStore", "../trustStore");
//		System.setProperty("javax.net.ssl.trustStorePassword", "123456");


        this.port = port;


       this.factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try {
            this.serverSocket = (SSLServerSocket) this.factory.createServerSocket(2000);
            this.serverSocket.setEnabledProtocols(ENC_PROTOCOLS);
            this.serverSocket.setEnabledCipherSuites(ENC_CYPHER_SUITES);

            while (true) {

                try {

                    this.client = (SSLSocket) this.serverSocket.accept();
                    //new Thread(new ServerWorker(socket)).start();
                    System.out.println("Server up!");

//			        PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
                    this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
                    System.out.println(this.in.readLine());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
