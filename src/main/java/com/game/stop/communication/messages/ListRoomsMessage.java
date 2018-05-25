package com.game.stop.communication.messages;


public class ListRoomsMessage extends Message {

    public ListRoomsMessage(){
        this.type = LISTROOMS;
    }

    public ListRoomsMessage(String[] args){

        if(args.length != 1){
            this.type = ERROR;
            return;
        }

        this.type  = LISTROOMS;
    }

    @Override
    public String toString() {
        return LISTROOMS;
    }

}
