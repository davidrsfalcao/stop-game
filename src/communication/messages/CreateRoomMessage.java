package communication.messages;

public class CreateRoomMessage extends Message {

    private String room_name;
    private int maxPlayers;

    public CreateRoomMessage(String room_name, int players) {
        this.room_name = room_name;
        this.maxPlayers = players;
    }

    public CreateRoomMessage(String[] args){

        if(args.length != 3){
            this.type = ERROR;
            return;
        }

        room_name = args[2];

        if(args[2].equals("")){
            this.type = ERROR;
        }
        else this.type  = CREATEROOM;
    }

    @Override
    public String toString() {
        return CREATEROOM + SEPARATOR + room_name;
    }

    public String getRoomName() {
        return room_name;
    }

}
