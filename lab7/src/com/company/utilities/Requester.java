package com.company.utilities;

import client.CommandSerialize;
import com.company.sourse.Dragon;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to create requests
 */
public class Requester implements Serializable {
    private CommandSerialize command;
    private Response response;
    private User user;
    private ConcurrentHashMap<Long,Dragon> collection = new ConcurrentHashMap<>();

    public Requester(CommandSerialize command, User user, Response response) {
        this.command = command;
        this.response = response;
        this.user = user;
    }

    public Requester(User user, Response response) {
        this.user = user;
        this.response = response;
    }
    public Requester(ConcurrentHashMap<Long, Dragon> collection,User user, Response response) {
        this.collection = collection;
        this.user = user;
        this.response = response;
    }
    public Requester(CommandSerialize command, ConcurrentHashMap<Long, Dragon> collection,User user, Response response) {
        this.command = command;
        this.collection = collection;
        this.user = user;
        this.response = response;
    }
    public Requester (Response response) {
        this.response = response;
    }
    public void setCommand(CommandSerialize command) {
        this.command = command;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Response getResponse() {
        return response;
    }

    public CommandSerialize getCommand() {
        return command;
    }

    public User getUser() {
        return user;
    }

    public ConcurrentHashMap<Long,Dragon> getCollection() {
        return collection;
    }
}
