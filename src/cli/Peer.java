package cli;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;


public class Peer {
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


    public Peer(int port, String ip) throws IOException {

        //File file = new File("sss.keys");
        InputStream file = getClass().getResourceAsStream("trustStore");
        byte[] buffer = new byte[file.available()];
        file.read(buffer);

        File targetFile = new File("ts");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);

        System.setProperty("javax.net.ssl.trustStore", targetFile.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");


        this.port = port;
        this.ip = ip;

        this.linkToServer(port, ip);

        this.factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try {
            this.socket = (SSLSocket) this.factory.createSocket(ip, port);
            this.socket.setEnabledProtocols(ENC_PROTOCOLS);
            this.socket.setEnabledCipherSuites(ENC_CYPHER_SUITES);
//			socket.setNeedClientAuth(true);

            this.socket.startHandshake();

            this.socket.addHandshakeCompletedListener(new HandshakeCompletedListener() {

                @Override
                public void handshakeCompleted(HandshakeCompletedEvent event) {

                    System.out.println(event);


                }
            });

            System.out.println("Server up!");
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.out.println("Hello from " + ip + ":" + port);
            this.out.close();


        }

        catch(IOException e) {

            e.printStackTrace();

        }

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
