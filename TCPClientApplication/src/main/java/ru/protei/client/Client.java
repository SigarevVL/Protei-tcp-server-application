package ru.protei.client;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.protei.client.request.ClientRequest;
import ru.protei.client.request.RequestBuilder;
import ru.protei.client.response.ResponseHandler;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static final Logger log = LogManager.getLogger(Client.class);
    private static final int SERVER_PORT = 3535;
    private static final String HOST_NAME = "localhost";
    private static final Gson GSON = new Gson();

    public static void main(String[] args) {

        RequestBuilder requestBuilder = new RequestBuilder();

        try (Socket socket = new Socket(HOST_NAME, SERVER_PORT)) {
            InputStream socketInputStream = socket.getInputStream();
            OutputStream socketOutputStream = socket.getOutputStream();

            DataInputStream dataInputStream = new DataInputStream(socketInputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);

            while (true) {
                ClientRequest clientRequest = requestBuilder.build();
                if (clientRequest.getRequestType().equals("exit")) {
                    log.info("Close the application");
                    break;
                }

                log.info(clientRequest.getRequestType() + " request was created");
                dataOutputStream.writeUTF(GSON.toJson(clientRequest));
                dataOutputStream.flush();
                log.info("Request was sent");
                log.info("Waiting for response from server... ");
                String response = dataInputStream.readUTF();
                log.info("Response received");
                new ResponseHandler().responseProcessing(response);
            }
        } catch (UnknownHostException e) {
            log.error("Host does not exist, close the application");
            log.error(e);
        } catch (IOException e) {
            log.error("Error, close the application");
            log.error(e);
        }

    }
}
