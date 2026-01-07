package vn.edu.iuh.fit;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            System.out.println("Server is running");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected " + socket.getInetAddress().getHostAddress());

                new Thread(new HandlingClient(socket)).start();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
