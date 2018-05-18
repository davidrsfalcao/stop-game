package communication.messages;

public class ErrorMessage extends Message {

    private String cause;

    public ErrorMessage(String cause){
        type = ERROR;
        this.cause = cause;
    }

    @Override
    public String toString() {
        return cause;
    }
}
