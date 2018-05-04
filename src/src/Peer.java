import java.io.*;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.*;
import java.net.*;


public class Peer {
    private static Peer peer;
    private static String username="";
    private static SSLSocket socket = null;
    private static SSLSocketFactory factory = null;
    private PrintWriter out;
    private BufferedReader in;
    private static int port;
    private static String ip;

    private static String room="";
    private static String currLetter="";
    private static int points=0;
    private static int rond=0;

    public static String[] ENC_PROTOCOLS = new String[] {"TLSv1.2"};
    public static String[] ENC_CYPHER_SUITES = new String[] {"TLS_DHE_RSA_WITH_AES_128_CBC_SHA"};

    public static Peer getInstance() {
        return peer;
    }

    public static void main(String[] args) throws IOException {

        System.setProperty("javax.net.ssl.trustStore", "../trustStore");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        peer = new Peer();
        if (!peer.validArgs(args)) {
            System.out.println("java Peer <Port> <Ip address>");
            return;
        }

        peer.port = Integer.parseInt(args[0]);
        peer.ip = args[1];

        peer.linkToServer(Integer.parseInt(args[0]), args[1]);

        peer.factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try {
            peer.socket = (SSLSocket) peer.factory.createSocket(peer.ip, 2000);
            peer.socket.setEnabledProtocols(ENC_PROTOCOLS);
            peer.socket.setEnabledCipherSuites(ENC_CYPHER_SUITES);
//			socket.setNeedClientAuth(true);

            peer.socket.startHandshake();

            peer.socket.addHandshakeCompletedListener(new HandshakeCompletedListener() {

                @Override
                public void handshakeCompleted(HandshakeCompletedEvent event) {

                    System.out.println(event);


                }
            });

            System.out.println("Server up!");
            peer.out = new PrintWriter(peer.socket.getOutputStream(), true);
            peer.out.println("Hello from");
            peer.out.close();


        }

        catch(IOException e) {

            e.printStackTrace();

        }

    }

    public Boolean validArgs(String[] args) {
        if (args.length != 2) {
            return false;
        }

        return true;
    }

    public void linkToServer(int port, String ip) throws IOException {
        //client = (SSLSocket) new Socket(ip, port);
        //out = new PrintWriter(client.getOutputStream(), true);
        //in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void logIn() {

    }

    public void register() {

    }

    public void enterRoom() {

    }

    public void createRoom() {

    }

    public void receiveLetter() {

    }

    public void setAndSendLetter() {

    }
}
