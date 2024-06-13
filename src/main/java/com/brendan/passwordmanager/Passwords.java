package com.brendan.passwordmanager;

public class Passwords {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String id;
    private String owner;
    private String name;
    private String userId;
    private String password;
    private String description;

    public Passwords(String id, String owner, String name, String userId, String password, String description) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.description = description;
    }
}
