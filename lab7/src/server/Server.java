package server;

import com.company.sourse.Dragon;
import com.company.utilities.Requester;
import com.company.utilities.Response;
import server.Collection.CollectionSorter;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveAction;

/**
 * Class to manage server
 */
public class Server extends RecursiveAction implements Serializable {
    private static final int PORT = 9610;
    private static final ByteBuffer buffer = ByteBuffer.allocate(4096);
    private static String[] command;
    public volatile static SocketAddress socketAddress;
    public static ConcurrentHashMap<Long, Dragon> collection = new ConcurrentHashMap<>();
    static Selector selector;
    static DatagramChannel dc;
    public static Log log;
    public static String[] temp = new String[14];

    public Server(DatagramChannel dc, Selector selector) throws IOException {
        this.dc = dc;
        this.selector = selector;
        log = new Log("log.txt");
    }

    public Server() {
    }

    @Override
    protected void compute() {
        commandHandler(Receiver.getCommand());
    }

    /**
     * Method to run server
     *
     * @throws IOException
     */
    public void runServer() throws IOException {
        dc = DatagramChannel.open();
        dc.configureBlocking(false);
        selector = Selector.open();
        dc.register(selector, SelectionKey.OP_READ);
        InetSocketAddress addr = new InetSocketAddress("localhost", PORT);
        try {
            dc.bind(addr);
        } catch (BindException e) {
            log.logger.warning("Адрес уже используется. Завершение работы сервера");
            System.exit(0);
        }
    }

    /**
     * Method to start to manage commands
     */
    public void commandHandler(String commands) {
        command = commands.split(" ");
        if (command.length == 2) new CommandHandlerServer(command[0], command[1]);
        else if (command.length == 1) new CommandHandlerServer(command[0]);
    }

    /**
     * Method to send answer to client
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendToClient() throws IOException, ClassNotFoundException, SQLException {
        buffer.clear();
        buffer.flip();
        byte[] data = CommandHandlerServer.executeCommand().toByteArray();
        dc.send(ByteBuffer.wrap(data),socketAddress);
    }

    /**
     * Method to send error to client
     *
     * @param string error
     * @throws IOException
     */
    public synchronized static void sendErrorToClient(String string) throws IOException {
        buffer.clear();
        buffer.flip();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(string);
        objectOutputStream.flush();
        byte[] buff = byteArrayOutputStream.toByteArray();
        dc.send(ByteBuffer.wrap(buff), socketAddress);
    }

    /**
     * Method to send response to client
     * @param requester - response
     * @throws IOException
     */
    public static void sendResponseToClient(Requester requester) throws IOException {
        buffer.clear();
        buffer.flip();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(requester);
        objectOutputStream.flush();
        byte[] buff = byteArrayOutputStream.toByteArray();
        dc.send(ByteBuffer.wrap(buff), socketAddress);
    }

    /**
     * Method to get selector
     * @return selector
     */
    public Selector getSelector() {
        return selector;
    }

    /**
     * Method to get socket address
     * @return socket address
     */
    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    /**
     * Method to get datagram channel
     * @return datagram channel
     */
    public DatagramChannel getDc() {
        return dc;
    }

    /**
     * Method to get logger
     * @return logger
     */
    public Log getLog() {
        return log;
    }
}
