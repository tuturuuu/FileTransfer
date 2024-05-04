package Server;

import javax.print.DocFlavor;
import java.net.*;
import java.io.*;

public class ServerFTP {

    public static void main(String[] args){
        try{
            ServerSocket serverSocket = new ServerSocket(9090);
            boolean stop = false;

            while(!stop){
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out.println("You just connected with the server");
                System.out.println("Client connected");
            }

        } catch (UnknownHostException e){
            System.out.println(e.toString());
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }

}