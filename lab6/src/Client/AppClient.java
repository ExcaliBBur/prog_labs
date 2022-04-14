package Client;

import java.io.IOException;
import java.net.SocketException;

/**
 * Class to run client
 */
public class AppClient {
    public static boolean flag = true;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Клиент запущен");
        while (flag) {
            try {
                new CommandHandlerClient();
                Client.receiveAnswer();
            } catch (SocketException e) {
            }
        }
    }
}
