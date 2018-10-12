package ru.protei.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger log = LogManager.getLogger(Server.class);
    private static final int PORT = 3535;

    public static void main(String[] args) {

        RequestHandler requestHandler = new RequestHandler();
        
        //в соответствии с заданием, подключение нескольких клиентов намерено не реализовано
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            log.info("Starting server...");
            log.info("Waiting for client...");
            Socket socket = serverSocket.accept();

            InputStream socketInputStream = socket.getInputStream();
            OutputStream socketOutputStream = socket.getOutputStream();

            DataInputStream dataInputStream = new DataInputStream(socketInputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);

            String string = null;
            while (true) {
                log.info("Waiting for client request...");
                string = dataInputStream.readUTF();
                log.info("Request received");
                log.info("Processing request");
                string = requestHandler.requestProcessing(string);
                log.info("Server sends respond");
                dataOutputStream.writeUTF(string);
            }
        } catch (IOException e) {
            log.error("Exception on server");
            log.error(e);
        }
    }
}
