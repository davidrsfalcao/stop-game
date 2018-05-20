package communication.messages;

public class RegisterMessage extends Message {

    private String username, password;

    public RegisterMessage(String username, String password){
        this.username = username;
        this.password= password;
        this.type = REGISTER;
    }

    public RegisterMessage(String[] args){

        if(args.length != 3){
            this.type = ERROR;
            return;
        }

        if(args[1] == ""){
            this.type = ERROR;
            return;
        }

        username = args[1];

        if(args[2] == "" ){
            this.type = ERROR;
            return;
        }
        password = args[2];
        this.type  = REGISTER;
    }

    @Override
    public String toString() {
        return REGISTER + SEPARATOR + username + SEPARATOR + password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
