package client;

import com.company.utilities.Requester;
import com.company.utilities.Response;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import static com.company.utilities.Response.AUTH;
import static com.company.utilities.Response.COMMAND;

/**
 * Class to run client
 */
public class AppClient {
    public static boolean flag = true;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Клиент запущен");
        while (true) {
            UserController userController = new UserController();
            userController.chooseMode();
            String temp = String.valueOf(Client.receiveResponse().getResponse());
            if (temp.equals("OK")) {
                System.out.println("Авторизация прошла успешно. Можно работать с коллекцией");
                break;
            }
            else if (temp.equals("AUTH_ERROR")) {
                System.out.println("Введён неправильный логин или пароль.");
            }
            else System.out.println("Такой пользователь уже существует.");
       }
        while (flag) {
            new CommandHandlerClient();
            Client.receiveAnswer();
        }
    }
}
