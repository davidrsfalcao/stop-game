package listeners;

import objects.Room;
import server.Server;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.charset.Charset;


public class JoinRoom implements Runnable {

  protected String ip;
  protected Server server;
  private int room_id;
  private SSLSocket peer_socket;
  private int peer_id;

  public JoinRoom (Server server) {
	  this.server = server;
	  try {
		this.ip = Inet4Address.getLocalHost().getHostAddress();
	  } catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
  }

  public void run() {

    while (true) {
        System.out.println("JoinRoom Thread Running...");

        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        String message = new String(packet.getData(), Charset.forName("ISO_8859_1"));
		try {
			peer_socket = (SSLSocket) Server.getServerSocket().accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (processMessage(message)) {
        	//TODO: Add peer to room.
        	Room room = this.server.rooms.get(room_id);
        	peer_id = server.peers_socket.get(peer_socket);
        	room.peers_id.put(peer_id,peer_socket);
        	room.peers_socket.put(peer_socket, peer_id);
        	System.out.println("JR: Peer " + this.peer_id + " was added to the room " + this.room_id + ".");
        }

      }
  }

  //checks if message received is valid for CreateRoom
  private boolean processMessage(String message) {
	  System.out.println("JR: Processing message: " + message);
	  String messageType = message.split(" ")[0];
	  String room_id = message.split(" ")[1];

	  //checks type of message received
	  if(!messageType.equals("JOIN")) {
		  return false;
	  }
	  //checks if second argument is an int
	  else if(!isInteger(room_id)) {
		  System.out.println("JR: Max_Players arg not an integer.");
		  return false;
	  }
	  else {
		  this.room_id = Integer.parseInt(room_id);
		  //checks if room exists with room_id
		  if(!this.server.rooms.containsKey(room_id)) {
			  System.out.println("JR: There is not a room with the given id.");
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
