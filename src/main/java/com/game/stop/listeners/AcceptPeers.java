package com.game.stop.listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.*;

import javax.net.ssl.SSLSocket;

import com.game.stop.server.Server;


public class AcceptPeers implements Runnable {
 
  protected int nextID;
  protected Server server;
  
  public AcceptPeers () {
	  this.nextID = 1;
	  this.server = Server.getInstance();
  }
  
  public void run() {

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
    
    try {
		PrintWriter pw = new PrintWriter(this.socket.getOutputStream());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
    	ap.server.in = new BufferedReader(new InputStreamReader(ap.server.getClient().getInputStream()));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		System.out.println(ap.server.in.readLine());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    ap.server.peers_id.put(id, socket);
    ap.server.peers_socket.put(socket,id);
    System.out.println("Peer " + id + " joined.");
  }
};