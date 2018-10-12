package ru.protei.client.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.protei.server.RequestHandler;
import ru.protei.server.ServerResponse;
import ru.protei.server.objects.Word;

public class ResponseHandler {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger log = LogManager.getLogger(RequestHandler.class);

    public void responseProcessing(String response) {
        ServerResponse<Word> serverResponse = GSON.fromJson(response, ServerResponse.class);
        switch (serverResponse.getResultCode()) {
            case 1:
                log.info("Success operation");
                if (!serverResponse.getList().isEmpty()){
                    System.out.println("Result:");

                    for (Object o : serverResponse.getList()) {
                        System.out.println(o);
                    }
                }
                break;

            case -1:
                log.error("ERROR, check input date");
                break;

            case 0:
                log.info("Unsuccess operation");
                break;
        }

    }
}
