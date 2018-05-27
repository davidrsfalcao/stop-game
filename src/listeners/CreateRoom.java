package listeners;

import objects.Room;
import server.Server;
import communication.Header;
import communication.messages.*;
import communication.responses.*;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.net.*;
import java.io.IOException;
import java.io.*;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
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

            Room new_room = new Room(this.server.ip, this.nextPort.getAndIncrement(), this.max_players, ((CreateRoomMessage) received_message).getRoomName());

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
