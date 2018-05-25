package communication.messages;

public class CreateRoomMessage extends Message {

    private String room_name;

    public CreateRoomMessage(String room_name) {
        this.room_name = room_name;
    }

    public CreateRoomMessage(String[] args){

        if(args.length != 2){
            this.type = ERROR;
            return;
        }

        room_name = args[1];

        if(args[1].equals("")){
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
