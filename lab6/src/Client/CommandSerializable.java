package Client;

import java.io.Serializable;


public class CommandSerializable implements Serializable {
    String command;
    String argument;
    public CommandSerializable(String command){
        this.command = command;
    }
    public CommandSerializable(String command, String argument){
        this.command = command;
        this.argument = argument;
    }
    public String toString(){
        if (argument != null) return command + " " + argument;
        else return command;
    }
}
