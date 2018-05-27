package communication.responses;

public class CreateRoomResponse extends  Response{

    private String result;

    public CreateRoomResponse(String result){
        this.result = result;
    }

    public CreateRoomResponse(String[] args){

        if(args.length != 2 ){
            this.type = ERROR;
            return;
        }

        if(args[1] == ""){
            this.type = ERROR;
            return;
        }

        this.type = CREATEROOM;
        this.result = args[1];

    }

    @Override
    public String toString() {
        return CREATEROOM + SEPARATOR + result;

    }

    public String getResult() {
        return result;
    }

}
