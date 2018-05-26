package cli;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.*;
import java.io.*;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap;

import communication.messages.*;
import communication.responses.*;
import communication.*;

public class Peer {
    private static Peer peer;

    private static String username="";
    private static SSLSocket socket = null;
    private static SSLServerSocket serverSocket = null;
    private static SSLSocketFactory factory = null;
    private PrintWriter out;
    private BufferedReader in;
    private static int port;
    private static String ip;

    private static String room="";
    private static String currLetter="";
    public static ConcurrentHashMap<Integer,SSLSocket> roomPeers = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer,PrintWriter> outPeers = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer,BufferedReader> inPeers = new ConcurrentHashMap<>();
    private static int maxPlayers = 0;
    private static int points=0;
    private static int rond=0;

    public static String[] ENC_PROTOCOLS = new String[] {"TLSv1.2"};
    public static String[] ENC_CYPHER_SUITES = new String[] {"TLS_DHE_RSA_WITH_AES_128_CBC_SHA"};


    public Peer(int port, String ip) throws IOException {

        this.port = port;
        this.ip = ip;

        //File file = new File("sss.keys");
        InputStream file = getClass().getResourceAsStream("trustStore");
        byte[] buffer = new byte[file.available()];
        file.read(buffer);

        File targetFile = new File("ts");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);

        System.setProperty("javax.net.ssl.trustStore", targetFile.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        this.factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try {
            this.socket = (SSLSocket) this.factory.createSocket(this.ip, this.port);
            this.socket.setEnabledProtocols(ENC_PROTOCOLS);
            this.socket.setEnabledCipherSuites(ENC_CYPHER_SUITES);
            //socket.setNeedClientAuth(true);

            this.socket.startHandshake();
            this.socket.addHandshakeCompletedListener(new HandshakeCompletedListener() {
              @Override
              public void handshakeCompleted(HandshakeCompletedEvent event) {
                System.out.println(event);
              }
            });

            System.out.println("Linked to Server!");
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.out.println("Hello from " + ip + ":" + port);

        }

        catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {


        if (args.length != 2) {
          System.out.println("java Peer <ip> <Port>");
          return;
        }

        peer = new Peer(Integer.parseInt(args[0]), args[1]);

        Boolean quitGame = false;
        quitGame = peer.startAutetincation();

        if (quitGame)
          return;

        Boolean temp=true;

        while (temp) {
          System.out.println("Choose what you want to do:");
          System.out.println("0 - quit;");
          System.out.println("1 - See rooms;");
          System.out.println("2 - Create room;");

          Scanner kb = new Scanner(System.in);
          int option = kb.nextInt();

          if (option == 0) {
            temp = false;
          } else if (option == 1) {
            peer.seeRooms();
          } else if (option == 2) {
            peer.createRoom();
          } else {
            System.out.println("Wrong number");
            continue;
          }
        }
    }

//////////////////////////////

    public Boolean startAutetincation() {
      Boolean temp=true;
      while (temp) {
        System.out.println("Choose how you want to be authenticated:");
        System.out.println("0 - quit;");
        System.out.println("1 - Log In;");
        System.out.println("2 - Register;");

        Scanner kb = new Scanner(System.in);
        int option = kb.nextInt();

        if (option == 0) {
          return true;
        } else if (option == 1) {
          temp = logIn();
        } else if (option == 2) {
          temp = register();
        } else {
          System.out.println("Wrong number");
          continue;
        }
      }
      return false;
    }

    public Boolean logIn() {

      System.out.println("-------------//--------------");
      System.out.println("Insert your username: ");
      Scanner kb = new Scanner(System.in);
      String user = kb.nextLine();

      System.out.println("Insert your password: ");
      Scanner kb2 = new Scanner(System.in);
      String pass = kb2.nextLine();

      String logInStr = new LoginMessage(user, pass).toString();
      this.out.println(logInStr);
      /*if (rejected) {
        return true;
      }*/

      this.username = user;
      return false;
    }

    public Boolean register() {

      System.out.println("-------------//--------------");
      System.out.println("Insert your username: ");
      Scanner kb = new Scanner(System.in);
      String user = kb.nextLine();

      System.out.println("Insert your password: ");
      Scanner kb2 = new Scanner(System.in);
      String pass = kb.nextLine();

      String registerStr = new RegisterMessage(user, pass).toString();
      this.out.println(registerStr);
      /*if (rejected) {
        return true;
      }*/

      this.username = user;
      return false;
    }

//////////////

    public void seeRooms() {

      /*String seeRoomsStr = createSeeRoomsMessage();

      //PRINT ROOMS INFORMATION : numbered from 1 to the total of rooms;

      System.out.println("Do you want to join a room?");

      Scanner kb = new Scanner(System.in);
      int option = kb.nextInt();

      Boolean control = true;

      if (option == 0) {
        return;
      } else if (option == 1) {
        this.joinRoom(number);
      } else if (option == 2) {
        this.joinRoom(number);
        ...

      } else {
        System.out.println("Wrong number");
        continue;
      }
      */

    }

    public Boolean joinRoom(String roomId) {
      /*String joinStr = createRegisterMessage(roomId);
      this.out.println(joinStr);
      if (rejected) {
        System.out.println("Room not available");
        return true;
      } else if (ok && yes) {
        join
        ...
      } else if (ok && yes) {
        join
        ...

        this.factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try {
            serverSocket = (SSLServerSocket) Server.factory.createServerSocket(port);
            serverSocket.setEnabledProtocols(ENC_PROTOCOLS);
            serverSocket.setEnabledCipherSuites(ENC_CYPHER_SUITES);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while () {

        }
      }
      */

      return false;
    }

    public void createRoom() {

      System.out.println("-------------//--------------");
      System.out.println("Insert the room name: ");
      Scanner kb = new Scanner(System.in);
      String roomName = kb.nextLine();

      System.out.println("Insert the number of players: ");
      Scanner kb2 = new Scanner(System.in);
      int players = kb2.nextInt();

/*
      String createStr = createMessage(roomNamem, players);
      this.out.println(joinStr);

      this.maxPlayers = players
      int i = 1;

      System.out.println("Accepting peers to join the room...");

      while (i < this.maxPlayers) {
        try {
            SSLSocket client = (SSLSocket) Server.getServerSocket().accept();
            ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
            scheduledPool.schedule(new linkPeer(i, client, this), 0, TimeUnit.MILLISECONDS);
            i++;

        } catch (IOException e) {
            e.printStackTrace();
        }
      }

      TimeUnit.SECONDS.sleep(1);
      */
    }

    public void receiveLetter() {

    }

    public void setAndSendLetter() {

    }
}


class linkPeer implements Runnable {

  private SSLSocket socket;
  private Peer p;
  private int i;

  public linkPeer (int i, SSLSocket socket, Peer p) {
    this.p = p;
    this.socket = socket;
    this.i = i;
  }

  public void run()
  {

    this.p.roomPeers.put(this.i, this.socket);

    try {
      PrintWriter inP = new PrintWriter(this.socket.getOutputStream());
      BufferedReader outP = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

      this.p.outPeers.put(this.i, inP);
      this.p.inPeers.put(this.i, outP);
      System.out.println("Peer " + this.i + " joined.");
    }
    catch(IOException e) {
        e.printStackTrace();
    }
  }
};
