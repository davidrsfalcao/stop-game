package listeners;

import objects.Room;
import server.Server;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class CreateRoom implements Runnable {
 
  protected int nextID;
  protected int nextPort;
  protected String ip;
  protected Server server;
  private int max_players;
  private Inet4Address peer_ip;
  
  public CreateRoom () {
	  this.nextID = 1;
	  this.nextPort = 5001;
	  this.server = Server.getInstance();
	  try {
		this.ip = Inet4Address.getLocalHost().getHostAddress();
	  } catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
  }

  public void run() {
      System.out.println("CreateRoom Thread Running...");
      byte[] buf = new byte[256];
      SSLServerSocket socket = server.getServerSocket();
    while (true) {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
       
        String message = new String(packet.getData(), Charset.forName("ISO_8859_1"));
        this.peer_ip = (Inet4Address) packet.getAddress();
        
        if (processMessage(message)) {
            Room new_room = new Room(this.peer_ip, this.nextPort, this.max_players);
			
			//Calls StoreRoom to store room on server's hashmap
			ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
			scheduledPool.schedule(new StoreRoom(nextID, this, new_room), 0, TimeUnit.MILLISECONDS);
			nextID++;
			nextPort++;
        }
        
      }
  }
  
  //checks if message received is valid for CreateRoom
  private boolean processMessage(String message) {
	  System.out.println("CR: Processing message: " + message);
	  String messageType = message.split(" ")[0];
	  String max_players = message.split(" ")[1];
	  
	  //checks type of message received
	  if(!messageType.equals("CREATE")) {
		  System.out.println("CR: not me");
		  return false;
	  }
	  //checks if second argument is an int
	  else if(!isInteger(max_players)) {
		  System.out.println("CR: Max_Players arg not an integer.");
		  return false;
	  }
	  else {
		  this.max_players = Integer.parseInt(max_players);
		  //checks if max_players is inside acceptable range
		  if(this.max_players > 5 || this.max_players < 2) { 
			  System.out.println("CR: 2 <= Max_Players => 5 not verified.");
			  return false;
		  }
	  }
	  return true;
  }
  
  //returns true if argument can be parsed to integer
  public static boolean isInteger(String string) {
	    try {
	        Integer.valueOf(string);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
}

class StoreRoom implements Runnable {

  private int id;
  private SSLSocket socket;
  private CreateRoom cr;
  private Room room;

  public StoreRoom(int id, CreateRoom cr, Room room) {
    this.id = id;
    this.cr = cr;
    this.room = room;
  }

  public void run()
  {
    System.out.println("StoreRoom Thread started...");
    
    try {
		PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		System.out.println(cr.server.in.readLine());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    cr.server.rooms.put(id, room);
    System.out.println("Room " + id + " created and stored.");
  }
};