package seabattlecommunicatorclient;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import seabattlecommunicatorshared.CommunicatorWebSocketMessage;
import seabattlecommunicatorshared.CommunicatorWebSocketMessageOperation;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class CommunicatorClientWebSocket extends Communicator {
    private static CommunicatorClientWebSocket instance = null;
    private final String uri = "ws://localhost:8095/communicator/";
    private Session session;
    private String message;

    private Gson gson = null;

    // Status of the webSocket client
    boolean isRunning = false;

    // Private constructor (singleton pattern)
    private CommunicatorClientWebSocket() {
        gson = new Gson();
    }


    public static CommunicatorClientWebSocket getInstance() {
        if (instance == null) {
            System.out.println("[WebSocket Client create singleton instance]");
            instance = new CommunicatorClientWebSocket();
        }
        return instance;
    }

    @Override
    public void start() {
        System.out.println("[WebSocket Client start connection]");
        if (!isRunning) {
            startClient();
            isRunning = true;
        }
    }

    @Override
    public void stop() {
        System.out.println("[WebSocket Client stop]");
        if (isRunning) {
            stopClient();
            isRunning = false;
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session session){
        System.out.println("[WebSocket Client open session] " + session.getRequestURI());
        this.session = session;
    }

    @OnMessage
    public void onWebSocketText(String message, Session session){
        this.message = message;
        System.out.println("[WebSocket Client message received] " + message);
        processMessage(message);
    }

    @OnError
    public void onWebSocketError(Session session, Throwable cause) {
        System.out.println("[WebSocket Client connection error] " + cause.toString());
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason){
        System.out.print("[WebSocket Client close session] " + session.getRequestURI());
        System.out.println(" for reason " + reason);
        session = null;
    }

    private void startClient() {
        System.out.println("[WebSocket Client start]");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));

        } catch (IOException | URISyntaxException | DeploymentException ex) {
            // do something useful eventually
            ex.printStackTrace();
        }
    }

    /**
     * Stop the client when it is running.
     */
    private void stopClient(){
        System.out.println("[WebSocket Client stop]");
        try {
            session.close();

        } catch (IOException ex){
            // do something useful eventually
            ex.printStackTrace();
        }
    }
    public void update(CommunicatorMessage message) {
        CommunicatorWebSocketMessage wsMessage = new CommunicatorWebSocketMessage(message.getPlayerNr(),message.getPosX(),message.getPosY());
        wsMessage.setOperation(CommunicatorWebSocketMessageOperation.PLACESHIP);
        sendMessageToServer(wsMessage);
    }

    private void sendMessageToServer(CommunicatorWebSocketMessage message) {
        String jsonMessage = gson.toJson(message);
        // Use asynchronous communication
        session.getAsyncRemote().sendText(jsonMessage);
    }

    // Process incoming json message
    private void processMessage(String jsonMessage) {

        // Parse incoming message
        CommunicatorWebSocketMessage wsMessage;
        try {
            wsMessage = gson.fromJson(jsonMessage, CommunicatorWebSocketMessage.class);
        }
        catch (JsonSyntaxException ex) {
            System.out.println("[WebSocket Client ERROR: cannot parse Json message " + jsonMessage);
            return;
        }

        switch(wsMessage.getOperation()){
            case PLACESHIP:
                CommunicatorMessage communicatorMessage = new CommunicatorMessage();
                communicatorMessage.setPlayerNr(wsMessage.getPlayerNr());
                communicatorMessage.setPosX(wsMessage.getPosX());
                communicatorMessage.setPosY(wsMessage.getPosY());
                setChanged();
                notifyObservers(communicatorMessage);
        }

    }

}
