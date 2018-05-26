package server;

import java.util.Calendar;
import java.util.Date;

public class Certificate {

    private String ip;
    private String certificate;
    private boolean expires = true;
    private boolean sessionActive;
    private Date validity;

    public Certificate(String username, int validTime) {
        sessionActive = true;

        if(validTime == -1)
            expires = false;

        validity = Calendar.getInstance().getTime();
        certificate = Utilities.hashString(username + validity, Utilities.saltGen());
    }

    public boolean expired() {
        Date date = Calendar.getInstance().getTime();
        return (validity.before(date) && expires && sessionActive);
    }

    public String getCertificate() {
        return certificate;
    }

    public String getIP() {
        return ip;
    }

}
