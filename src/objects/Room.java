package objects;

import java.net.*;
import java.net.Inet4Address;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
	private static int max_players;
	private static int no_players;
	private static int port;
	private static String ip;
	private static String roomName;

	public static ConcurrentHashMap<Integer,Socket> peers_id = new ConcurrentHashMap<>();
  public static ConcurrentHashMap<Socket,Integer> peers_socket = new ConcurrentHashMap<>();

	public Room(String ip, int port, int max_players, String roomName) {
		this.ip = ip;
		this.port = port;
		this.max_players = max_players;
		this.roomName = roomName;
		this.no_players = 0;

		System.out.println("Room was created with max_players = " + this.max_players + ", ip = " + this.ip + ", and port = " + port + ".");
	}

	public int getPort() {
		return port;
	}

	public void setPlayers() {
		this.no_players++;
	}

	public int getPlayersLeft() {
		return (max_players - no_players);
	}
}
