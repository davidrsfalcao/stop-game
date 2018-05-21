package communication.messages;

public class JoinRoomMessage extends Message {

    private String roomId;

    public JoinRoomMessage(String roomId) {
        this.roomId = roomId;
    }

    public JoinRoomMessage(String[] args){

        if(args.length != 2){
            this.type = ERROR;
            return;
        }

        roomId = args[1];

        this.type  = JOINROOM;
    }

    @Override
    public String toString() {
        return JOINROOM + SEPARATOR + roomId;
    }

    public String getRoomId() {
        return roomId;
    }

}
