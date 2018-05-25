package com.game.stop.objects;

import java.net.Inet4Address;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLSocket;

public class Room {
	private static int max_players;
	private static int no_players;
	private static int port;
	private static Inet4Address ip;
	
	public static ConcurrentHashMap<Integer,SSLSocket> peers_id;
    public static ConcurrentHashMap<SSLSocket,Integer> peers_socket;
	
	public Room(Inet4Address ip, int port, int max_players) {
		this.ip = ip;
		this.port = port;
		this.max_players = max_players;
		this.no_players = 0;
		
		System.out.println("Room was created with max_players = " + this.max_players + ", ip = " + this.ip + ", and port = " + port + ".");
	}
}
