package Client;

import com.company.utilities.CommandHandlerClient;

import java.io.IOException;
import java.net.SocketException;

public class AppClient {
    public static boolean flag = true;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Client();
        while (flag) {
            try {
                new CommandHandlerClient();
                Client.receiveAnswer();
            }catch (SocketException e){}
        }
    }
}
