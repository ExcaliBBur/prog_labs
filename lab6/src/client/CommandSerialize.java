package client;

import java.io.Serializable;

/**
 * Class to serialize commands
 */
public class CommandSerialize implements Serializable {
    String command;
    String argument;

    public CommandSerialize(String command) {
        this.command = command;
    }

    public CommandSerialize(String command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    public String toString() {
        if (argument != null) return command + " " + argument;
        else return command;
    }
}
