package listeners;

import communication.messages.*;
import communication.responses.*;
import communication.*;
import server.*;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.sql.*;

public class AcceptPeers implements Runnable {

  protected int nextID;
  protected Server server;

  public AcceptPeers (Server server) {
	  this.nextID = 1;
	  this.server = server;
  }

  public void run() {

  /*try {
     Authentication.connectToDB();
     Authentication.createUsersTable();
   } catch (SQLException e) {
       e.printStackTrace();
   }*/

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
		    pw = new PrintWriter(this.socket.getOutputStream());
        br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		    System.out.println(br.readLine());

        ap.server.peers_out.put(id, pw);
        ap.server.peers_in.put(id, br);

		    boolean done = false;
	      while(!done) {
    			String received = br.readLine();
    			Message message = Message.parse(received);
    			if(message instanceof ListRoomsMessage) {
    				System.out.println("Received a List Rooms Message");
    			}
    			else if(message instanceof JoinRoomMessage) {
    				System.out.println("Received a Join Room Message");
    			}
    			else if(message instanceof CreateRoomMessage) {
    				System.out.println("Received a Create Room Message");
    			} else if(message instanceof LoginMessage) {
    				System.out.println("Received a Login Message");
    			}
    			else {
    				System.out.println("Message received not accepted");
    			}
  	    }
      } catch (IOException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	 }
    }
};
