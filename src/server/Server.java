package server;

import listeners.AcceptPeers;
import objects.Room;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Server server;
    private static SSLServerSocket serverSocket = null;
    private static SSLServerSocketFactory factory = null;
    public PrintWriter out;
    public BufferedReader in;
    private static int port;

    public static ConcurrentHashMap<Integer,SSLSocket> peers_id = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<SSLSocket,Integer> peers_socket = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer,Room> rooms = new ConcurrentHashMap<>(); //DAR NOME

    public static String[] ENC_PROTOCOLS = new String[] {"TLSv1.2"};
    public static String[] ENC_CYPHER_SUITES = new String[] {"TLS_DHE_RSA_WITH_AES_128_CBC_SHA"};

    public Server(int port) {

    	String file = this.getClass().getResource("server.keys").getFile();
    	System.setProperty("javax.net.ssl.keyStore", file);
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        Server.port = port;
        Server.factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try {
            Server.serverSocket = (SSLServerSocket) Server.factory.createServerSocket(port);
            Server.serverSocket.setEnabledProtocols(ENC_PROTOCOLS);
            Server.serverSocket.setEnabledCipherSuites(ENC_CYPHER_SUITES);
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

    public static void main(String[] args) throws IOException {

        server = new Server(Integer.parseInt(args[0]));

    	if (!server.validArgs(args)) {
            System.out.println("java Server <Port>");
            return;
        }
    }

    public Boolean validArgs(String[] args) {
        if (args.length != 1) {
            return false;
        }

        return true;
    }

	public static SSLServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(SSLServerSocket serverSocket) {
		Server.serverSocket = serverSocket;
	}

	public static ConcurrentHashMap<Integer,SSLSocket> getPeers() {
		return peers_id;
	}

	public static void setPeers(ConcurrentHashMap<Integer,SSLSocket> peers) {
		Server.peers_id = peers;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		Server.port = port;
	}

}
