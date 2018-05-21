package communication.responses;

import communication.Header;


public abstract class Response implements Header {

    protected String type = "NONE";

    public String getType() {
        return type;
    }

    public static Response parse(String message){

        String[] args = message.split(SEPARATOR);
        String tp;

        tp = args[0];

        switch (tp){
            case LOGIN:
                return new LoginResponse(args);

            case REGISTER:
                return new RegisterResponse(args);

            case LISTROOMS:
                return new ListRoomsResponse(args);

            case JOINROOM:
                return new JoinRoomResponse(args);
        }

        return new ErrorResponse("Invalid response type");
    }
}
