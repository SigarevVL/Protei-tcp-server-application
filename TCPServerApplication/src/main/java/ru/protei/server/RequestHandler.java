package ru.protei.server;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.protei.client.ClientRequest;
import ru.protei.server.dao.implementations.WordDaoImpl;
import ru.protei.server.objects.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestHandler {

    private static final  Logger log = LogManager.getLogger(RequestHandler.class);
    private static final  Gson GSON = new Gson();

    private WordDaoImpl wordDao;

    public RequestHandler() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("configuration.xml");
        this.wordDao = (WordDaoImpl) applicationContext.getBean("mp3DaoImpl");
    }

    public String requestProcessing(String request) {

        ClientRequest clientRequest = GSON.fromJson(request, ClientRequest.class);
        ServerResponse<Word> serverResponse = new ServerResponse<>();

        switch (clientRequest.getRequestType()) {
            case "select":
                log.info("Word searching...");
                serverResponse = selectRequestProcessing(clientRequest, wordDao, false);
                break;

            case "select_by_mask":
                log.info("Word searching_by_mask...");
                serverResponse = selectRequestProcessing(clientRequest, wordDao, true);
                break;

            case "insert":
                log.info("Word insertion...");
                serverResponse = insertRequestProcessing(clientRequest, wordDao);
                break;

            case "update":
                log.info("Word update...");
                serverResponse = updateRequestProcessing(clientRequest, wordDao);
                break;

            case "delete":
                log.info("Word delete...");
                serverResponse = deleteRequestProcessing(clientRequest, wordDao);
                break;

            default:
                log.info("Incorrect operation");
                serverResponse.setResultCode(-1);
                serverResponse.setList(Collections.emptyList());
        }
        return GSON.toJson(serverResponse);
    }

    private ServerResponse selectRequestProcessing(ClientRequest clientRequest, WordDaoImpl wordDao, boolean mask) {
        ServerResponse<Word> serverResponse = new ServerResponse<>();
        Word word = new Word();
        word.setWord(clientRequest.getRequestContent());
        List<Word> searchResult = new ArrayList<>();
        int resultCode = 1;

        if (mask) {
            searchResult = wordDao.selectByMask(word);
        } else {
            searchResult = wordDao.select(word);
        }

        if (searchResult.isEmpty()) {
            log.info("Unsuccessful search");
            resultCode = -1;
        } else {
            log.info("Successful search");
            resultCode = 1;
        }

        serverResponse.setResultCode(resultCode);
        serverResponse.setList(searchResult);

        return serverResponse;
    }

    private ServerResponse insertRequestProcessing(ClientRequest clientRequest, WordDaoImpl wordDao) {
        ServerResponse<Word> serverResponse = new ServerResponse<>();
        Word word = new Word();
        String[] strings = clientRequest.getRequestContent().split("=");
        int resultCode = -1;

        if (strings.length == 2) {
            word.setWord(strings[0]);
            word.setWordExplanation(strings[1]);
            resultCode = wordDao.insert(word);
        }
        if (resultCode == 1) {
            log.info("Successful insert");
        } else {
            log.info("Unsuccessful insert");
        }

        serverResponse.setResultCode(resultCode);
        serverResponse.setList(Collections.emptyList());

        return serverResponse;
    }

    private ServerResponse updateRequestProcessing(ClientRequest clientRequest, WordDaoImpl wordDao) {
        ServerResponse<Word> serverResponse = new ServerResponse<>();
        Word word = new Word();
        String[] strings = clientRequest.getRequestContent().split("=");
        int resultCode = -1;

        if (strings.length == 2) {
            word.setWord(strings[0]);
            word.setWordExplanation(strings[1]);
            resultCode = wordDao.update(word);
        }

        if (resultCode == 1) {
            log.info("Successful update");
        } else {
            log.info("Unsuccessful update");
        }
        serverResponse.setResultCode(resultCode);
        serverResponse.setList(Collections.emptyList());

        return serverResponse;
    }

    private ServerResponse deleteRequestProcessing(ClientRequest clientRequest, WordDaoImpl wordDao) {
        ServerResponse<Word> serverResponse = new ServerResponse<>();
        Word word = new Word();
        word.setWord(clientRequest.getRequestContent());
        int resultCode = wordDao.delete(word);

        if (resultCode == 1) {
            log.info("Successful delete");
        } else if (resultCode == 0) {
            log.info("Word did not find in the table");
        } else {
            log.info("Unsuccessful delete");
        }
        serverResponse.setResultCode(resultCode);
        serverResponse.setList(Collections.emptyList());

        return serverResponse;
    }
}
