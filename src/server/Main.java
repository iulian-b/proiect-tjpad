package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Toti clienti sunt adaugati intr-o lista de socketuri
        ArrayList<ServerThread> threadList = new ArrayList<>();
        try (ServerSocket serversocket = new ServerSocket(6789)){
            while(true) {
                Socket socket = serversocket.accept();
                ServerThread serverThread = new ServerThread(socket, threadList);

                // Start list threaduri
                threadList.add(serverThread);
                serverThread.start();

            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}