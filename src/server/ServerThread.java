package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class ServerThread extends Thread {
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    @Override
    public void run() {
        try {
            //Citire input de la Client
            BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));

            //Returnare output catre Client
            output = new PrintWriter(socket.getOutputStream(),true);


            while(true) {
                String outputString = input.readLine();
                //if user types exit command
                if(outputString.equals("!q")) {
                    break;
                }
                printAll(outputString);
                System.out.println("[SERVER] " + outputString);
            }


        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    //Funcite pentru printarea unui mesaj catre toate Client-urile
    private void printAll(String outputString) {
        for( ServerThread sT: threadList) {
            sT.output.println(outputString);
        }
    }
}
