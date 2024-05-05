//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tuturuu.socket.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import at.favre.lib.crypto.bcrypt.BCrypt;


public class ClientThread extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isStop = false;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.out.println("You just connected with the server");
            String line = "";
            String username = "";
            String password = "";

            while(!isStop) {
                line = in.readLine();

                if(line == null){
                    System.out.println("Thread closed");
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
                        out.println("OK Password require");
                    } else if(!isExist){
                        out.println("ERR Not found user");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void close(){
        try{
            out.close();
            in.close();
            clientSocket.close();
            isStop = true;
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
