package ru.protei.client.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.protei.client.menu.SimpleMenu;


public class RequestBuilder {
    private static final Logger log = LogManager.getLogger(RequestBuilder.class);


    public ClientRequest build() {
        ClientRequest clientRequest = new ClientRequest();
        String[] strings = new SimpleMenu().launchMenu();
        log.info("Request creation");
        clientRequest.setRequestType(strings[0]);
        clientRequest.setRequestContent(strings[1]);
        return clientRequest;
    }
}
