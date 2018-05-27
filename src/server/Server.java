package server;

import listeners.AcceptPeers;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable {
    private SSLServerSocket serverSocket = null;
    private SSLServerSocketFactory factory = null;
    public PrintWriter out;
    public BufferedReader in;
    public static int port;
    private Thread server;
    private static Server instance;

    public static ConcurrentHashMap<Integer, String> peers_username = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer,SSLSocket> peers_id = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<SSLSocket,Integer> peers_socket = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer,PrintWriter> peers_out = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer,BufferedReader> peers_in = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer,Room> rooms = new ConcurrentHashMap<>(); //DAR NOME

    public static String[] ENC_PROTOCOLS = new String[] {"TLSv1.2"};
    public static String[] ENC_CYPHER_SUITES = new String[] {"TLS_DHE_RSA_WITH_AES_128_CBC_SHA"};

    private Server(int port) throws IOException {

        InputStream file = getClass().getResourceAsStream("server.keys");
        byte[] buffer = new byte[file.available()];
        file.read(buffer);

        File targetFile = new File("ss.keys");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);

    	System.setProperty("javax.net.ssl.keyStore", targetFile.getAbsolutePath());
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        port = port;
        factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

    }


    public Boolean validArgs(String[] args) {
        if (args.length != 1) {
            return false;
        }

        return true;
    }

	public SSLServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(SSLServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public ConcurrentHashMap<Integer,SSLSocket> getPeers() {
		return peers_id;
	}

	public void setPeers(ConcurrentHashMap<Integer,SSLSocket> peers) {
		this.peers_id = peers;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

    @Override
    public void run() {
        try {
            serverSocket = (SSLServerSocket) factory.createServerSocket(port);
            serverSocket.setEnabledProtocols(ENC_PROTOCOLS);
            serverSocket.setEnabledCipherSuites(ENC_CYPHER_SUITES);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AcceptPeers accept_thread = new AcceptPeers(this);
        new Thread(accept_thread).start();
        /*CreateRoom create_thread = new CreateRoom();
        	new Thread(create_thread).start();
        JoinRoom join_thread = new JoinRoom();
        	new Thread(join_thread).start();
        ShowRooms show_thread = new ShowRooms();
        	new Thread(show_thread).start();*/

    }

    public void start () {
        if (server == null) {
            server = new Thread (this, "server-game-stop");
            server.start ();
        }
    }

    public static Server getInstance() {
        if(instance == null) {
            try {
                instance = new Server(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
