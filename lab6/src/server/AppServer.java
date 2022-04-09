package server;

import com.company.utilities.FileController;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;

public class AppServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramChannel dc = null;
        Selector selector = null;
        Server server = new Server(dc ,selector);
        server.run();
        FileController file = new FileController();
        file.readCollection();
        while (true) {
            try {
                server.receiveCommands();
                server.commandHandler();
                server.sendToClient();
            }catch (NullPointerException e){}
        }
    }
}
