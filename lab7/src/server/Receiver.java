package server;

import client.CommandSerialize;
import com.company.sourse.Dragon;
import com.company.utilities.Requester;
import com.company.utilities.Response;
import com.company.utilities.User;
import server.Collection.CollectionSorter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class to receive requests from client
 */
public class Receiver implements Serializable, Runnable {
    private static final ByteBuffer buffer = ByteBuffer.allocate(4096);
    private Server server = new Server();
    private Selector selector = server.getSelector();
    private DatagramChannel dc = server.getDc();
    private Log log = server.getLog();
    private Requester requester;
    private Response response;
    private static User user;
    private volatile static CommandSerialize command;
    private String[] command_string;
    private static ConcurrentHashMap<Long, Dragon> collection = new ConcurrentHashMap<>();
    ReentrantLock reentrantLock = new ReentrantLock();

    public Receiver(){
    }

    /**
     * Method to receive messages
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void receive() throws IOException, ClassNotFoundException {
        reentrantLock.lock();
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        for (SelectionKey key : keys) {
            if (key.isReadable()) {
                buffer.clear();
                Server.socketAddress = dc.receive(buffer);
                log.logger.info("Получены данные от " + Server.socketAddress);
                buffer.flip();
                int limit = buffer.limit();
                byte[] bytes = new byte[limit];
                buffer.get(bytes, 0, limit);
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
                try {
                    requester = (Requester) objectInputStream.readObject();
                    response = requester.getResponse();
                    user = requester.getUser();
                    command = requester.getCommand();
                    command_string = command.toString().split(" ");
                    for (int i = 13; i > 0; i--) Server.temp[i] = Server.temp[i - 1];
                    Server.temp[0] = command_string[0];
                    if (command_string.length == 2)
                        log.logger.info("Получена команда " + command_string[0] + " с аргументом " +
                                command_string[1] + " от " + Server.socketAddress);
                    else log.logger.info("Получена команда " + command_string[0] + " от " + Server.socketAddress);
                } catch (NullPointerException ignored) {
                }
                reentrantLock.unlock();
            }
        }
    }

    /**
     * Method to receive collcetions
     * @throws IOException
     */
    public void receiveCollection() throws IOException {
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        for (SelectionKey key : keys) {
            if (key.isReadable()) {
                buffer.clear();
                Server.socketAddress = dc.receive(buffer);
                log.logger.info("Получена коллекция от " + Server.socketAddress);
                buffer.flip();
                int limit = buffer.limit();
                byte[] bytes = new byte[limit];
                buffer.get(bytes, 0, limit);
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
                try {
                    requester = (Requester) objectInputStream.readObject();
                    response = requester.getResponse();
                    user = requester.getUser();
                    collection = requester.getCollection();
                    command = requester.getCommand();
                    CollectionSorter.collection.put(0L, collection.get(0L));
                } catch (NullPointerException ignored) {
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("ошибка");
                }
            }
        }
    }

    public Response getResponse() {
        return response;
    }

    public static User getUser() {
        return user;
    }

    public static String getCommand() {
        return command.toString();
    }

    public static ConcurrentHashMap<Long, Dragon> getCollection() {
        return collection;
    }

    @Override
    public synchronized void run() {
        try {
            receive();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
