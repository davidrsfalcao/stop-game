import cli.Peer;
import gui.Menu;
import gui.PageManager;
import server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Launcher {


    public static void main(String[] args){


        PageManager pages = PageManager.getInstance();
        pages.push_page(new Menu());

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
                Server.port = 8080;
                Server server = Server.getInstance();

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
