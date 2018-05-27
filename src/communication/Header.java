package communication;

public interface Header {
    String SEPARATOR = "!@!";

    String ERROR = "ERROR";
    String LOGIN = "LOGIN";
    String REGISTER = "REGISTER";
    String LISTROOMS = "LISTROOMS";
    String JOINROOM = "JOINROOM";
    String CREATEROOM = "CREATEROOM";
    String PLAY = "PLAY";
    String LETTER = "LETTER";



    String SUCCESS = "SUCCESS";
    String FAILURE = "FAILURE";
}
