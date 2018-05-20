package com.game.stop.listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.concurrent.*;

import javax.net.ssl.SSLSocket;

import com.game.stop.server.Server;


public class CreateRoom implements Runnable {
 
  protected int nextID;
  protected int nextPort;
  protected String ip;
  protected Server server;
  
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

    while (true) {
        System.out.println("CreateRoom Thread Running...");
        
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        
            try {
                SSLSocket client = (SSLSocket) Server.getServerSocket().accept();
                
                ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
                scheduledPool.schedule(new StoreRoom(nextID, client, this), 0, TimeUnit.MILLISECONDS);
                nextID++;

            } catch (IOException e) {
                e.printStackTrace();
            }
      }
  }
}

class StoreRoom implements Runnable {

  private int id;
  private SSLSocket socket;
  private CreateRoom cr;

  public StoreRoom(int id, SSLSocket socket, CreateRoom cr) {
    this.id = id;
    this.socket = socket;
    this.cr = cr;
  }

  public void run()
  {
    System.out.println("StorePeer Thread started...");
    
    try {
		PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
    	cr.server.in = new BufferedReader(new InputStreamReader(cr.server.getClient().getInputStream()));
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
    
    cr.server.peers.put(id, socket);
    System.out.println("Peer " + id + " joined.");
  }
};