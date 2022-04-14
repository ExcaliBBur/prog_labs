package server;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.util.Scanner;

/**
 * Class to run server
 */
public class AppServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramChannel dc = null;
        Selector selector = null;
        Server server = new Server(dc, selector);
        server.run();
        FileController file = new FileController();
        file.readCollection();
        while (true) {
            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                String input;
                while (true) {
                    input = scanner.nextLine().toLowerCase();
                    if (input.equals("exit")) {
                        Server.log.logger.info("Завершение работы сервера...");
                        System.exit(0);
                    } else if (input.equals("save"))
                        file.writeCollection();
                }
            }).start();
            try {
                server.receiveCommands();
                server.commandHandler();
                server.sendToClient();
            } catch (NullPointerException e) {
            }
        }
    }
}
