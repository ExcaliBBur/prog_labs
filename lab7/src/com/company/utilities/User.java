package com.company.utilities;

import java.io.Serializable;

/**
 * Class to manage user data
 */
public class User implements Serializable {
    private String username;
    private String password;
    private Type type;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public enum Type {
        AUTH,
        REGISTRATION;
    }
}
