package com.example.demo.helpers;

import org.mindrot.jbcrypt.BCrypt;

public class Hashing {

    public static String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static Boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
