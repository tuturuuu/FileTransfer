//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tuturuu.socket.Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientFTP {
    public ClientFTP() {
    }

    public static void main(String[] args) {
        try {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            System.out.println("server ip address: " + serverAddress.getHostAddress());
            Socket socket = new Socket(serverAddress, 9090);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            String echoes = "";

            echoes = in.readLine();
            System.out.println(echoes);

            do {
                System.out.println("USERNAME: ");
                echoes = scanner.nextLine();
                out.println("USERNAME " + echoes);

                echoes = in.readLine();
                if (echoes.equals("OK Password require")) {
                    System.out.println(echoes);
                    break;
                } else if (echoes.equals("ERR Not found user")) {
                    System.out.println(echoes);
                }

            }while (true);

            do {
                System.out.println("PASSWORD: ");
                echoes = scanner.nextLine();
                out.println("PASSWORD " + echoes);

                echoes = in.readLine();
                if(echoes.equals("OK Login successful")){
                    System.out.println(echoes);
                    break;
                } else if (echoes.equals("ERR Password incorrect")){
                    System.out.println(echoes);
                }

            }while (true);

            echoes = in.readLine();
            System.out.println(echoes);
            int fileSize = 0;
            if(echoes.startsWith("File size: ")){
                fileSize = Integer.parseInt(echoes.substring(11));
            }

            try (InputStream inputStream = socket.getInputStream();
                 FileOutputStream imageOutputStream = new FileOutputStream("secret_image.png")) {

                byte[] buffer = new byte[1024];
                int bytesRead=0;

                while (fileSize > 0) {

                    bytesRead = inputStream.read(buffer);
                    fileSize = fileSize - bytesRead;
                    imageOutputStream.write(buffer, 0, bytesRead);

                }
                out.println("OK received");
                System.out.println("Image received from server");
            }


        } catch (IOException e) {
            System.out.println("IO exception: " + e.toString());
        }

    }
}
