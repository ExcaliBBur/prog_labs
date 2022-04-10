package server;

import com.company.sourse.Dragon;
import com.company.utilities.CollectionController;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.LinkedHashMap;
import java.util.Set;

public class Server implements Serializable {
    private static final int PORT = 61497;
    private static final ByteBuffer buffer = ByteBuffer.allocate(4096);
    private static String[] command;
    public static SocketAddress socketAddress;
    public static LinkedHashMap<Long, Dragon> collection = new LinkedHashMap<>();
    static Selector selector;
    static DatagramChannel dc;
    public static String[] temp = new String[14];
    public Server(DatagramChannel dc, Selector selector){
        this.dc = dc;
        this.selector = selector;
    }
    public void run() throws IOException{
        dc = DatagramChannel.open();
        dc.configureBlocking(false);
        selector = Selector.open();
        dc.register(selector, SelectionKey.OP_READ);
        InetSocketAddress addr = new InetSocketAddress("localhost",PORT);
        dc.bind(addr);
    }
    public void receiveCommands() throws IOException, ClassNotFoundException {
        selector.select();
        Set <SelectionKey> keys = selector.selectedKeys();
        for (SelectionKey key : keys){
            if (key.isReadable()){
                buffer.clear();
                socketAddress = dc.receive(buffer);
                System.out.println(socketAddress);
                buffer.flip();
                int limit = buffer.limit();
                byte [] bytes = new byte[limit];
                buffer.get(bytes,0,limit);
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
                String msg = (String) objectInputStream.readObject();
                command = msg.split(" ");
                for (int i = 13; i > 0; i--) temp[i] = temp[i - 1];
                temp[0] = command[0];
                try {
                    if (command[1] != null) System.out.println("Получена команда " + command[0] + " с аргументом " + command[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Получена команда "+ msg);
                };
            }
        }
    }
    public static void receiveCollection() throws IOException, ClassNotFoundException {
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        for (SelectionKey key : keys) {
            if (key.isReadable()) {
                buffer.clear();
                socketAddress = dc.receive(buffer);
                System.out.println(socketAddress);
                buffer.flip();
                int limit = buffer.limit();
                byte[] bytes = new byte[limit];
                buffer.get(bytes, 0, limit);
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
                collection = (LinkedHashMap<Long, Dragon>) objectInputStream.readObject();
                System.out.println(collection);
                CollectionController.collection.putAll(collection);
            }
        }
    }
    public void commandHandler(){
        try {
            new CommandHandlerServer(command[0], command[1]);
        }catch (ArrayIndexOutOfBoundsException e){
            new CommandHandlerServer(command[0]);
        }
    }
    public void sendToClient() throws IOException, ClassNotFoundException {
        buffer.clear();
        buffer.flip();
        byte[] buff = CommandHandlerServer.executeCommand().toByteArray();
        dc.send(ByteBuffer.wrap(buff),socketAddress);
    }
    public static void sendErrorToClient(String string) throws IOException {
        buffer.clear();
        buffer.flip();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(string);
        objectOutputStream.flush();
        byte[] buff = byteArrayOutputStream.toByteArray();
        dc.send(ByteBuffer.wrap(buff),socketAddress);
    }
}
