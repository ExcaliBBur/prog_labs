package Client;

import com.company.utilities.CommandHandlerClient;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class AppClient {
    public static boolean flag = true;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        while (flag) {
            try {
                new CommandHandlerClient();
                Client.receiveAnswer();
            }catch (SocketException e){}
        }
    }
}
