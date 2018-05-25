package communication.responses;

public class LoginResponse extends  Response{

    private String result, certificate;

    public LoginResponse(String result, String certificate){
        this.result = result;
        this.certificate = certificate;
        this.type = LOGIN;
    }

    public LoginResponse(String[] args){

        if(args.length > 3 || args.length < 2){
            this.type = ERROR;
            return;
        }

        if(args[1] == ""){
            this.type = ERROR;
            return;
        }
        else this.result = args[1];

        this.type = LOGIN;

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
            return LOGIN + SEPARATOR + result + SEPARATOR + certificate;
        }
        else return LOGIN + SEPARATOR + result;

    }

    public String getResult() {
        return result;
    }

    public String getCertificate() {
        return certificate;
    }
}
