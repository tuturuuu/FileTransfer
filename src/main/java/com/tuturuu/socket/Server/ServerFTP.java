//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tuturuu.socket.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerFTP {
    public ServerFTP() {
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9090);

            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client " + clientSocket.getInetAddress().getHostAddress() + " just connected !");
                ClientThread clientThread = new ClientThread(clientSocket);
                clientThread.start();
            }
        } catch (UnknownHostException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }
}
