package server;
import java.security.MessageDigest;
import java.util.Random;

public class Utilities {

    public static final String saltChars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int saltLength = 16;


    public static String hashString(String string, String salt) {
        String salted = string + salt;

        StringBuffer hexString = new StringBuffer();
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(salted.getBytes("UTF-8"));
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }

        return hexString.toString();
    }

    public static String saltGen() {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < saltLength) { // length of the random string.
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        return salt.toString();
    }
}
