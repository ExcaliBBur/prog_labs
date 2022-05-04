package server;


import server.Collection.CollectionDownloader;
import server.user.UserController;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Class to run server
 */
public class AppServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, ExecutionException, SQLException, TimeoutException {
        DatagramChannel dc = null;
        Selector selector = null;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Server server = new Server(dc, selector);
        server.runServer();
        Receiver receiver = new Receiver();
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            String input;
            while (true) {
                input = scanner.nextLine().toLowerCase();
                if (input.equals("exit")) {
                    Server.log.logger.info("Завершение работы сервера...");
                    System.exit(0);
                }
            }
        }).start();

        while (true) {
            executorService.submit(() -> {
                try {
                    receiver.receive();

                } catch (IOException | ClassNotFoundException ignored) {
                }
            }).get();
            System.out.println(receiver.getResponse());
            switch (receiver.getResponse()) {
                case AUTH:
                    forkJoinPool.invoke(new UserController());
                    break;
                case COMMAND:
                    forkJoinPool.invoke(new CollectionDownloader());
                    forkJoinPool.invoke(new Server());
                    new Thread(() -> {
                        try {
                            server.sendToClient();
                        } catch (IOException | SQLException | ClassNotFoundException | NullPointerException ignored) {
                        }
                    }).start();
                    break;
                case ADD_COMMAND:
                    forkJoinPool.invoke(new CollectionDownloader());
                    forkJoinPool.invoke(new Server());
                    executorService.submit(() -> {
                        try {
                            receiver.receiveCollection();
                            CommandHandlerServer.flag = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).get();
                    new Thread(() -> {
                        try {
                            server.sendToClient();
                            System.out.println("твет оотправлен");
                        } catch (IOException | SQLException | ClassNotFoundException ignored) {
                        }
                    }).start();
                    break;
            }
        }
    }
}
