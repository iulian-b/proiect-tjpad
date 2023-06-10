package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6789)){
            //Citire input de la server
            BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));

            //Returnare output catre server: autoFlush setat ca true pentru a evita utilizarea manuala si repetata a PrintWriter.flush()
            PrintWriter output = new PrintWriter(socket.getOutputStream(),true);

            //Input Utilizator
            Scanner scanner = new Scanner(System.in);
            String userInput;
            String raspuns;
            String clientName = "";

            ClientRunnable clientRun = new ClientRunnable(socket);


            new Thread(clientRun).start();
            do {
                if (clientName.equals("")) {
                    System.out.println("USERNAME:");
                    userInput = scanner.nextLine();
                    clientName = userInput;
                    output.println(userInput);
                    if (userInput.equals("!q")) {
                        break;
                    }
                }
                else {
                    String message = ( "[" + clientName + "]" + " : " );
                    System.out.println(message);
                    userInput = scanner.nextLine();
                    output.println(message + " " + userInput);
                    if (userInput.equals("!q")) {
                        break;
                    }
                }
            } while (!userInput.equals("!q"));


        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}