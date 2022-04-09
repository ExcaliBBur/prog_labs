package Client;

import com.company.utilities.CommandHandlerClient;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;


public class Client implements Serializable {
    public final static int PORT = 61497;
    public final static InetSocketAddress addr = new InetSocketAddress("localhost",PORT);
    static DatagramSocket socket;
    static {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
        }
    }
    //t = 1 => common command; t != 1 => add (or same) command
    public static void sendCommand(Object T, int t) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        if (t == 1) objectOutputStream.writeObject(T.toString());
        else objectOutputStream.writeObject(T);
        objectOutputStream.flush();
        byte[] buff = byteArrayOutputStream.toByteArray();
        DatagramPacket packet = new DatagramPacket(buff,buff.length,addr);
        socket.send(packet);
    }
    public static void receiveAnswer() throws IOException, ClassNotFoundException, SocketException {
        byte[] buff = new byte[10000];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        socket.receive(packet);
        byte[] data = packet.getData();
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        System.out.println(objectInputStream.readObject());
    }
    public static Object receiveError() throws IOException, ClassNotFoundException, StreamCorruptedException {
        byte[] buff = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        socket.receive(packet);
        byte[] data = packet.getData();
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        return objectInputStream.readObject();
    }
    public static void exit(){
        System.out.println("Клиент заканчивает свою работу...");
        socket.close();
        AppClient.flag = false;
    }
}
