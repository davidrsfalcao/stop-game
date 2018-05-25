package com.game.stop.communication.messages;

public class JoinRoomMessage extends Message {

    private String room_name;

    public JoinRoomMessage(String room_name) {
        this.room_name = room_name;
    }

    public JoinRoomMessage(String[] args){

        if(args.length != 2){
            this.type = ERROR;
            return;
        }

        room_name = args[1];

        if(args[1].equals("")){
            this.type = ERROR;
        }
        else this.type  = JOINROOM;
    }

    @Override
    public String toString() {
        return JOINROOM + SEPARATOR + room_name;
    }

    public String getRoomName() {
        return room_name;
    }

}
