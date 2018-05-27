package communication.responses;

public class JoinRoomResponse extends  Response{

    private String result;

    public JoinRoomResponse(String result){
        this.result = result;
    }

    public JoinRoomResponse(String[] args){

        if(args.length != 2 ){
            this.type = ERROR;
            return;
        }

        if(args[1] == ""){
            this.type = ERROR;
            return;
        }

        this.type = JOINROOM;
        this.result = args[1];

    }

    @Override
    public String toString() {
       return JOINROOM + SEPARATOR + result;

    }

    public String getResult() {
        return result;
    }

}
