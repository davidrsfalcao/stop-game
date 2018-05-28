package cli;
import communication.Header;
import communication.messages.*;
import communication.responses.*;

import javax.net.ssl.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.*;

public class Peer {
    private static Peer peer;

    private static String username="";
    private static SSLSocket socket = null;
    private static ServerSocket serverSocket = null;
    private static SSLSocketFactory factory = null;
    private PrintWriter out;
    private BufferedReader in;
    private static int port;
    private static String ip;

    private static String room="";
    private static String currLetter="";
    private static int roomPort;
    private Socket roomSocket;
    private PrintWriter outRoom;
    private BufferedReader inRoom;
    private static int maxPlayers = 0;
    private static int points=0;
    private static int rond=0;
    public String[] answer;

    public static String[] ENC_PROTOCOLS = new String[] {"TLSv1.2"};
    public static String[] ENC_CYPHER_SUITES = new String[] {"TLS_DHE_RSA_WITH_AES_128_CBC_SHA"};


    public Peer(int port, String ip) throws IOException {

        this.port = port;
        this.ip = ip;

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
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

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
            peer.playGame();
          } else {
            System.out.println("Wrong number");
            continue;
          }
        }
    }

//////////////////////////////

    public Boolean startAutetincation() throws IOException{
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

    public Boolean logIn() throws IOException{

      System.out.println("-------------//--------------");
      System.out.println("Insert your username: ");
      Scanner kb = new Scanner(System.in);
      String user = kb.nextLine();

      System.out.println("Insert your password: ");
      Scanner kb2 = new Scanner(System.in);
      String pass = kb2.nextLine();

      String logInStr = new LoginMessage(user, pass).toString();
      this.out.println(logInStr);

      String messageString = this.in.readLine();

      LoginResponse response = (LoginResponse) Response.parse(messageString);

      String result = response.getResult();


      if (result.equals(Header.FAILURE)) {
          System.out.println("Incorrect username/password combination");
          return true;
      }

      this.username = user;
      return false;
    }

    public Boolean register() throws IOException{

      System.out.println("-------------//--------------");
      System.out.println("Insert your username: ");
      Scanner kb = new Scanner(System.in);
      String user = kb.nextLine();

      System.out.println("Insert your password: ");
      Scanner kb2 = new Scanner(System.in);
      String pass = kb.nextLine();

      String registerStr = new RegisterMessage(user, pass).toString();
      this.out.println(registerStr);

      String messageString = this.in.readLine();

      RegisterResponse response = (RegisterResponse) Response.parse(messageString);

      String result = response.getResult();

      if (result.equals(Header.FAILURE)) {
          System.out.println("Username already in use");
          return true;
      }

        this.username = user;
        return false;
    }

//////////////

    public void seeRooms() throws IOException {

      System.out.println("-------------//--------------");

      String seeRoomsStr = new ListRoomsMessage().toString();
      this.out.println(seeRoomsStr);

      String responseString = this.in.readLine();
      System.out.println(responseString);
      String[] response2 = responseString.split(",");

      if (response2[0].equals(Header.FAILURE)) {
          System.out.println("No rooms available!");
          return;
      }

      System.out.println("Do you want to join a room? (0 to quit)");

      for (int i = 0; i < ( response2.length - 1 ); i++) {
        int j = i + 1;
        System.out.println(j + " - " + response2[j]);
      }

      Scanner kb = new Scanner(System.in);
      int option = kb.nextInt();

      if ( (option < 1) || (option > (response2.length - 1) ) )
        return;

      if (joinRoom(response2[option]))
        playGame();
    }

    public Boolean joinRoom(String roomName) throws IOException {
      String joinStr = new JoinRoomMessage(roomName).toString();
      this.out.println(joinStr);

      String responseString = this.in.readLine();
      System.out.println(responseString);
      JoinRoomResponse response = (JoinRoomResponse) Response.parse(responseString);

      if (response.getResult().equals(Header.FAILURE)) {
        System.out.println("Room not available");
        return false;
      }

      this.roomPort = response.getPort();
      System.out.println(this.ip);
      System.out.println(this.roomPort);
      this.roomSocket = new Socket(this.ip, this.roomPort);

      System.out.println("Joined room" + roomName + "!");

      return true;
      }

    public void createRoom() {

        System.out.println("-------------//--------------");
        System.out.println("Insert the room name: ");
        Scanner kb = new Scanner(System.in);
        String roomName = kb.nextLine();

        int players = 0;
        while ( (players < 2) || (players > 4) ) {
          System.out.println("Insert the number of players (2,3 or 4): ");
          Scanner kb2 = new Scanner(System.in);
          players = kb2.nextInt();
          if ( (players < 2) && (players > 4) )
            System.out.println("Wrong number");
        }


        String createStr = new CreateRoomMessage(roomName, players).toString();
        this.out.println(createStr);


        try {
          String responseString = this.in.readLine();

          CreateRoomResponse response = (CreateRoomResponse) Response.parse(responseString);
          System.out.println(response);

          if (response.getResult().equals(Header.FAILURE)) {
              System.out.println("Room not valid!");
              return;
          }

          this.room = roomName;
          this.roomPort = response.getPort();
          System.out.println(this.ip);
          System.out.println(this.roomPort);
          this.roomSocket = new Socket(this.ip, this.roomPort);

          System.out.println("Joined room " + room + "!");

        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      public void playGame() throws IOException {

        ExecutorService roundExecutor = Executors.newSingleThreadExecutor();
    	  Future<String> future;

        this.inRoom = new BufferedReader(new InputStreamReader(this.roomSocket.getInputStream()));
        this.outRoom = new PrintWriter(this.roomSocket.getOutputStream(), true);

        System.out.println();
        System.out.println("-------------//--------------");
        System.out.println("Waiting for the game to start...");

        Boolean done = false;
        while(!done) {
          String round = this.inRoom.readLine();
          System.out.println("Round " + round + " starts!");

          String letter = this.inRoom.readLine();

          System.out.println("The letter is: " + letter);

          peer.answer = new String[6];

          future = roundExecutor.submit(new Round(peer, letter));
          try {
              System.out.println("Started..");
              System.out.println(future.get(15, TimeUnit.SECONDS));
              System.out.println("Finished!");
          } catch (TimeoutException e) {
              future.cancel(true);
              System.out.println("Terminated!");
          } catch (InterruptedException e) {
      			e.printStackTrace();
      		} catch (ExecutionException e) {
      			e.printStackTrace();
      		}

          //roundExecutor.shutdownNow();

          String answer2 = "";
          for (int k = 0; k < peer.answer.length; k++) {
            if (k == 5)
              answer2 = answer2 + peer.answer[k];
            else
              answer2 = answer2 + peer.answer[k] + ",";
          }

          System.out.println(answer2);
          this.outRoom.println(answer2);

          String score = this.inRoom.readLine();
          System.out.println("Round " + round + " score: " + score);

          if (round.equals("2"))
            break;

        }

        String result = this.inRoom.readLine();
        System.out.println("You " + result + " the game!");
      }
  }

  class Round implements Callable<String> {

    private Peer peer;
    private String letter;

    public Round(Peer peer, String letter) {
      this.peer = peer;
      this.letter = letter;
    }

  	@Override
  	public String call() throws Exception {
  		while(!Thread.interrupted()) {
        System.out.println("Insert a Name with the letter: " + letter);
        Scanner kb = new Scanner(System.in);
        String temp = kb.nextLine();
        peer.answer[0] = temp;

        System.out.println("Insert a Capital with the letter: " + letter);
        kb = new Scanner(System.in);
        temp = kb.nextLine();
        peer.answer[1] = temp;

        System.out.println("Insert a Countries with the letter: " + letter);
        kb = new Scanner(System.in);
        temp = kb.nextLine();
        peer.answer[2] = temp;

        System.out.println("Insert a TV Shows with the letter: " + letter);
        kb = new Scanner(System.in);
        temp = kb.nextLine();
        peer.answer[3] = temp;

        System.out.println("Insert a Animals with the letter: " + letter);
        kb = new Scanner(System.in);
        temp = kb.nextLine();
        peer.answer[4] = temp;

        System.out.println("Insert a Brands with the letter: " + letter);
        kb = new Scanner(System.in);
        temp = kb.nextLine();
        peer.answer[5] = temp;
  		}
  		// TODO Auto-generated method stub
  		return null;
  	}
}
