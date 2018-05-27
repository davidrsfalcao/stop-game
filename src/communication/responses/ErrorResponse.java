package communication.responses;

public class ErrorResponse extends Response {

    private String cause;

    public ErrorResponse(String cause){
        type = ERROR;
        this.cause = cause;
    }

    @Override
    public String toString() {
        return cause;
    }
}
