package Client;

import java.net.*;
import java.io.*;
public class ClientFTP {
    public static void main(String[] args){
        try {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            System.out.println("server ip address: " + serverAddress.getHostAddress());
            Socket socket = new Socket(serverAddress, 9090);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";

            do{
                line = in.readLine();
                System.out.println(line);
            }while (line != null);
            out.println("Hello server");

        }catch (IOException e){

        }
    }
}
