package com.game.stop.server;

import com.game.stop.database.Users;

public class LoginRegister {

    private String username;
    private String password;
    private Users user_database;

    public LoginRegister(String username, String password) {
        this.username = username;
        this.password = password;
        user_database = new Users();
        verify_situation_username();

    }

    private void verify_situation_username(){

        if(user_database.user_exists(username)){

            if(user_database.verify_password(username, password)){
                System.out.println("Sucess");
            }
            else System.out.println("Wrong password");


        }
        else {
            System.out.println("Created user " + username);
            user_database.register_username(username, password);

        }

    }



}
