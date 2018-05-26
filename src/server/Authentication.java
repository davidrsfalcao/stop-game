package server;

import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;



public class Authentication
{
    private static ConcurrentHashMap<String, Certificate> logins = new ConcurrentHashMap<String, Certificate>();

    private static final int certificateValidity = -1;


    private static final String printLine = "  ______________________________________________________________________________________________________________________________________________________";
    // define the driver to use
    private static final String driver = "org.apache.derby.jdbc.ClientDriver";
    // the database name
    private static final String dbName = "StopGameTest2";
    // define the Derby connection URL to use
    private static final String connectionURL = "jdbc:derby://localhost:1527/" + dbName + ";create=true";
    // hold the connection object
    private static Connection conn = null;

    public static void main(String[] args) throws SQLException {
        connectToDB();
        createUsersTable();

        register("SalvadorSobral", "salvador@gmail.com", "Salvador", "Sobral", "eurovisao2017");
        if(login("SalvadorSobral", "eurovisao2017"))
        {
            System.out.println("Login successful");
            //TODO Send certificate back to client
        }
        printUsers();

        closeConnection();
    }

    public static void Initialize() throws SQLException {
        connectToDB();
        createUsersTable();


    }

    public static boolean ipLoggedIn(String ip) {
        for(String username : logins.keySet())
            if(logins.get(username).getIP().equals(ip))
                return true;
        return false;
    }

    public static void connectToDB() throws SQLException {
        conn = DriverManager.getConnection(connectionURL);
        System.out.println("Connected to database " + dbName + "\n--------------------------------------\n");
    }

    public static void closeConnection() throws SQLException {
        conn.close();
        System.out.println("\n--------------------------------------\nClosed connection");

        //   ## DATABASE SHUTDOWN SECTION ##
        /*** In embedded mode, an application should shut down Derby.
         Shutdown throws the XJ015 exception to confirm success. ***/
        if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
            boolean gotSQLExc = false;
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se)  {
                if ( se.getSQLState().equals("XJ015") ) {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
            }  else  {
                System.out.println("Database shut down normally");
            }
        }
    }

    public static void createUsersTable() throws SQLException {
        Statement s;
        String createString = "CREATE TABLE USERS  "
                +  "(USERNAME VARCHAR(30) PRIMARY KEY, "
                +  " EMAIL VARCHAR(50),"
                +  " FIRST_NAME VARCHAR(30),"
                +  " LAST_NAME VARCHAR(30),"
                +  " PASSHASH CHAR(64),"
                +  " SALT CHAR(" + Utilities.saltLength +"))";

        s = conn.createStatement();
        // Call utility method to check if table exists.
        //      Create the table if needed
        if (!checkUsersTable(conn))
        {
            System.out.println (" . . . . creating table USERS");
            s.execute(createString);
        }
        else
        {
            System.out.println ("Table USERS already exists, skipping the creation\n");
        }
    }

    public static boolean register(String username, String email, String firstName, String lastName, String password) throws SQLException {

        System.out.println("Registering username: " + username + "with password: " + password);

        if(userExists(username)) {
            System.out.println("Username already in use");
            return false;
        }

        PreparedStatement psInsert;
        psInsert = conn.prepareStatement("insert into USERS(USERNAME, EMAIL, FIRST_NAME, LAST_NAME, PASSHASH, SALT) values (?, ?, ?, ?, ?, ?)");

        //Insert the text entered into the WISH_ITEM table
        psInsert.setString(1, username);
        psInsert.setString(2, email);
        psInsert.setString(3, firstName);
        psInsert.setString(4, lastName);

        //PASSWORD HASHING

        String salt = Utilities.saltGen();
        String passHash = Utilities.hashString(password, salt);

        psInsert.setString(5, passHash.toString());
        psInsert.setString(6, salt.toString());
        psInsert.executeUpdate();

        // Release the resources (clean up )
        psInsert.close();

        return true;
    }

    public static ResultSet executeSelect(String query) throws SQLException{
        ResultSet users;

        Statement s = conn.createStatement();
        users = s.executeQuery(query);

        return users;
    }

    public static String loginSuccessful(String username) throws SQLException {

        Certificate certificate = new Certificate(username, certificateValidity);
        logins.put(username, certificate);

        return certificate.getCertificate();
    }

    public static boolean login(String username, String password) throws SQLException {

        System.out.println("Logging in . . . .");

        if(logins.containsKey(username))
        {
            System.out.println("User: " + username + " is already logged in!");
            return false;
        }

        if(!userExists(username)) {
            System.out.println("Username " + username + " does not exist");
            return false;
        }

        ResultSet response = executeSelect("select USERNAME, PASSHASH, SALT from USERS where USERNAME = '" + username + "'");
        response.next();

        String retrievedPassHash = response.getString(2);
        String salt = response.getString(3);

        // hashes password to compare with database
        String passHash = Utilities.hashString(password, salt);


        if(!passHash.equals(retrievedPassHash))
        {
            System.out.println("Incorrect password!");
            return false;
        }

        System.out.println("Login Successful!");
        return true;
    }

    public static boolean logout(String username) {
        if(!logins.containsKey(username))
            return false;

        logins.remove(username);
        return true;
    }
    // utility and debug functions


    private static boolean userExists(String username) throws SQLException {
        ResultSet response = executeSelect("select count(*) from USERS where USERNAME = '" + username + "'");
        response.next();

        if(response.getInt(1) != 1)
            return false;

        return true;
    }

    private static boolean checkUsersTable (Connection conTst ) throws SQLException {
        try {
            Statement s = conTst.createStatement();
            s.execute("update USERS set USERNAME = 'sss' where 1=3");
        }  catch (SQLException sqle) {
            String theError = (sqle).getSQLState();
            //   System.out.println("  Utils GOT:  " + theError);
            /** If table exists will get -  WARNING 02000: No row was found **/
            if (theError.equals("42X05"))   // Table does not exist
            {  return false;
            }  else if (theError.equals("42X14") || theError.equals("42821"))  {
                System.out.println("Incorrect table definition. Drop table USERS and rerun this program");
                throw sqle;
            } else {
                System.out.println("Unhandled SQLException" );
                throw sqle;
            }
        }
        //  System.out.println("Just got the warning - table exists OK ");
        return true;
    }



    @SuppressWarnings("unused")
    public static void printUsers() throws SQLException {
        ResultSet users = executeSelect("select USERNAME, EMAIL, FIRST_NAME, LAST_NAME, PASSHASH, SALT from USERS order by USERNAME");

        System.out.println(printLine);
        while(users.next())
            System.out.println("| " + users.getString(1) + " | " + users.getString(2) + " | "+ users.getString(3) + " | " + users.getString(4) + " | " + users.getString(5) + " | " + users.getString(6));
        System.out.println(printLine);
    }
}
