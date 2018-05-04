import javax.net.ssl.*;
import java.net.*;
import java.io.*;



public class Server {
    private static Server server;
    private static SSLSocket client;  //cuncurrent hash map!
    private static SSLServerSocket serverSocket = null;
    private static SSLServerSocketFactory factory = null;
    private PrintWriter out;
    private BufferedReader in;
    private static int port;

    public static String[] ENC_PROTOCOLS = new String[] {"TLSv1.2"};
    public static String[] ENC_CYPHER_SUITES = new String[] {"TLS_DHE_RSA_WITH_AES_128_CBC_SHA"};

    public static void main(String[] args) throws IOException {

        System.setProperty("javax.net.ssl.keyStore", "../server.keys");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
//		System.setProperty("javax.net.ssl.trustStore", "../trustStore");
//		System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        server = new Server();
        if (!server.validArgs(args)) {
            System.out.println("java Server <Port>");
            return;
        }

        server.port = Integer.parseInt(args[0]);


        server.factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try {
            server.serverSocket = (SSLServerSocket) server.factory.createServerSocket(2000);
            server.serverSocket.setEnabledProtocols(ENC_PROTOCOLS);
            server.serverSocket.setEnabledCipherSuites(ENC_CYPHER_SUITES);

            while (true) {

                try {

                    server.client = (SSLSocket) server.serverSocket.accept();
                    //new Thread(new ServerWorker(socket)).start();
                    System.out.println("Server up!");

                    System.out.println("Server up!");
//			        PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
                    server.in = new BufferedReader(new InputStreamReader(server.client.getInputStream()));
                    System.out.println(server.in.readLine());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean validArgs(String[] args) {
        if (args.length != 1) {
            return false;
        }

        return true;
    }
}
