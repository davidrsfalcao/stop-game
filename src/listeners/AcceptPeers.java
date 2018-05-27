package listeners;

import communication.Header;
import communication.messages.*;
import communication.responses.LoginResponse;
import server.Authentication;
import server.Server;

import javax.net.ssl.SSLSocket;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;

public class AcceptPeers implements Runnable {

  protected int nextID;
  protected Server server;
  protected Socket createSock;
  protected BufferedReader br_createSock;
  protected PrintWriter pw_createSock;

  public AcceptPeers (Server server) {
	  this.nextID = 1;
	  this.server = server;
  }

  public void run() {

      /*try{
          Authentication.Initialize();
      } catch (SQLException e){
          e.printStackTrace();
      }*/
    try {
      System.out.println(this.server.ip);
      this.createSock = new Socket(this.server.ip, 5000);
      pw_createSock = new PrintWriter(this.createSock.getOutputStream(), true);
      br_createSock = new BufferedReader(new InputStreamReader(this.createSock.getInputStream()));
    } catch (UnknownHostException e) {
        e.printStackTrace();
    } catch (IOException f) {
        f.printStackTrace();
    }

    System.out.println("Connected to create peers");

    while (true) {
        System.out.println("AcceptPeers Thread Running...");

            try {
                SSLSocket client = (SSLSocket) Server.getServerSocket().accept();
                ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
                scheduledPool.schedule(new StorePeer(nextID, client, this), 0, TimeUnit.MILLISECONDS);
                nextID++;

            } catch (IOException e) {
                e.printStackTrace();
            }
      }
  }
}

class StorePeer implements Runnable {

  private int id;
  private SSLSocket socket;
  private AcceptPeers ap;

  public StorePeer (int id, SSLSocket socket, AcceptPeers ap) {
    this.id = id;
    this.socket = socket;
    this.ap = ap;
  }

  public void run()
  {
    System.out.println("StorePeer Thread started...");
    BufferedReader br;
    PrintWriter pw;

    ap.server.peers_id.put(id, socket);
    ap.server.peers_socket.put(socket,id);
    System.out.println("Peer " + id + " joined.");

    try {
        pw = new PrintWriter(this.socket.getOutputStream(), true);
        br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        System.out.println(br.readLine());

        Server.peers_out.put(id, pw);
        Server.peers_in.put(id, br);


        boolean done = false;
        while(!done) {
    			String received = br.readLine();
    			Message message = Message.parse(received);
    			if(message instanceof ListRoomsMessage) {
    				System.out.println("Received a List Rooms Message");

            System.out.println("ConcurrentHashMap before iterator: "+ ap.server.rooms);

        		Iterator<String> it = ap.server.rooms.keySet().iterator();

        		while(it.hasNext()){
        			String key = it.next();
        			System.out.println(key);
        		}

        		System.out.println("ConcurrentHashMap after iterator: "+ ap.server.rooms);
    			}
    			else if(message instanceof JoinRoomMessage) {
    				System.out.println("Received a Join Room Message");
    			}
    			else if(message instanceof CreateRoomMessage) {
    				System.out.println("Received a Create Room Message");
            ap.pw_createSock.println(received);
            System.out.println("12345");
            String response = ap.br_createSock.readLine();
            System.out.println("123456");
            pw.println(response);
    			}
    			else if(message instanceof LoginMessage) {
              System.out.println("Received a Login Message");

    			    String user = ((LoginMessage) message).getUsername();
    			    String password = ((LoginMessage) message).getPassword();

              String response;

    			    /*if(Authentication.login(user, password)) {
                        System.out.println("User: " + user + " logged in with pass: " + password);
                        String certificate = Authentication.loginSuccessful(user);
                        Server.peers_username.put(id, user);*/
                        response = new LoginResponse(Header.SUCCESS, "certificate").toString();

                    /*}
    			    else {
                        System.out.println("User: " + user + " failed log in with pass: " + password);
                        response = new LoginResponse(Header.FAILURE, "").toString();
                    }*/

                    System.out.println(response);
                    pw.println(response);

    			}   else if(message instanceof RegisterMessage) {
    			    String user = ((RegisterMessage) message).getUsername();
    			    String password = ((RegisterMessage) message).getPassword();

    			    //TODO
    			    //Either request first and last name and email for register OR
                    //Remove those parameters from the USERS table
                    //Both ways are quite easy, let's not worry about it for now and use hardcoded values

                    String firstName = "TEMP";
                    String lastName = "TEMP";
                    String email = "TEMP@TEMP.TEMP";

                    String response;

                    /*if(Authentication.register(user, email, firstName, lastName, password)) {
                        System.out.println("User: " + user + " registered successfully with password: " + password);

                        //Registered successfully, now automatically logs in
                        String certificate = Authentication.loginSuccessful(user);
                        Server.peers_username.put(id, user);*/
                        response = new RegisterMessage(Header.SUCCESS, "certificate").toString();


                  /*  }
                    else {
                        System.out.println("User: " + user + " already exists in the database");
                        response = new RegisterMessage(Header.FAILURE, "").toString();
                    }*/

                    System.out.println(response);
                    pw.println(response);

                }
    			else {
    				System.out.println("Message received not accepted");
    			}
  	    }
      } catch (IOException e) {
  		if(Server.peers_username.containsKey(this.id)) {
  		    String username = Server.peers_username.get(this.id);

  		    if(Authentication.logout(username))
  		        System.out.println("User: " + username + " has logged out.");
  		    else
  		        System.out.println("User: " + username + " was already logged out.");

            Server.peers_username.remove(this.id);
        }
        else
  		    System.out.println("Peer with id: " + id + " has disconnected.");

  	 }
  	 /*catch (SQLException e){
        e.printStackTrace();
     }*/
    }
};
