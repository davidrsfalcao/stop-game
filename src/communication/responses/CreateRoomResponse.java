package communication.responses;

public class CreateRoomResponse extends  Response{

    private String result;
    private int port;

    public CreateRoomResponse(String result, int port){
        this.result = result;
        this.port = port;
    }

    public CreateRoomResponse(String[] args){

        if(args.length < 2 || args.length > 3 ){
            this.type = ERROR;
            return;
        }

        if(args[1] == ""){
            this.type = ERROR;
            return;
        }

        this.type = CREATEROOM;
        this.result = args[1];

        if((result.equals(SUCCESS) && (args.length == 2)) || (result.equals(FAILURE) && (args.length == 3))){
            this.type = ERROR;
            port = 0;
            return;
        }

        if(result.equals(FAILURE)){
            return;
        }

        try {
            port = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            port = 0;
            this.type = ERROR;
            return;
        }

    }

    @Override
    public String toString() {
        if(result.equals(SUCCESS)){
            return CREATEROOM + SEPARATOR + result + SEPARATOR + port;
        }
        else return CREATEROOM + SEPARATOR + result;


    }

    public String getResult() {
        return result;
    }

    public int getPort() {
        return port;
    }

}
