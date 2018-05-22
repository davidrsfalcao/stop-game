package server;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;


public class Server implements Runnable {
    private static SSLSocket client;  //cuncurrent hash map!
    private static SSLServerSocket serverSocket = null;
    private static SSLServerSocketFactory factory = null;
    private PrintWriter out;
    private BufferedReader in;
    private static int port;
    private Thread thread;

    public static String[] ENC_PROTOCOLS = new String[] {"TLSv1.2"};
    public static String[] ENC_CYPHER_SUITES = new String[] {"TLS_DHE_RSA_WITH_AES_128_CBC_SHA"};

    public Server(int port) throws IOException {

        //File file = new File("sss.keys");
        InputStream file = getClass().getResourceAsStream("server.keys");
        byte[] buffer = new byte[file.available()];
        file.read(buffer);

        File targetFile = new File("sss.keys");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);


        System.setProperty("javax.net.ssl.keyStore", targetFile.getAbsolutePath());
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
//		System.setProperty("javax.net.ssl.trustStore", "../trustStore");
//		System.setProperty("javax.net.ssl.trustStorePassword", "123456");


        this.port = port;


        this.factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

    }

    @Override
    public void run() {
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

    public void start () {
        if (thread == null) {
            thread = new Thread (this, "server");
            thread.start ();
        }
    }
}
