package Client;

import com.company.utilities.CommandHandlerClient;

import java.io.*;
import java.net.*;


public class Client implements Serializable {
    public final static int PORT = 61497;
    private final static int TIMEOUT = 3000;
    private final static int MAX_TIMEOUT_ATTEMPTS = 4;
    private static int timeout_attempts = 0;
    private static DatagramPacket sendPacket;
    public final static InetSocketAddress addr = new InetSocketAddress("localhost",PORT);
    public static DatagramSocket socket;
    static {
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(TIMEOUT);
        } catch (SocketException e) {}
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
        sendPacket = packet;
        socket.send(packet);
    }
    public static void receiveAnswer() throws IOException, ClassNotFoundException, SocketException {
        byte[] buff = new byte[10000];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        try {
            socket.receive(packet);
        } catch (SocketTimeoutException e){
            if (timeout_attempts == MAX_TIMEOUT_ATTEMPTS){
                System.out.println("Превышено кол-во попыток переподключения.");
                exit();
            }else {
                System.out.println("Сервер временно недоступен, попытка переподключения...");
                timeout_attempts++;
                socket.send(sendPacket);
                receiveAnswer();
            }
        }
        byte[] data = packet.getData();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            System.out.println(objectInputStream.readObject());
        }catch (StreamCorruptedException ignored){
            timeout_attempts = 0;
        }
    }
    public static void receiveError() throws IOException, ClassNotFoundException, StreamCorruptedException {
        byte[] buff = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        try {
            socket.receive(packet);
        }catch (SocketTimeoutException e){
            if (timeout_attempts != MAX_TIMEOUT_ATTEMPTS) {
                System.out.println("Сервер временно недоступен, попытка переподключения...");
                socket.send(sendPacket);
                receiveError();
                timeout_attempts++;
                return;
            }else {
                System.out.println("Превышено кол-во попыток переподключений");
                exit();
            }
        }
        timeout_attempts = 0;
        byte[] data = packet.getData();
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        CommandHandlerClient.error = (String) objectInputStream.readObject();
    }
    public static void exit(){
        System.out.println("Клиент заканчивает свою работу...");
        socket.close();
        AppClient.flag = false;
    }
}
