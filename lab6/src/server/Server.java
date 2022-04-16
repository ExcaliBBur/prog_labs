package server;

import com.company.sourse.Dragon;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Class to manage server
 */
public class Server implements Serializable {
    private static final int PORT = 61497;
    private static final ByteBuffer buffer = ByteBuffer.allocate(4096);
    private static String[] command;
    public static SocketAddress socketAddress;
    public static LinkedHashMap<Long, Dragon> collection = new LinkedHashMap<>();
    static Selector selector;
    static DatagramChannel dc;
    public static Log log;
    public static String[] temp = new String[14];

    public Server(DatagramChannel dc, Selector selector) throws IOException {
        this.dc = dc;
        this.selector = selector;
        log = new Log("log.txt");
    }

    /**
     * Method to run server
     *
     * @throws IOException
     */
    public void run() throws IOException {
        dc = DatagramChannel.open();
        dc.configureBlocking(false);
        selector = Selector.open();
        dc.register(selector, SelectionKey.OP_READ);
        InetSocketAddress addr = new InetSocketAddress("localhost", PORT);
        try {
            dc.bind(addr);
        }catch (BindException e) {
            log.logger.warning("Адрес уже используется. Завершение работы сервера");
            System.exit(0);
        }
    }

    /**
     * Method to receive commands from client
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void receiveCommands() throws IOException, ClassNotFoundException {
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        for (SelectionKey key : keys) {
            if (key.isReadable()) {
                buffer.clear();
                socketAddress = dc.receive(buffer);
                buffer.flip();
                int limit = buffer.limit();
                byte[] bytes = new byte[limit];
                buffer.get(bytes, 0, limit);
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
                String msg = (String) objectInputStream.readObject();
                command = msg.split(" ");
                for (int i = 13; i > 0; i--) temp[i] = temp[i - 1];
                temp[0] = command[0];
                if (command.length == 2)
                    log.logger.info("Получена команда " + command[0] + " с аргументом " + command[1] + " от " + socketAddress);
                else log.logger.info("Получена команда " + msg + " от " + socketAddress);
            }
        }
    }

    /**
     * Method to receive collection(add(or same) commands) from user
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void receiveCollection() throws IOException, ClassNotFoundException {
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        for (SelectionKey key : keys) {
            if (key.isReadable()) {
                buffer.clear();
                socketAddress = dc.receive(buffer);
                log.logger.info("Получен ответ от " + socketAddress);
                buffer.flip();
                int limit = buffer.limit();
                byte[] bytes = new byte[limit];
                buffer.get(bytes, 0, limit);
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
                collection = (LinkedHashMap<Long, Dragon>) objectInputStream.readObject();
                CollectionSorter.collection.putAll(collection);
            }
        }
    }

    /**
     * Method to start to manage commands
     */
    public void commandHandler() {
        if (command.length == 2) new CommandHandlerServer(command[0], command[1]);
        else if (command.length == 1) new CommandHandlerServer(command[0]);
    }

    /**
     * Method to send answer to client
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendToClient() throws IOException, ClassNotFoundException {
        buffer.clear();
        buffer.flip();
		byte[] buff = CommandHandlerServer.executeCommand().toByteArray();
		final int INCREMENT = 2048;
            for (int position = 0, limit = INCREMENT, capacity = 0; buff.length > capacity; position = limit,
                            limit += INCREMENT) {
                byte[] window = Arrays.copyOfRange(buff, position, limit);
                capacity += limit - position;
                dc.send(ByteBuffer.wrap(window),socketAddress);
			}
    }

    /**
     * Method to send error to client
     *
     * @param string error
     * @throws IOException
     */
    public static void sendErrorToClient(String string) throws IOException {
        buffer.clear();
        buffer.flip();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(string);
        objectOutputStream.flush();
        byte[] buff = byteArrayOutputStream.toByteArray();
        dc.send(ByteBuffer.wrap(buff), socketAddress);
    }
}
