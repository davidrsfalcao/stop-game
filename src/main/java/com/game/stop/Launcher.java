package com.game.stop;


import com.game.stop.cli.Peer;
import com.game.stop.gui.Menu;
import com.game.stop.server.Server;

import java.io.IOException;

public class Launcher {

    public static void main(String[] args){
        Menu game = new Menu();
        game.start();

        /*
        try {
            Server server = new Server(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */


        try {
            Peer peer = new Peer(8080, "172.30.18.58");
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
