//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tuturuu.socket.Server;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;


public class ClientThread extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isStop = false;

    private boolean isLogin = false;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.out.println("You just connected with the server");
        while (!isStop) {
            authentication(databaseConnection);
            if (isLogin == true) {
                fileTransfer();
            } else {
                authentication(databaseConnection);
            }
        }
        } catch (IOException e) {
            System.out.println(e.toString());
            close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void authentication(DatabaseConnection databaseConnection) throws IOException, SQLException {
        String line = "";
        String username = "";
        String password = "";

        while(true) {
            line = in.readLine();

            if(line == null){
                close();
                break;
            }

            System.out.println(line);

            if(line.startsWith("USERNAME ")){
                username = line.substring(9);
                boolean isExist = databaseConnection.authenticateByUsername(username);
                System.out.println("The username exists: " +  isExist);

                if(isExist){
                    out.println("OK Password require");
                } else if(!isExist){
                    out.println("ERR Not found user");
                }
            }

            if(line.startsWith("PASSWORD ")){
                password = line.substring(9);
                System.out.println();
                boolean isExist = databaseConnection.authenticate(password, username);
                System.out.println("The user authenticated: " +  isExist);

                if(isExist){
                    out.println("OK Login successful");
                    isLogin = true;
                    break;
                } else if(!isExist){
                    out.println("ERR Password incorrect");
                }
            }
        }
    }

    public void fileTransfer() throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        FileInputStream imageInputStream = new FileInputStream("image.png");

        byte[] imageData = new byte[imageInputStream.available()];
        imageInputStream.read(imageData);
        out.println("File size: "+imageData.length);
        outputStream.write(imageData);
        System.out.println("Image sent to client");

        String line = in.readLine();
        if(line.equals("OK received")){
            System.out.println(line);
        }
    }



    public void close(){
        try{
            System.out.println("Thread closed");
            out.close();
            in.close();
            clientSocket.close();
            isStop = true;
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
