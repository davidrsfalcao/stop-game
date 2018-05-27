package communication.responses;

public class JoinRoomResponse extends  Response{

    private String result;
    private int port;

    public JoinRoomResponse(String result, int port){
        this.result = result;
        this.port = port;
    }

    public JoinRoomResponse(String[] args){

        if(args.length != 3 ){
            this.type = ERROR;
            return;
        }

        if(args[1] == ""){
            this.type = ERROR;
            return;
        }

        this.type = JOINROOM;
        this.result = args[1];
        this.port = Integer.parseInt(args[2]);

    }

    @Override
    public String toString() {
       return JOINROOM + SEPARATOR + result + SEPARATOR + port;

    }

    public String getResult() {
        return result;
    }

    public int getPort() {
        return port;
    }
}
