package communication.responses;

import java.util.ArrayList;

public class ListRoomsResponse extends  Response{

    private ArrayList<String> rooms = new ArrayList<String>();
    private String result;

    public ListRoomsResponse(String result, ArrayList<String> rooms){
        this.result = result;
        this.rooms = rooms;
        this.type = LISTROOMS;
    }

    public ListRoomsResponse(String[] args){

        if(!args[1].equals(FAILURE)){
            result = SUCCESS;
            for(int i=1; i<args.length; i++){
                if(!args[i].equals(""))
                    rooms.add(args[i]);
            }

        }
        else result = FAILURE;

    }

    @Override
    public String toString() {
        if(result.equals(SUCCESS)){

            String string = LISTROOMS;

            for(int i=0; i< rooms.size(); i++){
                string += SEPARATOR + rooms.get(i);
            }
            return string;
        }
        else return LISTROOMS + SEPARATOR + result;

    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public String getResult() {
        return result;
    }
}
