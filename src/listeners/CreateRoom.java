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
import java.util.Iterator;
import java.util.Random;


public class CreateRoom implements Runnable {

  protected Server server;
  private static int port;
  public static PrintWriter pw;
  public static BufferedReader br;
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

        while (true) {

            String message = br.readLine();
            Message received_message = Message.parse(message);

            String room_name = ((CreateRoomMessage) received_message).getRoomName();

            Room new_room = new Room(this.server.ip, this.nextPort.getAndIncrement(), ((CreateRoomMessage) received_message).getMaxPlayers(), ((CreateRoomMessage) received_message).getRoomName());

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
  private int nextID;
  private ServerSocket roomServer;
  private BufferedReader[] ins;
  private PrintWriter[] outs;
  private String letters = "qertuiopasdfghjlzxcvbnm";
  private int round;

  public StoreRoom(String name, CreateRoom cr, Room room) {
    this.name = name;
    this.cr = cr;
    this.room = room;
    this.nextID = 1;
    this.ins = new BufferedReader[room.getMaxPlayers()];
    this.outs = new PrintWriter[room.getMaxPlayers()];
    this.round = 1;
  }

  public void run()
  {
    System.out.println("StoreRoom Thread started...");

    String response = new CreateRoomResponse("SUCCESS", room.getPort()).toString();
  	cr.pw.println(response);

    System.out.println("Pass.");

    cr.server.rooms.put(name, room);
    System.out.println("Room " + this.name + " created and stored.");
    System.out.println("Room " + this.name + " is accepting Peers.");

    boolean done = false;
    try {
        this.roomServer = new ServerSocket(room.getPort());
        while (!done) {
          System.out.println("Room " + this.name + " needs " + room.getPlayersLeft() + " more Peers.");

          Socket client = this.roomServer.accept();
          room.setPlayers();
          room.peers_id.put(this.nextID, client);
          room.peers_socket.put(client, this.nextID);
          if (room.getPlayersLeft() == 0)
            done = true;
          nextID++;
        }
      } catch (IOException e) {
            e.printStackTrace();
      }

      System.out.println("Room " + this.name + " started.");

      Iterator<Integer> it = room.peers_id.keySet().iterator();
      int i = 0;

      while(it.hasNext()){
        int key = it.next();

        try {
          PrintWriter pw = new PrintWriter(room.peers_id.get(key).getOutputStream(), true);
          BufferedReader br = new BufferedReader(new InputStreamReader(room.peers_id.get(key).getInputStream()));
          ins[i] = br;
          outs[i] = pw;
          i++;
        } catch (IOException e) {
              e.printStackTrace();
        }
      }

      for (int j = 0; j < outs.length; j++) {
        outs[j].println(round);
      }

      int randNum = new Random().nextInt(room.getMaxPlayers()) + 1;
      System.out.println(randNum);
  }
};
