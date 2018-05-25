package communication.responses;

public class RegisterResponse extends  Response{

    private String result, certificate;

    public RegisterResponse(String result, String certificate){
        this.result = result;
        this.certificate = certificate;
        this.type = REGISTER;
    }

    public RegisterResponse(String[] args){

        if(args.length > 3 || args.length < 2){
            this.type = ERROR;
            return;
        }

        if(args[1] == ""){
            this.type = ERROR;
            return;
        }
        else this.result = args[1];

        this.type = REGISTER;

        if (args.length == 2){
            this.certificate = "";
            return;

        }

        if(args[2] == ""){
            this.certificate = "";
            this.type = ERROR;
            return;
        }

        certificate = args[2];
    }

    @Override
    public String toString() {
        if(result.equals(SUCCESS)){
            return REGISTER + SEPARATOR + result + SEPARATOR + certificate;
        }
        else return REGISTER + SEPARATOR + result;

    }

    public String getResult() {
        return result;
    }

    public String getCertificate() {
        return certificate;
    }
}
