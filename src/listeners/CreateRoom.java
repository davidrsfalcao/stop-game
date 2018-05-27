package listeners;

import communication.messages.CreateRoomMessage;
import communication.messages.Message;
import server.Room;
import server.Server;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class CreateRoom implements Runnable {

  protected Server server;
  private static int port;
  private static PrintWriter pw;
  private static BufferedReader br;
  private int max_players;
  private Inet4Address peer_ip;
  private AtomicInteger nextPort = new AtomicInteger(5000);
  private ServerSocket createSocket;
  private Socket createAccept;

  public CreateRoom (Server server) {
    this.port = this.nextPort.getAndIncrement();
	  this.server = server;
  }

  public void run() {
      System.out.println("CreateRoom Thread Running...");
      try {
        this.createSocket = new ServerSocket(this.port);
        this.createAccept = this.createSocket.accept();
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("Connected to accept peers");

      try {
        this.pw = new PrintWriter(this.createAccept.getOutputStream(), true);
        this.br = new BufferedReader(new InputStreamReader(this.createAccept.getInputStream()));

        boolean done = false;
        while (!done) {

            String message = br.readLine();
            Message received_message = Message.parse(message);

            String room_name = ((CreateRoomMessage) received_message).getRoomName();

            Inet4Address ip = (Inet4Address) Server.ip;

            Room new_room = new Room(ip, this.nextPort.getAndIncrement(), this.max_players, ((CreateRoomMessage) received_message).getRoomName());

          	//Calls StoreRoom to store room on server's hashmap
          	ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
          	scheduledPool.schedule(new StoreRoom(room_name, this, new_room), 0, TimeUnit.MILLISECONDS);
          	this.nextPort.getAndIncrement();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

  }
}

class StoreRoom implements Runnable {

  private String name;
  private SSLSocket socket;
  private CreateRoom cr;
  private Room room;

  public StoreRoom(String name, CreateRoom cr, Room room) {
    this.name = name;
    this.cr = cr;
    this.room = room;
  }

  public void run()
  {
    System.out.println("StoreRoom Thread started...");

    try {
  		PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
  		System.out.println(cr.server.in.readLine());
  	} catch (IOException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}

    //cr.server.rooms.put(id, room);
    System.out.println("Room " + this.name + " created and stored.");
  }
};
