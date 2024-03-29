package communication.messages;

import communication.Header;

public abstract class Message implements Header {

    protected String type = "NONE";


    public String getType() {
        return type;
    }

    public static Message parse(String message){

        String[] args = message.split(SEPARATOR);
        String tp;

        tp = args[0];

        switch (tp){
            case LOGIN:
                return new LoginMessage(args);

            case REGISTER:
                return new RegisterMessage(args);

            case LISTROOMS:
                return new ListRoomsMessage();

            case JOINROOM:
                return new JoinRoomMessage(args);

            case CREATEROOM:
                return new CreateRoomMessage(args);

            case PLAY:
                return new PlayMessage(args);

        }

        return new ErrorMessage("Invalid message");

    }
}
