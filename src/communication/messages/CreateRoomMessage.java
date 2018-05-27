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

        room_name = args[1];

        if(args[1].equals("")){
            this.type = ERROR;
            return;
        }

        try {
            maxPlayers = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            maxPlayers = 0;
            this.type = ERROR;
            return;

        }


        this.type  = CREATEROOM;

    }

    @Override
    public String toString() {
        return CREATEROOM + SEPARATOR + room_name + SEPARATOR + maxPlayers;
    }

    public String getRoomName() {
        return room_name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
