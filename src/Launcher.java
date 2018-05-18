
import cli.Peer;
import gui.Menu;
import server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Launcher {

    public static void main(String[] args){
        Menu game = new Menu();
        game.start();

        System.out.println("Testing:\n");
        System.out.println("1: Server");
        System.out.println("2: Peer");
        System.out.println("Other: break");


        int a;
        Scanner S=new Scanner(System.in);
        a=S.nextInt();

        switch (a){
            case 1:
                System.out.println("SERVER");
                try {
                    Server server = new Server(8080);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.println("PEER");
                try {
                    InetAddress IP=InetAddress.getLocalHost();
                    System.out.println(IP.getHostAddress());
                    Peer peer = new Peer(8080, IP.getHostAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;

        }


    }
}
