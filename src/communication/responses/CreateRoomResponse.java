package communication.responses;

public class CreateRoomResponse extends  Response{

    private String result;
    private int port;

    public CreateRoomResponse(String result, int port){
        this.result = result;
        this.port = port;
    }

    public CreateRoomResponse(String[] args){

        if(args.length != 3 ){
            this.type = ERROR;
            return;
        }

        if(args[1] == ""){
            this.type = ERROR;
            return;
        }

        this.type = CREATEROOM;
        this.result = args[1];
        this.port = Integer.parseInt(args[2]);
    }

    @Override
    public String toString() {
        return CREATEROOM + SEPARATOR + result + SEPARATOR + port;

    }

    public String getResult() {
        return result;
    }

    public int getPort() {
        return port;
    }

}
