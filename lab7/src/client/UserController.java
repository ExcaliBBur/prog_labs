package client;

import com.company.utilities.Requester;
import com.company.utilities.Response;
import com.company.utilities.User;

import java.io.Console;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class to control user
 */
public class UserController {
    private String username;
    PasswordHasher hasher = new PasswordHasher();
    static User user = new User();

    public UserController() {}

    /**
     * Method to choose authorization mode for user
     * @throws IOException
     */
    public void chooseMode() throws IOException {
        System.out.println("Для дальнейшей работы необходимо авторизоваться. Если у Вас нет аккаунта - необходима регистрация. " +
                "(1 - авторизация, 2 - регистрация)");
        Scanner scanner = new Scanner(System.in);
        try {
            int mode = scanner.nextInt();
        if (mode == 1) {
            Client.sendResponse(new Requester(authorization(), Response.AUTH));
            return;
        }
        if (mode == 2) {
            Client.sendResponse(new Requester(registration(),Response.AUTH));
            return;
        }
        else {
            chooseMode();
        }
        } catch (InputMismatchException e) {
            chooseMode();
        }
    }

    /**
     * Method to authorize user
     * @return user
     */
    public User authorization() {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Логин: ");
        username = scanner.nextLine();
        user.setUsername(username);
        Console console = System.console();
        if (console != null) {
            user.setPassword(hasher.hashPassword(String.valueOf(console.readPassword("Пароль: "))));
        } else {
            System.out.print("Пароль: ");
            user.setPassword(hasher.hashPassword(scanner.nextLine()));
        }
        user.setType(User.Type.AUTH);
        this.user = user;
        return user;
    }

    /**
     * Method to registrate user
     * @return user
     */
    public User registration() {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя пользователя: ");
        username = scanner.nextLine();
        user.setUsername(username);
        Console console = System.console();
        if (console != null) {
            user.setPassword(hasher.hashPassword(String.valueOf(console.readPassword("Введите пароль: "))));
        } else {
            System.out.print("Введите пароль: ");
            user.setPassword(hasher.hashPassword(scanner.nextLine()));
        }
        user.setType(User.Type.REGISTRATION);
        this.user = user;
        return user;
    }

    /**
     * Method to get user
     * @return user
     */
    public static User getUser() {
        return user;
    }
}
