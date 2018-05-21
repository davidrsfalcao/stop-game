package communication;

public interface Header {
    public final static String SEPARATOR = "!@!";

    public final static String ERROR = "ERROR";
    public final static String LOGIN = "LOGIN";
    public final static String REGISTER = "REGISTER";
    public final static String LISTROOMS = "LISTROOMS";
    public final static String JOINROOM = "JOINROOM";


    public final static String SUCCESS = "SUCCESS";
    public final static String FAILURE = "FAILURE";
}
